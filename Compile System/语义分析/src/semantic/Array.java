package semantic;

public class Array
{
	private int length;  // 长度
	private Array type;  // 数组类型
	private String baseType;  // 基本类型

	
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
