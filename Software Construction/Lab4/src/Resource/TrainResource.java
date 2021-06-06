package Resource;

//immutable
/**
 * 
 * 描述一列车厢的ADT
 *
 */
public class TrainResource {
	private String trainNumber;
	private String type;
	private String passengerNumber;
	private String year;

	// AF
	// trainNumber,trainType,passengerNumber,year分别代表
	// 一架飞机的车厢编号，车厢类型，座位数,车龄
	// RI
	// trainNumber,type,passengerNumber,year都!=null
	// Safety from rep exposure
	// String是不可变数据类型
	private void checkRep() {
		assert trainNumber != null;
		assert type != null;
		assert passengerNumber != null;
		assert year != null;
	}

	/**
	 * 新建一个列车对象
	 * 
	 * @param trainNumber     车厢编号
	 * @param type            列车类型
	 * @param passengerNumber 座位数
	 * @param year            车龄
	 */
	public TrainResource(String trainNumber, String type, String passengerNumber, String year) {
		this.trainNumber = trainNumber;
		this.type = type;
		this.passengerNumber = passengerNumber;
		this.year = year;
		checkRep();
	}

	/**
	 * 获取车厢编号
	 * 
	 * @return 车厢编号
	 */
	public String trainNumber() {
		checkRep();
		return this.trainNumber;
	}

	/**
	 * 获取车厢类型
	 * 
	 * @return 车厢类型
	 */
	public String type() {
		checkRep();
		return this.type;
	}

	/**
	 * 获取车厢座位数
	 * 
	 * @return 车厢座位数
	 */
	public String passengerNumber() {
		checkRep();
		return this.passengerNumber;
	}

	/**
	 * 获取车厢车龄
	 * 
	 * @return 车厢车龄
	 */
	public String year() {
		checkRep();
		return this.year;
	}

	/**
	 * Override equals方法
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
	 * Override hashcode方法
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
