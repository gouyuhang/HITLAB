package semantic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import grammar.Tree;
import grammar.TreeNode;

public class Smantic
{
	private static ArrayList<Tree> tree =  new ArrayList<Tree>();  // 语法树
	static List<Attributes> S_Attributes;  // 语法树节点属性
	
	static List<Stack<Symbol>> table = new ArrayList<Stack<Symbol>>();  // 符号表  
	static List<Integer> tablesize = new ArrayList<Integer>();  // 符号表大小
	
	static List<String> three_addr = new ArrayList<String>();  // 三地址
	static List<FourAddr> four_addr = new ArrayList<FourAddr>();  // 四元式
	static List<String> errors = new ArrayList<String>();  // 错误
	
    static String t;  // 类型
    static int w;  // 大小
    static int offset;  // 偏移量
	static int temp_cnt = 0; 
    static int nextquad = 1; // 指令位置
    
	static List<String> queue = new ArrayList<String>();  // 过程调用参数队列
    static Stack<Integer> tblptr = new Stack<Integer>();  // 符号表指针栈
    static Stack<Integer> off = new Stack<Integer>();  //  符号表偏移大小栈
    
	static int nodeSize;
	static int treeSize;
	static int initial = nextquad;
	
	
	
	
	public static void print_ins(List<String> three_addr, List<FourAddr> four_addr)
	{	
		try {
			BufferedWriter b=new BufferedWriter(new FileWriter("io/addr.txt"));
			StringBuffer s = new StringBuffer();
			for (int i=0; i<three_addr.size(); i++)
			{
				s.append((Smantic.initial + i) + ": ");
				s.append(four_addr.get(i).toString());	
				s.append(" ");	
				s.append(three_addr.get(i));	
				s.append("\n");
			}
			b.write(s.toString());
			b.close();
		}catch(IOException e) {
			e.printStackTrace();
		}		
	}
	public static void print_table(List<Stack<Symbol>> table)
	{	
		try {
			BufferedWriter b=new BufferedWriter(new FileWriter("io/symbol_table.txt"));
			StringBuffer s = new StringBuffer();
			for (int i=0; i<table.size(); i++)
			{
				s.append((i)+" ");	
				s.append(table.get(i).toString());	
				s.append("\n");
			}
			b.write(s.toString());
			b.close();
		}catch(IOException e) {
			e.printStackTrace();
		}			
	}
	
	public static void print_errors(List<String> e)
	{	
		try {
			BufferedWriter b=new BufferedWriter(new FileWriter("io/error.txt"));
			StringBuffer s = new StringBuffer();
			for (int i=0; i<e.size(); i++)
			{
				s.append(e.get(i));	
				s.append("\n");
			}
			b.write(s.toString());
			b.close();
		}catch(IOException e1) {
			e1.printStackTrace();
		}			
	}
	
	
	
	public static ArrayList<Tree> initTree() {
		ArrayList<Tree> Atree =  new ArrayList<Tree>();
		try {
			BufferedReader b=new BufferedReader(new FileReader("referance/Tree.txt"));
			String s=null;
			while((s = b.readLine()) != null) {
				Tree atom;
				TreeNode fathernode = null;
				TreeNode sonnode;
				ArrayList<TreeNode> sonnodelist =new ArrayList<TreeNode>();
				int i=0;
				String father="";
				String son="";
				while(i<s.length()) {
					if(s.charAt(i)=='-'&&s.charAt(i+1)=='>') {
						String[] fs=father.split(",");						
						i+=1;
						fathernode=new TreeNode(Integer.valueOf(fs[0]),fs[1],fs[2],Integer.valueOf(fs[3]));
						break;
					}
					if(s.charAt(i)!='{'&&s.charAt(i)!='}'&&s.charAt(i)!=' ') {
						father+=s.charAt(i);
					}
						
					i++;
				}
				if(i+1==s.length()-1) {
					//System.out.println(fathernode.toString());
					atom=new Tree(fathernode,sonnodelist);
					Atree.add(atom);
					continue;
				}
				i+=2;
				while(i<s.length()) {
					//System.out.println(s.charAt(i));
					if(s.charAt(i)=='{') {
						son="";
						i+=1;
						//continue;
					}
					if(s.charAt(i)=='}') {
						String[] tmp=son.split(",");
						if(tmp[1].equals("dou")) {
							tmp[1]=",";
							tmp[2]=",";
						}
						sonnode=new TreeNode(Integer.valueOf(tmp[0]),tmp[1],tmp[2],Integer.valueOf(tmp[3]));
						sonnodelist.add(sonnode);
						i+=1;
						son="";
					}
					if(i==s.length()) {
						atom=new Tree(fathernode,sonnodelist);
						Atree.add(atom);
						break;
					}
					if(s.charAt(i)!='{'&&s.charAt(i)!='}')
					son+=s.charAt(i);
					i++;
					
				}
				
			}
		}catch(IOException e) {
			e.printStackTrace();
		}
		return Atree;	
	}
    public Smantic(String filename, List<Stack<Symbol>> table, 
    	List<String> three_addr, List<FourAddr> four_addr, List<String> errors)
    {	
    	tree=initTree();
		treeSize = tree.size();
		nodeSize = tree.get(treeSize-1).getFather().getId() + 1;
		S_Attributes = Arrays.asList(new Attributes[nodeSize]); 

		Smantic.three_addr = three_addr;
		Smantic.four_addr = four_addr;
		Smantic.table = table;
		Smantic.errors = errors;
		
		if (treeSize==0)
			return;
		search(tree.get(treeSize-1));

		print_ins(three_addr, four_addr);
		print_table(table);
		print_errors(errors);
    }
    
    
	public static void main(String[] args)
	{	
		List<Stack<Symbol>> table = new ArrayList<Stack<Symbol>>();  // 符号表  
		List<String> three_addr = new ArrayList<String>();  // 三地址指令序列
		List<FourAddr> four_addr = new ArrayList<FourAddr>();  // 三地址指令序列
		List<String> errors = new ArrayList<String>();  // 错误序列
		Smantic se = new Smantic("io/test.txt",table,three_addr,four_addr,errors);
		
	}
	public static void search(Tree tree)
	{				
		for(int i=0; i<tree.getChildren().size(); i++)
		{
			TreeNode tn = tree.getChildren().get(i);
			if (!endPoint(tn))  // 非终结符
			{
				Tree f = findTreeNode(tn.getId());  
				search(f);  // 递归遍历子节点
				findSemantic(f);  // 查找动作函数
			}			
		}
	}
	
