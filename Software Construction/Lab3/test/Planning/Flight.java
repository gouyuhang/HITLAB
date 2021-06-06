package Planning;

import static org.junit.Assert.*;
import java.util.Calendar;
import org.junit.Test;
import Timeslot.*;
import Location.*;
import Resource.*;
public class Flight {
	//�½�һ��FlightEntry���� �������еĹ���
	//������Դ������λ�ã��趨ʱ���
	@Test
	public void test() {
		FlightEntry  flight=PlanningEntry.getFlightInstance();
		FlightResource resource=new FlightResource("11","22","33","44");
		Timeslot t=new Timeslot();
		t.setStart(2000, 8, 15, 12, 10);
		t.setEnd(2000, 8, 15, 12, 20);
		Location l1=Location.getInstance("flight", "beijing", null, null, true);
		Location l2=Location.getInstance("flight", "tokyo", null, null, true);
		flight.setName("happy");
		assertEquals("happy",flight.getName());
		flight.begin();
		assertEquals("Waiting",flight.currentState());
		flight.stateMove("Allocated");
		assertEquals("Allocated",flight.currentState());
		flight.setResource(resource);
		assertEquals("11",flight.getResource().planeNumber());
		flight.setTime(t);
		assertEquals(10,flight.time().start().get(Calendar.MINUTE));
		assertEquals(20,flight.time().end().get(Calendar.MINUTE));
		flight.setLocation(l1);
		flight.setLocation(l2);
		assertEquals("beijing",flight.getLocation().get(0).getName());
		assertEquals("tokyo",flight.getLocation().get(1).getName());
	}

}
