package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	//Test Strategy
	//看observer方法是否正确
	//尝试添加看是否成功添加
	@Test
	public void playerTest() {
		Player p1=new Player("haha");
		assertEquals("haha",p1.getPlayerName());
		p1.addHistory("a+b->c");
		assertTrue(p1.getPlayerHistory().contains("a+b->c"));
	}

}
