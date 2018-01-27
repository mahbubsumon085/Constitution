package com.example.mami.myapplication.bean;

import android.support.annotation.NonNull;

import com.example.mami.myapplication.R;

import tellh.com.recyclertreeview_lib.LayoutItemType;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class ContentNode implements LayoutItemType ,Comparable {
    public boolean isPopulated() {
        return isPopulated;
    }

    public void setPopulated(boolean populated) {
        isPopulated = populated;
    }

    private boolean isPopulated;
    public String getColumnmTitle() {
        return columnmTitle;
    }

    public void setColumnmTitle(String columnmTitle) {
        this.columnmTitle = columnmTitle;
    }

    public String getColumnMessage() {
        return columnMessage;
    }

    public void setColumnMessage(String columnMessage) {
        this.columnMessage = columnMessage;
    }

    public String getColumnmBanglaTitle() {
        return columnmBanglaTitle;
    }

    public void setColumnmBanglaTitle(String columnmBanglaTitle) {
        this.columnmBanglaTitle = columnmBanglaTitle;
    }

    public String getColumnBanglaMessage() {
        return columnBanglaMessage;
    }

    public void setColumnBanglaMessage(String columnBanglaMessage) {
        this.columnBanglaMessage = columnBanglaMessage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    String columnmTitle;
    String columnMessage;
    String columnmBanglaTitle;
    String columnBanglaMessage;
    int id;

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    int articleId;
    int parentID;

    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        int compareage=((ContentNode)o).getId();
        /* For Ascending order*/
        return this.getId()-compareage;
    }
}
