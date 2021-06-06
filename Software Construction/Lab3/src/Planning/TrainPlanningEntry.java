package Planning;
import Resource.*;
/**
 * 
 * 接口组合实现委派
 *
 */
public interface TrainPlanningEntry extends MultipleLocationEntry,
	BlockableEntry,MultipleTimeslotEntry,MultipleResourceEntry<TrainResource>{

}
