package com.example.mami.myapplication.adapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.util.DiffUtil.Callback;
import android.support.v7.util.DiffUtil.DiffResult;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import com.example.mami.myapplication.bean.ContentNode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;



/**
 * Created by Mami on 1/17/2018.
 */

public class CustomTreeAdapter extends Adapter<ViewHolder> {
    private static final String KEY_IS_EXPAND = "IS_EXPAND";
    private final List<? extends CustomTreeViewBinder> viewBinders;
    private List<CustomTreeNode> displayNodes;
    private int padding;
    private CustomTreeAdapter.OnTreeNodeListener onTreeNodeListener;
    private boolean toCollapseChild;

    public CustomTreeAdapter(List<? extends CustomTreeViewBinder> viewBinders) {
        this((List)null, viewBinders);
    }

    public CustomTreeAdapter(List<CustomTreeNode> nodes, List<? extends CustomTreeViewBinder> viewBinders) {
        this.padding = 30;
        this.displayNodes = new ArrayList();
        if(nodes != null) {
            this.findDisplayNodes(nodes);
        }

        this.viewBinders = viewBinders;
    }
    private void findDisplayNodes(List<CustomTreeNode> nodes) {
        Iterator var2 = nodes.iterator();

        while(var2.hasNext()) {
            CustomTreeNode node = (CustomTreeNode)var2.next();
            this.displayNodes.add(node);
            if(!node.isLeaf() && node.isExpand()) {
                this.findDisplayNodes(node.getChildList());
            }
        }

    }

    public int getItemViewType(int position) {
        return ((CustomTreeNode)this.displayNodes.get(position)).getContent().getLayoutId();
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(viewType, parent, false);
        if(this.viewBinders.size() == 1) {
            return ((CustomTreeViewBinder)this.viewBinders.get(0)).provideViewHolder(v);
        } else {
            Iterator var4 = this.viewBinders.iterator();

            CustomTreeViewBinder viewBinder;
            do {
                if(!var4.hasNext()) {
                    return ((CustomTreeViewBinder)this.viewBinders.get(0)).provideViewHolder(v);
                }

                viewBinder = (CustomTreeViewBinder)var4.next();
            } while(viewBinder.getLayoutId() != viewType);

            return viewBinder.provideViewHolder(v);
        }
    }

    public void onBindViewHolder(ViewHolder holder, int position, List<Object> payloads) {
        if(payloads != null && !payloads.isEmpty()) {
            Bundle b = (Bundle)payloads.get(0);
            Iterator var5 = b.keySet().iterator();

            while(var5.hasNext()) {
                String key = (String)var5.next();
                byte var8 = -1;
                switch(key.hashCode()) {
                    case 296813391:
                        if(key.equals("IS_EXPAND")) {
                            var8 = 0;
                        }
                    default:
                        switch(var8) {
                            case 0:
                                if(this.onTreeNodeListener != null) {
                                    this.onTreeNodeListener.onToggle(b.getBoolean(key), holder);
                                }
                        }
                }
            }
        }

        super.onBindViewHolder(holder, position, payloads);
    }

