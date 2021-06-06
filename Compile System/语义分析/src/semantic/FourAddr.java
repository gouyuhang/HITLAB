package semantic;


public class FourAddr 
{
	private String op;  // ������
	private String param1;  // ����һ
	private String param2;  // ������
	private String toaddr;  // ��ַ
	
	public FourAddr(String op, String param1, String param2, String toaddr)
	{
		this.op = op;
		this.param1 = param1;
		this.param2 = param2;
		this.toaddr = toaddr;
	}
	
	public String getOp() 
	{
		return op;
	}
	
	public void setOp(String op) 
	{
		this.op = op;
	}
	public String getParam1() 
	{
		return param1;
	}
	
	public void setParam1(String param1) 
	{
		this.param1 = param1;
	}
	
	public String getParam2() 
	{
		return param2;
	}
	public void setParam2(String param2) 
	{
		this.param2 = param2;
	}
	
	public String getToaddr() {
		return toaddr;
	}
	
	public void setToaddr(String toaddr) 
	{
		this.toaddr = toaddr;
	}

	public String toString()
	{
		String result = "(" + op + ",\t" + param1 + ",\t" + param2 + ",\t" + toaddr + ")\t";
		return result;
	}
	
}
