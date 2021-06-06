package P3;

/**
 * 
 * �����ϵ�λ�ã��ú�������(x,y)��ʾ immutable
 */

public class Position {
	private int xPosition;
	private int yPosition;

	// AF
	// xPosition����������λ�õĺ�����
	// yPosition����������λ�õ�������
	// RI
	// xPosition yPosition������ȡֵ����Ϊ����������Ϸ�λ��
	// Safety from rep exposure
	// int���ɱ��������ͣ���private
	/**
	 * create a new position
	 * 
	 * @param x λ�õĺ����� >=0,<=18
	 * @param y λ�õ�������>=0,<=18
	 */
	public Position(int x, int y) {
		this.xPosition = x;
		this.yPosition = y;
		checkRep();
	}

	// checkRep()
	private void checkRep() {
	}

	/**
	 * ��ѯλ�õĺ�����
	 * 
	 * @return λ�ú�����
	 */
	public int xCord() {
		checkRep();
		return this.xPosition;
	}

	/**
	 * ��ѯλ�õ�������
	 * 
	 * @return λ��������
	 */
	public int yCord() {
		checkRep();
		return this.yPosition;
	}

	/**
	 * Override equals����
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Position))
			return false;
		Position that = (Position) o;
		checkRep();
		return this.xPosition == that.xPosition & this.yPosition == that.yPosition;
	}

	/**
	 * Override hashCode����
	 */
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + this.xPosition;
		h = 31 * h + this.yPosition;
		checkRep();
		return h;
	}
}
