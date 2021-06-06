package P3;

import java.util.*;

public class MyChessAndGoGame {

	/**
	 * 棋类游戏主程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("welcome to the chess and go game");
		Scanner in = new Scanner(System.in);
		StringBuilder currentPlayer;
		System.out.println("please choose the type :");
		String gametype = in.nextLine();
		System.out.println("please input player1 name:");
		String player1 = in.nextLine();
		System.out.println("please input player2 name:");
		String player2 = in.nextLine();
		Game game = new Game(gametype, player1, player2);
		currentPlayer = new StringBuilder(player1);
		while (true) {
			System.out.println(currentPlayer.toString() + "请出招(按0可显示菜单):");
			String cmdline = in.next();
			if (cmdline.equals("end")) {
				game.printHistory();
				System.out.println("see you next time");
				System.exit(0);
			} else {
				if (game.cmd(in, Integer.valueOf(cmdline).intValue(), currentPlayer.toString())) {
					if (currentPlayer.toString().equals(player1)) {
						currentPlayer = new StringBuilder(player2);
						continue;
					}
					if (currentPlayer.toString().equals(player2)) {
						currentPlayer = new StringBuilder(player1);
						continue;
					}
				}
			}
		}
	}

}
