package EntryState;
/**
 * 
 * �ƻ���״̬�Ľӿ�
 *
 */
public interface State {
	/**
	 * ��ȡ״̬������
	 * @return
	 */
	public String getName();
	/**
	 * ����״̬ת��
	 * @param cmd ״̬ת�Ƶ�ָ��
	 * @return ת��֮���״̬
	 */
	public State move(String cmd);
}
