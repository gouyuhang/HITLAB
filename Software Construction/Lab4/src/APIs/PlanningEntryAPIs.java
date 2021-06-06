package APIs;

import java.util.*;
import Planning.*;
import Resource.TrainResource;

/**
 * 
 * facade设计模式，PlanningEntryAPI集合在一起
 *
 * @param <R> 资源 泛型
 */
public class PlanningEntryAPIs<R> {
	/**
	 * 查看三种类型是否存在资源或者位置冲突
	 * 
	 * @param entries 一组计划项
	 * @return 存在相同资源，相同位置的提示信息
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
	 * 查看计划项是否有位置冲突
	 * 
	 * @param entries 一组计划项
	 * @return 是/否有冲突
	 */
	public boolean checkLocationConflict(List<? extends PlanningEntry> entries) {
		if (check(entries).equals("SameLocation")) {
			return true;
		}
		return false;
	}

	/**
	 * 查看计划项是否有资源冲突
	 * 
	 * @param list 一组计划项
	 * @return 是/否有冲突
	 */
	public boolean checkResourceConflict(List<? extends PlanningEntry> list) {
		if (check(list).equals("SameResource")) {
			return true;
		}
		return false;
	}

	/**
	 * 寻找前序计划项
	 * 
	 * @param strategy 策略
	 * @param resource 资源
	 * @param e        计划项
	 * @param entries  计划项列表
	 * @return 前序计划项
	 */
	public PlanningEntry findPreEntryPerResource(Strategy<?> strategy, R resource, PlanningEntry e, List<?> entries) {
		return strategy.find(resource, e, entries);
	}
}
