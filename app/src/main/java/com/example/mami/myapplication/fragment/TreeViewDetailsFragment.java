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
import android.widget.TextView;

import com.example.mami.myapplication.R;
import com.example.mami.myapplication.adapter.CustomTreeAdapter;
import com.example.mami.myapplication.adapter.CustomTreeNode;
import com.example.mami.myapplication.bean.AmmendMent;
import com.example.mami.myapplication.bean.ContentNode;
import com.example.mami.myapplication.database.DatabaseAccess;
import com.example.mami.myapplication.lisenar.SpanClickLisenar;
import com.example.mami.myapplication.utility.Utility;
import com.example.mami.myapplication.viewbinder.DirectoryNodeBinder;
import com.example.mami.myapplication.viewbinder.FileNodeBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



/**
 * Created by Mami on 11/30/2017.
 */

public class TreeViewDetailsFragment extends Fragment implements SpanClickLisenar {

    List<CustomTreeNode> nodes;
    DatabaseAccess databaseAccess;
    private CustomTreeAdapter adapter;
    Switch sw_engLish;
    ArrayList<AmmendMent> ammendList;
    ArrayList<AmmendMent> englishAmmendList;
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
            //List<TreeNode> nodes = Utility.generateTree(databaseAccess);
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
        String showable="Not found ";
        String title="Constitution Amendment";
        int index=span.indexOf("[");
        String newAmmentMent=span.substring(0,index);
       // newAmmentMent=newAmmentMent.replace("*","");
        Log.d("newAmmentMent","newAmmentMent "+newAmmentMent+"  ");
        if(sw_engLish.isChecked()){
            for(int i=0;i<englishAmmendList.size();i++){
                String checker=englishAmmendList.get(i).getAmmendMentString().substring(0,4);
                if(checker.contains(newAmmentMent)){
                    Log.d("newAmmentMent","showable "+checker);
                    showable=  englishAmmendList.get(i).getAmmendMentString();
                    break;
                }
            }
        }
        else{
            title=" সংবিধান সংশোধন";
            for(int i=0;i<ammendList.size();i++){
                String checker=ammendList.get(i).getAmmendMentString().substring(0,4);
                if(checker.contains(newAmmentMent)){
                    Log.d("newAmmentMent","showable "+checker);
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
        builder1.setCancelable(true);
        builder1.setTitle(title);
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