	/*
	 *  enter函数 向符号表中添加符号
	 */
    private static void enter(int i, String name, String type, int offset)
    {        
    	if(table.size()==0)
    	{
    		table.add(new Stack<Symbol>());
    	}
    	Symbol s = new Symbol(name,type,offset);
        table.get(i).push(s);  
    }  
    
    /*
     * lookup函数 查找符号表是否包含此函数
     */
    private static int[] lookup(String s) 
    {     
    	int[] a = new int[2]; 
    	for (int i=0; i<table.size(); i++)
    	{
        	for (int j=0; j<table.get(i).size(); j++)
        	{
	    		if(table.get(i).get(j).getName().equals(s))
	    		{
	    			a[0] = i;
	    			a[1] = j;
	    			return a;
	    		}
        	}
    	}
		a[0] = -1;
		a[1] = -1;
		return a;
    }  
  
    /*
     * newtemp函数 新建变量名
     */
    private static String newtemp()
    {                
        return "t" + (++temp_cnt);  
    }  
    
    /*
     * backpatch函数 返回回填地址
     */
    private static void backpatch(List<Integer> list, int quad)
    {
    	for(int i=0; i<list.size(); i++)
    	{
    		int x = list.get(i) - initial;
    		three_addr.set(x, three_addr.get(x)+quad);
    		four_addr.get(x).setToaddr(String.valueOf(quad));
    	}
    }

   /*
    * merge函数 合并
    */
    private static List<Integer> merge(List<Integer> a,List<Integer> b)
    {
    	List<Integer> a1 = a;
    	a1.addAll(b);
    	return a1;
    }
    
    /*
     * nextquad函数 返回下一条指令地址
     */
    private static int nextquad() 
    {          
    	 return three_addr.size() + nextquad;  
    }  
	
    /*
     * makelist函数
     */
    private static List<Integer> makelist(int i) 
    {          
    	 List<Integer> a1 = new ArrayList<Integer>();
    	 a1.add(i);
    	 return a1;  
    }  
    
    /*
     * 新建符号表
     */
    public static void mktable()
    {
    	table.add(new Stack<Symbol>());
    }

	public static Tree findTreeNode(int id)
	{
		for(Tree t:tree)
		{
			if (t.getFather().getId() == id)
			{
				return t;
			}
		}
		return null;
	}
	
	
    // P -> proc id ; M0 begin D S end  {addwidth(top(tblptr),top(offset));pop(tblptr);pop(offset)}
    public static void semantic_1(Tree tree)
    {
    	tblptr.pop();
    	off.pop();    	
    }
	
    // S -> S1 M S2  {backpatch(S1.nextlist,M.quad); S.nextlist=S2.nextlist;}
    public static void semantic_3(Tree tree)
    {
    	int S = tree.getFather().getId();  // S
    	int S1 = tree.getChildren().get(0).getId();  // S1
    	int M = tree.getChildren().get(1).getId();  // M
    	int S2 = tree.getChildren().get(2).getId();  // S2

    	backpatch(S_Attributes.get(S1).getNext(), S_Attributes.get(M).getQuad());
    	
    	Attributes a1 = new Attributes();
    	a1.setNext(S_Attributes.get(S2).getNext());
    	S_Attributes.set(S,a1);
    }
	
    // D -> T id ; {enter(top(tblptr),id.name,T.type,top(offset)); 
    //              top(offset) = top(offset)+T.width}
    public static void semantic_5(Tree tree)
    {
    	int T = tree.getChildren().get(0).getId();  // T
    	String id = tree.getChildren().get(1).getValue();  // id
    	
    	int[] i = lookup(id);
    	if (i[0] == -1)
    	{        	
        	enter(tblptr.peek(), id, S_Attributes.get(T).getType(), off.peek());
        	int s = off.pop();
        	off.push(s + S_Attributes.get(T).getWidth());
        	offset = offset + S_Attributes.get(T).getWidth();
    	}
    	else
    	{
    		String s = "Error at Line [" +  tree.getChildren().get(1).getLine() +
    				"]:\t[" + "变量" + id + "重复声明]" ;   		
    		errors.add(s);   		
    	}
    }

    // T -> X C {T.type=C.type; T.width=C.width;}
    public static void semantic_6(Tree tree)
    {
    	int T = tree.getFather().getId();  // T
    	ArrayList<TreeNode> c = tree.getChildren();
    	int X = c.get(0).getId();  // X
    	int C = c.get(1).getId();  // C
    	Attributes a1 = new Attributes();
    	a1.setType(S_Attributes.get(C).getType());
    	a1.setWidth(S_Attributes.get(C).getWidth());
    	S_Attributes.set(T,a1);
    }
    
    
    // T -> record N2 D end   {T.type=record(top(tblptr)); 
    //                         T.width=top(offset); pop(tblptr); pop(offset)}
    public static void semantic_7(Tree tree)
    {
    	int T = tree.getFather().getId();  // T
    	
    	Attributes a1 = new Attributes();
    	a1.setType("record");
    	a1.setWidth(off.pop());
    	tblptr.pop();
    	
    	S_Attributes.set(T,a1);
    }
    
    
    // X -> integer  {X.type=integer; X.width=4;}{t=X.type; w=X.width;}
    public static void semantic_8(Tree tree)
    {
    	int X = tree.getFather().getId();  // X
    	t = "integer";
    	w = 4;
    	
    	Attributes a1 = new Attributes();
    	a1.setType("integer");
    	a1.setWidth(4);
    	S_Attributes.set(X,a1);
    }
    
    // X -> real  {X.type=real; X.width=8;}{t=X.type; w=X.width;}
    public static void semantic_9(Tree tree)
    {
    	int X = tree.getFather().getId();  // X
    	t = "real";
    	w = 8;
    	   	
    	Attributes a1 = new Attributes();
    	a1.setType("real");
    	a1.setWidth(8);
    	S_Attributes.set(X,a1);
    }
    
