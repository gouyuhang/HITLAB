package APIs;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import Planning.*;
import Resource.*;
import Location.*;
import Timeslot.*;

public class APItest {
	// Testing strategy
	// 对API进行测试，分别通过CourseEntry,FlightEntry,TrainEntry测试
	// API是否正确返回结果
	@Test
	public void flighttest1() {
		PlanningEntryAPIs<FlightResource> api = new PlanningEntryAPIs<FlightResource>();
		List<PlanningEntry> list = new ArrayList<>();
		FlightEntry course1 = PlanningEntry.getFlightInstance();
		FlightEntry course2 = PlanningEntry.getFlightInstance();
		course1.setResource(new FlightResource("11", "22", "33", "44"));
		course2.setResource(new FlightResource("11", "22", "33", "44"));
		Timeslot t1 = new Timeslot();
		t1.setStart(2000, 8, 12, 15, 10);
		t1.setEnd(2000, 8, 12, 16, 10);
		Timeslot t2 = new Timeslot();
		t2.setStart(2000, 8, 12, 15, 30);
		t2.setEnd(2000, 8, 12, 16, 20);
		course1.setTime(t1);
		course2.setTime(t2);
		list.add(course1);
		list.add(course2);
		assertTrue(api.checkResourceConflict(list));
	}

	@Test
	public void coursetest1() {
		PlanningEntryAPIs<CourseResource> api = new PlanningEntryAPIs<CourseResource>();
		List<PlanningEntry> list = new ArrayList<>();
		CourseEntry course1 = PlanningEntry.getCourseInstance();
		CourseEntry course2 = PlanningEntry.getCourseInstance();
		course1.setResource(new CourseResource("11", "22", "33", "44"));
		course2.setResource(new CourseResource("11", "22", "33", "44"));
		Timeslot t1 = new Timeslot();
		t1.setStart(2000, 8, 12, 15, 10);
		t1.setEnd(2000, 8, 12, 16, 10);
		Timeslot t2 = new Timeslot();
		t2.setStart(2000, 8, 12, 15, 30);
		t2.setEnd(2000, 8, 12, 16, 20);
		course1.setTime(t1);
		course2.setTime(t2);
		list.add(course1);
		list.add(course2);
		assertTrue(api.checkResourceConflict(list));
	}

	@Test
	public void traintest1() {
		PlanningEntryAPIs<TrainResource> api = new PlanningEntryAPIs<TrainResource>();
		List<PlanningEntry> list = new ArrayList<>();
		TrainEntry course1 = PlanningEntry.getTrainInstance();
		TrainEntry course2 = PlanningEntry.getTrainInstance();
		course1.setResource(new TrainResource("11", "22", "33", "44"));
		course2.setResource(new TrainResource("11", "22", "33", "44"));
		Timeslot t1 = new Timeslot();
		t1.setStart(2000, 8, 12, 15, 10);
		t1.setEnd(2000, 8, 12, 16, 10);
		Timeslot t2 = new Timeslot();
		t2.setStart(2000, 8, 12, 15, 30);
		t2.setEnd(2000, 8, 12, 16, 20);
		course1.addTime(t1);
		course2.addTime(t2);
		list.add(course1);
		list.add(course2);
		assertTrue(api.checkResourceConflict(list));
	}

	@Test
	public void coursetest2() {
		PlanningEntryAPIs<CourseResource> api = new PlanningEntryAPIs<CourseResource>();
		List<PlanningEntry> list = new ArrayList<>();
		CourseEntry course1 = PlanningEntry.getCourseInstance();
		CourseEntry course2 = PlanningEntry.getCourseInstance();
		course1.setLocation(Location.getInstance("course", "正心21", null, null, false));
		course2.setLocation(Location.getInstance("course", "正心21", null, null, false));
		Timeslot t1 = new Timeslot();
		t1.setStart(2000, 8, 12, 15, 10);
		t1.setEnd(2000, 8, 12, 16, 10);
		Timeslot t2 = new Timeslot();
		t2.setStart(2000, 8, 12, 15, 30);
		t2.setEnd(2000, 8, 12, 16, 20);
		course1.setTime(t1);
		course2.setTime(t2);
		list.add(course1);
		list.add(course2);
		assertTrue(api.checkLocationConflict(list));
	}

