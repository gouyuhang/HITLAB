package Timeslot;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class TimeslotTest {
	// Testing strategy
	// 新建一个timeslot对象 看能否正确设定时间
	// 以及返回的时间是否正确
	@Test
	public void test() {
		Timeslot t = new Timeslot();
		t.setStart(2000, 8, 12, 12, 10);
		t.setEnd(2000, 8, 15, 14, 20);
		assertEquals(2000, t.start().get(Calendar.YEAR));
		assertEquals(2000, t.end().get(Calendar.YEAR));
		assertEquals(8, t.start().get(Calendar.MONTH));
		assertEquals(8, t.end().get(Calendar.MONTH));
		assertEquals(12, t.start().get(Calendar.DATE));
		assertEquals(15, t.end().get(Calendar.DATE));
		assertEquals(12, t.start().get(Calendar.HOUR_OF_DAY));
		assertEquals(14, t.end().get(Calendar.HOUR_OF_DAY));
		assertEquals(10, t.start().get(Calendar.MINUTE));
		assertEquals(20, t.end().get(Calendar.MINUTE));

	}

}
