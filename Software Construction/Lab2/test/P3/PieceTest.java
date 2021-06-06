package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PieceTest {
	//Test Strategy
	//创建Piece对象看返回信息是否正确
	@Test
	public void pieceTest() {
		Piece pi1=new Piece("B","haha");
		Piece pi2=new Piece("B","haha");
		assertEquals("B",pi1.getTypeName());
		assertEquals("haha",pi1.getPlayerName());
		assertTrue(pi1.equals(pi2));
		assertEquals(pi1.hashCode(),pi2.hashCode());
	}

}
