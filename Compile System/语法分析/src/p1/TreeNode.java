package p1;
import p1.Token;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<Verctor> implements Iterable<TreeNode<Verctor>>{
	public Verctor data;
	
    /**
     * ���ڵ㣬��û�и��ڵ�
     */
    public TreeNode<Verctor> parent;

    /**
     * �ӽڵ㣬Ҷ�ӽڵ�û���ӽڵ�
     */
    public List<TreeNode<Verctor>> children;

    /**
     * �����˵�ǰ�ڵ㼰�������ӽڵ㣬�����ѯ
     */
    private List<TreeNode<Verctor>> elementsIndex;

    /**
     * ���캯��
     *
     * @param data
     */
    public TreeNode(Verctor data) {
        this.data = data;
        this.children = new LinkedList<TreeNode<Verctor>>();
        this.elementsIndex = new LinkedList<TreeNode<Verctor>>();
        this.elementsIndex.add(this);
    }

    /**
     * �ж��Ƿ�Ϊ������û�и��ڵ�
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * �ж��Ƿ�ΪҶ�ӽڵ㣺�ӽڵ�û���ӽڵ�
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * ���һ���ӽڵ�
     *
     * @param child
     * @return
     */
    public TreeNode<Verctor> addChild(Verctor child) {
        TreeNode<Verctor> childNode = new TreeNode<Verctor>(child);

        childNode.parent = this;

        this.children.add(childNode);

        this.registerChildForSearch(childNode);

        return childNode;
    }

    /**
     * ��ȡ��ǰ�ڵ�Ĳ�
     *
     * @return
     */
    public int getLevel() {
        if (this.isRoot()) {
            return 0;
        } else {
            return parent.getLevel() + 1;
        }
    }

    /**
     * �ݹ�Ϊ��ǰ�ڵ��Լ���ǰ�ڵ�����и��ڵ������µĽڵ�
     *
     * @param node
     */
    private void registerChildForSearch(TreeNode<Verctor> node) {
        elementsIndex.add(node);
        if (parent != null) {
            parent.registerChildForSearch(node);
        }
    }

    /**
     * �ӵ�ǰ�ڵ㼰�������ӽڵ�������ĳ�ڵ�
     *
     * @param cmp
     * @return
     */
    public TreeNode<Verctor> findTreeNode(Verctor s) {
        for (TreeNode<Verctor> element : this.elementsIndex) {
            Verctor elData = element.data;
            Token a=(Token) elData;
            Token b=(Token) s;
            //System.out.print(a.name()+"\n");
            //System.out.print(b.name()+"\n");
            if (a.equals(b) && element.isLeaf())
                return element;
        }

        return null;
    }

    /**
     * ��ȡ��ǰ�ڵ�ĵ�����
     *
     * @return
     */
    @Override
    public Iterator<TreeNode<Verctor>> iterator() {
        TreeNodeIterator<Verctor> iterator = new TreeNodeIterator<Verctor>(this);
        return iterator;
    }

    @Override
    public String toString() {
        return data != null ? data.toString() : "[tree data null]";
    }
    private static String createIndent(int depth) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < depth; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }



}
