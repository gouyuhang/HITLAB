package Planning;

import java.util.*;

/**
 * 
 * 多个资源安排的接口
 *
 * @param <R> 车厢这类的资源，泛型
 */
public interface MultipleResourceEntry<R> {
	/**
	 * 计划项是否已经设定好资源
	 * 
	 * @return 是/否
	 */
	public boolean resourceIsSet();

	/**
	 * 为计划项添加资源
	 * 
	 * @param resource 资源(车厢等）
	 * @return 是否设定成功
	 */
	public boolean setResource(R resource);

	/**
	 * 获取所有安排好的资源
	 * 
	 * @return 安排好的资源列表
	 */
	public List<R> getSortedResourceList();
}
