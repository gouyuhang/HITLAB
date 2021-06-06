package Planning;

/**
 * 
 * �ƻ��ͬ�����Ľӿ�
 *
 */
public interface PlanningEntry {
	/**
	 * ��̬��������
	 * 
	 * @return ��ȡһ���µĺ���ƻ���
	 */
	public static FlightEntry getFlightInstance() {
		return new FlightEntry();
	}

	/**
	 * ��̬��������
	 * 
	 * @return ��ȡһ���µĿγ̼ƻ���
	 */
	public static CourseEntry getCourseInstance() {
		return new CourseEntry();
	}

	/**
	 * ��̬��������
	 * 
	 * @return ��ȡһ���µ��г��ƻ���
	 */
	public static TrainEntry getTrainInstance() {
		return new TrainEntry();
	}

	/**
	 * ���ƻ����趨����
	 * 
	 * @param name �ƻ��������
	 * @return �趨�Ƿ�ɹ�
	 */
	public boolean setName(String name);

	/**
	 * ��ȡ�ƻ��������
	 * 
	 * @return �ƻ��������
	 */
	public String getName();

	/**
	 * ʹһ���ƻ�������ʼ״̬(Waiting)
	 */
	public void begin();

	/**
	 * �ƻ����״̬ת��
	 * 
	 * @param cmd �ƻ���ת�Ƶ�ָ��
	 * @return �ƻ����״̬�Ƿ�ת���ɹ�
	 */
	public boolean stateMove(String cmd);

	/**
	 * ��ȡ�ƻ���ĵ�ǰ״̬
	 * 
	 * @return �ƻ����״̬���ַ�����ʾ
	 */
	public String currentState();
}
