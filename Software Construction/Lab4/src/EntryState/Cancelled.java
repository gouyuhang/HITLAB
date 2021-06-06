package EntryState;

/**
 * 
 * 取消状态的ADT
 *
 */
//immutable
public class Cancelled implements State {
	private String name = "Cancelled";
	static State instance = new Cancelled();

	// AF
	// name代表状态的名称
	// RI
	// name!=null
	// Safety from rep exposure
	// String是不可变数据类型
	private void checkRep() {
		assert name.equals("Cancelled");
	}

	/**
	 * 获取状态实例
	 */
	private Cancelled() {
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
