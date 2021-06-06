package Location;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {
	//Testing strategy
	//�½�������ͬ����ĵ�ַ(��̬��������)
	//������ֵ�Ƿ���ȷ
	@Test
	public void flightTest() {
		Location l1=Location.getInstance("flight", "beijing", "11", "22", true);
		Location l2=Location.getInstance("flight", "beijing", "11", "22", true);
		assertEquals("beijing",l1.getName());
		assertEquals("11",l1.lng());
		assertEquals("22",l1.lat());
		assertEquals(true,l1.share());
		assertTrue(l1.equals(l2));
		assertTrue(l1.hashCode()==l2.hashCode());
	}
	@Test
	public void trainTest() {
		Location l1=Location.getInstance("train", "beijing", "11", "22", true);
		Location l2=Location.getInstance("train", "beijing", "11", "22", true);
		assertEquals("beijing",l1.getName());
		assertEquals("11",l1.lng());
		assertEquals("22",l1.lat());
		assertEquals(true,l1.share());
		assertTrue(l1.equals(l2));
		assertTrue(l1.hashCode()==l2.hashCode());
	}
	@Test
	public void courseTest() {
		Location l1=Location.getInstance("course", "����21", null, null, false);
		Location l2=Location.getInstance("course", "����21", null, null, false);
		assertEquals("����21",l1.getName());
		assertEquals(false,l1.share());
		assertTrue(l1.equals(l2));
		assertTrue(l1.hashCode()==l2.hashCode());
	}
	

}
