package com.example.mami.myapplication.lisenar;

import com.example.mami.myapplication.adapter.CustomTreeNode;
import com.example.mami.myapplication.bean.ContentNode;
import com.example.mami.myapplication.viewbinder.DirectoryNodeBinder;

import tellh.com.recyclertreeview_lib.TreeNode;

/**
 * Created by Mami on 12/7/2017.
 */

public interface SpanClickLisenar {
    public void onSpanClickLisenar(String span);
    public void onSpanExpandOrRemoveLisenar(DirectoryNodeBinder.ViewHolder holder);
}
