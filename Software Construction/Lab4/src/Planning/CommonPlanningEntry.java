package Planning;

import EntryState.*;

//mutable
/**
 * 
 * 计划项共性操作的实现
 *
 */
public class CommonPlanningEntry implements PlanningEntry {
	private String planName;
	private State state;

	// AF
	// planName代表计划项的名字，state代表当前计划项的状态
	// RI
	// planName!=null state!=null
	// Safety from rep exposure
	// String是不可变数据类型
	private void checkRep() {
		assert planName != null;
	}

	@Override
	public String getName() {
		checkRep();
		return this.planName;
	}

	@Override
	public boolean setName(String name) {
		if (name == null) {
			return false;
		}
		this.planName = name;
		checkRep();
		return true;
	}

	@Override
	public void begin() {
		this.state = Waiting.instance;
		checkRep();
	}

	@Override
	public String currentState() {
		checkRep();
		return this.state.getName();
	}

	@Override
	public boolean stateMove(String cmd) {
		if (this.state.move(cmd) == null) {
			return false;
		} else {
			this.state = this.state.move(cmd);
		}
		checkRep();
		return true;
	}

}
