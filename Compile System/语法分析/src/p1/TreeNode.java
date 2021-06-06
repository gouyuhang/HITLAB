package p1;
import p1.Token;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class TreeNode<Verctor> implements Iterable<TreeNode<Verctor>>{
	public Verctor data;
	
    /**
     * 父节点，根没有父节点
     */
    public TreeNode<Verctor> parent;

    /**
     * 子节点，叶子节点没有子节点
     */
    public List<TreeNode<Verctor>> children;

    /**
     * 保存了当前节点及其所有子节点，方便查询
     */
    private List<TreeNode<Verctor>> elementsIndex;

    /**
     * 构造函数
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
     * 判断是否为根：根没有父节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断是否为叶子节点：子节点没有子节点
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 添加一个子节点
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
     * 获取当前节点的层
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
     * 递归为当前节点以及当前节点的所有父节点增加新的节点
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
     * 从当前节点及其所有子节点中搜索某节点
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
     * 获取当前节点的迭代器
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
