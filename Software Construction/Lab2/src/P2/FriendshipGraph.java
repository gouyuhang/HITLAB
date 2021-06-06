package P2;

import java.util.*;
import P1.graph.Graph;

/**
 * Mutable ����һ������Ȧ��ϵͼ ͼ�еı�ȨֵΪ1 ������ͼ�г�����ͬ���˷���ֱ���˳�
 */
public class FriendshipGraph {
	private final Graph<Person> graph = Graph.empty();
	// AF
	// ����p1�е�graph�γ��罻�����ϵͼ
	// RI
	// graph!=null;
	// Safety from rep exposure
	// private finalȥ����graph

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
	 * ��������뵽graph�У���Ϊһ�����㣬���������ڣ���ֱ���˳�
	 * 
	 * @param name1 Person���һ������ name1!=null
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
	 * ������֮����罻��ϵ�Աߵ���ʽ���뵽graph��
	 * 
	 * @param name1 ��ʼ��(Person ���һ������) name1!=null
	 * @param name2 ��ֹ��(Person���һ������ name2!=null
	 */
	public int addEdge(Person name1, Person name2) {
		int lastWeight = graph.set(name1, name2, 1);
		checkRep();
		return lastWeight;
	}

	/**
	 * ��ȡ��������֮�����̾���
	 * 
	 * @param name1 ��ʼ��(Person��һ������) name1!=null
	 * @param name2 ��ֹ��(Person��һ������) name2!=null;
	 * @return ��������֮�����̾���
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
	 * ������������lab1�Ĳ���������
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