    // C -> [ num ] C1  {C.type=array(num.val,C1.type); C.width=num.val*C1.width;}
    public static void semantic_10(Tree tree)
    {
    	int C = tree.getFather().getId();  // C
    	int num = Integer.parseInt(tree.getChildren().get(1).getValue());  // num
    	int C1 = tree.getChildren().get(3).getId();  // C1
    	Attributes a1 = new Attributes();
    	Array a2 = new Array();
    	
    	a2.setLength(num);
    	String type = S_Attributes.get(C1).getType();
    	if (type.startsWith("array"))
    	{
    		a2.setType(S_Attributes.get(C1).getArray());
    		a2.setBaseType("array");
    	}
    	else
    	{	
    		a2.setBaseType(type);
    	}	
		a1.setArray(a2);
    	a1.setType(arrayfunc.arrayString(a2));
    	a1.setWidth(num*S_Attributes.get(C1).getWidth());
    	S_Attributes.set(C,a1);
    }
    
    // C -> ε  {C.type=t; C.width=w;}
    public static void semantic_11(Tree tree)
    {
    	int C = tree.getFather().getId();  // C
    	Attributes a1 = new Attributes();    	
    	a1.setType(t);
    	a1.setWidth(w);
    	S_Attributes.set(C,a1);
    }
    
    //  S -> id = E ;  {p=lookup(id.lexeme); if p==nil then error; 
    //                  gencode(p'='E.addr); S.nextlist=null} 
    public static void semantic_12(Tree tree)
    {
    	int S = tree.getFather().getId();  // S
    	String id = tree.getChildren().get(0).getValue();  // id
    	int E = tree.getChildren().get(2).getId();  // E
    	
    	int[] i = lookup(id);
    	if (i[0]==-1)
    	{
    		String s = "Error at Line [" +  tree.getChildren().get(0).getLine() + "]:\t[" +
    				"变量" + id + "引用前未声明]" ;   		
    		errors.add(s);   
        	enter(tblptr.peek(), id, "integer", offset);
        	offset = offset + 4;
    	}
    	
    	String code = id + " = " + S_Attributes.get(E).getAddr();
    	three_addr.add(code);
    	four_addr.add(new FourAddr("=",S_Attributes.get(E).getAddr(),"-",id)); 	
    	Attributes a1 = new Attributes();    	
    	a1.setNext(new ArrayList<Integer>());
    	S_Attributes.set(S,a1);
    }
    
    //  S -> L = E ;  {gencode(L.array'['L.offset']''='E.addr); S.nextlist=null} 
    public static void semantic_13(Tree tree)
    {
    	int S = tree.getFather().getId();  // S
    	int L = tree.getChildren().get(0).getId();  // L
    	int E = tree.getChildren().get(2).getId();  // E
    	
    	String code = S_Attributes.get(L).getName() + "[" +S_Attributes.get(L).getOffset() 
    			+ "] = " + S_Attributes.get(E).getAddr();
    	three_addr.add(code);
    	four_addr.add(new FourAddr("=",S_Attributes.get(E).getAddr(),"-",
    			S_Attributes.get(L).getName() + "[" +S_Attributes.get(L).getOffset() + "]"));   	
    	Attributes a1 = new Attributes();    	
    	a1.setNext(new ArrayList<Integer>());
    	S_Attributes.set(S,a1);
    }
      
    
    //  E -> E1 + E2  {E.addr=newtemp(); gencode(E.addr'='E1.addr'+'E2.addr);}
    public static void semantic_14(Tree tree)
    {
    	int E = tree.getFather().getId();  // E 	
    	int E1 = tree.getChildren().get(0).getId();  // E1 	
    	int E2 = tree.getChildren().get(2).getId();  // E2 	
    	String newtemp1 = newtemp();
    	
    	
    	if ((S_Attributes.get(E1).getType().equals("integer") && S_Attributes.get(E2).getType().equals("integer")) ||
    		(S_Attributes.get(E1).getType().equals("real") && S_Attributes.get(E2).getType().equals("real")))
    	{
    		Attributes a1 = new Attributes();
	    	a1.setAddr(newtemp1);
	    	a1.setType(S_Attributes.get(E1).getType());
	    	S_Attributes.set(E,a1);
	    	
	    	String code = newtemp1 + " = " + S_Attributes.get(E1).getAddr() + 
	    			"+" + S_Attributes.get(E2).getAddr();
	    	three_addr.add(code);
	    	four_addr.add(new FourAddr("+",S_Attributes.get(E1).getAddr(),
	    			S_Attributes.get(E2).getAddr(),newtemp1)); 	
    	}
    	if ((S_Attributes.get(E1).getType().equals("real") && S_Attributes.get(E2).getType().equals("integer")))
        {
    		String newtemp2 = newtemp();
    		Attributes a1 = new Attributes();
	    	a1.setAddr(newtemp2);
	    	a1.setType("real");
	    	S_Attributes.set(E,a1);
	    	
	    	String code1 = newtemp1 + " = intTOreal " + S_Attributes.get(E2).getAddr();
	    	String code2 = newtemp2 + " = " + S_Attributes.get(E1).getAddr() + "+" + newtemp1;
	    	three_addr.add(code1);
	    	three_addr.add(code2);
	    	four_addr.add(new FourAddr("=","intTOreal" + S_Attributes.get(E2).getAddr(),"-",newtemp1));
	    	four_addr.add(new FourAddr("+",S_Attributes.get(E1).getAddr(),newtemp1,newtemp2));	
        }
    	if ((S_Attributes.get(E1).getType().equals("integer") && S_Attributes.get(E2).getType().equals("real")))
    	{
    		String newtemp2 = newtemp();
    		Attributes a1 = new Attributes();
	    	a1.setAddr(newtemp2);
	    	a1.setType("real");
	    	S_Attributes.set(E,a1);
	    	
	    	String code1 = newtemp1 + " = intTOreal " + S_Attributes.get(E1).getAddr();
	    	String code2 = newtemp2 + " = " + newtemp1 + "+" + S_Attributes.get(E2).getAddr();
	    	three_addr.add(code1);
	    	three_addr.add(code2);
	    	four_addr.add(new FourAddr("=","intTOreal" + S_Attributes.get(E1).getAddr(),"-",newtemp1));
	    	four_addr.add(new FourAddr("+",newtemp1,S_Attributes.get(E2).getAddr(),newtemp2));
    	}
    	if (S_Attributes.get(E1).getType().contains("array"))
    	{
    		String newtemp2 = newtemp();
    		Attributes a1 = new Attributes();
	    	a1.setAddr(newtemp2);
	    	a1.setType("integer");
	    	S_Attributes.set(E,a1);
	    	
	    	int x =arrayfunc.typeWidth(S_Attributes.get(E1).getType());
	    	String code1 = newtemp1 + " = " + x;
	    	String code2 = newtemp2 + " = " + newtemp1 + "+" + S_Attributes.get(E2).getAddr();
	    	three_addr.add(code1);
	    	three_addr.add(code2);
	    	four_addr.add(new FourAddr("=",String.valueOf(x),"-",newtemp1));
	    	four_addr.add(new FourAddr("+",newtemp1,S_Attributes.get(E2).getAddr(),newtemp2));
	    	
    		String s = "Error at Line [" +  tree.getChildren().get(0).getLine() + "]:\t[" +
    				"整型变量与数组变量相加减]" ;   		
    		errors.add(s);   	
    	}
    	if (S_Attributes.get(E2).getType().contains("array"))
    	{
    		String newtemp2 = newtemp();
    		Attributes a1 = new Attributes();
	    	a1.setAddr(newtemp2);
	    	a1.setType("integer");
	    	S_Attributes.set(E,a1);
	    	
	    	int x = arrayfunc.typeWidth(S_Attributes.get(E2).getType());
	    	String code1 = newtemp1 + " = " + x;
	    	String code2 = newtemp2 + " = " + S_Attributes.get(E1).getAddr() + "+" + newtemp1;
	    	three_addr.add(code1);
	    	three_addr.add(code2);
	    	four_addr.add(new FourAddr("=",String.valueOf(x),"-",newtemp1));
	    	four_addr.add(new FourAddr("+",S_Attributes.get(E1).getAddr(),newtemp1,newtemp2));
	    	
    		String s = "Error at Line [" +  tree.getChildren().get(1).getLine() + "]:\t[" +
    				"整型变量与数组变量相加减]" ;   		
    		errors.add(s);  
    	}
    }


