package Planning;
import Resource.*;
import Timeslot.Timeslot;

import java.util.*;

import Location.Location;
/**
 * 
 * �𳵼ƻ���������ʵ��
 *
 */
public class TrainEntry extends CommonPlanningEntry
	implements TrainPlanningEntry{
	MultipleLocationEntryImpl a=new MultipleLocationEntryImpl();
	BlockableEntryImpl b=new BlockableEntryImpl();
	MultipleTimeslotImpl c=new MultipleTimeslotImpl();
	MultipleResourceEntryImpl<TrainResource>  d=new MultipleResourceEntryImpl<TrainResource>();
	//AF
	//a��ί�еĶ����Դ����
	//b��ί�ɵĿ�����
	//c��ί�ɶ��ʱ��ΰ���
	//d��ί�ɶ����Դ����
	//RI
	//a,b,c,d!=null;
	//Safety from rep exposure
	//û�з����ڲ��κ�һ��rep
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
