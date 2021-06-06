package Planning;
import Resource.*;
/**
 * 
 * 课程计划项通过接口组合实现委派
 *
 */
public interface CoursePlanning extends SingleResourceEntry<CourseResource>,
	SingleLocationEntry,SingleTimeslotEntry{

}
