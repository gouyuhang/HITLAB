package Location;

//immutable
/**
 * 
 * 实现教室的一个ADT
 *
 */
public class CourseLocation implements Location {
	private String name;
	private boolean sharing;

	// AF
	// name代表教室的名称,sharing代表位置信息是否以共享
	// RI
	// name！=null sharing==false
	// Safety from rep exposure
	// String boolean都是不可变数据类型
	private void checkRep() {
		assert name != null;
		assert sharing == false;
	}

	/**
	 * 新建一个教室
	 * 
	 * @param name    教室的名称
	 * @param sharing 教室是否共享
	 */
	public CourseLocation(String name, boolean sharing) {
		this.name = name;
		this.sharing = sharing;
		checkRep();
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
		return null;
	}

	@Override
	public String lat() {
		return null;
	}

	/**
	 * Override equals方法
	 */
	@Override
	public boolean equals(Object thatone) {
		if (!(thatone instanceof CourseLocation)) {
			return false;
		}
		CourseLocation that = (CourseLocation) thatone;
		return this.name.equals(that.name);
	}

	/**
	 * Override hashcode方法
	 */
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + this.name.hashCode();
		return h;
	}

}
