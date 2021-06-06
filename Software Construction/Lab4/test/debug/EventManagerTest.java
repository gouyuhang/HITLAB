package debug;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventManagerTest {
	// Testing Strategy
	// 同一天时间段之间的交叉
	// 不同天数时间段之间的交叉
	@Test
	public void test() {
		assertEquals(1, EventManager.book(1, 10, 20)); // returns 1
		assertEquals(1, EventManager.book(1, 1, 7)); // returns 1
		assertEquals(2, EventManager.book(1, 10, 22)); // returns 2
		assertEquals(2, EventManager.book(2, 10, 22));
		assertEquals(3, EventManager.book(1, 5, 15)); // returns 3
		assertEquals(4, EventManager.book(1, 5, 12)); // returns 4
		assertEquals(4, EventManager.book(1, 7, 10)); // returns 4
	}

}
