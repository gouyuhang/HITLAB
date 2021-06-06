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
public class ConcreteVerticesGraph<L> implements Graph<L> {

	private final List<Vertex<L>> vertices = new ArrayList<>();

	// Abstraction function:
	// vertices��������ͼÿ���ڵ���Ϣ���б�
	// Representation invariant:
	// vertices!=null
	// vertices�в�������ͬԪ��
	// Safety from rep exposure:
	// private final����ɱ���������
	// collection unmodified

	// TODO constructor
	/**
	 * create new vertices graph
	 */
	public ConcreteVerticesGraph() {
		checkRep();
	}

	// TODO checkRep
	private void checkRep() {
		assert vertices != null;
		for (int i = 0; i < vertices.size() - 1; ++i) {
			for (int j = i + 1; j < vertices.size(); ++j) {
				assert !vertices.get(i).equals(vertices.get(j));
			}
		}
	}

	@Override
	public boolean add(L vertex) {
		if (vertex.equals(null)) {
			checkRep();
			return false;
		}
		for (Vertex<L> vertice : vertices) {
			if (vertice.getName().equals(vertex)) {
				checkRep();
				return false;
			}
		}
		Vertex<L> newVertex = new Vertex<L>(vertex);
		vertices.add(newVertex);
		checkRep();
		return true;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public int set(L source, L target, int weight) {
		if (source.equals(null) || target.equals(null)) {
			checkRep();
			return 0;
		}
		int lastWeight = 0;
		int sourceExistFlag = 0;
		int targetExistFlag = 0;
		int inMapFlag = 0;
		for (Vertex<L> vertice : vertices) {
			if (vertice.getName().equals(source)) {
				sourceExistFlag = 1;
			}
			if (vertice.getName().equals(target)) {
				targetExistFlag = 1;
			}
		}
		if (sourceExistFlag == 0) {
			Vertex<L> newVertex1 = new Vertex<L>(source);
			vertices.add(newVertex1);
		}
		if (targetExistFlag == 0) {
			Vertex<L> newVertex2 = new Vertex<L>(target);
			vertices.add(newVertex2);
		}
		if (weight > 0) {
			for (Vertex<L> vertice1 : vertices) {
				if (vertice1.getName().equals(source)) {
					for (L key : vertice1.getTarget().keySet()) {
						if (key.equals(target)) {
							lastWeight = vertice1.getTarget().get(key);
							vertice1.modifyTargetMap(target, weight);
							inMapFlag = 1;
							break;
						}
					}
					if (inMapFlag == 0) {
						lastWeight = 0;
						vertice1.modifyTargetMap(target, weight);
					}
					for (Vertex<L> vertice2 : vertices) {
						if (vertice2.getName().equals(target)) {
							vertice2.modifySourceMap(source, weight);
							return lastWeight;
						}
					}
				}
			}
		}
		if (weight == 0) {
			for (Vertex<L> vertice1 : vertices) {
				if (vertice1.getName().equals(source)) {
					for (L key : vertice1.getTarget().keySet()) {
						if (key.equals(target)) {
							lastWeight = vertice1.getTarget().get(key);
							vertice1.removeTargetMap(target);
							inMapFlag = 1;
							checkRep();
							break;
						}
					}
					if (inMapFlag == 0) {
						return 0;
					}
					for (Vertex<L> vertice2 : vertices) {
						if (vertice2.getName().equals(target)) {
							vertice2.removeSourceMap(source);
							checkRep();
							return lastWeight;
						}
					}
				}
			}
		}
		checkRep();
		return 0;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public boolean remove(L vertex) {
		if (vertex.equals(null)) {
			checkRep();
			return false;
		}
		for (Vertex<L> vertice1 : vertices) {
			if (vertice1.getName().equals(vertex)) {
				for (Vertex<L> vertice2 : vertices) {
					if (vertice1.getSource().containsKey(vertice2.getName())) {
						vertice2.removeTargetMap(vertex);
					}
					if (vertice1.getTarget().containsKey(vertice2.getName())) {
						vertice2.removeSourceMap(vertex);
					}
				}
				vertices.remove(vertice1);
				checkRep();
				return true;
			}
		}
		checkRep();
		return false;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public Set<L> vertices() {
		Set<L> targetSet = new HashSet<>();
		for (Vertex<L> vertice : vertices) {
			targetSet.add(vertice.getName());
		}
		checkRep();
		return targetSet;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public Map<L, Integer> sources(L target) {
		Map<L, Integer> target2 = new HashMap<>();
		for (Vertex<L> vertice : vertices) {
			if (vertice.getName().equals(target)) {
				checkRep();
				return Collections.unmodifiableMap(vertice.getSource());
			}
		}
		return target2;
		// throw new RuntimeException("not implemented");
	}

	@Override
	public Map<L, Integer> targets(L source) {
		Map<L, Integer> target = new HashMap<>();
		for (Vertex<L> vertice : vertices) {
			if (vertice.getName().equals(source)) {
				checkRep();
				return Collections.unmodifiableMap(vertice.getTarget());
			}
		}
		checkRep();
		return target;
		// throw new RuntimeException("not implemented");
	}

	// TODO toString()
	@Override
	public String toString() {
		String target = "";
		for (Vertex<L> vertice : vertices) {
			target = target.concat(vertice.toString());
		}
		checkRep();
		return target;
	}
}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph. ��������ͼ�����һ���ڵ���Ϣ
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L> {

	// TODO fields
	private L name;
	private final Map<L, Integer> source;
	private final Map<L, Integer> target;
	// Abstraction function:
	// name����ñ߽ڵ������
	// source��ָ��ǰ�ڵ�ıߵ���ʼ����Ȩֵ��ӳ��
	// target�Ǵӵ�ǰ�ڵ�ָ���ıߵ���ֹ����Ȩֵ��ӳ��
	// Representation invariant:
	// source,target��value>0
	// source,target��key!=null
	// soure,target�в�������ͬkeyֵ
	// Safety from rep exposure:
	// map source��map target��get�����÷���ʽ����
	// field����ȫ��private
	// collection unmodified

	// TODO constructor
	/**
	 * create a new vertex
	 * 
	 * @param name name!=null ��ʼ�������
	 */
	public Vertex(L name) {
		this.name = name;
		this.source = new HashMap<>();
		this.target = new HashMap<>();
		checkRep();
	}

	// TODO checkRep
	private void checkRep() {
		for (L key : this.source.keySet()) {
			assert key != null;
			assert this.source.get(key) > 0;
			assert key != this.name;
		}
		for (L key : this.target.keySet()) {
			assert key != null;
			assert this.target.get(key) > 0;
			assert key != this.name;
		}
	}

	// TODO methods
	/**
	 * ��ȡ�ڵ�����
	 * 
	 * @return �ڵ�����
	 */
	public L getName() {
		L name = this.name;
		checkRep();
		return name;
	}

	/**
	 * ��ȡָ��ýڵ��map������ʼ����Ȩֵ
	 * 
	 * @return ָ��ýڵ��map������ʼ����Ȩֵ
	 */
	public Map<L, Integer> getSource() {
		checkRep();
		return Collections.unmodifiableMap(this.source);
	}

	/**
	 * ��ȡ�Ӹýڵ�ָ����map
	 * 
	 * @return �Ӹýڵ�ָ����map������ֹ����Ȩֵ
	 */
	public Map<L, Integer> getTarget() {
		checkRep();
		return Collections.unmodifiableMap(this.target);
	}

	/**
	 * �޸�sourceӳ���нڵ���Ȩֵ����Ϣ
	 * 
	 * @param source ��Ҫ�޸���ʼ������� ��Ϊnull
	 * @param weight ��Ҫ�޸ĵ�Ȩֵ��ֵ>0
	 */
	public void modifySourceMap(L source, int weight) {
		this.source.put(source, weight);
		checkRep();
	}

	/**
	 * �޸�targetӳ���нڵ���Ȩֵ����Ϣ
	 * 
	 * @param source ��Ҫ�޸���ֹ������� ��Ϊnull
	 * @param weight ��Ҫ�޸ĵ�Ȩֵ��ֵ>0
	 */
	public void modifyTargetMap(L source, int weight) {
		this.target.put(source, weight);
		checkRep();
	}

	/**
	 * ��sourceӳ����ɾ���ڵ�
	 * 
	 * @param name ��Ҫɾ���Ľڵ����� ��Ϊnull
	 */
	public void removeSourceMap(L name) {
		this.source.remove(name);
		checkRep();
	}

	/**
	 * ��targetӳ����ɾ���ڵ�
	 * 
	 * @param name ��Ҫɾ���Ľڵ����� ��Ϊnull
	 */
	public void removeTargetMap(L name) {
		this.target.remove(name);
		checkRep();
	}

	// TODO toString()
	/**
	 * ���ɽڵ�Ŀɶ���Ϣ
	 * 
	 * @return �ڵ���Ϣ�Ŀɶ���ʽ
	 */
	@Override
	public String toString() {
		String s = "";
		for (L key : this.getTarget().keySet()) {
			s = s.concat(this.getName().toString() + "->" + key.toString() + ":" + this.getTarget().get(key));
		}
		for (L key : this.getSource().keySet()) {
			s = s.concat(key.toString() + "->" + this.getName().toString() + ":" + this.getSource().get(key));
		}
		checkRep();
		return s;
	}
}
