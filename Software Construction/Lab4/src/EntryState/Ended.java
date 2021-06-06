package EntryState;

/**
 * 
 * 终止状态的ADT
 *
 */
//immutable
public class Ended implements State {
	String name = "Ended";
	static State instance = new Ended();

	// AF
	// name代表状态的名称
	// RI
	// name!=null
	// Safety from rep exposure
	// String是不可变数据类型
	private void checkRep() {
		assert name.equals("Ended");
	}

	/**
	 * 获取状态实例
	 */
	private Ended() {
	};

	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public State move(String cmd) {
		checkRep();
		return null;
	}

}
