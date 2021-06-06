package p1;

import java.util.Iterator;

public class TreeNodeIterator<Token> implements Iterator<TreeNode<Token>>{
	enum ProcessStages {
        ProcessParent, ProcessChildCurNode, ProcessChildSubNode
    }

    private ProcessStages doNext;

    private TreeNode<Token> next;

    private Iterator<TreeNode<Token>> childrenCurNodeIter;

    private Iterator<TreeNode<Token>> childrenSubNodeIter;

    private TreeNode<Token> treeNode;

    public TreeNodeIterator(TreeNode<Token> treeNode) {
        this.treeNode = treeNode;
        this.doNext = ProcessStages.ProcessParent;
        this.childrenCurNodeIter = treeNode.children.iterator();
    }

    @Override
    public boolean hasNext() {

        if (this.doNext == ProcessStages.ProcessParent) {
            this.next = this.treeNode;
            this.doNext = ProcessStages.ProcessChildCurNode;
            return true;
        }

        if (this.doNext == ProcessStages.ProcessChildCurNode) {
            if (childrenCurNodeIter.hasNext()) {
                TreeNode<Token> childDirect = childrenCurNodeIter.next();
                childrenSubNodeIter = childDirect.iterator();
                this.doNext = ProcessStages.ProcessChildSubNode;
                return hasNext();
            } else {
                this.doNext = null;
                return false;
            }
        }

        if (this.doNext == ProcessStages.ProcessChildSubNode) {
            if (childrenSubNodeIter.hasNext()) {
                this.next = childrenSubNodeIter.next();
                return true;
            } else {
                this.next = null;
                this.doNext = ProcessStages.ProcessChildCurNode;
                return hasNext();
            }
        }

        return false;
    }

    @Override
    public TreeNode<Token> next() {
        return this.next;
    }
}
