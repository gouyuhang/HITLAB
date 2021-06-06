package P3;

import java.util.*;

/**
 * һ�����������
 */
public class Game {
	private String gameType;
	private Board board;
	private Player p1;
	private Player p2;
	private Action action;

	// AF
	// gameType������Ϸ����
	// board�������� p1 p2�����������
	// action����Ա��α������̵Ĳ���
	// RI
	// gameType board p1 p2 action!=null
	// safety from rep exposure
	// ȫ��Ϊprivate����
	/**
	 * create a new game
	 * 
	 * @param game ��Ϸ�������� ֻ��Ϊ"chess"����"go"
	 * @param p1   ���1���� ����Ϊ��
	 * @param p2   ���2���� ����Ϊ��
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
	 * ÿ�������Ϸ�Ķ��ֹ��ܵ�ʵ��
	 * 
	 * @param in            �Ӽ��̶�������Ϣ
	 * @param cmd           ��ұ��غ�ѡ��Ĳ����ı���
	 * @param currentPlayer ��ǰ��ҵ�����
	 * @return ���б��β������ж��Ƿ�Ϸ�
	 */
	public boolean cmd(Scanner in, int cmd, String currentPlayer) {
		switch (cmd) {
		case 0:
			System.out.println("�˵���");
			if (this.gameType.equals("chess")) {
				System.out.println("��Ҫ�ƶ������밴1");
				System.out.println("��Ҫ�����밴2");
			}
			if (this.gameType.equals("go")) {
				System.out.println("��Ҫ�����밴1");
				System.out.println("��Ҫ�����밴2");
			}
			System.out.println("�鿴�����밴3");
			System.out.println("��ѯ�ض������밴4");
			System.out.println("����˫�����������밴5");
			System.out.println("�������غ��밴6");
			System.out.println("�˳���Ϸ������end");
			return false;
		case 1:// move,put
			if (this.gameType.equals("chess")) {
				System.out.print("�������ƶ�������ʼ�㣬��ֹ������:");
				int a = in.nextInt();
				int b = in.nextInt();
				int c = in.nextInt();
				int d = in.nextInt();
				if (action.move(new Position(a, b), new Position(c, d), currentPlayer)) {
					if (p1.getPlayerName().equals(currentPlayer)) {
						p1.addHistory(currentPlayer + " move " + a + "," + b + " to " + c + "," + d + "\n");
						System.out.println(this.p1.getPlayerName() + "�����ɹ�");
					}
					if (p2.getPlayerName().equals(currentPlayer)) {
						p2.addHistory(currentPlayer + " move " + a + "," + b + " to " + c + "," + d + "\n");
						System.out.println(this.p2.getPlayerName() + "�����ɹ�");
					}
				} else {
					System.out.println("�Ƿ��ƶ���������˼��");
					return false;
				}
			}
			if (this.gameType.equals("go")) {
				System.out.print("�������������:");
				int a = in.nextInt();
				int b = in.nextInt();
				if (p1.getPlayerName().equals(currentPlayer)) {
					if (this.action.put(new Position(a, b), "B", currentPlayer)) {
						p1.addHistory(currentPlayer + " put " + a + "," + b + "\n");
						System.out.println(this.p1.getPlayerName() + "�����ɹ�");
					} else {
						System.out.println("�Ƿ����ӣ�������˼��");
						return false;
					}

				}
				if (p2.getPlayerName().equals(currentPlayer)) {
					if (action.put(new Position(a, b), "W", currentPlayer)) {
						p2.addHistory(currentPlayer + " put " + a + "," + b + "\n");
						System.out.println(p2.getPlayerName() + "�����ɹ�");
					} else {
						System.out.println("�Ƿ����ӣ�������˼��");

						return false;
					}

				}
			}
			break;
		case 2:// eat
			if (this.gameType.equals("go")) {
				System.out.print("��������������:");
				int a = in.nextInt();
				int b = in.nextInt();
				if (p1.getPlayerName().equals(currentPlayer)) {
					if (action.goEat(new Position(a, b), "B", currentPlayer)) {
						p1.addHistory(currentPlayer + " eat " + a + "," + b + "\n");
						System.out.println(p1.getPlayerName() + "�����ɹ�");
					} else {
						System.out.println("�Ƿ����ӣ�������˼��");
						return false;
					}

				}
				if (p2.getPlayerName().equals(currentPlayer)) {
					if (action.goEat(new Position(a, b), "W", currentPlayer)) {
						p2.addHistory(currentPlayer + " eat " + a + "," + b + "\n");
						System.out.println(p2.getPlayerName() + "�����ɹ�");
					} else {
						System.out.println("�Ƿ����ӣ�������˼��");
						return false;
					}

				}
			}
			if (this.gameType.equals("chess")) {
				System.out.print("�������������ʼ�㣬��ֹ������:");
				int a = in.nextInt();
				int b = in.nextInt();
				int c = in.nextInt();
				int d = in.nextInt();
				if (action.chessEat(new Position(a, b), new Position(c, d), currentPlayer)) {
					if (p1.getPlayerName().equals(currentPlayer)) {
						p1.addHistory(currentPlayer + " eat " + c + "," + d + " from " + a + "," + b + "\n");
						System.out.println(p1.getPlayerName() + "�����ɹ�");
					}
					if (p2.getPlayerName().equals(currentPlayer)) {
						p2.addHistory(currentPlayer + " eat " + c + "," + d + " to " + a + "," + b + "\n");
						System.out.println(p2.getPlayerName() + "�����ɹ�");
					}
				} else {
					System.out.println("�Ƿ��ƶ���������˼��");
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
			System.out.print("��������Ҫ����������:");
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
				System.out.println("����Ƿ���������");
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
	 * ����˫����������
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
		System.out.println(this.p1.getPlayerName() + "ʣ����������" + count1);
		System.out.println(this.p2.getPlayerName() + "ʣ����������" + count2);
	}

	/**
	 * ��ӡ˫��������ʷ
	 */
	public void printHistory() {
		System.out.println(this.p1.getPlayerName() + "��������ʷ��");
		for (String s1 : this.p1.getPlayerHistory()) {
			System.out.print(s1);
		}
		System.out.println(this.p2.getPlayerName() + "��������ʷ��");
		for (String s2 : this.p2.getPlayerHistory()) {
			System.out.print(s2);
		}
	}

	/**
	 * ��ĳһλ�õ����ӽ�������
	 * 
	 * @param p ��Ҫ������λ������
	 */
	public void serach(Position p) {
		System.out.println("�õ��������ͣ�" + this.board.getMap().get(p.xCord()*this.board.size() + p.yCord()).getTypeName());
		System.out.println("�õ�����ӵ���ߣ�" + this.board.getMap().get(p.xCord()*this.board.size() + p.yCord()).getPlayerName());
	}
}
