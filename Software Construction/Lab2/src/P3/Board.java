package P3;

import java.util.*;

/**
 * mutable 棋类游戏中的棋盘
 */
public class Board {
	private int size;
	private Map<Integer, Piece> boardMap = new HashMap<>();

	// AF
	// size代表棋盘的大小
	// broadMap代表棋盘位置编码与棋子的映射，横坐标*size+纵坐标=integer
	// RI
	// size是8或者19
	// boardMap!=null;
	// safety from rep exposure
	// boardMap防御式拷贝
	// private属性
	/**
	 * create a new board
	 * 
	 * @param gameType 游戏类型 只能为"go"或者"chess"
	 * @param player1  玩家1名字 不为空
	 * @param player2  玩家2名字 不为空
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
	 * @return 棋盘上位置与棋子的映射关系
	 */
	public Map<Integer, Piece> getMap() {
		checkRep();
		return Collections.unmodifiableMap(this.boardMap);
	}

	/**
	 * 移除棋盘上的一个棋子
	 * 
	 * @param p 想要移除的棋子的坐标
	 */
	public void remove(Position p) {
		boardMap.remove(p.xCord() * size + p.yCord());
		boardMap.put(p.xCord() * size + p.yCord(), new Piece("E", "common"));
		checkRep();
	}

	/**
	 * 在棋盘上放置一个棋子
	 * 
	 * @param p  想要放置的棋子的位置
	 * @param pi 想要放置的棋子
	 */
	public void set(Position p, Piece pi) {
		boardMap.put(p.xCord() * size + p.yCord(), pi);
		checkRep();
	}

	/**
	 * 
	 * @return 棋盘的大小
	 */
	public int size() {
		checkRep();
		return this.size;
	}

	/**
	 * 打印棋盘信息
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
