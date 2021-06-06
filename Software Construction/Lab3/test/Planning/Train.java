package Planning;

import static org.junit.Assert.*;
import java.util.Calendar;


import org.junit.Test;

import Location.*;
import Resource.*;
import Timeslot.*;

public class Train {
	//新建一个TrainEntry类型 测试所有的功能
	//分配资源，分配位置，设定时间等
	@Test
	public void test() {
		TrainEntry  train=PlanningEntry.getTrainInstance();
		TrainResource resource1=new TrainResource("11","22","33","44");
		TrainResource resource2=new TrainResource("22","33","33","44");
		TrainResource resource3=new TrainResource("10","22","33","44");
		Timeslot t1=new Timeslot();
		t1.setStart(2000, 8, 15, 12, 10);
		t1.setEnd(2000, 8, 15, 12, 20);
		Timeslot t2=new Timeslot();
		t2.setStart(2000, 8, 15, 12, 30);
		t2.setEnd(2000, 8, 15, 12, 40);
		Timeslot t3=new Timeslot();
		t3.setStart(2000, 8, 15, 12, 50);
		t3.setEnd(2000, 8, 15, 12, 55);	
		Location l1=Location.getInstance("train", "beijing", null, null, true);
		Location l2=Location.getInstance("train", "tokyo", null, null, true);
		Location l3=Location.getInstance("train", "sydney", null, null, true);
		train.addTime(t1);
		train.addTime(t2);
		train.addTime(t3);	
		train.setLocation(l1);
		train.setLocation(l2);
		train.setLocation(l3);
		train.setResource(resource1);
		train.setResource(resource2);
		train.setResource(resource3);
		train.setName("happy");
		assertEquals("happy",train.getName());
		train.begin();
		assertEquals("Waiting",train.currentState());
		train.stateMove("Allocated");
		assertEquals("Allocated",train.currentState());		
		assertEquals("11",train.getSortedResourceList().get(0).trainNumber());
		assertEquals(10,train.getTime().get(0).start().get(Calendar.MINUTE));
		assertEquals(30,train.getTime().get(1).start().get(Calendar.MINUTE));
		assertEquals(50,train.getTime().get(2).start().get(Calendar.MINUTE));
		assertEquals("beijing",train.getLocation().get(0).getName());
		assertEquals("tokyo",train.getLocation().get(1).getName());
	}

}
