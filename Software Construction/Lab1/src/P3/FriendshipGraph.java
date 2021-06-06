package P3;

import java.util.*;

import P3.Person;

public class FriendshipGraph {
	// 通过数组下标给姓名编码
	public ArrayList<String> nameVertex = new ArrayList<>();
	// 边的集合，二维编码为一维数组
	public ArrayList<Integer> edgeList = new ArrayList<>();

	public void addVertex(Person name) {
		// 判断输入的两个名字是否相同
		for (int i = 0; i < nameVertex.size(); ++i) {
			if (name.getNameString() == nameVertex.get(i)) {
				System.out.println("CONTIANS SAME NAME");
				System.exit(0);
			}
		}
		nameVertex.add(name.getNameString());
	}

	public void addEdge(Person name1, Person name2) {
		int i, j;
		// 遍历找到两个名字的编码
		for (i = 0; i < nameVertex.size(); ++i) {
			if (name1.getNameString() == nameVertex.get(i)) {
				break;
			}
		}
		for (j = 0; j <= nameVertex.size(); ++j) {
			if (name2.getNameString() == nameVertex.get(j)) {
				break;
			}
		}
		// 通过编码加边
		edgeList.add(i * nameVertex.size() + j);
	}

	public int getDistance(Person name1, Person name2) {
		int i, j, head;
		Queue<Integer> q = new LinkedList<>();
		for (i = 0; i < nameVertex.size(); ++i) {
			if (name1.getNameString() == nameVertex.get(i)) {
				break;
			}
		}
		for (j = 0; j <= nameVertex.size(); ++j) {
			if (name2.getNameString() == nameVertex.get(j)) {
				break;
			}
		}
		if (i == j) {
			return 0;
		}
		int n = nameVertex.size();
		int distance[] = new int[n];
		boolean visit[] = new boolean[n];
		// BFS找到最短路径 当点的距离被遍历到 直接返回
		q.offer(i);
		// 根据编码原则，边列表的值除n是起点，%n是终点
		while (!q.isEmpty()) {
			head = q.poll();
			for (int p = 0; p < edgeList.size(); ++p) {
				if (edgeList.get(p) / n == head && visit[edgeList.get(p) % n] == false) {
					visit[edgeList.get(p) % n] = true;
					q.offer(edgeList.get(p) % n);
					distance[edgeList.get(p) % n] = distance[head] + 1;
					if (edgeList.get(p) % n == j) {
						return distance[j];
					}
				}
			}
		}
		// 没有被搜索到则不可达，返回-1
		return -1;
	}

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
		//graph.addEdge(rachel, ross);
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