    //  E -> E1  {E.addr=E1.addr} 
    public static void semantic_15_17(Tree tree)
    {
    	int E = tree.getFather().getId();  // E 	
    	int E1 = tree.getChildren().get(0).getId();  // E1 	
    	
    	Attributes a1 = new Attributes();
    	a1.setAddr(S_Attributes.get(E1).getAddr());
    	a1.setType(S_Attributes.get(E1).getType());
    	S_Attributes.set(E,a1);	
    }
    
    
    //  E -> E1 * E2  {E.addr=newtemp(); gencode(E.addr'='E1.addr'*'E2.addr);}
    public static void semantic_16(Tree tree)
    {
    	int E = tree.getFather().getId();  // E 	
    	int E1 = tree.getChildren().get(0).getId();  // E1 	
    	int E2 = tree.getChildren().get(2).getId();  // E2 	
    	String newtemp = newtemp();
    	
    	Attributes a1 = new Attributes();
    	a1.setAddr(newtemp);
    	S_Attributes.set(E,a1);
    	
    	String code = newtemp + " = " + S_Attributes.get(E1).getAddr() + 
    			"*" + S_Attributes.get(E2).getAddr();
    	three_addr.add(code);
    	four_addr.add(new FourAddr("*",S_Attributes.get(E1).getAddr(),
    			S_Attributes.get(E2).getAddr(),newtemp));		
    }
    
    
    //  E -> ( E1 )  {E.addr=E1.addr}
    public static void semantic_18(Tree tree)
    {
    	int E = tree.getFather().getId();  // E 	
    	int E1 = tree.getChildren().get(1).getId();  // E1 	
    	
    	Attributes a1 = new Attributes();
    	a1.setAddr(S_Attributes.get(E1).getAddr());
    	a1.setType(S_Attributes.get(E1).getType());
    	S_Attributes.set(E,a1);	
    }
    
    
    //  E -> - E1  {E.addr=newtemp(); gencode(E.addr'=''uminus'E1.addr);}
    public static void semantic_19(Tree tree)
    {
    	int E = tree.getFather().getId();  // E 	
    	int E1 = tree.getChildren().get(1).getId();  // E1 	
    	String newtemp = newtemp();
    	
    	Attributes a1 = new Attributes();
    	a1.setAddr(newtemp);
    	a1.setType(S_Attributes.get(E1).getType());
    	S_Attributes.set(E,a1);
    	
    	String code = newtemp + " = -" + S_Attributes.get(E1).getAddr();
    	three_addr.add(code);
    	four_addr.add(new FourAddr("=","-" + S_Attributes.get(E1).getAddr(),
    			"-",newtemp));
    	//System.out.println(code); 	
    }
    
    
    //  E -> id  {E.addr=lookup(id.lexeme); if E.addr==null then error;}
    public static void semantic_20(Tree tree)
    {
       	int E = tree.getFather().getId();  // E 	
    	String id = tree.getChildren().get(0).getValue();  // id
    	
    	int[] i = lookup(id);
    	if (i[0]==-1)
    	{
    		String s = "Error at Line [" +  tree.getChildren().get(0).getLine() + "]:\t[" +
    				"变量" + id + "引用前未声明]" ;   		
    		errors.add(s);   
        	enter(tblptr.peek(), id, "integer", offset);
        	offset = offset + 4;
        	//System.out.println("ddd");
        	
        	Attributes a1 = new Attributes();
        	a1.setAddr(id);
        	a1.setType("integer");
        	S_Attributes.set(E,a1);
        	return;
    	}	
    	
    	Attributes a1 = new Attributes();
    	a1.setAddr(id);
    	a1.setType(table.get(i[0]).get(i[1]).getType());
    	S_Attributes.set(E,a1);
    }
    
    
    //  E -> num  {E.addr=lookup(num.lexeme); if E.addr==null then error}
    public static void semantic_21(Tree tree)
    {
       	int E = tree.getFather().getId();  // E 	
    	String num = tree.getChildren().get(0).getValue();  // num
    	Attributes a1 = new Attributes();
    	a1.setAddr(num);
    	a1.setType("integer");
    	S_Attributes.set(E,a1);
    }
    
