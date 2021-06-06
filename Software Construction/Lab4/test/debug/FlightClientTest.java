package debug;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class FlightClientTest {
	// Testing Strategy
	// 测试可以分配和不可以分配的两种情况
	@Test
	public void test() {
		Plane p1 = new Plane();
		p1.setPlaneNo("B1222");
		Flight f1 = new Flight();
		f1.setFlightNo("CA138");
		Flight f2 = new Flight();
		f2.setFlightNo("CA152");
		Calendar c1 = Calendar.getInstance();
		c1.set(2000, 03, 11, 8, 10);
		Calendar c2 = Calendar.getInstance();
		c2.set(2000, 03, 11, 9, 10);
		Calendar c3 = Calendar.getInstance();
		c3.set(2000, 03, 11, 9, 0);
		Calendar c4 = Calendar.getInstance();
		c4.set(2000, 03, 11, 10, 0);
		f1.setFlightDate(c1);
		f1.setDepartTime(c1);
		f1.setArrivalTime(c2);
		f2.setFlightDate(c3);
		f2.setDepartTime(c3);
		f2.setArrivalTime(c4);
		List<Plane> l1 = new ArrayList<>();
		l1.add(p1);
		List<Flight> l2 = new ArrayList<>();
		l2.add(f1);
		l2.add(f2);
		FlightClient fc = new FlightClient();
		assertTrue(!fc.planeAllocation(l1, l2));
	}

	@Test
	public void test2() {
		Plane p1 = new Plane();
		p1.setPlaneNo("B1222");
		Flight f1 = new Flight();
		f1.setFlightNo("CA138");
		Flight f2 = new Flight();
		f2.setFlightNo("CA152");
		Calendar c1 = Calendar.getInstance();
		c1.set(2000, 03, 11, 8, 10);
		Calendar c2 = Calendar.getInstance();
		c2.set(2000, 03, 11, 9, 10);
		Calendar c3 = Calendar.getInstance();
		c3.set(2000, 03, 11, 9, 20);
		Calendar c4 = Calendar.getInstance();
		c4.set(2000, 03, 11, 10, 0);
		f1.setFlightDate(c1);
		f1.setDepartTime(c1);
		f1.setArrivalTime(c2);
		f2.setFlightDate(c3);
		f2.setDepartTime(c3);
		f2.setArrivalTime(c4);
		List<Plane> l1 = new ArrayList<>();
		l1.add(p1);
		List<Flight> l2 = new ArrayList<>();
		l2.add(f1);
		l2.add(f2);
		FlightClient fc = new FlightClient();
		assertTrue(fc.planeAllocation(l1, l2));
	}

}
