package Location;
/**
 * 
 * ʵ��λ����Ϣ�Ľӿ�
 *
 */
public interface Location {
	/**
	 * ��ȡһ����վ/�ɻ�վ/��ʦ����
	 * @param type λ�õ�����
	 * @param name λ�õ�����
	 * @param lng λ�õľ���
	 * @param lat λ�õ�γ��
	 * @param sharing λ���Ƿ���Թ���
	 * @return �����λ�ö���
	 */
	public static Location getInstance(String type,String name,String lng,String lat,boolean sharing) {
		if(type.equals("flight")) {
			return new FlightLocation(name,lng,lat,sharing);
		}
		if(type.equals("train")) {
			return new TrainLocation(name,lng,lat,sharing);
		}
		if(type.equals("course")) {
			return new CourseLocation(name,sharing);
		}
		return null;
	}
	/**
	 * ��ȡλ�õ�����
	 * @return λ�õ�����
	 */
	public String getName();
	/**
	 * λ���Ƿ���
	 * @return ��/�� ����
	 */
	public boolean share();
	/**
	 * ��ȡλ�õľ���
	 * @return λ�õľ���
	 */
	public String lng();
	/**
	 * ��ȡλ�õ�γ��
	 * @return λ�õ�γ��
	 */
	public String lat();
	
}
