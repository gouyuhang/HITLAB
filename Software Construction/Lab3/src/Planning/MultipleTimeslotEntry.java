package Planning;
import Timeslot.*;
import java.util.*;
/**
 * 
 * ���ʱ��ΰ��ŵĽӿ�
 *
 */
public interface MultipleTimeslotEntry {
	/**
	 * ��ȡʱ���Ƿ��Ѿ��趨
	 * @return ��/��
	 */
	public boolean timeIsSet();
	/**
	 * ���ʱ���
	 * @param t ��Ҫ��ӵ�ʱ���
	 * @return �Ƿ���ӳɹ�
	 */
	public boolean addTime(Timeslot t);
	/**
	 * ��ȡ���е�ʱ���
	 * @return ����ʱ����γɵ��б�
	 */
	public List<Timeslot> getTime();
}
