package Planning;

import Timeslot.*;

/**
 * 
 * ����ʱ��ΰ��ŵĽӿ�
 *
 */
public interface SingleTimeslotEntry {
	/**
	 * ����ʱ��
	 * 
	 * @param t ʱ��ζ���timeslot
	 * @return �Ƿ��趨�ɹ�
	 */
	public boolean setTime(Timeslot t);

	/**
	 * ʱ���Ƿ��趨
	 * 
	 * @return ��/��
	 */
	public boolean timeIsSet();

	/**
	 * �趨��ʱ��
	 * 
	 * @return �趨��ʱ���
	 */
	public Timeslot time();
}
