package Planning;
import Resource.*;
/**
 * 
 * 航班计划项接口组合实现委派
 *
 */
public interface FlightPlanningEntry extends MultipleLocationEntry,SingleResourceEntry<FlightResource>
,SingleTimeslotEntry{

}