    //  E -> L {E.addr=newtemp(); gencode(E.addr'='L.array'['L.offset']');}
    public static void semantic_22(Tree tree)
    {
       	int E = tree.getFather().getId();  // E 	
       	int L = tree.getChildren().get(0).getId();  // L 
    	String newtemp = newtemp();
    	
    	Attributes a1 = new Attributes();
    	a1.setAddr(newtemp);
    	a1.setType("integer");
    	S_Attributes.set(E,a1);

    	String code = newtemp + " = " + S_Attributes.get(L).getName() +
    			"[" +S_Attributes.get(L).getOffset() + "] ";
    	three_addr.add(code);
    	four_addr.add(new FourAddr("=",
    			S_Attributes.get(L).getName() +"[" +S_Attributes.get(L).getOffset() + "]","-",newtemp));	
    }
    
    //  L -> id [ E ] {L.array=lookup(id.lexeme); if L.array==nil then error; 
    //  L.type=L.array.type.elem; L.offset=newtemp(); gencode(L.offset'='E.addr'*'L.type.width);}
    public static void semantic_23(Tree tree)
    {
       	int L = tree.getFather().getId();  // L 	
       	String id = tree.getChildren().get(0).getValue();  // id
       	int E = tree.getChildren().get(2).getId();  // E
    	String newtemp = newtemp();
       	
    	int[] i = lookup(id);
    	if (i[0]==-1)
    	{
    		String error = id + "符号未定义";
    		//System.out.println(error); 		
    		
    		String s = "Error at Line [" +  tree.getChildren().get(0).getLine() + "]:\t[" +
    				"数组变量" + id + "引用前未声明]" ;   		
    		errors.add(s);  
    		//return;
    		
    		Attributes a1 = new Attributes();
        	a1.setName(id);
        	a1.setType("array(1,integer)");
        	a1.setOffset(newtemp);
        	S_Attributes.set(L,a1);
        	
        	String code = newtemp + " = " + 4;
            four_addr.add(new FourAddr("=",String.valueOf(4),"-",newtemp));
        	
        	three_addr.add(code);
        	return;
    	}	

    	if(!table.get(i[0]).get(i[1]).getType().contains("array"))
    	{   		
    		String s = "Error at Line [" +  tree.getChildren().get(0).getLine() + "]:\t[" +
    				"非数组变量" + id + "访问数组]" ;   		
    		errors.add(s);  
    	}
    	
    	Attributes a1 = new Attributes();
    	a1.setName(id);
    	a1.setType(arrayfunc.elemType(table.get(i[0]).get(i[1]).getType()));
    	a1.setOffset(newtemp);
    	S_Attributes.set(L,a1);
    	
    	String code = "";   	
    	String s = arrayfunc.elemType(table.get(i[0]).get(i[1]).getType());
    	if (s.contains("array"))
    	{
    		code = newtemp + " = " + S_Attributes.get(E).getAddr() + "*" + arrayfunc.typeWidth(s);
        	four_addr.add(new FourAddr("*",S_Attributes.get(E).getAddr(),
        			String.valueOf(arrayfunc.typeWidth(s)),newtemp));
    	}
    	else
    	{
    		code = newtemp + " = " + S_Attributes.get(E).getAddr();
        	four_addr.add(new FourAddr("=",S_Attributes.get(E).getAddr(),"-",newtemp));
    	}
    	three_addr.add(code);
    	
    }
    
    
    //  L -> L1 [ E ]  {L.array=L1.array; L.type=L1.type.elem; t=newtemp(); 
    //  gencode(t'='E.addr'*'L.type.width); L.offset=newtemp(); gencode(L.offset'='L1.offset'+'t);}
    public static void semantic_24(Tree tree)
    {
       	int L = tree.getFather().getId();  // L 	
       	int L1 = tree.getChildren().get(0).getId();  // L1
       	int E = tree.getChildren().get(2).getId();  // E
    	String newtemp1 = newtemp();
    	String newtemp2 = newtemp();
       	
    	Attributes a1 = new Attributes();
    	a1.setName(S_Attributes.get(L1).getName());
    	a1.setType(arrayfunc.elemType(S_Attributes.get(L1).getType()));
    	a1.setOffset(newtemp2);
    	S_Attributes.set(L,a1);
    	
    	String code1 = "";   
    	String s = arrayfunc.elemType(S_Attributes.get(L1).getType());
    	if (s.contains("array"))
    	{
        	code1 = newtemp1 + " = " + S_Attributes.get(E).getAddr() + "*" + arrayfunc.typeWidth(s);
        	four_addr.add(new FourAddr("*",S_Attributes.get(E).getAddr(),
        			String.valueOf(arrayfunc.typeWidth(s)),newtemp1));
    	}
    	else
    	{
    		code1 = newtemp1 + " = " + S_Attributes.get(E).getAddr() + "*" + w;
        	four_addr.add(new FourAddr("*",S_Attributes.get(E).getAddr(),
        			String.valueOf(w),newtemp1));
    	}
    	three_addr.add(code1);
    	
    	String code2 = newtemp2 + " = " + S_Attributes.get(L1).getOffset() +
    			"+" + newtemp1;
    	three_addr.add(code2);
    	four_addr.add(new FourAddr("+",S_Attributes.get(L1).getOffset(),newtemp1,newtemp2)); 	
    }
    
    
    //  B -> B1 or M B2  {backpatch(B1.falselist,M.quad); 
    //                    B.truelist=merge(B1.truelist,B2.truelist); 
    //                    B.falselist=B2.falselist}
    public static void semantic_25(Tree tree)
    {
       	int B = tree.getFather().getId();  // B 	
       	int B1 = tree.getChildren().get(0).getId();  // B1
       	int M = tree.getChildren().get(2).getId();  // M
       	int B2 = tree.getChildren().get(3).getId();  // B2
       	
       	backpatch(S_Attributes.get(B1).getFalse(),S_Attributes.get(M).getQuad());
       	
       	Attributes a1 = new Attributes();
    	a1.setTrue(merge(S_Attributes.get(B1).getTrue(),S_Attributes.get(B2).getTrue()));
    	a1.setFalse(S_Attributes.get(B2).getFalse());
    	S_Attributes.set(B,a1);
    }
    
    
    //  B -> B1  {B.truelist=B1.truelist; B.falselist=B1.falselist}
    public static void semantic_26_28(Tree tree)
    {	
       	int B = tree.getFather().getId();  // B 	
       	int B1 = tree.getChildren().get(0).getId();  // B1
       	
       	Attributes a1 = new Attributes();
    	a1.setTrue(S_Attributes.get(B1).getTrue());
    	a1.setFalse(S_Attributes.get(B1).getFalse());
    	S_Attributes.set(B,a1);
    }
    
    
    //  B -> B1 and M B2  {backpatch(B1.truelist M.quad); B.truelist=B2.truelist; 
    //                     B.falselist=merge(B1.falselist, B2.falselist)}
    public static void semantic_27(Tree tree)
    {
       	int B = tree.getFather().getId();  // B 	
       	int B1 = tree.getChildren().get(0).getId();  // B1
       	int M = tree.getChildren().get(2).getId();  // M
       	int B2 = tree.getChildren().get(3).getId();  // B2
       	
       	backpatch(S_Attributes.get(B1).getTrue(),S_Attributes.get(M).getQuad());
       	
       	Attributes a1 = new Attributes();
    	a1.setTrue(S_Attributes.get(B2).getTrue());
    	a1.setFalse(merge(S_Attributes.get(B1).getFalse(),S_Attributes.get(B2).getFalse()));
    	S_Attributes.set(B,a1);
    }
    
