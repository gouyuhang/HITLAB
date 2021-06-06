package Resource;
//immutable
/**
 * �����ɻ���һ��ADT
 * @author gyh
 *
 */
public class FlightResource {
	private String planeNumber;
	private String planeType;
	private String seatNumber;
	private String planeAge;
	//AF
	//planeNumber,planeType,seatNumber,planeAge�ֱ����
	//һ�ܷɻ��ķɻ���ţ��ɻ����ͣ���λ��,����
	//RI
	//planeNumber,planeType,seatNumber,planeAge��!=null
	//Safety from rep exposure
	//String�ǲ��ɱ���������
	private void checkRep() {
		assert planeNumber!=null;
		assert planeType!=null;
		assert seatNumber!=null;
		assert planeAge!=null;
	}
	/**�½�һ���ɻ�����
	 * @param planeNumber �ɻ����
	 * @param planeType �ɻ�����
	 * @param seatNumber �ɻ���λ��
	 * @param planeAge �ɻ�����
	 */
	public FlightResource(String planeNumber,String planeType,String seatNumber,String planeAge) {
		this.planeNumber=planeNumber;
		this.planeType=planeType;
		this.seatNumber=seatNumber;
		this.planeAge=planeAge;
		checkRep();
	}
	/**
	 * ��ȡ�ɻ����
	 * @return �ɻ��ı��
	 */
	public String planeNumber() {
		checkRep();
		return this.planeNumber;
	}
	/**
	 * ��ȡ�ɻ�������
	 * @return �ɻ�����
	 */
	public String planeType() {
		checkRep();
		return this.planeType;
	}
	/**
	 * ��ȡ�ɻ�����λ��
	 * @return �ɻ�����λ��
	 */
	public String seatNumber() {
		checkRep();
		return this.seatNumber;
	}
	/**
	 * ��ȡ�ɻ��Ļ���
	 * @return �ɻ��Ļ���
	 */
	public String planeAge() {
		return this.planeAge;				
	}
	/**
	 * Override equals����
	 */
	@Override
	public boolean equals(Object thatone) {
		if (!(thatone instanceof FlightResource))
			return false;
		FlightResource that = (FlightResource) thatone;
		if(!that.planeAge.equals(this.planeAge)) {
			return false;
		}
		if(!that.seatNumber.equals(this.seatNumber)) {
			return false;
		}
		if(!that.planeType.equals(this.planeType)) {
			return false;
		}
		if(!that.planeNumber.equals(this.planeNumber)) {
			return false;
		}
		return true;
	}
	/**
	 * Override hashcode����
	 */
	@Override
	public int hashCode() {
		int h=1;
		h=31*h+this.planeNumber.hashCode();
		h=31*h+this.planeType.hashCode();
		h=31*h+this.seatNumber.hashCode();
		h=31*h+this.planeAge.hashCode();
		return h;
	}
	
}
