package P2;

import java.util.*;

/**
 * immutable 社交关系图里的每个成员类
 */
public class Person {
	private String personName;
	// AF
	// personName代表该成员的姓名
	// RI
	// personName!=null
	// Safety from rep exposure
	// String 都是不可变数据类型

	// Constructor
	/**
	 * create a new person
	 * 
	 * @param name name!=null 成员的名字
	 */
	public Person(String name) {
		this.personName = name;
		checkRep();
	}

	// checkRep
	private void checkRep() {
		assert this.personName != null;
	}

	// Method
	/**
	 * 获取对象的名字
	 * 
	 * @return 对象名
	 */
	public String getNameString() {
		checkRep();
		return this.personName;
	}

	/**
	 * Override equals
	 */
	@Override
	public boolean equals(Object that) {
		if (!(that instanceof Person))
			return false;
		Person thatPerson = (Person) that;
		if (this.personName.equals(thatPerson.personName)) {
			return true;
		}
		checkRep();
		return false;
	}

	/**
	 * Override hashCode
	 */
	@Override
	public int hashCode() {
		checkRep();
		return Objects.hashCode(this.personName);
	}
}