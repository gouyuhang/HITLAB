package Planning;
/**
 * 
 * ������Դ���ŵĽӿ�
 *
 * @param <R> ��Դ(��ɻ���)
 */
public interface SingleResourceEntry<R> {
	/**
	 * ������Դ
	 * @param resource ��Դ
	 * @return ��Դ�Ƿ��ųɹ�
	 */
	public boolean setResource(R resource);
	/**
	 * ��ȡ��ǰ���ŵ���Դ
	 * @return ��ǰ���ŵ���Դ
	 */
	public R getResource();
	/**
	 * ��Դ�Ƿ��Ѿ����趨
	 * @return ��/��
	 */
	public boolean resourceIsSet();
}
