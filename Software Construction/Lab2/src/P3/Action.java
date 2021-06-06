package P3;

/**
 * 
 * mutable 对棋盘上的棋子进行操作
 */
public class Action {
	private Board gameboard;

	// AF
	// gameboard代表需要进行操作的棋盘
	// RI
	// gameboard!=null
	// safety from rep exposure
	// private属性
	/**
	 * 新建对棋盘的操作
	 * 
	 * @param gameboard 想要操作的棋盘 gameboard!=null
	 */
	public Action(Board gameboard) {
		this.gameboard = gameboard;
		checkRep();
	}

	// checkRep
	private void checkRep() {
		assert this.gameboard != null;
	}

	/**
	 * 围棋的放子操作
	 * 
	 * @param p      放置的位置
	 * @param color  棋子的颜色 只能为白黑
	 * @param player 放子的玩家的名字，不为空
	 * @return 放子并判断此次放子操作是否正确
	 */
	public boolean put(Position p, String color, String player) {
		if (p.xCord() < 0 || p.xCord() >= this.gameboard.size() || p.yCord() < 0
				|| p.yCord() >= this.gameboard.size()) {
			return false;
		}
		if (!this.gameboard.getMap().get(p.xCord() * this.gameboard.size() + p.yCord()).getPlayerName()
				.equals("common")) {
			return false;
		}
		this.gameboard.remove(p);
		this.gameboard.set(p, new Piece(color, player));
		checkRep();
		return true;
	}

	/**
	 * 国际象棋的移动棋子操作
	 * 
	 * @param p1     需要移动棋子的起始位置
	 * @param p2     需要移动棋子的终止位置
	 * @param player 进行移动棋子的玩家的名字 名字不能为空
	 * @return 移动棋子并判断操作是否成功
	 */
	public boolean move(Position p1, Position p2, String player) {
		if (p1.xCord() < 0 || p1.xCord() >= this.gameboard.size() || p1.yCord() < 0
				|| p1.yCord() >= this.gameboard.size()) {
			return false;
		}
		if (p2.xCord() < 0 || p2.xCord() >= this.gameboard.size() || p2.yCord() < 0
				|| p2.yCord() >= this.gameboard.size()) {
			return false;
		}
		if (!this.gameboard.getMap().get(p1.xCord() * this.gameboard.size() + p1.yCord()).getPlayerName()
				.equals(player)) {
			return false;
		}
		if (!this.gameboard.getMap().get(p2.xCord() * this.gameboard.size() + p2.yCord()).getPlayerName()
				.equals("common")) {
			return false;
		}
		this.gameboard.remove(p2);
		this.gameboard.set(p2, new Piece(
				this.gameboard.getMap().get(p1.xCord() * this.gameboard.size() + p1.yCord()).getTypeName(), player));
		this.gameboard.remove(p1);
		return true;
	}

	/**
	 * 围棋中的提子操作
	 * 
	 * @param p1     想要提的子的坐标
	 * @param color  想要放置子的颜色，只能是白黑
	 * @param player 提子玩家的名字 不能为空
	 * @return 提子并判断操作是否成功
	 */
	public boolean goEat(Position p1, String color, String player) {
		if (p1.xCord() < 0 || p1.xCord() >= this.gameboard.size() || p1.yCord() < 0
				|| p1.yCord() >= this.gameboard.size()) {
			return false;
		}
		if (this.gameboard.getMap().get(p1.xCord() * this.gameboard.size() + p1.yCord()).getPlayerName()
				.equals("common")
				|| this.gameboard.getMap().get(p1.xCord() * this.gameboard.size() + p1.yCord()).getPlayerName()
						.equals(player)) {
			return false;
		}
		this.gameboard.remove(p1);
		this.gameboard.set(p1, new Piece(color, player));
		return true;
	}

	/**
	 * 国际象棋中的吃子操作
	 * 
	 * @param p1     己方想要移动的棋子的坐标
	 * @param p2     想要吃掉对方棋子的坐标
	 * @param player 进行吃子操作的玩家名字
	 * @return 吃子并判断是否操作正确
	 */
	public boolean chessEat(Position p1, Position p2, String player) {
		if (p1.xCord() < 0 || p1.xCord() >= this.gameboard.size() || p1.yCord() < 0
				|| p1.yCord() >= this.gameboard.size()) {
			return false;
		}
		if (p2.xCord() < 0 || p2.xCord() >= this.gameboard.size() || p2.yCord() < 0
				|| p2.yCord() >= this.gameboard.size()) {
			return false;
		}
		if (!this.gameboard.getMap().get(p1.xCord() * this.gameboard.size() + p1.yCord()).getPlayerName()
				.equals(player)) {
			return false;
		}
		if (this.gameboard.getMap().get(p2.xCord() * this.gameboard.size() + p2.yCord()).getPlayerName()
				.equals("common")
				|| this.gameboard.getMap().get(p2.xCord() * this.gameboard.size() + p2.yCord()).getPlayerName()
						.equals(player)) {
			return false;
		}
		this.gameboard.remove(p2);
		this.gameboard.set(p2, new Piece(
				this.gameboard.getMap().get(p1.xCord() * this.gameboard.size() + p1.yCord()).getTypeName(), player));
		this.gameboard.remove(p1);
		return true;
	}
}
