package semantic;

public class Symbol
{
	private String name;  // ������
	private String type;  // ��������
	private int offset;  // ƫ����
	public Symbol(String name, String type, int offset)
	{
		this.name = name;
		this.type = type;
		this.offset = offset;
	}
	
	public String getName() 
	{
		return name;
	}
	
	public void setName(String name) 
	{
		this.name = name;
	}
	
	public String getType() 
	{
		return type;
	}
	
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public int getOffset() 
	{
		return offset;
	}
	
	public void setOffset(int offset) 
	{
		this.offset = offset;
	}
	
	public String toString()
	{
		String result = "(" + name + "," + type + "," + offset + ")";
		return result;
	}
	

}
