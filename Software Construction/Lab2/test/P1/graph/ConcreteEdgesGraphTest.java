/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   图为空时判定string是否为空
    //   加入边之后看toString返回值是否包含
    
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void ConcreteEdgesGraph_toStringTest() {
    	Graph<String> graph = new ConcreteEdgesGraph<>();
    	assertEquals("",graph.toString());
    	graph.add("abc");
    	graph.add("bcd");
    	graph.add("cde");
    	graph.add("def");
    	graph.set("abc", "bcd", 4);
    	assertTrue(graph.toString().contains("abc->bcd:4"));
    }
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   getSourceTest和getTargetTest:
    //   getSource和getTarget用两种类型，判断顶点信息是否正确
    //	 getWeightTest:
    //   判断值是否正确即可
    //   toStringTest:
    //   两种类型，判断string是否正确
    //   equals hashcodeTest:
    //   创建两个对象进行比较
    // TODO tests for operations of Edge
    @Test
    public void Edge_getSourceTest() {
    	Edge<String> e=new Edge<>("bcd","abc",5);
    	assertEquals("bcd",e.getSource());
    	Edge<Integer> ed=new Edge<>(1,4,5);
    	assertEquals(1,ed.getSource().intValue());
    }
    @Test
    public void Edge_getTargetTest() {
    	Edge<String> e=new Edge<>("bcd","abc",5);
    	assertEquals("abc",e.getTarget());
    	Edge<Integer> ed=new Edge<>(1,4,5);
    	assertEquals(4,ed.getTarget().intValue());
    }
    @Test
    public void Edge_getWeightTest() {
    	Edge<String> e=new Edge<>("bcd","abc",5);
    	assertEquals(5,e.getWeight());
    }
    @Test
    public void Edge_toString() {
    	Edge<String> e=new Edge<>("bcd","abc",5);
    	assertTrue(e.toString().contains("bcd->abc:5"));
    	Edge<Integer> ed=new Edge<>(1,4,5);
    	assertTrue(ed.toString().contains("1->4:5"));
    }
    @Test
    public void Edge_equalshashcodeTest() {
    	Edge<String> e1=new Edge<>("bcd","abc",5);
    	Edge<String> e2=new Edge<>("bcd","abc",5);
    	assertTrue(e1.equals(e2));
    	assertEquals(e1.hashCode(),e2.hashCode());
    }
}
