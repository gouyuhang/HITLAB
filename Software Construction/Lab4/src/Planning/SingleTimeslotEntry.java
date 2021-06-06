package Planning;

import Timeslot.*;

/**
 * 
 * 单个时间段安排的接口
 *
 */
public interface SingleTimeslotEntry {
	/**
	 * 安排时间
	 * 
	 * @param t 时间段对象timeslot
	 * @return 是否设定成功
	 */
	public boolean setTime(Timeslot t);

	/**
	 * 时间是否设定
	 * 
	 * @return 是/否
	 */
	public boolean timeIsSet();

	/**
	 * 设定的时间
	 * 
	 * @return 设定的时间段
	 */
	public Timeslot time();
}
