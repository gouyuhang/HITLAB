package Planning;
/**
 * 
 * 单个资源安排的接口
 *
 * @param <R> 资源(如飞机等)
 */
public interface SingleResourceEntry<R> {
	/**
	 * 安排资源
	 * @param resource 资源
	 * @return 资源是否安排成功
	 */
	public boolean setResource(R resource);
	/**
	 * 获取当前安排的资源
	 * @return 当前安排的资源
	 */
	public R getResource();
	/**
	 * 资源是否已经被设定
	 * @return 是/否
	 */
	public boolean resourceIsSet();
}
