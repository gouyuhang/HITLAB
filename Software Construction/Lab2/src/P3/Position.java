package P3;

/**
 * 
 * 棋盘上的位置，用横纵坐标(x,y)表示 immutable
 */

public class Position {
	private int xPosition;
	private int yPosition;

	// AF
	// xPosition代表棋盘上位置的横坐标
	// yPosition代表棋盘上位置的纵坐标
	// RI
	// xPosition yPosition可随意取值，因为不仅仅代表合法位置
	// Safety from rep exposure
	// int不可变数据类型，且private
	/**
	 * create a new position
	 * 
	 * @param x 位置的横坐标 >=0,<=18
	 * @param y 位置的纵坐标>=0,<=18
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
	 * 查询位置的横坐标
	 * 
	 * @return 位置横坐标
	 */
	public int xCord() {
		checkRep();
		return this.xPosition;
	}

	/**
	 * 查询位置的纵坐标
	 * 
	 * @return 位置纵坐标
	 */
	public int yCord() {
		checkRep();
		return this.yPosition;
	}

	/**
	 * Override equals方法
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
	 * Override hashCode方法
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
