package Resource;

//immutable
/**
 * 
 * ����һ�г����ADT
 *
 */
public class TrainResource {
	private String trainNumber;
	private String type;
	private String passengerNumber;
	private String year;

	// AF
	// trainNumber,trainType,passengerNumber,year�ֱ����
	// һ�ܷɻ��ĳ����ţ��������ͣ���λ��,����
	// RI
	// trainNumber,type,passengerNumber,year��!=null
	// Safety from rep exposure
	// String�ǲ��ɱ���������
	private void checkRep() {
		assert trainNumber != null;
		assert type != null;
		assert passengerNumber != null;
		assert year != null;
	}

	/**
	 * �½�һ���г�����
	 * 
	 * @param trainNumber     ������
	 * @param type            �г�����
	 * @param passengerNumber ��λ��
	 * @param year            ����
	 */
	public TrainResource(String trainNumber, String type, String passengerNumber, String year) {
		this.trainNumber = trainNumber;
		this.type = type;
		this.passengerNumber = passengerNumber;
		this.year = year;
		checkRep();
	}

	/**
	 * ��ȡ������
	 * 
	 * @return ������
	 */
	public String trainNumber() {
		checkRep();
		return this.trainNumber;
	}

	/**
	 * ��ȡ��������
	 * 
	 * @return ��������
	 */
	public String type() {
		checkRep();
		return this.type;
	}

	/**
	 * ��ȡ������λ��
	 * 
	 * @return ������λ��
	 */
	public String passengerNumber() {
		checkRep();
		return this.passengerNumber;
	}

	/**
	 * ��ȡ���ᳵ��
	 * 
	 * @return ���ᳵ��
	 */
	public String year() {
		checkRep();
		return this.year;
	}

	/**
	 * Override equals����
	 */
	@Override
	public boolean equals(Object thatone) {
		if (!(thatone instanceof TrainResource))
			return false;
		TrainResource that = (TrainResource) thatone;
		if (!that.trainNumber.equals(this.trainNumber)) {
			return false;
		}
		if (!that.type.equals(this.type)) {
			return false;
		}
		if (!that.passengerNumber.equals(this.passengerNumber)) {
			return false;
		}
		if (!that.year.equals(this.year)) {
			return false;
		}
		return true;
	}

	/**
	 * Override hashcode����
	 */
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + this.trainNumber.hashCode();
		h = 31 * h + this.type.hashCode();
		h = 31 * h + this.passengerNumber.hashCode();
		h = 31 * h + this.year.hashCode();
		return h;
	}
}
