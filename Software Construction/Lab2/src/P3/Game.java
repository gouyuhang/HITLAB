package P3;

import java.util.*;

/**
 * 一局棋盘类比赛
 */
public class Game {
	private String gameType;
	private Board board;
	private Player p1;
	private Player p2;
	private Action action;

	// AF
	// gameType代表游戏类型
	// board代表棋盘 p1 p2代表两个玩家
	// action代表对本次比赛棋盘的操作
	// RI
	// gameType board p1 p2 action!=null
	// safety from rep exposure
	// 全部为private属性
	/**
	 * create a new game
	 * 
	 * @param game 游戏类型名称 只能为"chess"或者"go"
	 * @param p1   玩家1名称 不能为空
	 * @param p2   玩家2名称 不能为空
	 */
	public Game(String game, String p1, String p2) {
		this.gameType = game;
		this.p1 = new Player(p1);
		this.p2 = new Player(p2);
		this.board = new Board(game, p1, p2);
		this.action = new Action(this.board);
		checkRep();
	}

	// checkRep
	private void checkRep() {
		assert this.gameType != null;
		assert this.p1 != null;
		assert this.p2 != null;
		assert this.board != null;
		assert this.action != null;
	}

	/**
	 * 每个玩家游戏的多种功能的实现
	 * 
	 * @param in            从键盘读到的信息
	 * @param cmd           玩家本回合选择的操作的编码
	 * @param currentPlayer 当前玩家的名字
	 * @return 进行本次操作并判断是否合法
	 */
	public boolean cmd(Scanner in, int cmd, String currentPlayer) {
		switch (cmd) {
		case 0:
			System.out.println("菜单：");
			if (this.gameType.equals("chess")) {
				System.out.println("想要移动棋子请按1");
				System.out.println("想要吃子请按2");
			}
			if (this.gameType.equals("go")) {
				System.out.println("想要放子请按1");
				System.out.println("想要提子请按2");
			}
			System.out.println("查看棋盘请按3");
			System.out.println("查询特定坐标请按4");
			System.out.println("计算双方棋子总数请按5");
			System.out.println("跳过本回合请按6");
			System.out.println("退出游戏请输入end");
			return false;
		case 1:// move,put
			if (this.gameType.equals("chess")) {
				System.out.print("请输入移动棋子起始点，终止点坐标:");
				int a = in.nextInt();
				int b = in.nextInt();
				int c = in.nextInt();
				int d = in.nextInt();
				if (action.move(new Position(a, b), new Position(c, d), currentPlayer)) {
					if (p1.getPlayerName().equals(currentPlayer)) {
						p1.addHistory(currentPlayer + " move " + a + "," + b + " to " + c + "," + d + "\n");
						System.out.println(this.p1.getPlayerName() + "操作成功");
					}
					if (p2.getPlayerName().equals(currentPlayer)) {
						p2.addHistory(currentPlayer + " move " + a + "," + b + " to " + c + "," + d + "\n");
						System.out.println(this.p2.getPlayerName() + "操作成功");
					}
				} else {
					System.out.println("非法移动，请重新思考");
					return false;
				}
			}
			if (this.gameType.equals("go")) {
				System.out.print("请输入放子坐标:");
				int a = in.nextInt();
				int b = in.nextInt();
				if (p1.getPlayerName().equals(currentPlayer)) {
					if (this.action.put(new Position(a, b), "B", currentPlayer)) {
						p1.addHistory(currentPlayer + " put " + a + "," + b + "\n");
						System.out.println(this.p1.getPlayerName() + "操作成功");
					} else {
						System.out.println("非法放子，请重新思考");
						return false;
					}

				}
				if (p2.getPlayerName().equals(currentPlayer)) {
					if (action.put(new Position(a, b), "W", currentPlayer)) {
						p2.addHistory(currentPlayer + " put " + a + "," + b + "\n");
						System.out.println(p2.getPlayerName() + "操作成功");
					} else {
						System.out.println("非法放子，请重新思考");

						return false;
					}

				}
			}
			break;
		case 2:// eat
			if (this.gameType.equals("go")) {
				System.out.print("请输入提子坐标:");
				int a = in.nextInt();
				int b = in.nextInt();
				if (p1.getPlayerName().equals(currentPlayer)) {
					if (action.goEat(new Position(a, b), "B", currentPlayer)) {
						p1.addHistory(currentPlayer + " eat " + a + "," + b + "\n");
						System.out.println(p1.getPlayerName() + "操作成功");
					} else {
						System.out.println("非法放子，请重新思考");
						return false;
					}

				}
				if (p2.getPlayerName().equals(currentPlayer)) {
					if (action.goEat(new Position(a, b), "W", currentPlayer)) {
						p2.addHistory(currentPlayer + " eat " + a + "," + b + "\n");
						System.out.println(p2.getPlayerName() + "操作成功");
					} else {
						System.out.println("非法放子，请重新思考");
						return false;
					}

				}
			}
			if (this.gameType.equals("chess")) {
				System.out.print("请输入吃棋子起始点，终止点坐标:");
				int a = in.nextInt();
				int b = in.nextInt();
				int c = in.nextInt();
				int d = in.nextInt();
				if (action.chessEat(new Position(a, b), new Position(c, d), currentPlayer)) {
					if (p1.getPlayerName().equals(currentPlayer)) {
						p1.addHistory(currentPlayer + " eat " + c + "," + d + " from " + a + "," + b + "\n");
						System.out.println(p1.getPlayerName() + "操作成功");
					}
					if (p2.getPlayerName().equals(currentPlayer)) {
						p2.addHistory(currentPlayer + " eat " + c + "," + d + " to " + a + "," + b + "\n");
						System.out.println(p2.getPlayerName() + "操作成功");
					}
				} else {
					System.out.println("非法移动，请重新思考");
					return false;
				}
			}
			break;
		case 3:// boardcheck
			this.board.printBoard();
			if (p1.getPlayerName().equals(currentPlayer)) {
				p1.addHistory(currentPlayer + " check the board" + "\n");
			}
			if (p2.getPlayerName().equals(currentPlayer)) {
				p2.addHistory(currentPlayer + " check the board" + "\n");
			}
			break;
		case 4:// search
			System.out.print("请输入想要搜索的坐标:");
			int a = in.nextInt();
			int b = in.nextInt();
			if (a >= 0 && a < this.board.size() && b >= 0 && b < this.board.size()) {
				this.serach(new Position(a, b));
				if (this.p1.getPlayerName().equals(currentPlayer)) {
					p1.addHistory(currentPlayer + " search the board " + a + "," + b + "\n");
				}
				if (this.p2.getPlayerName().equals(currentPlayer)) {
					p2.addHistory(currentPlayer + " search the board " + a + "," + b + "\n");
				}
			} else {
				System.out.println("坐标非法，请重输");
				return false;
			}
			break;
		case 5:// sumup
			this.sumUp();
			if (p1.getPlayerName().equals(currentPlayer)) {
				p1.addHistory(currentPlayer + " sum up the board" + "\n");
			}
			if (p2.getPlayerName().equals(currentPlayer)) {
				p2.addHistory(currentPlayer + " sum up the board" + "\n");
			}
			break;
		case 6:// skip
			if (p1.getPlayerName().equals(currentPlayer)) {
				p1.addHistory(currentPlayer + " skip" + "\n");
			}
			if (p2.getPlayerName().equals(currentPlayer)) {
				p2.addHistory(currentPlayer + " skip" + "\n");
			}
			break;

		}
		checkRep();
		return true;
	}

