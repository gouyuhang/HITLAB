package Location;

//immutable
/**
 * 
 * ʵ�ֽ��ҵ�һ��ADT
 *
 */
public class CourseLocation implements Location {
	private String name;
	private boolean sharing;

	// AF
	// name������ҵ�����,sharing����λ����Ϣ�Ƿ��Թ���
	// RI
	// name��=null sharing==false
	// Safety from rep exposure
	// String boolean���ǲ��ɱ���������
	private void checkRep() {
		assert name != null;
		assert sharing == false;
	}

	/**
	 * �½�һ������
	 * 
	 * @param name    ���ҵ�����
	 * @param sharing �����Ƿ���
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
	 * Override equals����
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
	 * Override hashcode����
	 */
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + this.name.hashCode();
		return h;
	}

}
