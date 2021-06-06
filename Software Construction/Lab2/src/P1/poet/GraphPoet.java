/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import java.io.*;
import java.util.*;
import P1.graph.Graph;

/**
 * A graph-based poetry generator.
 * 
 * <p>
 * GraphPoet is initialized with a corpus of text, which it uses to derive a
 * word affinity graph. Vertices in the graph are words. Words are defined as
 * non-empty case-insensitive strings of non-space non-newline characters. They
 * are delimited in the corpus by spaces, newlines, or the ends of the file.
 * Edges in the graph count adjacencies: the number of times "w1" is followed by
 * "w2" in the corpus is the weight of the edge from w1 to w2.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     Hello, HELLO, hello, goodbye!
 * </pre>
 * <p>
 * the graph would contain two edges:
 * <ul>
 * <li>("hello,") -> ("hello,") with weight 2
 * <li>("hello,") -> ("goodbye!") with weight 1
 * </ul>
 * <p>
 * where the vertices represent case-insensitive {@code "hello,"} and
 * {@code "goodbye!"}.
 * 
 * <p>
 * Given an input string, GraphPoet generates a poem by attempting to insert a
 * bridge word between every adjacent pair of words in the input. The bridge
 * word between input words "w1" and "w2" will be some "b" such that w1 -> b ->
 * w2 is a two-edge-long path with maximum-weight weight among all the
 * two-edge-long paths from w1 to w2 in the affinity graph. If there are no such
 * paths, no bridge word is inserted. In the output poem, input words retain
 * their original case, while bridge words are lower case. The whitespace
 * between every word in the poem is a single space.
 * 
 * <p>
 * For example, given this corpus:
 * 
 * <pre>
 *     This is a test of the Mugar Omni Theater sound system.
 * </pre>
 * <p>
 * on this input:
 * 
 * <pre>
 *     Test the system.
 * </pre>
 * <p>
 * the output poem would be:
 * 
 * <pre>
 *     Test of the system.
 * </pre>
 * 
 * <p>
 * PS2 instructions: this is a required ADT class, and you MUST NOT weaken the
 * required specifications. However, you MAY strengthen the specifications and
 * you MAY add additional methods. You MUST use Graph in your rep, but otherwise
 * the implementation of this class is up to you.
 */
public class GraphPoet {

	private final Graph<String> graph = Graph.empty();

	// Abstraction function:
	// 存储语料库的graph
	// Representation invariant:
	// graph不能为null
	// Safety from rep exposure:
	// private final定义graph

	/**
	 * Create a new poet with the graph from corpus (as described above).
	 * 
	 * @param corpus text file from which to derive the poet's affinity graph
	 * @throws IOException if the corpus file cannot be found or read
	 */
	public GraphPoet(File corpus) throws IOException {
		BufferedReader bw = null;
		ArrayList<String> word = new ArrayList<>();
		bw = new BufferedReader(new FileReader(corpus));
		String line = "";
		while ((line = bw.readLine()) != null) {
			for (String s : line.split(" ")) {
				word.add(s.toLowerCase());
				graph.add(s.toLowerCase());
			}
		}
		bw.close();
		for (int i = 1; i < word.size(); ++i) {
			int lastEdgeWeight = graph.set(word.get(i - 1).toLowerCase(), word.get(i).toLowerCase(), 1);
			if (lastEdgeWeight != 0)
				graph.set(word.get(i - 1).toLowerCase(), word.get(i).toLowerCase(), lastEdgeWeight + 1);

		}
		checkRep();
		// throw new RuntimeException("not implemented");
	}

	// TODO checkRep
	private void checkRep() {
		assert graph != null;
	}

	/**
	 * Generate a poem.
	 * 
	 * @param input string from which to create the poem
	 * @return poem (as described above)
	 */
	public String poem(String input) {
		String[] word = input.split("\\s");
		String answer;
		String b = "";
		int weight = 0;
		int currentWeight = 0;
		answer = word[0];
		Set<String> result = new HashSet<>();
		Map<String, Integer> source = new HashMap<>();
		Map<String, Integer> target = new HashMap<>();
		for (int i = 1; i < word.length; ++i) {
			if (!graph.vertices().contains(word[i - 1].toLowerCase())
					|| !graph.vertices().contains(word[i].toLowerCase())) {
				answer = answer.concat(" " + word[i]);
				continue;
			}
			source = graph.sources(word[i].toLowerCase());
			target = graph.targets(word[i - 1].toLowerCase());

			result.clear();
			result.addAll(source.keySet());
			result.retainAll(target.keySet());
			if (!result.isEmpty()) {
				for (String s : result) {
					currentWeight = source.get(s).intValue() + target.get(s).intValue();
					if (currentWeight >= weight) {
						weight = currentWeight;
						b = s;
					}
				}
				answer = answer.concat(" " + b + " " + word[i]);
				b = "";
			} else {
				answer = answer.concat(" " + word[i]);
			}
			source.clear();
			target.clear();
			weight=0;
		}

		checkRep();
		return answer;
		// throw new RuntimeException("not implemented");
	}

	// TODO toString()
	/**
	 * 返回可读形式
	 * 
	 * @return String型 语料库可读形式
	 */
	@Override
	public String toString() {
		return graph.toString();
	}

}
