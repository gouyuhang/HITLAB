package APIs;

import java.util.*;
import Planning.*;
import Resource.TrainResource;

/**
 * 
 * facade���ģʽ��PlanningEntryAPI������һ��
 *
 * @param <R> ��Դ ����
 */
public class PlanningEntryAPIs<R> {
	/**
	 * �鿴���������Ƿ������Դ����λ�ó�ͻ
	 * 
	 * @param entries һ��ƻ���
	 * @return ������ͬ��Դ����ͬλ�õ���ʾ��Ϣ
	 */
	private String check(List<? extends PlanningEntry> entries) {
		if (entries.get(0) instanceof CourseEntry) {
			for (int i = 0; i < entries.size(); i++) {
				CourseEntry a1 = (CourseEntry) entries.get(i);
				if (a1.timeIsSet() == false) {
					continue;
				}
				for (int j = i + 1; j < entries.size(); ++j) {
					CourseEntry a2 = (CourseEntry) entries.get(j);
					if (a2.timeIsSet() == false) {
						continue;
					}
					Calendar startTime = a1.time().start();
					Calendar endTime = a1.time().end();
					Calendar startTimeItem = a2.time().start();
					Calendar endTimeItem = a2.time().end();
					if (startTime.compareTo(startTimeItem) >= 0 && startTime.compareTo(endTimeItem) < 0
							|| endTime.compareTo(startTimeItem) > 0 && endTime.compareTo(endTimeItem) <= 0
							|| startTimeItem.compareTo(startTime) >= 0 && startTimeItem.compareTo(endTime) < 0) {
						if (a1.locIsSet() && a2.locIsSet()) {
							if (a1.getLocation().equals(a2.getLocation())) {
								return "SameLocation";
							}
						}
						if (a1.resourceIsSet() && a2.resourceIsSet()) {
							if (a1.getResource().equals(a2.getResource())) {
								return "SameResource";
							}
						}
					}
				}
			}
		}
		if (entries.get(0) instanceof FlightEntry) {
			for (int i = 0; i < entries.size(); i++) {
				FlightEntry a1 = (FlightEntry) entries.get(i);
				if (a1.timeIsSet() == false) {
					continue;
				}
				for (int j = i + 1; j < entries.size(); ++j) {
					FlightEntry a2 = (FlightEntry) entries.get(j);
					if (a2.timeIsSet() == false) {
						continue;
					}
					Calendar startTime = a1.time().start();
					Calendar endTime = a1.time().end();
					Calendar startTimeItem = a2.time().start();
					Calendar endTimeItem = a2.time().end();
					if (startTime.compareTo(startTimeItem) >= 0 && startTime.compareTo(endTimeItem) < 0
							|| endTime.compareTo(startTimeItem) > 0 && endTime.compareTo(endTimeItem) <= 0
							|| startTimeItem.compareTo(startTime) >= 0 && startTimeItem.compareTo(endTime) < 0) {
						if (a1.resourceIsSet() && a2.resourceIsSet()) {
							if (a1.getResource().equals(a2.getResource())) {
								return "SameResource";
							}
						}
					}
				}
			}
		}
		if (entries.get(0) instanceof TrainEntry) {
			for (int i = 0; i < entries.size(); i++) {
				TrainEntry a1 = (TrainEntry) entries.get(i);
				if (a1.timeIsSet() == false || a1.resourceIsSet() == false) {
					continue;
				}
				for (int j = i + 1; j < entries.size(); ++j) {
					TrainEntry a2 = (TrainEntry) entries.get(j);
					if (a2.timeIsSet() == false || a2.resourceIsSet() == false) {
						continue;
					}
					Calendar startTime = a1.getTime().get(0).start();
					Calendar endTime = a1.getTime().get(a1.getTime().size() - 1).end();
					Calendar startTimeItem = a2.getTime().get(0).start();
					;
					Calendar endTimeItem = a2.getTime().get(a1.getTime().size() - 1).end();
					if (startTime.compareTo(startTimeItem) >= 0 && startTime.compareTo(endTimeItem) < 0
							|| endTime.compareTo(startTimeItem) > 0 && endTime.compareTo(endTimeItem) <= 0
							|| startTimeItem.compareTo(startTime) >= 0 && startTimeItem.compareTo(endTime) < 0) {
						List<TrainResource> list1 = a1.getSortedResourceList();
						List<TrainResource> list2 = a2.getSortedResourceList();
						for (int p = 0; i < list1.size(); ++i) {
							for (int q = 0; q < list2.size(); ++j) {
								if (list1.get(p).equals(list2.get(q))) {
									return "SameResource";
								}
							}
						}
					}
				}
			}
		}
		return "empty";
	}

	/**
	 * �鿴�ƻ����Ƿ���λ�ó�ͻ
	 * 
	 * @param entries һ��ƻ���
	 * @return ��/���г�ͻ
	 */
	public boolean checkLocationConflict(List<? extends PlanningEntry> entries) {
		if (check(entries).equals("SameLocation")) {
			return true;
		}
		return false;
	}

	/**
	 * �鿴�ƻ����Ƿ�����Դ��ͻ
	 * 
	 * @param list һ��ƻ���
	 * @return ��/���г�ͻ
	 */
	public boolean checkResourceConflict(List<? extends PlanningEntry> list) {
		if (check(list).equals("SameResource")) {
			return true;
		}
		return false;
	}

	/**
	 * Ѱ��ǰ��ƻ���
	 * 
	 * @param strategy ����
	 * @param resource ��Դ
	 * @param e        �ƻ���
	 * @param entries  �ƻ����б�
	 * @return ǰ��ƻ���
	 */
	public PlanningEntry findPreEntryPerResource(Strategy<?> strategy, R resource, PlanningEntry e, List<?> entries) {
		return strategy.find(resource, e, entries);
	}
}
