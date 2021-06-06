package Timeslot;

import java.util.*;

//mutable
/**
 * 
 * ����ʱ��ε�ADT
 *
 */
public class Timeslot {
	private Calendar start = Calendar.getInstance();
	private Calendar end = Calendar.getInstance();

	// AF
	// start����ʱ��ε���ʼʱ��
	// end����ʱ��ε���ֹʱ��
	// RI
	// start!=null
	// end!=null
	// Safety from rep exposure
	// Calendar����ʽ����
	private void checkRep() {
		assert start != null;
		assert end != null;
	}

	/**
	 * �趨��ʼʱ��
	 * 
	 * @param year   ��ʼ���
	 * @param month  ��ʼ�·�
	 * @param date   ��ʼ����
	 * @param hour   ��ʼСʱ
	 * @param minute ��ʼ����
	 * @return �趨ʱ���Ƿ�ɹ�
	 */
	public boolean setStart(int year, int month, int date, int hour, int minute) {
		this.start.set(year, month, date, hour, minute);
		checkRep();
		return true;
	}

	/**
	 * �趨��ֹʱ��
	 * 
	 * @param year   ��ֹ���
	 * @param month  ��ֹ�·�
	 * @param date   ��ֹ����
	 * @param hour   ��ֹСʱ
	 * @param minute ��ֹ����
	 * @return �趨ʱ���Ƿ�ɹ�
	 */
	public boolean setEnd(int year, int month, int date, int hour, int minute) {
		this.end.set(year, month, date, hour, minute);
		checkRep();
		return true;
	}

	/**
	 * ��ȡ��ʼʱ��
	 * 
	 * @return ��ʼʱ��
	 */
	public Calendar start() {
		Calendar target = Calendar.getInstance();
		target.set(this.start.get(Calendar.YEAR), this.start.get(Calendar.MONTH), this.start.get(Calendar.DATE),
				this.start.get(Calendar.HOUR_OF_DAY), this.start.get(Calendar.MINUTE));
		checkRep();
		return target;
	}

	/**
	 * ��ȡ��ֹʱ��
	 * 
	 * @return ��ֹʱ��
	 */
	public Calendar end() {
		Calendar target = Calendar.getInstance();
		target.set(this.end.get(Calendar.YEAR), this.end.get(Calendar.MONTH), this.end.get(Calendar.DATE),
				this.end.get(Calendar.HOUR_OF_DAY), this.end.get(Calendar.MINUTE));
		checkRep();
		return target;
	}
}
