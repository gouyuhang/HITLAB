package Planning;

/**
 * 
 * 可阻塞的计划项的接口
 *
 */
public interface BlockableEntry {
	/**
	 * 记录计划项被阻塞了几次
	 * 
	 * @return 阻塞次数
	 */
	public int blockTime();

	/**
	 * 阻塞计划项
	 */
	public void block();
}
