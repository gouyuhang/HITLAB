package p1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;

public class Grammar {
	public Vector<String> T = new Vector<>();
	public Vector<String> N = new Vector<>();
	public Vector<Production> prob = new Vector<>();
	public Map<String, HashSet<String>> first = new HashMap<>();
	public Map<String, HashSet<String>> follow = new HashMap<>();
	public table[][] m = new table[100][100];
	public Stack<String> ST = new Stack<>();

	void readGrammar() {
		this.T.add(">");
		this.T.add("int");
		this.T.add("IDN");
		this.T.add("+");
		this.T.add("<");
		this.T.add("double");
		this.T.add("INT10");
		this.T.add("-");
		this.T.add("=");
		this.T.add("float");
		this.T.add("FLOAT");
		this.T.add("*");
		this.T.add(">=");
		this.T.add("CHAR");
		this.T.add("CHAR");
		this.T.add("/");
		this.T.add("<=");
		this.T.add("void");
		this.T.add("STR");
		this.T.add("&");
		this.T.add("&&");
		this.T.add("unsigned");
		this.T.add(";");
		this.T.add("|");
		this.T.add("||");
		this.T.add("if");
		this.T.add("(");
		this.T.add("!=");
		this.T.add("else");
		this.T.add(")");
		this.T.add("*=");
		this.T.add("do");
		this.T.add("[");
		this.T.add("/=");
		this.T.add("while");
		this.T.add("]");
		this.T.add("-=");
		this.T.add("switch");
		this.T.add("{");
		this.T.add("+=");
		this.T.add("case");
		this.T.add("return");
		this.T.add("}");
		this.T.add("#");
		this.T.add("$");
		try {
			BufferedReader in = new BufferedReader(new FileReader("io/grammar.txt"));
			String str;
			while ((str = in.readLine()) != null) {
				String[] tmp_str = str.split(" ");
				String a = tmp_str[0];
				if (!this.N.contains(a)) {
					this.N.add(a);
				}
				Vector<String> b = new Vector<>();
				for (int i = 2; i < tmp_str.length; i++) {
					b.add(tmp_str[i]);
				}
				Production p = new Production(a, b);
				this.prob.add(p);
			}
		} catch (IOException e) {
		}
	}

	int isInT(String ch) {
		for (int i = 0; i < this.T.size(); i++) {
			if (this.T.get(i).equals(ch)) {
				return i + 1;
			}
		}
		return 0;
	}

	/* �ж�ch�Ƿ��Ƿ��ս�� */
	int isInN(String ch) {
		for (int i = 0; i < this.N.size(); i++) {
			if (this.N.get(i).equals(ch)) {
				return i + 1;
			}
		}
		return 0;
	}

	void getFirstSet() {
		/* �ս����FIRST�����䱾�� */
		for (int i = 0; i < this.T.size(); i++) {
			String X = this.T.get(i);
			HashSet<String> tmp = new HashSet<>();
			tmp.add(X);
			this.first.put(X, tmp);
		}
		for (int i = 0; i < this.N.size(); i++) {
			String X = this.N.get(i);
			HashSet<String> tmp = new HashSet<>();
			this.first.put(X, tmp);
		}
		boolean change = true;
		while (change) {
			change = false;
			for (int i = 0; i < this.prob.size(); i++) {
				Production P = this.prob.get(i);
				String X = P.left();
				HashSet<String> FX = first.get(X);
				/* ����Ҳ���һ�������ǿջ������ս��������뵽�󲿵�FIRST���� */
				if (isInT(P.rights().firstElement()) > 0 || P.rights().firstElement().equals("$")) {
					if (FX != null) {
						if (!FX.contains(P.rights().firstElement())) {
							change = true;
							FX.add(P.rights().firstElement());
						}
					}

				} else {
					boolean next = true;
					int idx = 0;
					while (next && idx < P.rights().size()) {
						next = false;
						String Y = P.rights().get(idx);
						HashSet<String> FY = first.get(Y);
						if (FY != null) {
							for (String s : FY) {
								if (!s.equals("$")) {
									if (!FX.contains(s)) {
										change = true;
										FX.add(s);
									}
								} else {
									next = true;
									idx = idx + 1;
								}
							}
						}

					}

				}
			}
		}
		/*
		 * for (int i = 0; i < this.N.size(); i++) { String X = this.N.get(i);
		 * System.out.print(X + ": "); for (String s : first.get(X)) {
		 * System.out.print(s + " "); } System.out.print('\n'); }
		 */
	}

