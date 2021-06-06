package Planning;
import Timeslot.*;
import java.util.*;
/**
 * 
 * 多个时间段安排的接口
 *
 */
public interface MultipleTimeslotEntry {
	/**
	 * 获取时间是否已经设定
	 * @return 是/否
	 */
	public boolean timeIsSet();
	/**
	 * 添加时间段
	 * @param t 需要添加的时间段
	 * @return 是否添加成功
	 */
	public boolean addTime(Timeslot t);
	/**
	 * 获取所有的时间段
	 * @return 所有时间段形成的列表
	 */
	public List<Timeslot> getTime();
}
