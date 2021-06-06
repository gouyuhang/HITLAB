package EntryState;

/**
 * 
 * 已分配资源状态的ADT
 *
 */
//immutable
public class Allocated implements State {
	private String name = "Allocated";
	static State instance = new Allocated();

	// AF
	// name代表状态的名称
	// RI
	// name!=null
	// Safety from rep exposure
	// String是不可变数据类型
	private void checkRep() {
		assert name.equals("Allocated");
	}

	/**
	 * 获取状态实例
	 */
	private Allocated() {
	};

	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public State move(String cmd) {
		switch (cmd) {
		case "Running":
			return Running.instance;
		case "Cancelled":
			return Cancelled.instance;
		}
		checkRep();
		return null;
	}
}