	/* ����alpha��FIRST�� */
	void getFirstByAlphaSet(Vector<String> alpha, HashSet<String> FS) {
		boolean next = true;
		int idx = 0;
		while (idx < alpha.size() && next) {
			next = false;
			/* ��ǰ�������ս����գ����뵽FIRST���� */
			if (isInT(alpha.elementAt(idx)) > 0 || alpha.elementAt(idx).equals("$")) {
				/* �ж��Ƿ��Ѿ���FIRST���� */
				if (!FS.contains(alpha.elementAt(idx))) {
					FS.add(alpha.elementAt(idx));
				}
			} else {
				String B = alpha.elementAt(idx);
				HashSet<String> FB = this.first.get(B);
				if (FB != null) {
					for (String s : FB) {
						/* ��ǰ����FIRST�������գ����nextΪ�棬��������ǰѭ�� */
						if (s.equals("$")) {
							next = true;
							continue;
						}
						/* �ѷǿ�Ԫ�ؼ��뵽FIRST���� */
						if (!FS.contains(s)) {
							FS.add(s);
						}
					}
				}

			}
			idx = idx + 1;
		}
		if (next) {
			FS.add("$");
		}
	}

	/* ��FOLLOW�� */
	void getFollowSet() {
		for (int i = 0; i < this.N.size(); i++) {
			String B = this.N.get(i);
			this.follow.put(B, new HashSet<String>());
		}
		String S = this.N.get(0);
		this.follow.get(S).add("#");

		boolean change = true;
		while (change) {
			change = false;
			/* ö��ÿ������ʽ */
			for (int i = 0; i < this.prob.size(); i++) {
				Production P = this.prob.get(i);
				for (int j = 0; j < P.rights().size(); j++) {
					String B = P.rights().get(j);
					/* ���ս�� */
					if (isInN(B) > 0) {
						HashSet<String> FB = this.follow.get(B);
						HashSet<String> FS = new HashSet<>();
						/* alpha�Ǵӵ�ǰ������һ�����ſ�ʼ�ķ��Ŵ� */
						Vector<String> alpha = new Vector<>();
						for (int k = j + 1; k < P.rights().size(); ++k) {
							alpha.add(P.rights().get(k));
						}
						/* ��alpha��FIRST������FS */
						getFirstByAlphaSet(alpha, FS);
						/* ��alpha��FIRST�������зǿ�Ԫ�ؼ��뵽��ǰ��FOLLOW���� */
						for (String s : FS) {
							if (s.equals("$")) {
								continue;
							}
							if (!FB.contains(s)) {
								change = true;
								FB.add(s);
							}
						}
						/* ���alpha���ƿգ����ߵ�ǰ�����ǲ���ʽ�Ҳ�ĩβ�����ķ��󲿷��ŵ�FOLLOW�����뵽��ǰ���ŵ�FOLLOW���� */
						if (FS.contains("$") || (j + 1) >= P.rights().size()) {
							String A = P.left(); // AΪ�󲿷���
							for (String s : this.follow.get(A)) {
								if (!FB.contains(s)) {
									change = true;
									FB.add(s);
								}
							}
						}
					}
				}
			}
		}
		/*
		 * System.out.print("FOLLOW:\n"); for (int i = 0; i < this.N.size(); i++) {
		 * String X = this.N.get(i); System.out.print(X + ":"); for (String s :
		 * this.follow.get(X)) { System.out.print(s + " "); } System.out.print("\n"); }
		 */
	}

