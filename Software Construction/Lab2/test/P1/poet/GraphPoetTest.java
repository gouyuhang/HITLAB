/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {

	// Testing strategy
	// 一起测试语料库生成的图和toString
	// main函数自带的测试样例
	// 自己创作的一个测试样例

	@Test(expected = AssertionError.class)
	public void testAssertionsEnabled() {
		assert false; // make sure assertions are enabled with VM argument: -ea
	}

	// TODO tests
	@Test
	public void corpusTest() {
		try {
			final GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/my_corpus.txt"));
			assertTrue(nimoy.toString().contains("when->you:1"));
			assertTrue(nimoy.toString().contains("his->face:1"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void GraphPoet_Test() {
		try {
			final GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/mugar-omni-theater.txt"));
			final String input = "Test the system";
			assertEquals("Test of the system", nimoy.poem(input));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void MyPoetTest() {
		try {
			final GraphPoet nimoy = new GraphPoet(new File("src/P1/poet/my_corpus.txt"));
			final String input = "And loved your with love or true";
			assertEquals("And loved your beauty with love false or true", nimoy.poem(input));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
