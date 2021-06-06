package Planning;

import java.util.*;
/**
 * 
 *多个资源安排接口的实现
 *
 * @param <R>资源，泛型
 */
public class MultipleResourceEntryImpl<R> implements MultipleResourceEntry<R>{
	private List<R> list=new ArrayList<>();
	//AF
	//list代表安排的资源的列表
	//RI
	//list!=null
	//Safety from rep exposure
	//unmodified
	private void checkRep() {
		assert list!=null;
	}
	@Override
	public boolean setResource(R resource) {
		if(resource.equals(null)) {
			return false;
		}
		this.list.add(resource);
		checkRep();
		return true;
	}
	@Override
	public List<R> getSortedResourceList() {
		checkRep();
		return Collections.unmodifiableList(this.list);

	}
	@Override
	public boolean resourceIsSet() {
		if(this.list.isEmpty()) {
			return false;
		}
		checkRep();
		return true;
	}

}
