package P3;

import java.util.*;

/**
 * һ����ҵ���Ϣ mutable
 */
public class Player {
	private String playerName;
	private List<String> playerHistory;

	// AF
	// playerName������ҵ�����
	// playerHistory�洢������ҵ����в���
	// RI
	// playerName!=null
	// playerHistory!=null
	// safety from rep exposure
	// unmodified
	/**
	 * create a new player
	 * 
	 * @param name ��ҵ���������ҵ�������Ϊ��
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
	 * @return ��ҵ�����
	 */
	public String getPlayerName() {
		checkRep();
		return this.playerName;
	}

	/**
	 * 
	 * @return ������в�������ʷ
	 */
	public List<String> getPlayerHistory() {
		return Collections.unmodifiableList(this.playerHistory);
	}

	/**
	 * �����Ҳ�����¼
	 * 
	 * @param s ��ҵ�һ�β�����¼ �ò���ӦΪ�Ϸ�����
	 */
	public void addHistory(String s) {
		this.playerHistory.add(s);
		checkRep();
	}
}