    //  B -> not B1  {B.truelist=B1.falselist; B.falselist=B1.truelist}
    public static void semantic_29(Tree tree)
    {
       	int B = tree.getFather().getId();  // B 	
       	int B1 = tree.getChildren().get(1).getId();  // B1
       	
       	Attributes a1 = new Attributes();
    	a1.setTrue(S_Attributes.get(B1).getFalse());
    	a1.setFalse(S_Attributes.get(B1).getTrue());
    	S_Attributes.set(B,a1);
    }
    
    
    //  B -> ( B1 )  {B.truelist := B1.truelist; B.falselist := B1.falselist}
    public static void semantic_30(Tree tree)
    {
       	int B = tree.getFather().getId();  // B 	
       	int B1 = tree.getChildren().get(1).getId();  // B1
       	
       	Attributes a1 = new Attributes();
    	a1.setTrue(S_Attributes.get(B1).getTrue());
    	a1.setFalse(S_Attributes.get(B1).getFalse());
    	S_Attributes.set(B,a1);
    }
    
    
    //  B -> E1 R E2  {B.truelist=makelist(nextquad); B.falselist= makelist(nextquad+1); 
    //                gencode('if' E1.addr relop.op E2.addr 'goto –'); gencode('goto –')}
    public static void semantic_31(Tree tree)
    {
       	int B = tree.getFather().getId();  // B	
       	int E1 = tree.getChildren().get(0).getId();  // E1	
       	int R = tree.getChildren().get(1).getId();  // R	
       	int E2 = tree.getChildren().get(2).getId();  // E2	
    	
       	Attributes a1 = new Attributes();
    	a1.setTrue(makelist(nextquad()));
    	a1.setFalse(makelist(nextquad()+1));
    	S_Attributes.set(B,a1);

    	String code1 = "if " + S_Attributes.get(E1).getAddr() + S_Attributes.get(R).getName()
    			+ S_Attributes.get(E2).getAddr() + " goto ";
    	three_addr.add(code1);
    	four_addr.add(new FourAddr("j" + S_Attributes.get(R).getName(),
    			S_Attributes.get(E1).getAddr(),S_Attributes.get(E2).getAddr(),"-"));
    	
    	String code2 = "goto ";
    	three_addr.add(code2);
    	four_addr.add(new FourAddr("j","-","-","-"));
    }
    
    //  B -> true  {B.truelist=makelist(nextquad); gencode('goto –')}
    public static void semantic_32(Tree tree)
    {
       	int B = tree.getFather().getId();  // B	
    	
       	Attributes a1 = new Attributes();
    	a1.setTrue(makelist(nextquad()));
    	S_Attributes.set(B,a1);

    	String code = "goto ";
    	three_addr.add(code);
    	four_addr.add(new FourAddr("j","-","-","-"));
    }
    
    //  B -> false  {B.falselist=makelist(nextquad); gencode('goto –')}
    public static void semantic_33(Tree tree)
    {
       	int B = tree.getFather().getId();  // B	
    	
       	Attributes a1 = new Attributes();
    	a1.setFalse(makelist(nextquad()));
    	S_Attributes.set(B,a1);
    	String code = "goto ";
    	three_addr.add(code);
    	four_addr.add(new FourAddr("j","-","-","-")); 
    }
    
    //  R -> < | <= | == | != | > | >=  {R.name=op}
    public static void semantic_34to39(Tree tree)
    {
       	int R = tree.getFather().getId();  // R	
       	String op = tree.getChildren().get(0).getValue();  // op	
       	
       	Attributes a1 = new Attributes();
    	a1.setName(op);
    	S_Attributes.set(R,a1);
    }
    
    //  S -> S1  {S.nextlist=S1.nextlist}
    public static void semantic_40_41_50(Tree tree)
    {
       	int S = tree.getFather().getId();  // S	
       	int S1 = tree.getChildren().get(0).getId();  // S1	
       	
       	Attributes a1 = new Attributes();
    	List<Integer> li = S_Attributes.get(S1).getNext();
    	a1.setNext(S_Attributes.get(S1).getNext());
    	S_Attributes.set(S,a1);
    }
    
    //  S -> if B then M1 S1 N else M2 S2
    //  {backpatch(B.truelist, M1.quad); backpatch(B.falselist,M2.quad); 
    //  S.nextlist=merge(S1.nextlist,merge(N.nextlist, S2.nextlist))}
    public static void semantic_42_44(Tree tree)
    {
       	int S = tree.getFather().getId();  // S	
       	int B = tree.getChildren().get(1).getId();  // B	
       	int M1 = tree.getChildren().get(3).getId();  // M1
       	int S1 = tree.getChildren().get(4).getId();  // S1
       	int N = tree.getChildren().get(5).getId();  // N
       	int M2 = tree.getChildren().get(7).getId();  // M2
       	int S2 = tree.getChildren().get(8).getId();  // S2
       	
       	backpatch(S_Attributes.get(B).getTrue(), S_Attributes.get(M1).getQuad());
       	backpatch(S_Attributes.get(B).getFalse(), S_Attributes.get(M2).getQuad());
       	Attributes a1 = new Attributes();
    	a1.setNext(merge(S_Attributes.get(S1).getNext(),
    			merge(S_Attributes.get(N).getNext(), S_Attributes.get(S2).getNext())));
    	S_Attributes.set(S,a1);
    }
    
    
    //  S -> while M1 B do M2 S1  {backpatch(S1.nextlist, M1.quad); 
    //       backpatch(B.truelist,M2.quad); S.nextlist=B.falselist; gencode('goto'M1.quad)}
    public static void semantic_43(Tree tree)
    {
       	int S = tree.getFather().getId();  // S	
       	int M1 = tree.getChildren().get(1).getId();  // M1
       	int B = tree.getChildren().get(2).getId();  // B	     	
       	int M2 = tree.getChildren().get(4).getId();  // M2
       	int S1 = tree.getChildren().get(5).getId();  // S1

       	backpatch(S_Attributes.get(S1).getNext(), S_Attributes.get(M1).getQuad());
       	backpatch(S_Attributes.get(B).getTrue(), S_Attributes.get(M2).getQuad());
       	Attributes a1 = new Attributes();
    	a1.setNext(S_Attributes.get(B).getFalse());
    	S_Attributes.set(S,a1);
    	
    	String code = "goto " + S_Attributes.get(M1).getQuad();
    	three_addr.add(code);
    	four_addr.add(new FourAddr("j","-","-",String.valueOf(S_Attributes.get(M1).getQuad())));
    	//System.out.println(code); 
    }
    
