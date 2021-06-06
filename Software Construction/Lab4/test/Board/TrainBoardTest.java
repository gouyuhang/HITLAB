package Board;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import Location.Location;
import Planning.PlanningEntry;
import Planning.TrainEntry;
import Resource.TrainResource;
import Timeslot.Timeslot;

public class TrainBoardTest {

	public static void main(String[] args) {
		List<TrainEntry> list = new ArrayList<>();
		TrainEntry course1 = PlanningEntry.getTrainInstance();
		course1.setName("AA01");
		course1.setLocation(Location.getInstance("train", "beijing", null, null, true));
		course1.setLocation(Location.getInstance("train", "tianjin", null, null, true));
		course1.setLocation(Location.getInstance("train", "shanghai", null, null, true));
		course1.setResource(new TrainResource("11", "22", "33", "44"));
		Calendar T1 = Calendar.getInstance();
		T1.add(Calendar.MINUTE, -10);
		Calendar T2 = Calendar.getInstance();
		T2.add(Calendar.MINUTE, 10);
		Calendar T3 = Calendar.getInstance();
		T3.add(Calendar.MINUTE, 20);
		Calendar T4 = Calendar.getInstance();
		T4.add(Calendar.MINUTE, 50);
		Timeslot t1 = new Timeslot();
		t1.setStart(T1.get(Calendar.YEAR), T1.get(Calendar.MONTH), T1.get(Calendar.DATE), T1.get(Calendar.HOUR_OF_DAY),
				T1.get(Calendar.MINUTE));
		t1.setEnd(T2.get(Calendar.YEAR), T2.get(Calendar.MONTH), T2.get(Calendar.DATE), T2.get(Calendar.HOUR_OF_DAY),
				T2.get(Calendar.MINUTE));
		Timeslot t2 = new Timeslot();
		t2.setStart(T3.get(Calendar.YEAR), T3.get(Calendar.MONTH), T3.get(Calendar.DATE), T3.get(Calendar.HOUR_OF_DAY),
				T3.get(Calendar.MINUTE));
		t2.setEnd(T4.get(Calendar.YEAR), T4.get(Calendar.MONTH), T4.get(Calendar.DATE), T4.get(Calendar.HOUR_OF_DAY),
				T4.get(Calendar.MINUTE));
		course1.addTime(t1);
		course1.addTime(t2);
		course1.begin();
		list.add(course1);
		TrainBoard b = new TrainBoard(list);
		b.visualize("beijing");
		b.visualize2("shanghai");
		b.visualize3("tianjin");

	}

}
