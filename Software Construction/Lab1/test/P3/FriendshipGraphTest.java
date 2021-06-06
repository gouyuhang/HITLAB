package P3;

import static org.junit.Assert.*;

import org.junit.Test;
public class FriendshipGraphTest {
	@Test
	public void addVertexTest() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		assertTrue(graph.nameVertex.contains("Rachel"));
		assertTrue(graph.nameVertex.contains("Ross"));
		assertTrue(graph.nameVertex.contains("Ben"));
		assertTrue(graph.nameVertex.contains("Kramer"));		
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
		graph.addEdge(rachel, ross);
		assertTrue(graph.edgeList.contains(1));
		graph.addEdge(ross, rachel);
		assertTrue(graph.edgeList.contains(4));
		graph.addEdge(ross, ben);
		assertTrue(graph.edgeList.contains(6));
		graph.addEdge(ben, ross);
		assertTrue(graph.edgeList.contains(9));
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
		//graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		assertEquals(-1,graph.getDistance(rachel, ross));
		assertEquals(-1,graph.getDistance(rachel,ben));
		assertEquals(0,graph.getDistance(rachel, rachel));
		assertEquals(-1,graph.getDistance(rachel, kramer));
	}
	@Test
	public void getDistanceTestSelfProduced() {
		FriendshipGraph graph=new FriendshipGraph();
		Person A=new Person("A");
		Person B=new Person("B");
		Person C=new Person("C");
		Person D=new Person("D");
		graph.addVertex(A);
		graph.addVertex(B);
		graph.addVertex(C);
		graph.addVertex(D);
		graph.addEdge(A, B);
		graph.addEdge(B, D);
		graph.addEdge(D, C);
		graph.addEdge(C, B);
		assertEquals(3,graph.getDistance(A, C));
		assertEquals(-1,graph.getDistance(C,A));
		assertEquals(0,graph.getDistance(A, A));
		assertEquals(2,graph.getDistance(B, C));
	}
	/*@Test
	public void addVertexTest_SameName() {
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		graph.addVertex(rachel);
		graph.addVertex(rachel);
			
	}
	*/
}
