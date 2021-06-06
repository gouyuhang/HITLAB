/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //  图为空时判定string是否为空
    //   加入边之后看toString返回值是否包含
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void ConcreteVerticesGraph_toStringTest() {
    	Graph<String> graph = new ConcreteVerticesGraph<>();
    	assertEquals("",graph.toString());
    	graph.add("abc");
    	graph.add("bcd");
    	graph.add("cde");
    	graph.add("def");
    	graph.set("abc", "bcd", 4);
    	assertTrue(graph.toString().contains("abc->bcd:4"));
    }
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   getName Test：
    //   判断节点名是否正确
    //   modifySourceMapTest/modifyTargetMapTest：
    //   两种情况，边不存在时，加边。边存在时，修改值
    //   判断Source/Target Map映射关系是否相等
    //   getSource/getTarget Test:
    //	 判断返回的映射key和key->value对应关系是否正确
    //   removeSourceMap/removeTarget Test:
    //   判断是否删除且删除之后对原映射是否有影响
    //	 toString Test:
    //   判断返回的字符串是否包含应有形式的字符串
    
    // TODO tests for operations of Vertex
    @Test
    public void Vertex_getSourceTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	v1.modifySourceMap("sun", 100);
    	assertTrue(v1.getSource().containsKey("sun"));
    	assertEquals(100,v1.getSource().get("sun").intValue());
    }
    @Test
    public void Vertex_getTargetTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	v1.modifyTargetMap("sun", 100);
    	assertTrue(v1.getTarget().containsKey("sun"));
    	assertEquals(100,v1.getTarget().get("sun").intValue());
    }
    @Test
    public void Vertex_getNameTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	assertEquals("trump",v1.getName());
    	Vertex<Integer> v2=new Vertex<>(250);
    	assertEquals(250,v2.getName().intValue());
    }
    @Test
    public void Vertex_modifySourceMapTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	v1.modifySourceMap("sun", 100);
    	assertTrue(v1.getSource().containsKey("sun"));
    	assertEquals(100,v1.getSource().get("sun").intValue());
    	v1.modifySourceMap("sun",200);
    	assertEquals(200,v1.getSource().get("sun").intValue());
    	
    }
    @Test
    public void Vertex_modifyTargetMapTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	v1.modifyTargetMap("sun", 100);
    	assertTrue(v1.getTarget().containsKey("sun"));
    	assertEquals(100,v1.getTarget().get("sun").intValue());
    	v1.modifyTargetMap("sun",200);
    	assertEquals(200,v1.getTarget().get("sun").intValue());
    }
    @Test
    public void Vertex_removeSourceMapTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	v1.modifySourceMap("sun", 100);
    	v1.modifySourceMap("moon", 100);
    	v1.removeSourceMap("sun");
    	assertTrue(v1.getSource().containsKey("moon"));
    	assertTrue(!v1.getSource().containsKey("sun"));
    }
    @Test
    public void Vertex_removeTargetMapTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	v1.modifyTargetMap("sun", 100);
    	v1.modifyTargetMap("moon", 100);
    	v1.removeTargetMap("sun");
    	assertTrue(v1.getTarget().containsKey("moon"));
    	assertTrue(!v1.getTarget().containsKey("sun"));
    }
    @Test
    public void Vertex_toStringTest() {
    	Vertex<String> v1=new Vertex<>("trump");
    	v1.modifySourceMap("sun", 100);
    	v1.modifySourceMap("moon", 100);
    	v1.modifyTargetMap("sun", 100);
    	v1.modifyTargetMap("moon", 100);
    	assertTrue(v1.toString().contains("trump->sun:100"));
    	assertTrue(v1.toString().contains("sun->trump:100"));
    }
    
}
