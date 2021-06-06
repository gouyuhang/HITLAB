package APIs;

import Planning.*;
import java.util.*;

/**
 * 
 * Strategy设计模式，两种策略
 *
 * @param <R> 资源，泛型
 */
public interface Strategy<R> {
	/**
	 * 静态工厂方法，可返回两种策略
	 * 
	 * @param stra 策略名称
	 * @return 两种策略其中一种的子类型
	 */
	public static Strategy<?> getInstance(String stra) {
		if (stra.equals("FindFast")) {
			return new FindFast<Object>();
		}
		if (stra.equals("FindLatest")) {
			return new FindLatest<Object>();
		}
		return null;
	}

	/**
	 * 寻找前序计划项
	 * 
	 * @param resource 资源
	 * @param e        计划项
	 * @param entries  计划项列表
	 * @return 前序计划项
	 */
	public PlanningEntry find(Object resource, PlanningEntry e, List<?> entries);

}
