package Location;
/**
 * 
 * 实现位置信息的接口
 *
 */
public interface Location {
	/**
	 * 获取一个火车站/飞机站/教师对象
	 * @param type 位置的类型
	 * @param name 位置的名称
	 * @param lng 位置的经度
	 * @param lat 位置的纬度
	 * @param sharing 位置是否可以共享
	 * @return 具体的位置对象
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
	 * 获取位置的名称
	 * @return 位置的名称
	 */
	public String getName();
	/**
	 * 位置是否共享
	 * @return 是/否 共享
	 */
	public boolean share();
	/**
	 * 获取位置的经度
	 * @return 位置的精度
	 */
	public String lng();
	/**
	 * 获取位置的纬度
	 * @return 位置的纬度
	 */
	public String lat();
	
}
