package P3;

/**
 * 
 * mutable �������ϵ����ӽ��в���
 */
public class Action {
	private Board gameboard;

	// AF
	// gameboard������Ҫ���в���������
	// RI
	// gameboard!=null
	// safety from rep exposure
	// private����
	/**
	 * �½������̵Ĳ���
	 * 
	 * @param gameboard ��Ҫ���������� gameboard!=null
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
	 * Χ��ķ��Ӳ���
	 * 
	 * @param p      ���õ�λ��
	 * @param color  ���ӵ���ɫ ֻ��Ϊ�׺�
	 * @param player ���ӵ���ҵ����֣���Ϊ��
	 * @return ���Ӳ��жϴ˴η��Ӳ����Ƿ���ȷ
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
	 * ����������ƶ����Ӳ���
	 * 
	 * @param p1     ��Ҫ�ƶ����ӵ���ʼλ��
	 * @param p2     ��Ҫ�ƶ����ӵ���ֹλ��
	 * @param player �����ƶ����ӵ���ҵ����� ���ֲ���Ϊ��
	 * @return �ƶ����Ӳ��жϲ����Ƿ�ɹ�
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
	 * Χ���е����Ӳ���
	 * 
	 * @param p1     ��Ҫ����ӵ�����
	 * @param color  ��Ҫ�����ӵ���ɫ��ֻ���ǰ׺�
	 * @param player ������ҵ����� ����Ϊ��
	 * @return ���Ӳ��жϲ����Ƿ�ɹ�
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
	 * ���������еĳ��Ӳ���
	 * 
	 * @param p1     ������Ҫ�ƶ������ӵ�����
	 * @param p2     ��Ҫ�Ե��Է����ӵ�����
	 * @param player ���г��Ӳ������������
	 * @return ���Ӳ��ж��Ƿ������ȷ
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
