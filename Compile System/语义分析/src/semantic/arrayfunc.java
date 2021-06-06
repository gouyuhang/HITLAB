package semantic;

public class arrayfunc {
	public static String arrayString(Array a)
	{
		String b = a.getBaseType();
		if(!b.equals("array"))
		{
			return "array" + "(" + a.getLength() + "," + b + ")";
		}
		else
		{
			return "array" + "(" + a.getLength() + "," + arrayString(a.getType()) + ")";
		}
	}
	
	// "array(3,array(5,array(8,int)))" ---> "array(5,array(8,int))"
	public static String elemType(String s)
	{
		if (!s.contains("array"))
			return "integer";
		
		int i;
		int len = s.length();
		for (i=0; i<len; i++) 
		{
            if (s.charAt(i) == ',') 
            {
            	break;
            }
        }		
		return s.substring(i+1, len-1);
	}
    
	//  "array(3,array(5,array(8,int)))" ---> 3
	public static int typeWidth(String s)
	{
		if (!s.contains("array"))
			return 4;
		
		int i,j=0;
		int len = s.length();
		for (i=0; i<len; i++) 
		{
            if (s.charAt(i) == '(') 
            {
            	j = i;
            }
            if (s.charAt(i) == ',') 
            {
            	break;
            }
        }		
		return Integer.valueOf(s.substring(j+1,i));
	}
}
