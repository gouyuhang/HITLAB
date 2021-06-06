package Planning;
import Resource.*;
import Timeslot.Timeslot;

import java.util.*;

import Location.Location;
/**
 * 
 * 火车计划项子类型实现
 *
 */
public class TrainEntry extends CommonPlanningEntry
	implements TrainPlanningEntry{
	MultipleLocationEntryImpl a=new MultipleLocationEntryImpl();
	BlockableEntryImpl b=new BlockableEntryImpl();
	MultipleTimeslotImpl c=new MultipleTimeslotImpl();
	MultipleResourceEntryImpl<TrainResource>  d=new MultipleResourceEntryImpl<TrainResource>();
	//AF
	//a是委托的多个资源安排
	//b是委派的可阻塞
	//c是委派多个时间段安排
	//d是委派多个资源安排
	//RI
	//a,b,c,d!=null;
	//Safety from rep exposure
	//没有返回内部任何一个rep
	private void checkRep() {
		assert a!=null;
		assert b!=null;
		assert c!=null;
		assert d!=null;
	}
	@Override
	public boolean locIsSet() {
		checkRep();
		return a.locIsSet();
	}

	@Override
	public boolean setLocation(Location list) {
		checkRep();
		return a.setLocation(list);
	}

	@Override
	public List<Location> getLocation() {
		checkRep();
		return a.getLocation();
	}

	@Override
	public int blockTime() {
		checkRep();
		return b.blockTime();
	}

	@Override
	public void block() {
		b.block();
		checkRep();
	}

	@Override
	public boolean timeIsSet() {
		checkRep();
		return c.timeIsSet();
	}

	@Override
	public boolean addTime(Timeslot t) {
		checkRep();
		return c.addTime(t);
	}

	@Override
	public List<Timeslot> getTime() {
		checkRep();
		return c.getTime();
	}

	@Override
	public boolean setResource(TrainResource list) {
		checkRep();
		return d.setResource(list);
	}

	@Override
	public List<TrainResource> getSortedResourceList() {
		checkRep();
		return d.getSortedResourceList();
	}

	@Override
	public boolean resourceIsSet() {
		checkRep();
		return d.resourceIsSet();
	}

}