	void productForecastAnalysisTable() {
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				this.m[i][j] = new table();
			}
		}
		/* ö�����в���ʽ */
		for (int i = 0; i < this.prob.size(); i++) {
			/* ����PΪ A->alpha */
			Production P = this.prob.get(i);
			HashSet<String> FS = new HashSet<>();
			/* ��ÿ�� a in FIRST(alpha) �� A->alpha����M[A, a]�� */
			getFirstByAlphaSet(P.rights(), FS);
			for (String s : FS) {
				int x = isInN(P.left()) - 1;
				int y = isInT(s) - 1;
				m[x][y].insert(P.left);
				m[x][y].insert("->");
				for (String s2 : P.rights()) {
					m[x][y].insert(s2);
				}
			}
			/* ���alpha���ƿգ����ÿ��b in FOLLOW(A) �� A->alpha����M[A, b]�� */
			if (FS.contains("$")) {
				for (String s3 : this.follow.get(P.left())) {
					int x = isInN(P.left()) - 1;
					int y = isInT(s3) - 1;
					/* ��P.left->P.rights���� */
					if (y >= 0) {
						m[x][y].insert(P.left);
						m[x][y].insert("->");
						for (String s2 : P.rights()) {
							m[x][y].insert(s2);
						}
					}

				}
			}
		}
		/*
		System.out.print("forecast analysis table:\n");
		System.out.print("\t");
		for (int i = 0; i < this.T.size(); i++) {
			System.out.print(this.T.get(i) + "\t\t"
					);
		}
		System.out.print("\n");
		for (int i = 0; i < this.N.size(); i++) {
			System.out.print(this.N.get(i) + "\t\t");
			for (int j = 0; j < this.T.size(); j++) {
				System.out.print(m[i][j].write());
				System.out.print("\t\t");
			}
			System.out.print("\n");
		}
		*/
	}

	TreeNode<Token> process(Vector<inputToken> in) {
		int lineNumber = 1;
		TreeNode<Token> tree = new TreeNode<Token>(new Token("S", "S", 1));
		inputToken t = new inputToken("#", "#", -1);
		in.add(t);
		ST.push("#");
		ST.push(this.N.get(0));
		Vector<inputToken> copy = new Vector<>();
		for (inputToken tk : in) {
			copy.add(tk);
		}
		int ip = 0;
		String X, a = "";
		//System.out.print("The answer:\n");
		do {
			X = ST.peek();

			a = in.elementAt(ip).name();
			
			/* ������ս������$ */
			if (isInT(X) > 0) {
				if (X.equals(a)) {
					ST.pop();
					ip = ip + 1;
				} else {
					System.out.print("line"+" "+lineNumber+":"+"����δ�պ�"+"\n");
					break;
				}
			} else { // ���ս��
				Vector<String> s;
				int i = isInN(X) - 1;
				int j = isInT(a) - 1;
				// System.out.print(i+" "+j+"\n");
				if (i >= 0 && j >= 0) {
					s = m[i][j].get();
					if (s.size() != 0) {
						ST.pop();
						for (int i1 = s.size() - 1; i1 >= 2; i1--) {
							if (!s.get(i1).equals("$")) { 
								ST.push(s.get(i1));
							}
						}
						//System.out.print(m[i][j].write());
						//System.out.print("\n");
						int k = 0;
						if(m[i][j].left().equals("define_stmts")||m[i][j].left().equals("stmts")) {
							lineNumber+=1;
						}
						TreeNode<Token> n = tree.findTreeNode(new Token(m[i][j].left(), "", -1));
						for (String s1 : m[i][j].rights()) {

							if (isInT(s1) > 0) {
								for (k = 0; k < copy.size(); k++) {
									if (copy.get(k).name().equals(s1))
										break;
								}
								if (k < copy.size()) {
									Token token = new Token(copy.get(k).name(), copy.get(k).value(),
											copy.get(k).line());
									n.addChild(token);
									copy.remove(k);
								} else {
									Token token = new Token(s1, s1, lineNumber);
									n.addChild(token);
								}

							} else {
								// System.out.println(s1);
								
								Token token = new Token(s1, s1, lineNumber);
								n.addChild(token);
							}
						}
					} else {
						//System.out.print(lineNumber);
						//System.out.print("error2\n");
						break;
					}
				}
			}

		} while (!X.equals("#"));
		return tree;
	}

	private static String createIndent(int depth) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < depth; i++) {
			sb.append(' ');
			sb.append(' ');
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		Vector<inputToken> v = new Vector<>();
		Grammar g = new Grammar();
		g.readGrammar();
		g.getFirstSet();
		g.getFollowSet();
		g.productForecastAnalysisTable();
		Vector<String> str = new Vector<String>();
		try {
			BufferedReader in = new BufferedReader(new FileReader("io/afterAnalysis.txt"));
			String str1;
			while ((str1 = in.readLine()) != null) {
				String[] tmp_str = str1.split(" ");
				inputToken t = new inputToken(tmp_str[1], tmp_str[0], Integer.valueOf(tmp_str[2]));
				v.add(t);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter("io/output.txt"));
			for (TreeNode<Token> node : g.process(v)) {
				String indent = createIndent(node.getLevel());
				if (node.data.name().equals(node.data.value())) {
					if (!node.data.name().equals("$")) {
			
							out.write(indent + node.data.name() + " " + "(" + (node.data.line()) + ")" + "\n");
					} else {
						//out.write(indent + (node.data.name()+1)+"\n");
					}

				} else {
					out.write(
							indent + node.data.name() + " :" + node.data.value() + "(" + node.data.line() + ")" + "\n");
				}
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
