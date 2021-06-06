package Resource;
//immutable
/**
 * 描述飞机的一个ADT
 * @author gyh
 *
 */
public class FlightResource {
	private String planeNumber;
	private String planeType;
	private String seatNumber;
	private String planeAge;
	//AF
	//planeNumber,planeType,seatNumber,planeAge分别代表
	//一架飞机的飞机编号，飞机类型，座位数,机龄
	//RI
	//planeNumber,planeType,seatNumber,planeAge都!=null
	//Safety from rep exposure
	//String是不可变数据类型
	private void checkRep() {
		assert planeNumber!=null;
		assert planeType!=null;
		assert seatNumber!=null;
		assert planeAge!=null;
	}
	/**新建一个飞机对象
	 * @param planeNumber 飞机编号
	 * @param planeType 飞机类型
	 * @param seatNumber 飞机座位号
	 * @param planeAge 飞机机龄
	 */
	public FlightResource(String planeNumber,String planeType,String seatNumber,String planeAge) {
		this.planeNumber=planeNumber;
		this.planeType=planeType;
		this.seatNumber=seatNumber;
		this.planeAge=planeAge;
		checkRep();
	}
	/**
	 * 获取飞机编号
	 * @return 飞机的编号
	 */
	public String planeNumber() {
		checkRep();
		return this.planeNumber;
	}
	/**
	 * 获取飞机的类型
	 * @return 飞机类型
	 */
	public String planeType() {
		checkRep();
		return this.planeType;
	}
	/**
	 * 获取飞机的座位数
	 * @return 飞机的座位数
	 */
	public String seatNumber() {
		checkRep();
		return this.seatNumber;
	}
	/**
	 * 获取飞机的机龄
	 * @return 飞机的机龄
	 */
	public String planeAge() {
		return this.planeAge;				
	}
	/**
	 * Override equals方法
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
	 * Override hashcode方法
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
