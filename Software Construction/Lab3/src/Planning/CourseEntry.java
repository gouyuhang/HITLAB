package Planning;
import Location.Location;
import Resource.*;
import Timeslot.Timeslot;
/**
 * 
 * 课程计划项的子类型实现
 *
 */
//mutable
public class CourseEntry extends CommonPlanningEntry
	implements CoursePlanning,Comparable<CourseEntry>{
	SingleResourceImpl<CourseResource> a=new SingleResourceImpl<CourseResource>();
	SingleLoacationEntryImpl  b=new SingleLoacationEntryImpl();
	SingleTimeslotEntryImpl c=new SingleTimeslotEntryImpl();
	//AF
	//a代表委托的单独资源安排
	//b代表委托的单独位置安排
	//c代表委托的单独时间段安排
	//RI
	//a,b,c!=null
	//Safety from rep exposure
	//没有返回任何的rep
	private void checkRep() {
		assert a!=null;
		assert b!=null;
		assert c!=null;
	}
	@Override
	public boolean setResource(CourseResource resource) {
		checkRep();
		return a.setResource(resource);
	}

	@Override
	public CourseResource getResource() {
		checkRep();
		return a.getResource();
	}

	@Override
	public boolean setLocation(Location l) {
		checkRep();
		return b.setLocation(l);
	}

	@Override
	public Location getLocation() {
		checkRep();
		return b.getLocation();
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
		return a.resourceIsSet();
	}

	@Override
	public boolean locIsSet() {
		checkRep();
		return b.locIsSet();
	}

	@Override
	public int compareTo(CourseEntry o) {
		checkRep();
		return c.time().start().compareTo(o.time().start());
	}

}
