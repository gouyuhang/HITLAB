package p1;

public class inputToken {
	public String tokenName;
	public String tokenValue;
	public int lineNumber;
	public inputToken (String a,String b,int c) {
		this.tokenName=a;
		this.tokenValue=b;
		this.lineNumber=c;
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
}
