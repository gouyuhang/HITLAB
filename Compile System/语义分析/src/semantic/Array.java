package semantic;

public class Array
{
	private int length;  // ����
	private Array type;  // ��������
	private String baseType;  // ��������

	
	public int getLength()
	{
		return length;
	}
	
	public Array getType()
	{
		return type;
	}
	
	public String getBaseType()
	{
		return baseType;
	}
	
	public void setLength(int length)
	{
		this.length=length;
	}
	
	public void setType(Array type)
	{
		this.type=type;
	}
	
	public void setBaseType(String baseType)
	{
		this.baseType=baseType;
	}

}
