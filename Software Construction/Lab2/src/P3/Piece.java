package P3;

import java.util.*;

/**
 * �����ϵ�ÿ������ immutable
 */
public class Piece {
	private String typeName;
	private String playerName;

	// AF
	// typeName ���ӵ���������,�׺�(go),��,��(chess)
	// playerName ����������һλ���
	// RI
	// typeName!=null
	// playerName!=null
	// safety from rep exposure
	// private ����StringΪ���ɱ���������
	/**
	 * create a new piece
	 * 
	 * @param name1 ���ӵ����� name1!=null
	 * @param name2 ���ӵĹ����� name2!=null
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
	 * @return ���ӵ�����
	 */
	public String getTypeName() {
		checkRep();
		return this.typeName;
	}

	/**
	 * @return ���ӵ�ӵ����
	 */
	public String getPlayerName() {
		checkRep();
		return this.playerName;
	}

	/**
	 * Override equals����
	 */
	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Piece))
			return false;
		Piece that = (Piece) o;
		return this.typeName.equals(that.typeName) & this.playerName.equals(that.playerName);
	}

	/**
	 * Override hashCode����
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.typeName, this.playerName);
	}
}
