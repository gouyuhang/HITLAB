package Exception;

public class SameFlightException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SameFlightException() {

	}

	public SameFlightException(String str) {
		super(str);
	}
}
