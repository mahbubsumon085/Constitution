package com.example.mami.myapplication.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.mami.myapplication.R;
import com.example.mami.myapplication.adapter.CustomTreeAdapter;
import com.example.mami.myapplication.adapter.CustomTreeNode;
import com.example.mami.myapplication.bean.AmmendMent;
import com.example.mami.myapplication.database.DatabaseAccess;
import com.example.mami.myapplication.lisenar.SpanClickLisenar;
import com.example.mami.myapplication.utility.Utility;
import com.example.mami.myapplication.viewbinder.DirectoryNodeBinder;
import com.example.mami.myapplication.viewbinder.FileNodeBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by Mami on 12/5/2017.
 */

public class FragmentArticle extends Fragment implements SpanClickLisenar {
    List<CustomTreeNode> nodes;
    ArrayList<AmmendMent> ammendList;
    ArrayList<AmmendMent> englishAmmendList;
    DatabaseAccess databaseAccess;
    private CustomTreeAdapter adapter;
    Switch sw_engLish;
    public void  setData(List<CustomTreeNode> nodes, DatabaseAccess databaseAccess
    ,ArrayList<AmmendMent> ammendList,ArrayList<AmmendMent> englishAmmendList) {
        this.nodes=nodes;
        this.databaseAccess=databaseAccess;
        this.ammendList=ammendList;
        this.englishAmmendList=englishAmmendList;

    }



    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tree_view_details, container, false);
        initView(view);
        if(nodes!=null){
            initData();
        }
        return view;
    }
    public void englishChangeLisenar(boolean isChecked){
        nodeBinder.setEnglish(isChecked);
        adapter.notifyDataSetChanged();
    }
    DirectoryNodeBinder nodeBinder;
    private void initData() {
        if(this.getActivity()!=null){
            nodeBinder=new DirectoryNodeBinder(databaseAccess,false,this);
            rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            adapter = new CustomTreeAdapter(nodes, Arrays.asList(new FileNodeBinder(),nodeBinder ));

            rv.setAdapter(adapter);
        }

    }
    RecyclerView rv;
    @SuppressLint("WrongViewCast")
    private void initView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);
        sw_engLish=view.findViewById(R.id.sw_engLish);

        sw_engLish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                englishChangeLisenar(isChecked);
            }
        });
    }

    @Override
    public void onSpanClickLisenar(String span) {
        String showable="Not found";
        String title="Constitution Amendment";
       String newAmmentMent=span.replace("[","");
     if(sw_engLish.isChecked()){
         for(int i=0;i<englishAmmendList.size();i++){
             if(englishAmmendList.get(i).getAmmendMentString().contains(newAmmentMent)){
                 showable=  englishAmmendList.get(i).getAmmendMentString();
                 break;
             }
         }
     }
     else{
         title=" সংবিধান সংশোধন";
         for(int i=0;i<ammendList.size();i++){
             if(ammendList.get(i).getAmmendMentString().contains(newAmmentMent)){
                 showable=  ammendList.get(i).getAmmendMentString();
                 break;
             }
         }
     }
        showAlert(showable,title);

    }

    @Override
    public void onSpanExpandOrRemoveLisenar(DirectoryNodeBinder.ViewHolder holder) {
        Log.d("onSpanExpandOr","Called");
        if(holder!=null){
//            Log.d("onSpanExpandOr","Called 1");
//
//            if(node.isExpand()){
//                Log.d("onSpanExpandOr","Called 2");
//
//                node.collapse();
//            }
//            else{
//                Log.d("onSpanExpandOr","Called 3");
//
//                node.expand();
//            }

            adapter.onclickNode(holder);
        }


    }


    public void showAlert(String message,String title){
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(message);
        builder1.setTitle(title);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
