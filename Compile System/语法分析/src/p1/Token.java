package p1;

public class Token {
	public String tokenName;
	public String tokenValue;
	public int lineNumber;

	public Token(String a, String b, int c) {
		this.tokenName = a;
		this.tokenValue = b;
		this.lineNumber = c;
	}

	public String name() {
		return this.tokenName;
	}

	public String value() {
		return this.tokenValue;
	}

	public int line() {
		return this.lineNumber;
	}

	@Override
	public boolean equals(Object o) {
		Token that = (Token) o;
		if (this.tokenName.equals(that.tokenName)) {
			return true;
		}
		return false;

	}
}