    public void onclickNode(ViewHolder holder){
        CustomTreeNode selectedNode = (CustomTreeNode)CustomTreeAdapter.this.displayNodes.get(holder.getLayoutPosition());
        try {
            long lastClickTime = ((Long)holder.itemView.getTag()).longValue();
            if(System.currentTimeMillis() - lastClickTime < 500L) {
                return;
            }
        } catch (Exception var5) {
            holder.itemView.setTag(Long.valueOf(System.currentTimeMillis()));
        }
        Log.d("selectedNo","5");
        holder.itemView.setTag(Long.valueOf(System.currentTimeMillis()));
        if(CustomTreeAdapter.this.onTreeNodeListener == null || !CustomTreeAdapter.this.onTreeNodeListener.onClick(selectedNode, holder)) {

            if(selectedNode.isLeaf()){
                selectedNode=selectedNode.getParent();
                if(selectedNode==null){
                    return;
                }

            }
            final ContentNode dirNode = (ContentNode) selectedNode.getContent();
            Log.d("selectedNo","7");
            boolean isExpand = selectedNode.isExpand();
            int positionStart = CustomTreeAdapter.this.displayNodes.indexOf(selectedNode) + 1;
            if(!isExpand) {
                Log.d("selectedNo","8");
                CustomTreeAdapter.this.notifyItemRangeInserted(positionStart, CustomTreeAdapter.this.addChildNodes(selectedNode, positionStart));
            } else {
                Log.d("selectedNo","9");
                CustomTreeAdapter.this.notifyItemRangeRemoved(positionStart, CustomTreeAdapter.this.removeChildNodes(selectedNode, true));
            }


        }
    }
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.itemView.setPadding(((CustomTreeNode)this.displayNodes.get(position)).getHeight() * this.padding, 3, 3, 3);
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                onclickNode(holder);
            }
        });
        Iterator var3 = this.viewBinders.iterator();

        while(var3.hasNext()) {
            CustomTreeViewBinder viewBinder = (CustomTreeViewBinder)var3.next();
            if(viewBinder.getLayoutId() == ((CustomTreeNode)this.displayNodes.get(position)).getContent().getLayoutId()) {
                viewBinder.bindView(holder, position, (CustomTreeNode)this.displayNodes.get(position));
            }
        }

    }

    private int addChildNodes(CustomTreeNode pNode, int startIndex) {
        List<CustomTreeNode> childList = pNode.getChildList();
        int addChildCount = 0;
        Iterator var5 = childList.iterator();

        while(var5.hasNext()) {
            CustomTreeNode treeNode = (CustomTreeNode)var5.next();
            this.displayNodes.add(startIndex + addChildCount++, treeNode);
            if(treeNode.isExpand()) {
                addChildCount += this.addChildNodes(treeNode, startIndex + addChildCount);
            }
        }

        if(!pNode.isExpand()) {
            pNode.toggle();
        }

        return addChildCount;
    }

    private int removeChildNodes(CustomTreeNode pNode) {
        return this.removeChildNodes(pNode, true);
    }

    private int removeChildNodes(CustomTreeNode pNode, boolean shouldToggle) {
        if(pNode.isLeaf()) {
            return 0;
        } else {
            List<CustomTreeNode> childList = pNode.getChildList();
            int removeChildCount = childList.size();
            this.displayNodes.removeAll(childList);
            Iterator var5 = childList.iterator();

            while(var5.hasNext()) {
                CustomTreeNode child = (CustomTreeNode)var5.next();
                if(child.isExpand()) {
                    if(this.toCollapseChild) {
                        child.toggle();
                    }

                    removeChildCount += this.removeChildNodes(child, false);
                }
            }

            if(shouldToggle) {
                pNode.toggle();
            }

            return removeChildCount;
        }
    }

    public int getItemCount() {
        return this.displayNodes == null?0:this.displayNodes.size();
    }

    public void setPadding(int padding) {
        this.padding = padding;
    }

    public void ifCollapseChildWhileCollapseParent(boolean toCollapseChild) {
        this.toCollapseChild = toCollapseChild;
    }

    public void setOnTreeNodeListener(CustomTreeAdapter.OnTreeNodeListener onTreeNodeListener) {
        this.onTreeNodeListener = onTreeNodeListener;
    }

    public void refresh(List<CustomTreeNode> treeNodes) {
        this.displayNodes.clear();
        this.findDisplayNodes(treeNodes);
        this.notifyDataSetChanged();
    }

    public Iterator<CustomTreeNode> getDisplayNodesIterator() {
        return this.displayNodes.iterator();
    }

    private void notifyDiff(final List<CustomTreeNode> temp) {
        DiffResult diffResult = DiffUtil.calculateDiff(new Callback() {
            public int getOldListSize() {
                return temp.size();
            }

            public int getNewListSize() {
                return CustomTreeAdapter.this.displayNodes.size();
            }

            public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return CustomTreeAdapter.this.areItemsTheSame((CustomTreeNode)temp.get(oldItemPosition), (CustomTreeNode)CustomTreeAdapter.this.displayNodes.get(newItemPosition));
            }

            public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return CustomTreeAdapter.this.areContentsTheSame((CustomTreeNode)temp.get(oldItemPosition), (CustomTreeNode)CustomTreeAdapter.this.displayNodes.get(newItemPosition));
            }

            @Nullable
            public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                return CustomTreeAdapter.this.getChangePayload((CustomTreeNode)temp.get(oldItemPosition), (CustomTreeNode)CustomTreeAdapter.this.displayNodes.get(newItemPosition));
            }
        });
        diffResult.dispatchUpdatesTo(this);
    }

    private Object getChangePayload(CustomTreeNode oldNode, CustomTreeNode newNode) {
        Bundle diffBundle = new Bundle();
        if(newNode.isExpand() != oldNode.isExpand()) {
            diffBundle.putBoolean("IS_EXPAND", newNode.isExpand());
        }

        return diffBundle.size() == 0?null:diffBundle;
    }

    private boolean areContentsTheSame(CustomTreeNode oldNode, CustomTreeNode newNode) {
        return oldNode.getContent() != null && oldNode.getContent().equals(newNode.getContent()) && oldNode.isExpand() == newNode.isExpand();
    }

    private boolean areItemsTheSame(CustomTreeNode oldNode, CustomTreeNode newNode) {
        return oldNode.getContent() != null && oldNode.getContent().equals(newNode.getContent());
    }

    public void collapseAll() {
        List<CustomTreeNode> temp = this.backupDisplayNodes();
        List<CustomTreeNode> roots = new ArrayList();
        Iterator var3 = this.displayNodes.iterator();

        CustomTreeNode root;
        while(var3.hasNext()) {
            root = (CustomTreeNode)var3.next();
            if(root.isRoot()) {
                roots.add(root);
            }
        }

        var3 = roots.iterator();

        while(var3.hasNext()) {
            root = (CustomTreeNode)var3.next();
            if(root.isExpand()) {
                this.removeChildNodes(root);
            }
        }

        this.notifyDiff(temp);
    }
    @NonNull
    private List<CustomTreeNode> backupDisplayNodes() {
        List<CustomTreeNode> temp = new ArrayList();
        Iterator var2 = this.displayNodes.iterator();

        while(var2.hasNext()) {
            CustomTreeNode displayNode = (CustomTreeNode)var2.next();

            try {
                temp.add(displayNode.clone());
            } catch (CloneNotSupportedException var5) {
                temp.add(displayNode);
            }
        }

        return temp;
    }


    public void collapseNode(CustomTreeNode pNode) {
        List<CustomTreeNode> temp = this.backupDisplayNodes();
        this.removeChildNodes(pNode);
        this.notifyDiff(temp);
    }

    public void collapseBrotherNode(CustomTreeNode pNode) {
        List<CustomTreeNode> temp = this.backupDisplayNodes();
        if(pNode.isRoot()) {
            List<CustomTreeNode> roots = new ArrayList();
            Iterator var4 = this.displayNodes.iterator();

            CustomTreeNode root;
            while(var4.hasNext()) {
                root = (CustomTreeNode)var4.next();
                if(root.isRoot()) {
                    roots.add(root);
                }
            }

            var4 = roots.iterator();

            while(var4.hasNext()) {
                root = (CustomTreeNode)var4.next();
                if(root.isExpand() && !root.equals(pNode)) {
                    this.removeChildNodes(root);
                }
            }
        } else {
            CustomTreeNode parent = pNode.getParent();
            if(parent == null) {
                return;
            }

            List<CustomTreeNode> childList = parent.getChildList();
            Iterator var9 = childList.iterator();

            while(var9.hasNext()) {
                CustomTreeNode node = (CustomTreeNode)var9.next();
                if(!node.equals(pNode) && node.isExpand()) {
                    this.removeChildNodes(node);
                }
            }
        }

        this.notifyDiff(temp);
    }

    public interface OnTreeNodeListener {
        boolean onClick(CustomTreeNode var1, ViewHolder var2);

        void onToggle(boolean var1, ViewHolder var2);
    }
}
