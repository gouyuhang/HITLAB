package P3;

import java.util.*;

/**
 * 棋盘上的每个棋子 immutable
 */
public class Piece {
	private String typeName;
	private String playerName;

	// AF
	// typeName 棋子的类型名称,白黑(go),王,后(chess)
	// playerName 棋子属于哪一位玩家
	// RI
	// typeName!=null
	// playerName!=null
	// safety from rep exposure
	// private 而且String为不可变数据类型
	/**
	 * create a new piece
	 * 
	 * @param name1 棋子的种类 name1!=null
	 * @param name2 棋子的归属者 name2!=null
	 */
	public Piece(String name1, String name2) {
		this.typeName = name1;
		this.playerName = name2;
		checkRep();
	}

	// checkRep
	private void checkRep() {
		assert this.typeName != null;
		assert this.playerName != null;
	}

	/**
	 * @return 棋子的种类
	 */
	public String getTypeName() {
		checkRep();
		return this.typeName;
	}

	/**
	 * @return 棋子的拥有者
	 */
	public String getPlayerName() {
		checkRep();
		return this.playerName;
	}

	/**
	 * Override equals方法
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Piece))
			return false;
		Piece that = (Piece) o;
		return this.typeName.equals(that.typeName) & this.playerName.equals(that.playerName);
	}

	/**
	 * Override hashCode方法
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.typeName, this.playerName);
	}
}
