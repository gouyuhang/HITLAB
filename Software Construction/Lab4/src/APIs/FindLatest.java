package APIs;

import java.util.*;
import Planning.*;
import Resource.*;

/**
 * 
 * 寻找前序最近的计划项的策略
 *
 * @param <R> 资源，泛型
 */
//immutable
public class FindLatest<R> implements Strategy<Object> {

	@Override
	public PlanningEntry find(Object thatr, PlanningEntry thate, List<?> thatentries) {
		if (thatr instanceof FlightResource && thate instanceof FlightEntry) {
			List<FlightEntry> target = new ArrayList<>();
			FlightEntry e = (FlightEntry) thate;
			List<FlightEntry> entries = new ArrayList<>();
			for (Object o : thatentries) {
				FlightEntry thato = (FlightEntry) o;
				entries.add(thato);
			}
			Calendar startTime = e.time().start();
			for (FlightEntry f : entries) {
				if (f.getResource().equals(e.getResource())) {
					if (f.time().end().compareTo(startTime) <= 0) {
						target.add(f);
					}
				}
			}
			if (target.isEmpty()) {
				return null;
			}
			if (target.size() == 1) {
				return target.get(0);
			}
			FlightEntry x = target.get(0);
			for (int i = 0; i < target.size() - 1; ++i) {
				if (target.get(i + 1).time().end().compareTo(x.time().end()) > 0) {
					x = target.get(i + 1);
				}
			}
			return x;
		}
		if (thatr instanceof CourseResource && thate instanceof CourseEntry) {
			List<CourseEntry> target = new ArrayList<>();
			CourseEntry e = (CourseEntry) thate;
			List<CourseEntry> entries = new ArrayList<>();
			for (Object o : thatentries) {
				CourseEntry thato = (CourseEntry) o;
				entries.add(thato);
			}
			Calendar startTime = e.time().start();
			for (CourseEntry f : entries) {
				if (f.getResource().equals(e.getResource())) {
					if (f.time().end().compareTo(startTime) <= 0) {
						target.add(f);
					}
				}
			}
			if (target.isEmpty()) {
				return null;
			}
			if (target.size() == 1) {
				return target.get(0);
			}
			CourseEntry x = target.get(0);
			for (int i = 0; i < target.size() - 1; ++i) {
				if (target.get(i + 1).time().end().compareTo(x.time().end()) > 0) {
					x = target.get(i + 1);
				}
			}
			return x;
		}
		if (thatr instanceof TrainResource && thate instanceof TrainEntry) {
			List<TrainEntry> target = new ArrayList<>();
			TrainResource r = (TrainResource) thatr;
			TrainEntry e = (TrainEntry) thate;
			List<TrainEntry> entries = new ArrayList<>();
			for (Object o : thatentries) {
				TrainEntry thato = (TrainEntry) o;
				entries.add(thato);
			}

			for (TrainEntry f : entries) {
				for (TrainResource l : f.getSortedResourceList()) {
					if (l.equals(r)) {
						if (f.getTime().get(f.getTime().size() - 1).end()
								.compareTo(e.getTime().get(e.getTime().size() - 1).start()) <= 0) {
							target.add(f);
						}
					}
				}

			}
			if (target.isEmpty()) {
				return null;
			}
			if (target.size() == 1) {
				return target.get(0);
			}
			TrainEntry x = target.get(0);
			for (int i = 0; i < target.size() - 1; ++i) {
				if (target.get(i + 1).getTime().get(target.get(i + 1).getTime().size() - 1).end()
						.compareTo(x.getTime().get(x.getTime().size() - 1).end()) > 0) {
					x = target.get(i + 1);
				}
			}
			return x;
		}
		return null;
	}

}
