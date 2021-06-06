package P3;

import java.util.*;

/**
 * 一个玩家的信息 mutable
 */
public class Player {
	private String playerName;
	private List<String> playerHistory;

	// AF
	// playerName代表玩家的姓名
	// playerHistory存储的是玩家的所有操作
	// RI
	// playerName!=null
	// playerHistory!=null
	// safety from rep exposure
	// unmodified
	/**
	 * create a new player
	 * 
	 * @param name 玩家的姓名，玩家的姓名不为空
	 */
	public Player(String name) {
		this.playerName = name;
		this.playerHistory = new ArrayList<>();
		checkRep();
	}

	// checkRep
	private void checkRep() {
		assert this.playerName != null;
		assert this.playerHistory != null;
	}

	/**
	 * 
	 * @return 玩家的姓名
	 */
	public String getPlayerName() {
		checkRep();
		return this.playerName;
	}

	/**
	 * 
	 * @return 玩家所有操作的历史
	 */
	public List<String> getPlayerHistory() {
		return Collections.unmodifiableList(this.playerHistory);
	}

	/**
	 * 添加玩家操作记录
	 * 
	 * @param s 玩家的一次操作记录 该操作应为合法操作
	 */
	public void addHistory(String s) {
		this.playerHistory.add(s);
		checkRep();
	}
}
