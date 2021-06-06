package EntryState;
/**
 * 
 * 计划项状态的接口
 *
 */
public interface State {
	/**
	 * 获取状态的名称
	 * @return
	 */
	public String getName();
	/**
	 * 进行状态转移
	 * @param cmd 状态转移的指令
	 * @return 转移之后的状态
	 */
	public State move(String cmd);
}
