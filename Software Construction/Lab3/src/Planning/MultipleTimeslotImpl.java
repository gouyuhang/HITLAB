package Planning;

import java.util.*;

import Timeslot.Timeslot;
/**
 * 
 * ���ʱ��ΰ��ŵĽӿڵ�ʵ��
 *
 */
//mutable
public class MultipleTimeslotImpl implements MultipleTimeslotEntry {
	private List<Timeslot> list=new ArrayList<>();
	//AF
	//list�������а��ŵ�ʱ��ε��б�
	//RI
	//list!=null
	//Safety from rep exposure
	//umnodified
	/**
	 * �ڲ�����������������ʼCalendar�½�һ��ʱ���timeslot����
	 * @param start ��ʼʱ��
	 * @param end ��ֹʱ��
	 * @param ti �����ɵ�ʱ��ζ���
	 */
	private void setting(Calendar start, Calendar end, Timeslot ti) {
		ti.setStart(start.get(Calendar.YEAR), start.get(Calendar.MONTH), start.get(Calendar.DATE),
				start.get(Calendar.HOUR_OF_DAY), start.get(Calendar.MINUTE));
		ti.setEnd(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end.get(Calendar.DATE), end.get(Calendar.HOUR_OF_DAY),
				end.get(Calendar.MINUTE));
	}

	@Override
	public boolean timeIsSet() {
		if(this.list.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public boolean addTime(Timeslot t) {
		if (t.equals(null)) {
			return false;
		}
		Calendar startTime = t.start();
		Calendar endTime = t.end();
		for (Timeslot item : this.list) {
			Calendar startTimeItem = item.start();
			Calendar endTimeItem = item.end();
			if (startTime.compareTo(startTimeItem) >= 0 && startTime.compareTo(endTimeItem) < 0
					|| endTime.compareTo(startTimeItem) > 0 && endTime.compareTo(endTimeItem) <= 0
					|| startTimeItem.compareTo(startTime) >= 0 && startTimeItem.compareTo(endTime) < 0) {
				return false;
			}
		}
		Timeslot target = new Timeslot();
		setting(t.start(), t.end(), target);
		this.list.add(target);
		return true;
	}

	@Override
	public List<Timeslot> getTime() {
        for (int i = 0; i < this.list.size(); i++) {
            for (int j = i + 1; j < this.list.size(); j++) {
                Timeslot item1 = this.list.get(i);
                Timeslot item2 = this.list.get(j);
                if (item2.end().compareTo(item1.start()) <= 0) {
                    this.list.set(i, item2);
                    this.list.set(j, item1);
                }
            }
        }
		return Collections.unmodifiableList(this.list);
	}

}
