package Planning;

//mutable
/**
 * 
 * 堵塞接口的实现
 *
 */
public class BlockableEntryImpl implements BlockableEntry {
	private int times = 0;

	// AF
	// times代表阻塞的次数
	// RI
	// times>=0
	// Safety from rep exposure
	// int是不可变数据类型
	private void checkRep() {
		assert times >= 0;
	}

	@Override
	public int blockTime() {
		checkRep();
		return this.times;
	}

	@Override
	public void block() {
		checkRep();
		this.times += 1;
	}

}
