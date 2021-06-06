package Planning;
import java.util.List;
import Location.Location;
import Resource.*;
import Timeslot.Timeslot;
/**
 * 
 * ����ƻ���������ʵ��
 *
 */
public class FlightEntry extends CommonPlanningEntry
implements FlightPlanningEntry{
	private MultipleLocationEntryImpl a=new MultipleLocationEntryImpl();
	private SingleResourceImpl<FlightResource> b=new SingleResourceImpl<FlightResource>();
	private SingleTimeslotEntryImpl c=new SingleTimeslotEntryImpl();
	//AF
	//a����ί�ɵĶ��λ�ð���
	//b����ί�ɵĵ�����Դ����
	//c����ί�ɵĵ���ʱ��ΰ���
	//RI
	//a,b,c!=null
	//Safety from rep exposure
	//û�з����κ��ڲ���rep
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