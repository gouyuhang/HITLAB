package Planning;

import static org.junit.Assert.*;
import java.util.Calendar;
import org.junit.Test;
import Location.Location;
import Resource.CourseResource;
import Timeslot.Timeslot;

public class Course {
	//新建一个CourseEntry类型 测试所有的功能
	//分配资源，分配位置，设定时间等
	@Test
	public void test() {
		CourseEntry  flight=PlanningEntry.getCourseInstance();
		CourseResource resource=new CourseResource("11","22","33","44");
		Timeslot t=new Timeslot();
		t.setStart(2000, 8, 15, 12, 10);
		t.setEnd(2000, 8, 15, 12, 20);
		Location l1=Location.getInstance("course", "正心", null, null, false);
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
		assertEquals("正心",flight.getLocation().getName());
	}

}
