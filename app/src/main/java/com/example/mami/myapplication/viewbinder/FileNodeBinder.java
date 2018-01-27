package com.example.mami.myapplication.viewbinder;

import android.view.View;
import android.widget.TextView;

import com.example.mami.myapplication.R;
import com.example.mami.myapplication.adapter.CustomTreeNode;
import com.example.mami.myapplication.adapter.CustomTreeViewBinder;
import com.example.mami.myapplication.bean.File;
import com.example.mami.myapplication.database.DatabaseAccess;



/**
 * Created by tlh on 2016/10/1 :)
 */

public class FileNodeBinder extends CustomTreeViewBinder<FileNodeBinder.ViewHolder> {

    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, CustomTreeNode node) {
        File fileNode = (File) node.getContent();
        holder.tvName.setText(fileNode.fileName);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_file;
    }

    public class ViewHolder extends CustomTreeViewBinder.ViewHolder {
        public TextView tvName;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvName = (TextView) rootView.findViewById(R.id.tv_name);
        }

    }
}