	@Test
	public void coursetest3() {
		PlanningEntryAPIs<CourseResource> api = new PlanningEntryAPIs<CourseResource>();
		List<PlanningEntry> list = new ArrayList<>();
		CourseEntry course1 = PlanningEntry.getCourseInstance();
		CourseEntry course2 = PlanningEntry.getCourseInstance();
		course1.setResource(new CourseResource("11", "22", "33", "44"));
		course2.setResource(new CourseResource("11", "22", "33", "44"));
		Timeslot t1 = new Timeslot();
		t1.setStart(2000, 8, 12, 15, 10);
		t1.setEnd(2000, 8, 12, 16, 10);
		Timeslot t2 = new Timeslot();
		t2.setStart(2000, 8, 12, 14, 00);
		t2.setEnd(2000, 8, 12, 15, 00);
		course1.setTime(t1);
		course2.setTime(t2);
		list.add(course1);
		list.add(course2);
		CourseEntry c3 = (CourseEntry) (api.findPreEntryPerResource(Strategy.getInstance("FindFast"),
				course1.getResource(), course1, list));
		CourseEntry c4 = (CourseEntry) (api.findPreEntryPerResource(Strategy.getInstance("FindLatest"),
				course1.getResource(), course1, list));
		assertEquals(14, c3.time().start().get(Calendar.HOUR_OF_DAY));
		assertEquals(14, c4.time().start().get(Calendar.HOUR_OF_DAY));
	}

	@Test
	public void traintest3() {
		PlanningEntryAPIs<TrainResource> api = new PlanningEntryAPIs<TrainResource>();
		List<PlanningEntry> list = new ArrayList<>();
		TrainEntry course1 = PlanningEntry.getTrainInstance();
		TrainEntry course2 = PlanningEntry.getTrainInstance();
		course1.setResource(new TrainResource("11", "22", "33", "44"));
		course2.setResource(new TrainResource("11", "22", "33", "44"));
		Timeslot t1 = new Timeslot();
		t1.setStart(2000, 8, 12, 15, 10);
		t1.setEnd(2000, 8, 12, 16, 10);
		Timeslot t2 = new Timeslot();
		t2.setStart(2000, 8, 12, 14, 00);
		t2.setEnd(2000, 8, 12, 15, 00);
		course1.addTime(t1);
		course2.addTime(t2);
		list.add(course1);
		list.add(course2);
		TrainEntry c3 = (TrainEntry) (api.findPreEntryPerResource(Strategy.getInstance("FindFast"),
				course1.getSortedResourceList().get(0), course1, list));
		TrainEntry c4 = (TrainEntry) (api.findPreEntryPerResource(Strategy.getInstance("FindLatest"),
				course1.getSortedResourceList().get(0), course1, list));
		assertEquals(14, c3.getTime().get(0).start().get(Calendar.HOUR_OF_DAY));
		assertEquals(14, c4.getTime().get(0).start().get(Calendar.HOUR_OF_DAY));
	}

	@Test
	public void flighttest3() {
		PlanningEntryAPIs<FlightResource> api = new PlanningEntryAPIs<FlightResource>();
		List<PlanningEntry> list = new ArrayList<>();
		FlightEntry course1 = PlanningEntry.getFlightInstance();
		FlightEntry course2 = PlanningEntry.getFlightInstance();
		course1.setResource(new FlightResource("11", "22", "33", "44"));
		course2.setResource(new FlightResource("11", "22", "33", "44"));
		Timeslot t1 = new Timeslot();
		t1.setStart(2000, 8, 12, 15, 10);
		t1.setEnd(2000, 8, 12, 16, 10);
		Timeslot t2 = new Timeslot();
		t2.setStart(2000, 8, 12, 14, 00);
		t2.setEnd(2000, 8, 12, 15, 00);
		course1.setTime(t1);
		course2.setTime(t2);
		list.add(course1);
		list.add(course2);
		FlightEntry c3 = (FlightEntry) (api.findPreEntryPerResource(Strategy.getInstance("FindFast"),
				course1.getResource(), course1, list));
		FlightEntry c4 = (FlightEntry) (api.findPreEntryPerResource(Strategy.getInstance("FindLatest"),
				course1.getResource(), course1, list));
		assertEquals(14, c3.time().start().get(Calendar.HOUR_OF_DAY));
		assertEquals(14, c4.time().start().get(Calendar.HOUR_OF_DAY));
	}

}
