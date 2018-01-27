package com.example.mami.myapplication.adapter;

import android.support.annotation.IdRes;
import android.view.View;

import tellh.com.recyclertreeview_lib.LayoutItemType;

/**
 * Created by Mami on 1/17/2018.
 */

import android.support.annotation.IdRes;
        import android.view.View;

public abstract class CustomTreeViewBinder<VH extends android.support.v7.widget.RecyclerView.ViewHolder> implements LayoutItemType {
    public CustomTreeViewBinder() {
    }

    public abstract VH provideViewHolder(View var1);

    public abstract void bindView(VH var1, int var2, CustomTreeNode var3);

    public static class ViewHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        public ViewHolder(View rootView) {
            super(rootView);
        }

        protected <T extends View> T findViewById(@IdRes int id) {
            return this.itemView.findViewById(id);
        }
    }
}

