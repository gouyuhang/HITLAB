package Planning;

import java.util.*;
import java.util.List;

import Location.*;
/**
 * 
 * ���λ�ð��Žӿڵ�ʵ��
 *
 */
//mutable
public class MultipleLocationEntryImpl implements MultipleLocationEntry{
	private List<Location> list=new ArrayList<>();
	//AF
	//list�����ŵĶ��λ����Ϣ�γɵ��б�
	//RI
	//list!=null
	//Safety from rep exposure
	//unmodified
	private void checkRep() {
		assert list!=null;
	}
	@Override
	public boolean setLocation(Location l) {
		if(l.equals(null)) {
			return false;
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
		if(this.list.isEmpty()) {
			return false;
		}
		checkRep();
		return true;
	}

}
