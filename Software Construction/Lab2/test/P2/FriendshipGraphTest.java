package P2;

import static org.junit.Assert.*;

import org.junit.Test;


public class FriendshipGraphTest {
	//Testing strategy
	//addVertex Test:
	//����㿴����ֵ�ǲ���true
	//addEdge Test:
	//����ߣ�������ֵ
	//�߲����ڣ�����ֵΪ0
	//�����Ѵ��ڣ��ټ���һ�Σ�����ֵӦ����1
	//getDistance Test
	//һ����CMU����ɾ��ע�͵�һ���罻ͼ
	//��һ�����Լ�д��һ���Ƚϸ��ӵ�ͼ
	//�����Ҫ���ǵ�-1,0,>0�������Ҫ���Ե�
	//Test
	@Test
	public void addVertexTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		assertTrue(graph.addVertex(rachel));
		assertTrue(graph.addVertex(ross));
		assertTrue(graph.addVertex(ben));
		assertTrue(graph.addVertex(kramer));
	}
	@Test
	public void addEdgeTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		assertEquals(0,graph.addEdge(rachel, ross));
		assertEquals(1,graph.addEdge(rachel, ross));
	}
	@Test
	public void getDistanceTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		// graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		assertEquals(-1, graph.getDistance(rachel, ross));
		assertEquals(-1, graph.getDistance(rachel, ben));
		assertEquals(0, graph.getDistance(rachel, rachel));
		assertEquals(-1, graph.getDistance(rachel, kramer));
	}
	@Test
	public void getDistanceTestSelfProduced() {
		FriendshipGraph graph = new FriendshipGraph();
		Person A = new Person("A");
		Person B = new Person("B");
		Person C = new Person("C");
		Person D = new Person("D");
		graph.addVertex(A);
		graph.addVertex(B);
		graph.addVertex(C);
		graph.addVertex(D);
		graph.addEdge(A, B);
		graph.addEdge(B, D);
		graph.addEdge(D, C);
		graph.addEdge(C, B);
		assertEquals(3, graph.getDistance(A, C));
		assertEquals(-1, graph.getDistance(C, A));
		assertEquals(0, graph.getDistance(A, A));
		assertEquals(2, graph.getDistance(B, C));
	}

}
