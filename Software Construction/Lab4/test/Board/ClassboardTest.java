package Board;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import Location.Location;
import Planning.CourseEntry;
import Planning.PlanningEntry;
import Resource.CourseResource;
import Timeslot.Timeslot;

public class ClassboardTest {

	public static void main(String[] args) {
		List<CourseEntry> list = new ArrayList<>();
		CourseEntry course1 = PlanningEntry.getCourseInstance();
		course1.setName("Èí¼þ¹¹Ôì");
		course1.setLocation(Location.getInstance("course", "d01", null, null, false));
		course1.setResource(new CourseResource("11", "jack", "33", "44"));
		Calendar T1 = Calendar.getInstance();
		T1.add(Calendar.MINUTE, -10);
		Calendar T2 = Calendar.getInstance();
		T2.add(Calendar.MINUTE, 10);
		Timeslot t1 = new Timeslot();
		t1.setStart(T1.get(Calendar.YEAR), T1.get(Calendar.MONTH), T1.get(Calendar.DATE), T1.get(Calendar.HOUR_OF_DAY),
				T1.get(Calendar.MINUTE));
		t1.setEnd(T2.get(Calendar.YEAR), T2.get(Calendar.MONTH), T2.get(Calendar.DATE), T2.get(Calendar.HOUR_OF_DAY),
				T2.get(Calendar.MINUTE));
		course1.setTime(t1);
		course1.begin();
		list.add(course1);
		ClassBoard b = new ClassBoard(list);
		b.visualize("d01");
	}

}
