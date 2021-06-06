package P3;

import static org.junit.Assert.*;

import org.junit.Test;

public class PositionTest {
	//Test Strategy
	//����position���� ��������Ϣ�Ƿ���ȷ
	@Test
	public void positionTest() {
		Position p1=new Position(1,2);
		Position p2=new Position(1,2);
		assertEquals(1,p1.xCord());
		assertEquals(2,p1.yCord());
		assertTrue(p1.equals(p2));
		assertEquals(p1.hashCode(),p2.hashCode());
	}

}
