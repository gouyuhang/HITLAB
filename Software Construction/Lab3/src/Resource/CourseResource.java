package Resource;
//immutable
/**
 * 
 * 描述老师的一个ADT
 *
 */
public class CourseResource {
	private String id;
	private String name;
	private String sex;
	private String position;
	//AF
	//id,name,sex,position分别代表教师的id，姓名，性别，职位
	//RI
	//id,name,sex,position都!=null
	//Safety from rep exposure
	//String是不可变数据类型
	private void checkRep() {
		assert id!=null;
		assert name!=null;
		assert sex!=null;
		assert position!=null;
	}
	/**
	 * 新建一个教师对象
	 * @param id 教师的id
	 * @param name 教师的姓名
	 * @param sex 教师的性别
	 * @param position 教师的职位
	 */
	public CourseResource(String id,String name,String sex,String position) {
		this.id=id;
		this.name=name;
		this.sex=sex;
		this.position=position;
		checkRep();
	}
	/**
	 * 获取教师的id
	 * @return 教师的id
	 */
	public String id() {
		checkRep();
		return this.id;
	}
	/**
	 * 获取教师的名字
	 * @return 教师的名字
	 */
	public String name() {
		checkRep();
		return this.name;
	}
	/**
	 * 获取教师的性别
	 * @return 教师的性别
	 */
	public String sex() {
		checkRep();
		return this.sex;
	}
	/**
	 * 获取教师的职位
	 * @return 教师的职位
	 */
	public String position() {
		checkRep();
		return this.position;
	}
	/**
	 * Override equals方法
	 */
	@Override
	public boolean equals(Object thatone) {
		if (!(thatone instanceof CourseResource))
			return false;
		CourseResource that = (CourseResource) thatone;
		if(!that.id.equals(this.id)) {
			return false;
		}
		if(!that.name.equals(this.name)) {
			return false;
		}
		if(!that.sex.equals(this.sex)) {
			return false;
		}
		if(!that.position.equals(this.position)) {
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
		h=31*h+this.id.hashCode();
		h=31*h+this.name.hashCode();
		h=31*h+this.sex.hashCode();
		h=31*h+this.position.hashCode();
		return h;
	}
}