    //  S -> if B then M S1  {backpatch(B.truelist,M.quad); 
    //                        S.nextlist=merge(B.falselist,S1.nextlist)}
    public static void semantic_45(Tree tree)
    {
       	int S = tree.getFather().getId();  // S	
       	int B = tree.getChildren().get(1).getId();  // B	
       	int M = tree.getChildren().get(3).getId();  // M
       	int S1 = tree.getChildren().get(4).getId();  // S1

       	backpatch(S_Attributes.get(B).getTrue(), S_Attributes.get(M).getQuad());

       	Attributes a1 = new Attributes();
    	a1.setNext(merge(S_Attributes.get(B).getFalse(), S_Attributes.get(S1).getNext()));
    	S_Attributes.set(S,a1);
    }
    
    //  S -> begin S1 end  {S.nextlist=S1.nextlist}
    public static void semantic_46_47_48(Tree tree)
    {
       	int S = tree.getFather().getId();  // S	
       	int S1 = tree.getChildren().get(1).getId();  // S1	
       	
       	Attributes a1 = new Attributes();
    	a1.setNext(S_Attributes.get(S1).getNext());
    	S_Attributes.set(S,a1);
    }
    
    
    //  S -> S1 ; M S2  {backpatch(S1.nextlist, M.quad); S.nextlist=S2.nextlist}
    public static void semantic_49(Tree tree)
    {
       	int S = tree.getFather().getId();  // S	
       	int S1 = tree.getChildren().get(0).getId();  // S1	
       	int M = tree.getChildren().get(2).getId();  // M
       	int S2 = tree.getChildren().get(3).getId();  // S2

       	backpatch(S_Attributes.get(S1).getNext(), S_Attributes.get(M).getQuad());

       	Attributes a1 = new Attributes();
    	a1.setNext(S_Attributes.get(S2).getNext());
    	S_Attributes.set(S,a1);
    }
        
    //  {t := mktable(nil); push(t, tblptr); push(0, offset)}
    //  M0 -> ε {offset=0;}
    public static void semantic_51()
    {
    	mktable();
    	int size = table.size()-1;
    	tblptr.push(size);
    	off.push(0);
    	offset = 0;
    }
    
    //  M -> ε  {M.quad=nextquad}
    public static void semantic_52(Tree tree)
    {
       	int M = tree.getFather().getId();  // M	
       	
       	Attributes a1 = new Attributes();
    	a1.setQuad(nextquad());
    	S_Attributes.set(M,a1);
    }
    
    //  N -> ε  {N.nextlist=makelist(nextquad); gencode('goto –')}
    public static void semantic_53(Tree tree)
    {
       	int N = tree.getFather().getId();  // N	
       	
       	Attributes a1 = new Attributes();
    	a1.setNext(makelist(nextquad()));
    	S_Attributes.set(N,a1);
    	
    	String code = "goto ";
    	three_addr.add(code);
    	four_addr.add(new FourAddr("j","-","-","-"));
    	//System.out.println(code); 
    }
    

    
    //  S -> call id ( EL )  
    //  {n=0; for queue中的每个t do {gencode('param't); n=n+1} 
    //   gencode('call'id.addr','n);}
    public static void semantic_54(Tree tree)
    {
    	int S = tree.getFather().getId();  // S
    	String id = tree.getChildren().get(1).getValue();  // id
    	int[] index = lookup(id);
    	
    	if (!table.get(index[0]).get(index[1]).getType().equals("函数"))
    	{
    		String s = "Error at Line [" +  tree.getChildren().get(0).getLine() 
    				+ "]:\t[" + id + "不是函数,不能用于call语句]" ;   		
    		errors.add(s);   
    		Attributes a1 = new Attributes();    	
        	a1.setNext(new ArrayList<Integer>());
        	S_Attributes.set(S,a1);
        	return;
    	}
    	
    	int size = queue.size();
    	for (int i=0; i<size; i++)
    	{
        	String code = "param " + queue.get(i);
        	three_addr.add(code);
        	four_addr.add(new FourAddr("param","-","-",queue.get(i)));
    	}
    	String code = "call " + id + " " + size;
    	three_addr.add(code);
    	four_addr.add(new FourAddr("call",String.valueOf(size),"-",id));
    	
    	Attributes a1 = new Attributes();    	
    	a1.setNext(new ArrayList<Integer>());
    	S_Attributes.set(S,a1);
    }
    
    
    //  EL -> EL , E  {将E.addr添加到queue的队尾}
    public static void semantic_55(Tree tree)
    {
    	int E = tree.getChildren().get(2).getId();  // E
    	queue.add(S_Attributes.get(E).getAddr());
    }
    
    
    //  EL -> E  {初始化queue,然后将E.addr加入到queue的队尾}
    public static void semantic_56(Tree tree)
    {
    	int E = tree.getChildren().get(0).getId();  // E
    	queue.clear();
    	queue.add(S_Attributes.get(E).getAddr());
    }
    
    
    //  D -> proc id; N1 D S 
    // {t=top(tblptr); addwidth(t, top(offset));
    //  pop(tblptr); pop(offset); enterproc(top(tblptr), id.name,t)}
    public static void semantic_57(Tree tree)
    {
    	String id = tree.getChildren().get(1).getValue();
    	int t = tblptr.peek();
    	tblptr.pop();
    	off.pop();
    	
    	enter(tblptr.peek(), id, "函数", t);
    }
    
