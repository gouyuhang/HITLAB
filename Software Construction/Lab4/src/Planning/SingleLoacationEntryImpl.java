package Planning;

import Location.*;
import java.util.*;

/**
 * 
 * 单个位置安排的接口实现
 *
 */
//mutable
public class SingleLoacationEntryImpl implements SingleLocationEntry {
	private List<Location> list = new ArrayList<>();

	// AF
	// list代表所有安排位置的信息，列表最后一个元素代表当前设定的位置
	// RI
	// list!=null
	// Safety from rep exposure
	// Location是不可变数据类型
	private void checkRep() {
		assert list != null;
	}

	@Override
	public boolean setLocation(Location l) {
		if (l == null) {
			return false;
		}
		this.list.add(l);
		checkRep();
		return true;
	}

	@Override
	public Location getLocation() {
		if (this.list.isEmpty()) {
			return null;
		}
		checkRep();
		return list.get(this.list.size() - 1);
	}

	@Override
	public boolean locIsSet() {
		if (this.list.isEmpty()) {
			return false;
		}
		checkRep();
		return true;
	}

}
