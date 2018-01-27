package com.example.mami.myapplication.database;

/**
 * Created by Mami on 11/29/2017.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mami.myapplication.bean.AmmendMent;
import com.example.mami.myapplication.bean.ContentNode;

import java.lang.invoke.ConstantCallSite;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    /**
     * Private constructor to aboid object creation from outside classes.
     *
     * @param context
     */
    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    /**
     * Return a singleton instance of DatabaseAccess.
     *
     * @param context the Context
     * @return the instance of DabaseAccess
     */
    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    /**
     * Open the database connection.
     */
    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    /**
     * Close the database connection.
     */
    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    /**
     * Read all quotes from the database.
     *
     * @return a List of quotes
     */
    public List<ContentNode> getContentNode() {
        Log.d("Collections","2  ");
        ContentNode wrongPlaced9A=null;
        ArrayList<ContentNode> list = new ArrayList<>();
        Log.d("Collections","3 ");
        Cursor cursor = database.rawQuery("SELECT "+ DataBaseConstant.COLUMN_ID_KEY+","+DataBaseConstant.COLUMN_PARENT_ID_KEY+","+
                DataBaseConstant.COLUMN_ARTICLE_ID +" FROM "+DataBaseConstant.TABLE_NAME_CONTENT, null);
        cursor.moveToFirst();
        Log.d("Collections","4 ");
        while (!cursor.isAfterLast()) {
            ContentNode contentNode=new ContentNode();
            contentNode.setId(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.COLUMN_ID_KEY)));
            contentNode.setParentID(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.COLUMN_PARENT_ID_KEY)));
            contentNode.setArticleId(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.COLUMN_ARTICLE_ID)));
            if(contentNode.getId()==694){
                wrongPlaced9A=new ContentNode();
                wrongPlaced9A.setId(contentNode.getId());
            }
            else{
                list.add(contentNode);
            }
            cursor.moveToNext();


            Log.d("ArticleId","Size   "+contentNode.getId()+"     "+contentNode.getArticleId());
            String b="";

        }


        cursor.close();
        Collections.sort(list);
        if(wrongPlaced9A!=null){
            list.add(17,wrongPlaced9A);
        }
        Log.d("Collections","Size   "+list.size());

        return list;

    }

    public ArrayList<AmmendMent> getEnglishAmmendMent(){
        ArrayList<AmmendMent> list = new ArrayList<>();
        Log.d("BanglaAmmend","3 ");
        Cursor cursor = database.rawQuery("SELECT "+ DataBaseConstant.COLUMN_ID_KEY+","+DataBaseConstant.COLUMN_BANGLA_AMMENDMENT_MESSAGE+
                " FROM "+DataBaseConstant.TABLE_ENGLISH_AMMENDMENT, null);
        cursor.moveToFirst();
        Log.d("BanglaAmmend","4 ");
        while (!cursor.isAfterLast()) {
            AmmendMent contentNode=new AmmendMent();
            contentNode.setAmmendMentId(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.COLUMN_ID_KEY)));
            contentNode.setAmmendMentString(cursor.getString(cursor.getColumnIndex(DataBaseConstant.COLUMN_BANGLA_AMMENDMENT_MESSAGE)));
            list.add(contentNode);
            cursor.moveToNext();
            Log.d("BanglaAmmend","Size   "+contentNode.getAmmendMentId()+"     "+contentNode.getAmmendMentString());
            String b="";

        }
        cursor.close();
        return list;
    }
    public ArrayList<AmmendMent> getBanglaAmmendMent(){
        ArrayList<AmmendMent> list = new ArrayList<>();
        Log.d("BanglaAmmend","3 ");
        Cursor cursor = database.rawQuery("SELECT "+ DataBaseConstant.COLUMN_ID_KEY+","+DataBaseConstant.COLUMN_BANGLA_AMMENDMENT_MESSAGE+
                " FROM "+DataBaseConstant.TABLE_BANGLA_AMMENDMENT, null);
        cursor.moveToFirst();
        Log.d("BanglaAmmend","4 ");
        while (!cursor.isAfterLast()) {
            AmmendMent contentNode=new AmmendMent();
            contentNode.setAmmendMentId(cursor.getInt(cursor.getColumnIndex(DataBaseConstant.COLUMN_ID_KEY)));
            contentNode.setAmmendMentString(cursor.getString(cursor.getColumnIndex(DataBaseConstant.COLUMN_BANGLA_AMMENDMENT_MESSAGE)));
            list.add(contentNode);
            cursor.moveToNext();
             Log.d("BanglaAmmend","Size   "+contentNode.getAmmendMentId()+"     "+contentNode.getAmmendMentString());
            String b="";

        }
        cursor.close();
        return list;
    }
    public void populateContentNodeDetails(ContentNode contentNode){
//        database.rawQuery("select * from content_table where id=?"
//                ,new String [] {String.valueOf(contentNode.getId())});
        String query ="SELECT * FROM "+DataBaseConstant.TABLE_NAME_CONTENT+" where "+
                DataBaseConstant.COLUMN_ID_KEY +" = '"+contentNode.getId()+"'";
        Cursor cursor=database.rawQuery(query,null);
//      ("SELECT * FROM "+DataBaseConstant.TABLE_NAME_CONTENT+" where "+
//                        Data  Cursor cursor = database.rawQueryBaseConstant.COLUMN_ID_KEY +"=?"
//                , new String[] {String.valueOf(contentNode.getId())});
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            contentNode.setColumnmTitle(cursor.getString(cursor.getColumnIndex(DataBaseConstant.COLUMN_TITLE_KEY)));
            contentNode.setColumnMessage(cursor.getString(cursor.getColumnIndex(DataBaseConstant.COLUMN_MESSAGE_KEY)));
            contentNode.setColumnmBanglaTitle(cursor.getString(cursor.getColumnIndex(DataBaseConstant.COLUMN_BENGALI_TITLE_KEY)));
            contentNode.setColumnBanglaMessage(cursor.getString(cursor.getColumnIndex(DataBaseConstant.COLUMN_BENGALI_MESSAGE_KEY)));
            contentNode.setPopulated(true);
            break;
        }
        cursor.close();
    }
}