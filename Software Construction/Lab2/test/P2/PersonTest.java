package P2;

import static org.junit.Assert.*;

import org.junit.Test;

public class PersonTest {
	//Testing strategy
	//��ʼ��һ��Person��
	//���Ի�ȡ�����Ƿ���ȷ
	//equals hashcodeTest:
   //��������������бȽ�
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