    //  N1 -> ε {t:= mktable(top(tblptr)); push(t, tblptr); push(0, offset)}
    public static void semantic_58(Tree tree)
    {    	
    	mktable();
    	int size = table.size()-1;
    	tblptr.push(size);
    	off.push(0);
    }
    
    //  N2 -> ε {t:= mktable(nil); push(t, tblptr); push(0, offset)}
    public static void semantic_59(Tree tree)
    {    	
    	mktable();
    	int size = table.size()-1;
    	tblptr.push(size);
    	off.push(0);
    }
    
    public static void findSemantic(Tree tree)
    {
    	String s = treeToPro(tree);  	
    	if (s.equals("P -> proc id ; M0 begin D S end"))
    	{
    		semantic_1(tree);
    	}
    	else if (s.equals("S -> S M S"))
    	{
    		semantic_3(tree);
    	}
    	else if (s.equals("D -> T id ;"))
    	{
    		semantic_5(tree);
    	}
    	else if (s.equals("T -> X C"))
    	{
    		semantic_6(tree);
    	}
    	else if (s.equals("T -> record begin N2 D end"))
    	{
    		semantic_7(tree);
    	}
    	else if (s.equals("X -> integer"))
    	{
    		semantic_8(tree);
    	}
    	else if (s.equals("X -> real"))
    	{
    		semantic_9(tree);
    	}
    	else if (s.equals("C -> [ num ] C"))
    	{
    		semantic_10(tree);
    	}
    	else if (s.equals("C ->"))
    	{
    		semantic_11(tree);
    	}
    	else if (s.equals("S -> id = E ;"))
    	{
    		semantic_12(tree);
    	}
    	else if (s.equals("S -> L = E ;"))
    	{
    		semantic_13(tree);
    	}
    	else if (s.equals("E -> E + E1"))
    	{
    		semantic_14(tree);
    	}
    	else if (s.equals("E -> E1") || s.equals("E1 -> E2"))
    	{
    		semantic_15_17(tree);
    	}
    	else if (s.equals("E1 -> E1 * E2"))
    	{
    		semantic_16(tree);
    	}
    	else if (s.equals("E2 -> ( E )"))
    	{
    		semantic_18(tree);
    	}
    	else if (s.equals("E2 -> - E"))
    	{
    		semantic_19(tree);
    	}
    	else if (s.equals("E2 -> id"))
    	{
    		semantic_20(tree);
    	}
    	else if (s.equals("E2 -> num"))
    	{
    		semantic_21(tree);
    	}
    	else if (s.equals("E2 -> L"))
    	{
    		semantic_22(tree);
    	}
    	else if (s.equals("L -> id [ E ]"))
    	{
    		semantic_23(tree);
    	}
    	else if (s.equals("L -> L [ E ]"))
    	{
    		semantic_24(tree);
    	}
    	else if (s.equals("B -> B or M B1"))
    	{
    		semantic_25(tree);
    	}
    	else if (s.equals("B -> B1") || s.equals("B1 -> B2"))
    	{
    		semantic_26_28(tree);
    	}
    	else if (s.equals("B1 -> B1 and M B2"))
    	{
    		semantic_27(tree);
    	}
    	else if (s.equals("B2 -> not B"))
    	{
    		semantic_29(tree);
    	}
    	else if (s.equals("B2 -> ( B )"))
    	{
    		semantic_30(tree);
    	}
    	else if (s.equals("B2 -> E R E"))
    	{
    		semantic_31(tree);
    	}
    	else if (s.equals("B2 -> true"))
    	{
    		semantic_32(tree);
    	}
    	else if (s.equals("B2 -> false"))
    	{
    		semantic_33(tree);
    	}
    	else if (s.equals("R -> <") || s.equals("R -> <=") || s.equals("R -> ==") 
    			|| s.equals("R -> !=") || s.equals("R -> >") || s.equals("R -> >="))
    	{
    		semantic_34to39(tree);
    	}
    	else if (s.equals("S -> S1") || s.equals("S -> S2") || s.equals("S3 -> S"))
    	{
    		semantic_40_41_50(tree);
    	}
    	else if (s.equals("S1 -> if B then M S1 N else M S1") || 
    			s.equals("S2 -> if B then M S1 N else M S2"))
    	{
    		semantic_42_44(tree);
    	}
    	else if (s.equals("S1 -> while M B do M S0"))
    	{
    		semantic_43(tree);
    	}
    	else if (s.equals("S2 -> if B then M S0"))
    	{
    		semantic_45(tree);
    	}
    	else if (s.equals("S0 -> begin S3 end") || s.equals("S1 -> begin S3 end") ||
    			s.equals("S2 -> begin S3 end"))
    	{
    		semantic_46_47_48(tree);
    	}
    	else if (s.equals("S3 -> S3 ; M S"))
    	{
    		semantic_49(tree);
    	}
    	else if (s.equals("M0 ->"))
    	{
    		semantic_51();
    	}
    	else if (s.equals("M ->"))
    	{
    		semantic_52(tree);
    	}
    	else if (s.equals("N ->"))
    	{
    		semantic_53(tree);
    	}
    	else if (s.equals("S -> call id ( EL ) ;"))
    	{
    		semantic_54(tree);
    	}
    	else if (s.equals("EL -> EL , E"))
    	{
    		semantic_55(tree);
    	}
    	else if (s.equals("EL -> E"))
    	{
    		semantic_56(tree);
    	}
    	else if (s.equals("D -> proc id ; N1 begin D S end"))
    	{
    		semantic_57(tree);
    	}
    	else if (s.equals("N1 ->"))
    	{
    		semantic_58(tree);
    	}
    	else if (s.equals("N2 ->"))
    	{
    		semantic_59(tree);
    	}
    }
    public static String treeToPro(Tree tree)
	{
		String result = tree.getFather().getSymbol()+" -> ";
		for(TreeNode c:tree.getChildren())
		{
			result += c.getSymbol();
			result += " ";
		}
		return result.trim();
	}
		
	public static Boolean endPoint(TreeNode t)
	{
		if (t.getValue().equals("--"))
		{
			return false;
		}
		return true;
	}
}
