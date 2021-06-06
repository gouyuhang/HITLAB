package APIs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import Planning.*;
import Resource.CourseResource;
import Resource.FlightResource;
import Resource.TrainResource;
/**
 * 
 * 快速寻找前序计划项的方法
 *
 * @param <R> 资源 泛型
 */
//immutable
public class FindFast<R> implements  Strategy<Object>{
	@Override
	public PlanningEntry find(Object thatr, PlanningEntry thate, List<?> thatentries) {
		if(thatr instanceof FlightResource) {
			List<FlightEntry> target=new ArrayList<>();
			FlightEntry x=new FlightEntry();
			//FlightResource r=(FlightResource) thatr;
			FlightEntry e=(FlightEntry) thate;
			List<FlightEntry> entries=new ArrayList<>();
			for(Object o:thatentries) {
				FlightEntry thato=(FlightEntry) o;
				entries.add(thato);
			}
			Calendar startTime=e.time().start();
			for(FlightEntry f:entries) {
				if(f.getResource().equals(e.getResource())) {
					if(f.time().end().compareTo(startTime)<=0) {
						target.add(f);
					}
				}
			}
			if(target.isEmpty()) {
				return null;
			}
			if(target.size()==1) {
				return target.get(0);
			}
			
			for(int i=0;i<target.size()-1;++i) {
				x=target.get(i);
				boolean flag=true;
				for(int j=i+1;j<target.size();++j) {
					if(x.time().end().compareTo(target.get(j).time().start())<=0) {
						flag=false;
					}
				}
				if(flag==true) {
					return x;
				}
			}
			return null;
		}
		if(thatr instanceof CourseResource) {
			List<CourseEntry> target=new ArrayList<>();
			CourseEntry x=new CourseEntry();
			//CourseResource r=(CourseResource) thatr;
			CourseEntry e=(CourseEntry) thate;
			List<CourseEntry> entries=new ArrayList<>();
			for(Object o:thatentries) {
				CourseEntry thato=(CourseEntry) o;
				entries.add(thato);
			}
			Calendar startTime=e.time().start();
			for(CourseEntry f:entries) {
				if(f.getResource().equals(e.getResource())) {
					if(f.time().end().compareTo(startTime)<=0) {
						target.add(f);
					}
				}
			}
			if(target.isEmpty()) {
				return null;
			}
			if(target.size()==1) {
				return target.get(0);
			}
			for(int i=0;i<target.size()-1;++i) {
				x=target.get(i);
				boolean flag=true;
				for(int j=i+1;j<target.size();++j) {
					if(x.time().end().compareTo(target.get(j).time().start())<=0) {
						flag=false;
					}
				}
				if(flag==true) {
					return x;
				}
			}
			return null;
		}
		if(thatr instanceof TrainResource) {
			List<TrainEntry> target=new ArrayList<>();
			TrainEntry x=new TrainEntry();
			TrainResource r=(TrainResource) thatr;
			TrainEntry e=(TrainEntry) thate;
			List<TrainEntry> entries=new ArrayList<>();
			for(Object o:thatentries) {
				TrainEntry thato=(TrainEntry) o;
				entries.add(thato);
			}
			//Calendar startTime=e.getTime().get(0).start();
			for(TrainEntry f:entries) {
				for(TrainResource l:f.getSortedResourceList()) {
					if(l.equals(r)) {
						if(f.getTime().get(f.getTime().size()-1).end().compareTo
								(e.getTime().get(e.getTime().size()-1).start())<=0) {
							target.add(f);
						}
					}
				}
				
			}
			if(target.isEmpty()) {
				return null;
			}
			if(target.size()==1) {
				return target.get(0);
			}
			for(int i=0;i<target.size()-1;++i) {
				x=target.get(i);
				boolean flag=true;
				for(int j=i+1;j<target.size();++j) {
					if(x.getTime().get(x.getTime().size()-1).end().
							compareTo(target.get(j).getTime().get(0).start())<=0) {
						flag=false;
					}
				}
				if(flag==true) {
					return x;
				}
			}
		}
		return null;
	}

}
