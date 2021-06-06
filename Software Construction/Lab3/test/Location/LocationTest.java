package Location;

import static org.junit.Assert.*;

import org.junit.Test;

public class LocationTest {
	//Testing strategy
	//新建三个不同种类的地址(静态工厂方法)
	//看返回值是否正确
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
		Location l1=Location.getInstance("course", "正心21", null, null, false);
		Location l2=Location.getInstance("course", "正心21", null, null, false);
		assertEquals("正心21",l1.getName());
		assertEquals(false,l1.share());
		assertTrue(l1.equals(l2));
		assertTrue(l1.hashCode()==l2.hashCode());
	}
	

}
