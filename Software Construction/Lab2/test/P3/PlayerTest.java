package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {
	//Test Strategy
	//��observer�����Ƿ���ȷ
	//������ӿ��Ƿ�ɹ����
	@Test
	public void playerTest() {
		Player p1=new Player("haha");
		assertEquals("haha",p1.getPlayerName());
		p1.addHistory("a+b->c");
		assertTrue(p1.getPlayerHistory().contains("a+b->c"));
	}

}
