package Exception;

import static org.junit.Assert.*;

import org.junit.Test;

import App.FlightScheduleApp;

public class ExceptionTest {

	@Test
	public void test() {
		FlightScheduleApp f1 = new FlightScheduleApp();
		try {
			f1.readfile("test/Exception/txt/AgeOutBound.txt");
		} catch (Exception e) {
			assertTrue(e instanceof AgeOutBoundException);
		}
		try {
			f1.readfile("test/Exception/txt/DateConflict.txt");
		} catch (Exception e) {
			assertTrue(e instanceof DateConflictException);
		}
		try {
			f1.readfile("test/Exception/txt/DateFormatIncorrect.txt");
		} catch (Exception e) {
			assertTrue(e instanceof DateFormatIncorrectException);
		}
		try {
			f1.readfile("test/Exception/txt/FormatIncorrect.txt");
		} catch (Exception e) {
			assertTrue(e instanceof FormatIncorrectException);
		}
		try {
			f1.readfile("test/Exception/txt/MoreTime.txt");
		} catch (Exception e) {
			assertTrue(e instanceof MoreTimeException);
		}
		try {
			f1.readfile("test/Exception/txt/MoreZero.txt");
		} catch (Exception e) {
			assertTrue(e instanceof MoreZeroException);
		}
		try {
			f1.readfile("test/Exception/txt/NameIncorrect.txt");
		} catch (Exception e) {
			assertTrue(e instanceof NameIncorrectException);
		}

		try {
			FlightScheduleApp f2 = new FlightScheduleApp();
			f2.readfile("test/Exception/txt/NotMatch.txt");
			f2.check();
		} catch (Exception e) {
			assertTrue(e instanceof NotSameAirportException);
		}

		try {
			f1.readfile("test/Exception/txt/PlaneIncorrect.txt");
		} catch (Exception e) {
			assertTrue(e instanceof PlaneIncorrectException);
		}
		try {
			FlightScheduleApp f4 = new FlightScheduleApp();
			f4.readfile("test/Exception/txt/ResourceConflict.txt");
			f4.check();
		} catch (Exception e) {
			assertTrue(e instanceof ResourceConflictException);
		}
		try {
			FlightScheduleApp f3 = new FlightScheduleApp();
			f3.readfile("test/Exception/txt/SameFlight.txt");
			f3.check();
		} catch (Exception e) {
			assertTrue(e instanceof SameFlightException);
		}

		try {
			f1.readfile("test/Exception/txt/SeatIncorrect.txt");
		} catch (Exception e) {
			assertTrue(e instanceof SeatIncorrectException);
		}
		try {
			f1.readfile("test/Exception/txt/TypeIncorrect.txt");
		} catch (Exception e) {
			assertTrue(e instanceof TypeIncorrectException);
		}
	}

}
