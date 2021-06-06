package Planning;

import java.util.*;

/**
 * 
 * �����Դ���ŵĽӿ�
 *
 * @param <R> �����������Դ������
 */
public interface MultipleResourceEntry<R> {
	/**
	 * �ƻ����Ƿ��Ѿ��趨����Դ
	 * 
	 * @return ��/��
	 */
	public boolean resourceIsSet();

	/**
	 * Ϊ�ƻ��������Դ
	 * 
	 * @param resource ��Դ(����ȣ�
	 * @return �Ƿ��趨�ɹ�
	 */
	public boolean setResource(R resource);

	/**
	 * ��ȡ���а��źõ���Դ
	 * 
	 * @return ���źõ���Դ�б�
	 */
	public List<R> getSortedResourceList();
}
