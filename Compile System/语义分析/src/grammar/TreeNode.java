package grammar;


public class TreeNode
{
	private int id;  // �ڵ��ţ����Թ���һ���ڽӱ�ʱ�����ӽڵ�
	private String symbol;  // ��������
	private String value;  // ����ֵ
	private int line;  // ��������
	
	/**
	 * �﷨��ÿһ���ڵ�Ĺ��캯��
	 * @param id �ڵ��ţ����Թ���һ���ڽӱ�ʱ�����ӽڵ�
	 * @param symbol ��������
	 * @param value ����ֵ
	 * @param line ��������
	 */
	public TreeNode(int id, String symbol, String value, int line)
	{
		this.id = id;
		this.symbol = symbol;
		this.value = value;
		this.line = line;
	}
	
	public int getId() 
	{
		return id;
	}

	public String getSymbol() 
	{
		return symbol;
	}
	
	public String getValue() 
	{
		return value;
	}

	public int getLine() 
	{
		return line;
	}
	
	public String toString()
	{
		String result = "{" + String.valueOf(id) + "," + symbol + "," + value + "}";
		return result;
	}
	
	public void print()
	{
		String result = "{" + String.valueOf(id) + "," + symbol + "," + value + "}";
		System.out.println(result);
	}

}
