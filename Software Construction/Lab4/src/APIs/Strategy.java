package APIs;

import Planning.*;
import java.util.*;

/**
 * 
 * Strategy���ģʽ�����ֲ���
 *
 * @param <R> ��Դ������
 */
public interface Strategy<R> {
	/**
	 * ��̬�����������ɷ������ֲ���
	 * 
	 * @param stra ��������
	 * @return ���ֲ�������һ�ֵ�������
	 */
	public static Strategy<?> getInstance(String stra) {
		if (stra.equals("FindFast")) {
			return new FindFast<Object>();
		}
		if (stra.equals("FindLatest")) {
			return new FindLatest<Object>();
		}
		return null;
	}

	/**
	 * Ѱ��ǰ��ƻ���
	 * 
	 * @param resource ��Դ
	 * @param e        �ƻ���
	 * @param entries  �ƻ����б�
	 * @return ǰ��ƻ���
	 */
	public PlanningEntry find(Object resource, PlanningEntry e, List<?> entries);

}
