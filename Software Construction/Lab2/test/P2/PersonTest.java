package P2;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {
	//Testing strategy
	//初始化一个Person类
	//测试获取名字是否正确
	//equals hashcodeTest:
   //创建两个对象进行比较
	//Test
	@Test
	public void getNameStringTest() {
		Person p=new Person("rachel");
		assertEquals("rachel",p.getNameString());
	}
	@Test
    public void Edge_equalshashcodeTest() {
    	Person p1=new Person("bcd");
    	Person p2=new Person("bcd");
    	assertTrue(p1.equals(p2));
    	assertEquals(p1.hashCode(),p2.hashCode());
    }
}
