package Resource;
//immutable
/**
 * 
 * ������ʦ��һ��ADT
 *
 */
public class CourseResource {
	private String id;
	private String name;
	private String sex;
	private String position;
	//AF
	//id,name,sex,position�ֱ�����ʦ��id���������Ա�ְλ
	//RI
	//id,name,sex,position��!=null
	//Safety from rep exposure
	//String�ǲ��ɱ���������
	private void checkRep() {
		assert id!=null;
		assert name!=null;
		assert sex!=null;
		assert position!=null;
	}
	/**
	 * �½�һ����ʦ����
	 * @param id ��ʦ��id
	 * @param name ��ʦ������
	 * @param sex ��ʦ���Ա�
	 * @param position ��ʦ��ְλ
	 */
	public CourseResource(String id,String name,String sex,String position) {
		this.id=id;
		this.name=name;
		this.sex=sex;
		this.position=position;
		checkRep();
	}
	/**
	 * ��ȡ��ʦ��id
	 * @return ��ʦ��id
	 */
	public String id() {
		checkRep();
		return this.id;
	}
	/**
	 * ��ȡ��ʦ������
	 * @return ��ʦ������
	 */
	public String name() {
		checkRep();
		return this.name;
	}
	/**
	 * ��ȡ��ʦ���Ա�
	 * @return ��ʦ���Ա�
	 */
	public String sex() {
		checkRep();
		return this.sex;
	}
	/**
	 * ��ȡ��ʦ��ְλ
	 * @return ��ʦ��ְλ
	 */
	public String position() {
		checkRep();
		return this.position;
	}
	/**
	 * Override equals����
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
	 * Override hashcode����
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
