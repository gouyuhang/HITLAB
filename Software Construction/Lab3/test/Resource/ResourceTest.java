package Resource;

import static org.junit.Assert.*;

import org.junit.Test;

public class ResourceTest {
	//Testing strategy
	//新建三个不同种类的资源，比较返回的属性是否正确
	@Test
	public void flightTest() {
		FlightResource r=new FlightResource("11","22","33","44");
		assertEquals("11",r.planeNumber());
		assertEquals("22",r.planeType());
		assertEquals("33",r.seatNumber());
		assertEquals("44",r.planeAge());
		FlightResource r2=new FlightResource("11","22","33","44");
		assertTrue(r.equals(r2));
		assertTrue(r.hashCode()==r2.hashCode());
	}
	@Test
	public void trainTest() {
		TrainResource r=new TrainResource("11","22","33","44");
		assertEquals("11",r.trainNumber());
		assertEquals("22",r.type());
		assertEquals("33",r.passengerNumber());
		assertEquals("44",r.year());
		TrainResource r2=new TrainResource("11","22","33","44");
		assertTrue(r.equals(r2));
		assertTrue(r.hashCode()==r2.hashCode());
	}
	@Test
	public void courseTest() {
		CourseResource r=new CourseResource("11","22","33","44");
		assertEquals("11",r.id());
		assertEquals("22",r.name());
		assertEquals("33",r.sex());
		assertEquals("44",r.position());
		CourseResource r2=new CourseResource("11","22","33","44");
		assertTrue(r.equals(r2));
		assertTrue(r.hashCode()==r2.hashCode());
	}

}
