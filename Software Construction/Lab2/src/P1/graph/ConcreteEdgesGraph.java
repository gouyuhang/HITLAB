/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {

	private final Set<L> vertices = new HashSet<>();
	private final List<Edge<L>> edges = new ArrayList<>();

	// Abstraction function:
	// vertices代表图中点的集合
	// edges代表所有边的集合
	// Representation invariant:
	// vertices!=null
	// edges!=null
	// Safety from rep exposure:
	// filed全部private final
	// vertices unmodified

	// TODO constructor
	/**
	 * create new edge graph
	 */
	public ConcreteEdgesGraph() {
		checkRep();
	}

	// TODO checkRep
	private void checkRep() {
		assert vertices != null;
		assert edges != null;
	}

	@Override
	public boolean add(L vertex) {
		if (vertices.contains(vertex) || vertex.equals(null)) {
			checkRep();
			return false;
		}
		vertices.add(vertex);
		checkRep();
		return true;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public int set(L source, L target, int weight) {
		int lastEdgeWeight;
		if (source.equals(target) || source.equals(null) || target.equals(null)) {
			checkRep();
			return 0;
		}
		if (weight > 0) {
			for (Edge<L> edge : edges) {
				if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
					lastEdgeWeight = edge.getWeight();
					edges.remove(edge);
					Edge<L> updateEdge = new Edge<L>(source, target, weight);
					edges.add(updateEdge);
					checkRep();
					return lastEdgeWeight;
				}
			}
			if (!vertices.contains(source)) {
				vertices.add(source);
			}
			if (!vertices.contains(target)) {
				vertices.add(target);
			}
			Edge<L> updateEdge = new Edge<L>(source, target, weight);
			edges.add(updateEdge);
			checkRep();
			return 0;
		}
		if (weight == 0) {
			for (Edge<L> edge : edges) {
				if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
					lastEdgeWeight = edge.getWeight();
					edges.remove(edge);
					checkRep();
					return lastEdgeWeight;
				}
			}
			checkRep();
			return 0;
		}
		return 0;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public boolean remove(L vertex) {
		if (!vertices.contains(vertex) || vertex.equals(null)) {
			checkRep();
			return false;
		}
		edges.removeIf(edge -> edge.getSource().equals(vertex) || edge.getTarget().equals(vertex));
		vertices.remove(vertex);
		return true;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public Set<L> vertices() {
		checkRep();
		return Collections.unmodifiableSet(vertices);
		// throw new RuntimeException("not implemented");
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> map = new HashMap<>();
		for (Edge<L> edge : edges) {
			if (edge.getTarget().equals(target)) {
				map.put(edge.getSource(), edge.getWeight());
			}
		}
		checkRep();
		return map;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> map = new HashMap<>();
		for (Edge<L> edge : edges) {
			if (edge.getSource().equals(source)) {
				map.put(edge.getTarget(), edge.getWeight());
			}
		}
		checkRep();
		return map;
		// throw new RuntimeException("not implemented");
	}

	// TODO toString()
	/**
	 * 生成边的可读形式
	 * 
	 * @return target graph可直接阅读形式
	 */
	@Override
	public String toString() {
		String target = "";
		for (Edge<L> edge : edges) {
			target = target.concat(edge.toString());
		}
		checkRep();
		return target;
	}

}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph. 有向图中边的实现
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L> {

	// TODO fields
	private L source;
	private L target;
	private int weight;
	// Abstraction function:
	// source代表边的起始点
	// target代表边的终点
	// weight代表边的权值
	// Representation invariant:
	// weight>=0
	// source!=target
	// source!=null&&target!=null
	// Safety from rep exposure:
	// private而且返回用了防御式拷贝

	// TODO constructor
	/**
	 * create a new edge
	 * 
	 * @param source source!=null 起始点
	 * @param target target!=null 终止点
	 * @param weight weight>0 权值
	 */
	public Edge(L source, L target, int weight) {
		this.source = source;
		this.target = target;
		this.weight = weight;
		checkRep();
	}

	// TODO checkRep
	private void checkRep() {
		assert weight >= 0;
		assert source != null;
		assert target != null;
	}

	// TODO methods
	/**
	 * 获取边的起始点信息
	 * 
	 * @return 边的起点
	 */
	public L getSource() {
		L source = this.source;
		checkRep();
		return source;
	}

	/**
	 * 获取边的终止点信息
	 * 
	 * @return 边的终点
	 */
	public L getTarget() {
		L target = this.target;
		checkRep();
		return target;
	}

	/**
	 * 获取边的权值
	 * 
	 * @return 边的权值
	 */
	public int getWeight() {
		checkRep();
		return this.weight;
	}

	// TODO toString()
	/**
	 * 生成边的可读形式
	 * 
	 * @return 边的信息的可读形式
	 */
	public String toString() {
		String s = this.getSource().toString() + "->" + this.getTarget().toString() + ":" + this.getWeight();
		checkRep();
		return s;
	}

	/**
	 * Override equals
	 */
	@Override
	public boolean equals(Object thatOne) {
		if (!(thatOne instanceof Edge))
			return false;
		Edge<?> that = (Edge<?>) thatOne;
		checkRep();
		return this.source.equals(that.source) & this.target.equals(that.target) & this.weight == that.weight;

	}

	/**
	 * Override hashCode
	 */
	@Override
	public int hashCode() {
		int h = 1;
		h = 31 * h + this.source.hashCode();
		h = 31 * h + this.target.hashCode();
		h = 31 * h + this.weight;
		checkRep();
		return h;
	}

}
