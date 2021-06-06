/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    // testInitialVerticesEmpty��
	// �ж����ɵ�ͼ�Ƿ�Ϊ�յ�ͼ
	// add Test:
	// ����㿴���ص�true
	// �����ظ��ĵ㿴�Ƿ񷵻�false
	// vertices Test��
	// �ӵ㿴�Ƿ��ڷ��صļ�����
	// ��set���������½ڵ㿴�Ƿ���vertices��
    // set Test��
	// ����������Ѵ��ڵĵ㣬weight>0,weight=0
	// �����ڵĵ�weight>0
	// remove Test��
	// �Ѵ��ڵĵ�ɾ������ԭͼ����Ӱ��
	// �����ڵĵ�remove֮���Ƿ񷵻�false
	// source/target Test:
	// �����ص�map keyֵ��key->valueӳ���Ƿ���ȷ
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void Graph_AddTest() {
    	Graph<String> graph = emptyInstance();
    	assertEquals(true,graph.add("abc"));
    	assertEquals(true,graph.add("ccc"));
    	assertEquals(false,graph.add("abc"));
    }
    @Test
    public void Graph_SetTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("abc");
    	graph.add("bcd");
    	graph.add("cde");
    	graph.add("def");
    	assertEquals(0,graph.set("abc", "bcd", 4));
    	assertEquals(4,graph.set("abc", "bcd", 8));
    	assertEquals(8,graph.set("abc", "bcd", 0));
    	assertEquals(0,graph.set("abc", "bcd", 4));
    	assertEquals(0,graph.set("A", "B", 4));
    	assertEquals(4,graph.set("A", "B", 8));
    	assertEquals(0,graph.set("cde", "bcd", 0));
    }
    @Test
    public void Graph_RemoveTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("abc");
    	graph.add("bcd");
    	graph.add("cde");
    	graph.add("def");
    	graph.set("abc", "bcd", 4);
    	graph.set("abc", "cde", 4);    	
    	assertTrue(graph.remove("abc"));    	
    	assertTrue(!graph.toString().contains("abc->bcd:4"));
    	assertTrue(!graph.toString().contains("abc->cde:4"));
    	assertTrue(!graph.vertices().contains("abc"));
    	assertTrue(!graph.remove("B"));
    }
    @Test
    public void Graph_VerticesTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("abc");
    	graph.add("bcd");
    	graph.add("cde");
    	graph.add("def");
    	assertTrue(graph.vertices().contains("abc"));
    	assertTrue(graph.vertices().contains("bcd"));
    	assertTrue(graph.vertices().contains("cde"));
    	graph.set("A", "B", 8);
    	assertTrue(graph.vertices().contains("A"));
    	assertTrue(graph.vertices().contains("B"));
    }
    @Test
    public void Graph_SourceTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("abc");
    	graph.add("bcd");
    	graph.add("cde");
    	graph.add("def");
    	graph.set("bcd", "abc", 4);
    	graph.set("cde", "abc", 5);
    	graph.set("def", "abc", 6);
    	assertTrue(graph.sources("abc").keySet().contains("bcd"));
    	assertTrue(graph.sources("abc").keySet().contains("cde"));
    	assertTrue(graph.sources("abc").keySet().contains("def"));
    	assertEquals(4,graph.sources("abc").get("bcd").intValue());
    	assertEquals(5,graph.sources("abc").get("cde").intValue());
    	assertEquals(6,graph.sources("abc").get("def").intValue());
    }
    @Test
    public void Graph_TargetTest() {
    	Graph<String> graph = emptyInstance();
    	graph.add("abc");
    	graph.add("bcd");
    	graph.add("cde");
    	graph.add("def");
    	graph.set("abc", "bcd", 4);
    	graph.set("abc", "cde", 5);
    	graph.set("abc", "def", 6);
    	assertTrue(graph.targets("abc").keySet().contains("bcd"));
    	assertTrue(graph.targets("abc").keySet().contains("cde"));
    	assertTrue(graph.targets("abc").keySet().contains("def"));
    	assertEquals(4,graph.targets("abc").get("bcd").intValue());
    	assertEquals(5,graph.targets("abc").get("cde").intValue());
    	assertEquals(6,graph.targets("abc").get("def").intValue());
    }
}
