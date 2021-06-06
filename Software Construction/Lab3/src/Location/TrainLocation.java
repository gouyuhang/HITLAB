package Location;
//immutable
/**
 * 
 * 描述火车站的ADT
 *
 */
public class TrainLocation implements Location{
	private String name;
	private String lng;
	private String lat;
	private boolean sharing;
	//AF
	//name代表飞机站名称，lng代表飞机场的经度，lat代表火车站的纬度
	//sharing代表火车站是否可共享
	//RI
	//name！=null
	//sharing=true
	//Safety from rep exposure
	//String boolean都是不可变数据类型
	private void checkRep() {
		assert name!=null;
		assert sharing==true;
	}
	/**
	 * 新建一个火车站对象
	 * @param name 火车站的名称
	 * @param lng 火车站经度
	 * @param lat 火车站纬度
	 * @param sharing 是否可共享
	 */
	public TrainLocation(String name,String lng,String lat,boolean sharing) {
		this.name=name;
		this.lng=lng;
		this.lat=lat;
		this.sharing=sharing;
	}
	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public boolean share() {
		checkRep();
		return this.sharing;
	}

	@Override
	public String lng() {
		checkRep();
		return this.lng;
	}

	@Override
	public String lat() {
		checkRep();
		return this.lat;
	}
	/**
	 * Override  equals方法
	 */
	@Override
	public boolean equals(Object thatone) {
		if(! (thatone instanceof TrainLocation)) {
			return false;
		}
		TrainLocation that=(TrainLocation) thatone;
		return this.name.equals(that.name);
	}
	/**
	 * Override hashcode方法
	 */
	@Override
	public int hashCode() {
		int h=1;
		h=31*h+this.name.hashCode();
		return h;
	}

}
