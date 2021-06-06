package Planning;
import java.util.List;
import Location.Location;
import Resource.*;
import Timeslot.Timeslot;
/**
 * 
 * 航班计划项子类型实现
 *
 */
public class FlightEntry extends CommonPlanningEntry
implements FlightPlanningEntry{
	private MultipleLocationEntryImpl a=new MultipleLocationEntryImpl();
	private SingleResourceImpl<FlightResource> b=new SingleResourceImpl<FlightResource>();
	private SingleTimeslotEntryImpl c=new SingleTimeslotEntryImpl();
	//AF
	//a代表委派的多个位置安排
	//b代表委派的单个资源安排
	//c代表委派的单个时间段安排
	//RI
	//a,b,c!=null
	//Safety from rep exposure
	//没有返回任何内部的rep
	private void checkRep() {
		assert a!=null;
		assert b!=null;
		assert c!=null;
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
	public boolean locIsSet() {
		checkRep();
		return a.locIsSet();
	}
	
	@Override
	public boolean setResource(FlightResource resource) {
		checkRep();
		return b.setResource(resource);
	}

	@Override
	public FlightResource getResource() {
		checkRep();
		return b.getResource();
	}

	@Override
	public boolean setTime(Timeslot t) {
		checkRep();
		return c.setTime(t);
	}

	@Override
	public boolean timeIsSet() {
		checkRep();
		return c.timeIsSet();
	}

	@Override
	public Timeslot time() {
		checkRep();
		return c.time();
	}

	@Override
	public boolean resourceIsSet() {
		checkRep();
		return b.resourceIsSet();
	}


}