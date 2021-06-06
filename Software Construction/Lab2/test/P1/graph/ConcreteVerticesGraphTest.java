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
    //  ͼΪ��ʱ�ж�string�Ƿ�Ϊ��
    //   �����֮��toString����ֵ�Ƿ����
    
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
    //   getName Test��
    //   �жϽڵ����Ƿ���ȷ
    //   modifySourceMapTest/modifyTargetMapTest��
    //   ����������߲�����ʱ���ӱߡ��ߴ���ʱ���޸�ֵ
    //   �ж�Source/Target Mapӳ���ϵ�Ƿ����
    //   getSource/getTarget Test:
    //	 �жϷ��ص�ӳ��key��key->value��Ӧ��ϵ�Ƿ���ȷ
    //   removeSourceMap/removeTarget Test:
    //   �ж��Ƿ�ɾ����ɾ��֮���ԭӳ���Ƿ���Ӱ��
    //	 toString Test:
    //   �жϷ��ص��ַ����Ƿ����Ӧ����ʽ���ַ���
    
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
