package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {
	//Test Strategy
	//Print部分直接打印看结果即可
	//对chess和board要分别进行测试
	//board的所有方法都是对一个map进行操作
	//通过方法修改之后看map是否符合预期即可
	@Test
	public void chessBoardTest() {
		Board b1=new Board("chess","haha","hehe");
		assertEquals(8, b1.size());
		b1.set(new Position(1,2), new Piece("K","haha"));
		assertEquals("K",b1.getMap().get(1*8+2).getTypeName());
		b1.remove(new Position(1,2));
		assertEquals("E",b1.getMap().get(1*8+2).getTypeName());
	}
	@Test
	public void goBoardTest() {
		Board b1=new Board("go","haha","hehe");
		assertEquals(19, b1.size());
		b1.set(new Position(1,2), new Piece("B","haha"));
		assertEquals("B",b1.getMap().get(1*19+2).getTypeName());
		b1.remove(new Position(1,2));
		assertEquals("E",b1.getMap().get(1*19+2).getTypeName());
	}
	@Test
	public void broadPrintTest() {
		Board b1=new Board("chess","haha","hehe");
		b1.printBoard();
		Board b2=new Board("go","haha","hehe");
		b2.printBoard();
	}
}
