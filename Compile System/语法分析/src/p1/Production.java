package p1;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;

public class Production {
	public String left;
	public Vector<String> rights=new Vector<>();
	public Production(String a,Vector<String> b) {
		this.left=a;
		this.rights=b;
	}
	String left() {
		return this.left;
	}
	Vector<String> rights() {
		return this.rights;
	}
}
