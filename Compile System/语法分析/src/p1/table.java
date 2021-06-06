package p1;

import java.util.Vector;

public class table {
	public Vector<String> s;
	public table() {
		this.s=new Vector<>();
	}
	Vector<String> get() {
		return this.s;
	}
	void insert(String s) {
		this.s.add(s);
	}
	String write() {
		String result="";
		for(String str : this.s) {
			result+=str;
		}
		return result;
	}
	String left(){
		return s.get(0);
	}
	Vector<String> rights() {
		Vector<String> result=new Vector<String>();
		for(int i=2;i<s.size();++i) {
			result.add(s.get(i));
		}
		return result;
	}
}
