









package com.example.mami.myapplication.viewbinder;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mami.myapplication.R;
import com.example.mami.myapplication.adapter.CustomTreeNode;
import com.example.mami.myapplication.adapter.CustomTreeViewBinder;
import com.example.mami.myapplication.bean.ContentNode;
import com.example.mami.myapplication.database.DatabaseAccess;
import com.example.mami.myapplication.lisenar.SpanClickLisenar;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by tlh on 2016/10/1 :)
 */

public class DirectoryNodeBinder extends CustomTreeViewBinder<DirectoryNodeBinder.ViewHolder> {
    DatabaseAccess databaseAccess;
    SpanClickLisenar spanClickLisenar;
    public void setEnglish(boolean english) {
        isEnglish = english;
    }

    boolean isEnglish;
    public DirectoryNodeBinder(DatabaseAccess databaseAccess, boolean isEnglish, SpanClickLisenar spanClickLisenar){
        this.databaseAccess=databaseAccess;
        this.isEnglish=isEnglish;
        this.spanClickLisenar=spanClickLisenar;
    }
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }
    public void setTitle(ViewHolder finalHolder,ContentNode dirNode){


    }
    @Override
    public void bindView(final ViewHolder holder, int position, CustomTreeNode node) {
        int rotateDegree = node.isExpand() ? 90 : 0;
        final ContentNode dirNode = (ContentNode) node.getContent();
            if(dirNode.isPopulated()){
            if(isEnglish){
                if(dirNode.getColumnmTitle()!=null){
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.tvTitle.setText(dirNode.getColumnmTitle());

                    holder.tvMessage.setVisibility(View.VISIBLE);
                    // holder.tvMessage.setText(dirNode.getColumnMessage());
                    holder.tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.tvMessage.setText(addClickablePart(dirNode.getColumnmTitle()));
                }
                else{
                    holder.tvTitle.setVisibility(View.GONE);
                }
                if(dirNode.getColumnMessage()!=null){
                    holder.tvMessage.setVisibility(View.VISIBLE);
                    // holder.tvMessage.setText(dirNode.getColumnMessage());
                    holder.tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.tvMessage.setText(addClickablePart(dirNode.getColumnMessage()));
                }
                else{
                    holder.tvMessage.setVisibility(View.GONE);
                }
            }
            else{
                if(dirNode.getColumnmBanglaTitle()!=null){
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.tvTitle.setText(dirNode.getColumnmBanglaTitle());
                }
                else{
                    holder.tvTitle.setVisibility(View.GONE);
                }
                if(dirNode.getColumnBanglaMessage()!=null){
                    holder.tvMessage.setVisibility(View.VISIBLE);
                    // holder.tvMessage.setText(dirNode.getColumnBanglaMessage());
                    holder.tvMessage.setText(addClickablePart(dirNode.getColumnBanglaMessage()));
                    holder.tvMessage.setMovementMethod(LinkMovementMethod.getInstance());

                }
                else{
                    holder.tvMessage.setVisibility(View.GONE);
                }
            }

        }
        else{
            databaseAccess.populateContentNodeDetails(dirNode);
            if(isEnglish){
                if(dirNode.getColumnmTitle()!=null){
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.tvTitle.setText(dirNode.getColumnmTitle());
                }
                else{
                    holder.tvTitle.setVisibility(View.GONE);
                }
                if(dirNode.getColumnMessage()!=null){
                    holder.tvMessage.setVisibility(View.VISIBLE);
                    holder.tvMessage.setMovementMethod(LinkMovementMethod.getInstance());
                    holder.tvMessage.setText(addClickablePart(dirNode.getColumnMessage()));
                }
                else{
                    holder.tvMessage.setVisibility(View.GONE);
                }
            }
            else{
                if(dirNode.getColumnmBanglaTitle()!=null){
                    holder.tvTitle.setVisibility(View.VISIBLE);
                    holder.tvTitle.setText(dirNode.getColumnmBanglaTitle());
                }
                else{
                    holder.tvTitle.setVisibility(View.GONE);
                }
                if(dirNode.getColumnBanglaMessage()!=null){
                    holder.tvMessage.setVisibility(View.VISIBLE);
                    // holder.tvMessage.setText(dirNode.getColumnBanglaMessage());
                    holder.tvMessage.setText(addClickablePart(dirNode.getColumnBanglaMessage()));
                    holder.tvMessage.setMovementMethod(LinkMovementMethod.getInstance());

                }
                else{
                    holder.tvMessage.setVisibility(View.GONE);
                }
            }
        }
        holder.tvMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position =(int)v.getTag();
                    spanClickLisenar.onSpanExpandOrRemoveLisenar(holder);
                    Log.d("BapreBap","Babaji Click dilam ");




            }
        });
       // setTextClickMethod(holder.tvMessage,node);
        holder.tvMessage.setTag(position);
    }


    private SpannableStringBuilder addClickablePart(String str) {
        SpannableStringBuilder ssb = new SpannableStringBuilder(str);

        int idx1 = str.indexOf("[");
        String matched="০১২৩৪৫৬৭৮৯";
        String guess="[";
        int index = str.indexOf(guess);
        while (index >= 0) {
            System.out.println(index);

            Log.d("ClickeSpan","index  "+index);
            if(index>0){
                boolean isFound=false;
                int start=0;
                if((index-3)>=0){
                    if(matched.contains(str.substring(index-1,index))){
                        isFound=true;
                        start=index-1;
                        if(matched.contains(str.substring(index-2,index-1))) {
                            start=index-2;
                        }
                        if(matched.contains(str.substring(index-3,index-2))){
                            start=index-3;
                        }
                    }
                }
               else  if((index-2)>=0){
                    if(matched.contains(str.substring(index-1,index))){
                        isFound=true;
                        start=index-1;
                        if(matched.contains(str.substring(index-2,index-1))) {
                            start=index-2;
                        }
                    }
                }
                else if((index-1)>=0){
                    if(matched.contains(str.substring(index-1,index))){
                        isFound=true;
                        start=index-1;
                    }
                }

                if(isFound){
                    final String clickString=str.substring(start, index+2);
                    Log.d("ClickeSpan","clickString "+clickString);

                    ssb.setSpan(new ClickableSpan() {
                        @Override
                        public void onClick(View v) {
                            Log.d("main", "link clicked");
                            if (v instanceof ClickPreventableTextView) {
                                if (((ClickPreventableTextView)v).ignoreSpannableClick())
                                    return;
                                ((ClickPreventableTextView)v).preventNextClick();
                            }

                            spanClickLisenar.onSpanClickLisenar(clickString);
                        } }, start, index, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    ssb.setSpan(new ClickableSpan() {
//                        https://stackoverflow.com/questions/5183645/android-clickablespan-in-clickable-textview
//                        @Override
//                        public void onClick(View widget) {
//                            Log.d("clickString","clickString   "+clickString);
//                            spanClickLisenar.onSpanClickLisenar(clickString);
//                        }
//                    }, start, index, 0);
                }


            }
            index = str.indexOf(guess, index + guess.length());
        }
      //  ssb.setSpan();
        return ssb;
    }
    @Override
    public int getLayoutId() {
        return R.layout.item_dir;
    }



    public class ViewHolder extends CustomTreeViewBinder.ViewHolder {
        private  TextView tvTitle ;
        private ClickPreventableTextView  tvMessage;

        public ViewHolder(View rootView) {
            super(rootView);
            this.tvTitle = (TextView) rootView.findViewById(R.id.tv_title);
            this.tvTitle.setTextColor(Color.parseColor("#0000FF"));
            this.tvMessage = (ClickPreventableTextView) rootView.findViewById(R.id.tv_message);
        }



        public TextView getTvName() {
            return tvTitle;
        }
        public TextView getTVMessage() {
            return tvMessage;
        }
    }
}
