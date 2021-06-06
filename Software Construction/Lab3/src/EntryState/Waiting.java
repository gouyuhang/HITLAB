package EntryState;
/**
 * 
 * 等待状态的ADT
 *
 */
//immutable
public class Waiting implements State {
	private String name="Waiting";
	public static State instance = new Waiting();
	//AF
	//name代表状态的名称
	//RI
	//name!=null
	//Safety from rep exposure
	//String是不可变数据类型
	private void checkRep() {
		assert name=="Waiting";
	}
	/**
	 * 获取状态实例
	 */
	private Waiting() {} ;
	@Override
	public String getName() {
		checkRep();
		return this.name;
	}

	@Override
	public State move(String cmd) {
		switch(cmd) {
		case "Allocated":
			return Allocated.instance;
		case "Cancelled":
			return Cancelled.instance;
		}
		checkRep();
		return null;
	}
}