	/**
	 * 计算双方棋子总数
	 */
	public void sumUp() {
		int count1 = 0;
		int count2 = 0;
		for (Piece pi : this.board.getMap().values()) {
			if (pi.getPlayerName().equals(this.p1.getPlayerName())) {
				count1++;
			}
			if (pi.getPlayerName().equals(this.p2.getPlayerName())) {
				count2++;
			}
		}
		System.out.println(this.p1.getPlayerName() + "剩余棋子数：" + count1);
		System.out.println(this.p2.getPlayerName() + "剩余棋子数：" + count2);
	}

	/**
	 * 打印双方走棋历史
	 */
	public void printHistory() {
		System.out.println(this.p1.getPlayerName() + "的下棋历史：");
		for (String s1 : this.p1.getPlayerHistory()) {
			System.out.print(s1);
		}
		System.out.println(this.p2.getPlayerName() + "的下棋历史：");
		for (String s2 : this.p2.getPlayerHistory()) {
			System.out.print(s2);
		}
	}

	/**
	 * 对某一位置的棋子进行搜索
	 * 
	 * @param p 想要搜索的位置坐标
	 */
	public void serach(Position p) {
		System.out.println("该点棋子类型：" + this.board.getMap().get(p.xCord()*this.board.size() + p.yCord()).getTypeName());
		System.out.println("该点棋子拥有者：" + this.board.getMap().get(p.xCord()*this.board.size() + p.yCord()).getPlayerName());
	}
}
