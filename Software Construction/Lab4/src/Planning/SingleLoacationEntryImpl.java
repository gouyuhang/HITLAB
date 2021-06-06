package Planning;

import Location.*;
import java.util.*;

/**
 * 
 * ����λ�ð��ŵĽӿ�ʵ��
 *
 */
//mutable
public class SingleLoacationEntryImpl implements SingleLocationEntry {
	private List<Location> list = new ArrayList<>();

	// AF
	// list�������а���λ�õ���Ϣ���б����һ��Ԫ�ش���ǰ�趨��λ��
	// RI
	// list!=null
	// Safety from rep exposure
	// Location�ǲ��ɱ���������
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
