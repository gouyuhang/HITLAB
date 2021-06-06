package Planning;

import Timeslot.Timeslot;
import java.util.*;
/**
 * 
 * 单个时间段安排接口的实现
 *
 */
//mutable
public class SingleTimeslotEntryImpl implements SingleTimeslotEntry{
	private Timeslot t=new Timeslot();
	//AF
	//时间段t
	//RI
	//t!=null
	//Safety from rep exposure
	//防御式拷贝
	private void checkRep() {
		assert t!=null;
	}
	private void setting(Calendar start,Calendar end,Timeslot ti) {
		ti.setStart(start.get(Calendar.YEAR), start.get(Calendar.MONTH), 
				start.get(Calendar.DATE),start.get(Calendar.HOUR_OF_DAY),start.get(Calendar.MINUTE));
		ti.setEnd(end.get(Calendar.YEAR), end.get(Calendar.MONTH), 
				end.get(Calendar.DATE),end.get(Calendar.HOUR_OF_DAY),end.get(Calendar.MINUTE));
	}
	@Override
	public boolean setTime(Timeslot t) {
		if(t.equals(null)) {
			return false;
		}
		if(t.start().compareTo(t.end())>=0) {
			return false;
		}
		setting(t.start(),t.end(),this.t);
		checkRep();
		return true;
	}

	@Override
	public boolean timeIsSet() {
		if(t==null) {
			return false;
		}
		checkRep();
		return true;
	}

	@Override
	public Timeslot time() {
		Timeslot target=new Timeslot();
		setting(this.t.start(),this.t.end(),target);
		checkRep();
		return target;
	}

}
