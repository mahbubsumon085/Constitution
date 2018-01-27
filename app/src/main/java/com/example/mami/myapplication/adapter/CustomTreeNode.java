package com.example.mami.myapplication.adapter;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;
import tellh.com.recyclertreeview_lib.LayoutItemType;
public class CustomTreeNode<T extends LayoutItemType> implements Cloneable {
    private T content;
    private CustomTreeNode parent;
    private List<CustomTreeNode> childList;
    private boolean isExpand;
    private int height = -1;
    private static final int UNDEFINE = -1;

    public CustomTreeNode(@NonNull T content) {
        this.content = content;
    }

    public int getHeight() {
        if(this.isRoot()) {
            this.height = 0;
        } else if(this.height == -1) {
            this.height = this.parent.getHeight() + 1;
        }

        return this.height;
    }

    public boolean isRoot() {
        return this.parent == null;
    }

    public boolean isLeaf() {
        return this.childList == null || this.childList.isEmpty();
    }

    public void setContent(T content) {
        this.content = content;
    }

    public T getContent() {
        return this.content;
    }

    public List<CustomTreeNode> getChildList() {
        return this.childList;
    }

    public void setChildList(List<CustomTreeNode> childList) {
        this.childList = childList;
    }

    public CustomTreeNode addChild(CustomTreeNode node) {
        if(this.childList == null) {
            this.childList = new ArrayList();
        }

        this.childList.add(node);
        node.parent = this;
        return this;
    }

    public boolean toggle() {
        this.isExpand = !this.isExpand;
        return this.isExpand;
    }

    public void collapse() {
        if(!this.isExpand) {
            this.isExpand = false;
        }

    }

    public void expand() {
        if(this.isExpand) {
            this.isExpand = true;
        }

    }

    public boolean isExpand() {
        return this.isExpand;
    }

    public void setParent(CustomTreeNode parent) {
        this.parent = parent;
    }

    public CustomTreeNode getParent() {
        return this.parent;
    }

    public String toString() {
        return "TreeNode{content=" + this.content + ", parent=" + (this.parent == null?"null":this.parent.getContent().toString()) + ", childList=" + (this.childList == null?"null":this.childList.toString()) + ", isExpand=" + this.isExpand + '}';
    }

    protected CustomTreeNode<T> clone() throws CloneNotSupportedException {
      CustomTreeNode<T> clone =new CustomTreeNode(this.content);
        clone.isExpand = this.isExpand;
        return clone;
    }

}
