package P2;

import java.util.*;

/**
 * immutable �罻��ϵͼ���ÿ����Ա��
 */
public class Person {
	private String personName;
	// AF
	// personName����ó�Ա������
	// RI
	// personName!=null
	// Safety from rep exposure
	// String ���ǲ��ɱ���������

	// Constructor
	/**
	 * create a new person
	 * 
	 * @param name name!=null ��Ա������
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
	 * ��ȡ���������
	 * 
	 * @return ������
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