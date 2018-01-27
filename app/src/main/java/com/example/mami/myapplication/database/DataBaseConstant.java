package com.example.mami.myapplication.database;

/**
 * Created by Mami on 11/29/2017.
 */

public class DataBaseConstant {
    public static String DATABASE_NAME="constitutin";
    public static int DATABASE_VERSION=1;
    public static String DATABASENAME="constitutin";

    /*
     table name
     */
    public static String TABLE_NAME_ROOT_INDEX="rootindex";
    public static String TABLE_NAME_CONTENT="content_table";

    /*
       Table column key
     */
    public static String COLUMN_ARTICLE_ID="article_id";
    public static String COLUMN_ID_KEY="id";
    public static String COLUMN_PARENT_ID_KEY="parent_id";
    public static String COLUMN_TITLE_KEY="title";
    public static String COLUMN_MESSAGE_KEY="message";
    public static String COLUMN_BENGALI_TITLE_KEY="bengali_title";
    public static String COLUMN_BENGALI_MESSAGE_KEY="bengali_message";
    public static String TABLE_BANGLA_AMMENDMENT="bangla_ammendment_table";
    public static String COLUMN_BANGLA_AMMENDMENT_MESSAGE="ammendment";
    public static String TABLE_ENGLISH_AMMENDMENT="english_ammendment_table";
}
