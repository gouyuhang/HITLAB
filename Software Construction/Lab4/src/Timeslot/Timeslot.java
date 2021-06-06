package Timeslot;

import java.util.*;

//mutable
/**
 * 
 * 描述时间段的ADT
 *
 */
public class Timeslot {
	private Calendar start = Calendar.getInstance();
	private Calendar end = Calendar.getInstance();

	// AF
	// start代表时间段的起始时间
	// end代表时间段的终止时间
	// RI
	// start!=null
	// end!=null
	// Safety from rep exposure
	// Calendar防御式拷贝
	private void checkRep() {
		assert start != null;
		assert end != null;
	}

	/**
	 * 设定起始时间
	 * 
	 * @param year   起始年份
	 * @param month  起始月份
	 * @param date   起始日期
	 * @param hour   起始小时
	 * @param minute 起始分钟
	 * @return 设定时间是否成功
	 */
	public boolean setStart(int year, int month, int date, int hour, int minute) {
		this.start.set(year, month, date, hour, minute);
		checkRep();
		return true;
	}

	/**
	 * 设定终止时间
	 * 
	 * @param year   终止年份
	 * @param month  终止月份
	 * @param date   终止日期
	 * @param hour   终止小时
	 * @param minute 终止分钟
	 * @return 设定时间是否成功
	 */
	public boolean setEnd(int year, int month, int date, int hour, int minute) {
		this.end.set(year, month, date, hour, minute);
		checkRep();
		return true;
	}

	/**
	 * 获取初始时间
	 * 
	 * @return 初始时间
	 */
	public Calendar start() {
		Calendar target = Calendar.getInstance();
		target.set(this.start.get(Calendar.YEAR), this.start.get(Calendar.MONTH), this.start.get(Calendar.DATE),
				this.start.get(Calendar.HOUR_OF_DAY), this.start.get(Calendar.MINUTE));
		checkRep();
		return target;
	}

	/**
	 * 获取终止时间
	 * 
	 * @return 终止时间
	 */
	public Calendar end() {
		Calendar target = Calendar.getInstance();
		target.set(this.end.get(Calendar.YEAR), this.end.get(Calendar.MONTH), this.end.get(Calendar.DATE),
				this.end.get(Calendar.HOUR_OF_DAY), this.end.get(Calendar.MINUTE));
		checkRep();
		return target;
	}
}
