package P3;

import java.util.*;

import P3.Person;

public class FriendshipGraph {
	// ͨ�������±����������
	public ArrayList<String> nameVertex = new ArrayList<>();
	// �ߵļ��ϣ���ά����Ϊһά����
	public ArrayList<Integer> edgeList = new ArrayList<>();

	public void addVertex(Person name) {
		// �ж���������������Ƿ���ͬ
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
		// �����ҵ��������ֵı���
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
		// ͨ������ӱ�
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
		// BFS�ҵ����·�� ����ľ��뱻������ ֱ�ӷ���
		q.offer(i);
		// ���ݱ���ԭ�򣬱��б��ֵ��n����㣬%n���յ�
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
		// û�б��������򲻿ɴ����-1
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
