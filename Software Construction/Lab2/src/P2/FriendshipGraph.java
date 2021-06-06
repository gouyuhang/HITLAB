package P2;

import java.util.*;
import P1.graph.Graph;

/**
 * Mutable 生成一个朋友圈关系图 图中的边权值为1 不允许图中出现相同两人否则直接退出
 */
public class FriendshipGraph {
	private final Graph<Person> graph = Graph.empty();
	// AF
	// 延用p1中的graph形成社交网络关系图
	// RI
	// graph!=null;
	// Safety from rep exposure
	// private final去定义graph

	// Constructor
	/**
	 * create a new friendshipgraph
	 */
	public FriendshipGraph() {
		checkRep();
	}

	// checkRep()
	private void checkRep() {
		assert graph != null;
	}

	// method
	/**
	 * 将对象加入到graph中，视为一个顶点，如果顶点存在，则直接退出
	 * 
	 * @param name1 Person类的一个对象 name1!=null
	 */
	public boolean addVertex(Person name1) {
		if (graph.add(name1) == false) {
			System.out.println("CONTIANS SAME NAME");
			System.exit(0);
		}
		checkRep();
		graph.add(name1);
		return true;
	}

	/**
	 * 将两者之间的社交关系以边的形式加入到graph中
	 * 
	 * @param name1 起始点(Person 类的一个对象) name1!=null
	 * @param name2 终止点(Person类的一个对象） name2!=null
	 */
	public int addEdge(Person name1, Person name2) {
		int lastWeight = graph.set(name1, name2, 1);
		checkRep();
		return lastWeight;
	}

	/**
	 * 获取两个对象之间的最短距离
	 * 
	 * @param name1 起始点(Person的一个对象) name1!=null
	 * @param name2 终止点(Person的一个对象) name2!=null;
	 * @return 两个对象之间的最短距离
	 */
	public int getDistance(Person name1, Person name2) {
		Set<String> visit = new HashSet<>();
		if (name1.getNameString().equals(name2.getNameString())) {
			return 0;
		}
		int distance = 0;
		int flag = 0;
		Queue<Person> q = new LinkedList<>();
		q.offer(name1);
		while (!q.isEmpty()) {
			for (Person p : graph.targets(q.poll()).keySet()) {
				if (!visit.contains(p.getNameString())) {
					q.add(p);
					visit.add(p.getNameString());
				}
				if (p.getNameString().equals(name2.getNameString())) {
					flag = 1;
				}
			}
			distance++;
			if (flag == 1) {
				checkRep();
				return distance;
			}
		}
		checkRep();
		return -1;
	}

	/**
	 * 主函数，沿用lab1的测试主程序
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println(graph.getDistance(rachel, ross));
		// should print 1
		System.out.println(graph.getDistance(rachel, ben));
		// should print 2
		System.out.println(graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println(graph.getDistance(rachel, kramer));
		// should print -1

	}
}
