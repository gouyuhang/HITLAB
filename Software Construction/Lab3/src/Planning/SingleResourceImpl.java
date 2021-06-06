package Planning;
import java.util.*;
/**
 * 
 * 单个资源设定的接口实现
 *
 * @param <R>
 */
//mutable
public class SingleResourceImpl<R> implements SingleResourceEntry<R>{
	private  List<R> list=new ArrayList<>();
	//AF
	//所有安排的资源形成的列表,列表最后一个元素为当前设定的资源
	//RI
	//list！=null
	//Safety from rep exposure
	//Location是不可变数据类型
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
	public R getResource() {
		if(this.list.isEmpty()) {
			return null;
		}
		checkRep();
		return list.get(list.size()-1);
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
