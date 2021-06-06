package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class ActionTest {
	//Test strategy
	//分为chess和go的测试
	//测试合法与非法的操作
	//通过boardMap来查看棋盘情况
	@Test
	public void goActionTest() {
		Board b=new Board("go","p1","p2");
		Action a=new Action(b);
		a.put(new Position(1,2), "B", "p1");
		a.put(new Position(2,3), "W", "p2");
		assertEquals("B",b.getMap().get(1*19+2).getTypeName());
		assertTrue(!a.put(new Position(20,20), "B", "p1"));
		assertTrue(!a.put(new Position(1,2), "B", "p1"));
		assertTrue(!a.put(new Position(2,3), "B", "p1"));
		
		assertTrue(!a.goEat(new Position(20,20), "B", "p1"));
		assertTrue(!a.goEat(new Position(1,2), "B", "p1"));
		
		a.goEat(new Position(2,3), "B", "p1");
		assertEquals("B",b.getMap().get(2*19+3).getTypeName());
		
	}
	@Test
	public void chessActionTest() {
		Board b=new Board("chess","p1","p2");
		Action a=new Action(b);
		a.move(new Position(0,1), new Position(0,2), "p1");
		assertEquals("P",b.getMap().get(0*8+2).getTypeName());
		assertTrue(!a.move(new Position(18,18), new Position(16,16), "p1"));
		assertTrue(!a.move(new Position(1,1), new Position(0,7), "p1"));
		assertTrue(!a.move(new Position(1,1), new Position(7,7), "p1"));
		assertTrue(!a.move(new Position(0,7), new Position(1,1), "p1"));
		
		
		assertTrue(!a.chessEat(new Position(18,18), new Position(16,16), "p1"));
		assertTrue(!a.chessEat(new Position(0,7), new Position(1,1), "p1"));
		assertTrue(!a.chessEat(new Position(0,0), new Position(0,0), "p1"));
		assertTrue(!a.chessEat(new Position(0,0), new Position(0,5), "p1"));
		
		a.chessEat(new Position(0,2), new Position(0,6), "p1");
		assertEquals("E",b.getMap().get(0*8+2).getTypeName());
		assertEquals("P",b.getMap().get(0*8+6).getTypeName());
		assertEquals("p1",b.getMap().get(0*8+6).getPlayerName());
		
	}
	 

}
