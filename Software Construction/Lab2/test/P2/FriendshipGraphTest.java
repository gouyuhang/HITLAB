package P2;

import static org.junit.Assert.*;

import org.junit.Test;


public class FriendshipGraphTest {
	//Testing strategy
	//addVertex Test:
	//加入点看返回值是不是true
	//addEdge Test:
	//加入边，看返回值
	//边不存在，返回值为0
	//若边已存在，再加入一次，返回值应该是1
	//getDistance Test
	//一个是CMU给的删除注释的一个社交图
	//另一个是自己写的一个比较复杂的图
	//求距离要考虑到-1,0,>0等情况都要测试到
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
