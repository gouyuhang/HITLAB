package Planning;

import java.util.*;
import java.util.List;

import Location.*;

/**
 * 
 * 多个位置安排接口的实现
 *
 */
//mutable
public class MultipleLocationEntryImpl implements MultipleLocationEntry {
	private List<Location> list = new ArrayList<>();

	// AF
	// list代表安排的多个位置信息形成的列表
	// RI
	// list!=null
	// Safety from rep exposure
	// unmodified
	private void checkRep() {
		assert list != null;
		if (this.list.size() >= 2) {
			assert !this.list.get(0).getName().equals(this.list.get(this.list.size() - 1).getName());
		}
	}

	@Override
	public boolean setLocation(Location l) {
		if (l == null) {
			assert false;
		}
		this.list.add(l);
		checkRep();
		return true;
	}

	@Override
	public List<Location> getLocation() {
		checkRep();
		return Collections.unmodifiableList(this.list);
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
