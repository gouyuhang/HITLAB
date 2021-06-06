package P3;

import java.util.*;

/**
 * mutable ������Ϸ�е�����
 */
public class Board {
	private int size;
	private Map<Integer, Piece> boardMap = new HashMap<>();

	// AF
	// size�������̵Ĵ�С
	// broadMap��������λ�ñ��������ӵ�ӳ�䣬������*size+������=integer
	// RI
	// size��8����19
	// boardMap!=null;
	// safety from rep exposure
	// boardMap����ʽ����
	// private����
	/**
	 * create a new board
	 * 
	 * @param gameType ��Ϸ���� ֻ��Ϊ"go"����"chess"
	 * @param player1  ���1���� ��Ϊ��
	 * @param player2  ���2���� ��Ϊ��
	 */
	public Board(String gameType, String player1, String player2) {
		if (gameType.equals("go")) {
			this.size = 19;
			for (int i = 0; i < 19; ++i) {
				for (int j = 0; j < 19; ++j) {
					boardMap.put(i * 19 + j, new Piece("E", "common"));
				}
			}
		}

		if (gameType.equals("chess")) {
			this.size = 8;
			for (int i = 0; i < 8; ++i) {
				for (int j = 0; j < 8; ++j) {
					if (j == 1) {
						boardMap.put(i * 8 + j, new Piece("P", player1));
						continue;
					}
					if (j == 6) {
						boardMap.put(i * 8 + j, new Piece("p", player2));
						continue;
					}
					if ((i == 0 && j == 0) || (j == 0 && i == 7)) {
						boardMap.put(i * 8 + j, new Piece("R", player1));
						continue;
					}
					if ((j == 7 && i == 0) || (j == 7 && i == 7)) {
						boardMap.put(i * 8 + j, new Piece("r", player2));
						continue;
					}
					if ((j == 0 && i == 1) || (j == 0 && i == 6)) {
						boardMap.put(i * 8 + j, new Piece("N", player1));
						continue;
					}
					if ((j == 7 && i == 1) || (j == 7 && i == 6)) {
						boardMap.put(i * 8 + j, new Piece("n", player2));
						continue;
					}
					if ((j == 0 && i == 2) || (j == 0 && i == 5)) {

						boardMap.put(i * 8 + j, new Piece("B", player1));
						continue;
					}
					if ((j == 7 && i == 2) || (j == 7 && i == 5)) {
						boardMap.put(i * 8 + j, new Piece("b", player2));
						continue;
					}
					if (j == 0 && i == 3) {
						boardMap.put(i * 8 + j, new Piece("Q", player1));
						continue;
					}
					if (j == 7 && i == 3) {
						boardMap.put(i * 8 + j, new Piece("q", player2));
						continue;
					}
					if (j == 0 && i == 4) {
						boardMap.put(i * 8 + j, new Piece("K", player1));
						continue;
					}
					if (j == 7 && i == 4) {
						boardMap.put(i * 8 + j, new Piece("k", player2));
						continue;
					}
					boardMap.put(i * 8 + j, new Piece("E", "common"));
				}
			}
		}
		checkRep();
	}

	// checkRep
	private void checkRep() {
		assert this.size == 8 || this.size == 19;
		assert this.boardMap != null;
	}

	/**
	 * 
	 * @return ������λ�������ӵ�ӳ���ϵ
	 */
	public Map<Integer, Piece> getMap() {
		checkRep();
		return Collections.unmodifiableMap(this.boardMap);
	}

	/**
	 * �Ƴ������ϵ�һ������
	 * 
	 * @param p ��Ҫ�Ƴ������ӵ�����
	 */
	public void remove(Position p) {
		boardMap.remove(p.xCord() * size + p.yCord());
		boardMap.put(p.xCord() * size + p.yCord(), new Piece("E", "common"));
		checkRep();
	}

	/**
	 * �������Ϸ���һ������
	 * 
	 * @param p  ��Ҫ���õ����ӵ�λ��
	 * @param pi ��Ҫ���õ�����
	 */
	public void set(Position p, Piece pi) {
		boardMap.put(p.xCord() * size + p.yCord(), pi);
		checkRep();
	}

	/**
	 * 
	 * @return ���̵Ĵ�С
	 */
	public int size() {
		checkRep();
		return this.size;
	}

	/**
	 * ��ӡ������Ϣ
	 */
	public void printBoard() {
		for (int j = this.size - 1; j > -1; --j) {
			for (int i = 0; i < this.size; ++i) {
				System.out.print(boardMap.get(i * this.size + j).getTypeName() + " ");
			}
			System.out.print("\n");
		}
		checkRep();
	}
}
