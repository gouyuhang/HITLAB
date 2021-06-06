package Planning;

import static org.junit.Assert.*;
import java.util.Calendar;
import org.junit.Test;
import Location.Location;
import Resource.CourseResource;
import Timeslot.Timeslot;

public class Course {
	//�½�һ��CourseEntry���� �������еĹ���
	//������Դ������λ�ã��趨ʱ���
	@Test
	public void test() {
		CourseEntry  flight=PlanningEntry.getCourseInstance();
		CourseResource resource=new CourseResource("11","22","33","44");
		Timeslot t=new Timeslot();
		t.setStart(2000, 8, 15, 12, 10);
		t.setEnd(2000, 8, 15, 12, 20);
		Location l1=Location.getInstance("course", "����", null, null, false);
		flight.setName("happy");
		assertEquals("happy",flight.getName());
		flight.begin();
		assertEquals("Waiting",flight.currentState());
		flight.stateMove("Allocated");
		assertEquals("Allocated",flight.currentState());
		flight.setResource(resource);
		assertEquals("11",flight.getResource().id());
		flight.setTime(t);
		assertEquals(10,flight.time().start().get(Calendar.MINUTE));
		assertEquals(20,flight.time().end().get(Calendar.MINUTE));
		flight.setLocation(l1);
		assertEquals("����",flight.getLocation().getName());
	}

}
