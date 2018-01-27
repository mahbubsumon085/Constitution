package com.example.mami.myapplication.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.mami.myapplication.R;
import com.example.mami.myapplication.adapter.AmmendMentAdapter;
import com.example.mami.myapplication.bean.AmmendMent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mami on 12/4/2017.
 */

public class FragmentAmmendMent extends Fragment {

    public void setAmmendList(ArrayList<AmmendMent> ammendList,ArrayList<AmmendMent> englishAmmendList) {
        this.ammendList = ammendList;
        this.englishAmmendList=englishAmmendList;
    }
    ArrayList<AmmendMent> englishAmmendList;
    ArrayList<AmmendMent> ammendList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_ammendment, container, false);
        initView(view);
        initData();
        return view;
    }
    LinearLayoutManager layoutManager;
    RecyclerView rv;
    Switch sw_engLish;
    AmmendMentAdapter mAdapter;
    @SuppressLint("WrongViewCast")
    private void initView(View view) {
        rv = (RecyclerView) view.findViewById(R.id.rv);
        sw_engLish=view.findViewById(R.id.sw_engLish);

        sw_engLish.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked){
                mAdapter.setData(englishAmmendList);
            }
            else{
               mAdapter.setData(ammendList);
            }
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void initData(){
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(rv.getContext(),
                layoutManager.getOrientation());
        rv.addItemDecoration(dividerItemDecoration);
        mAdapter = new AmmendMentAdapter(ammendList);
        rv.setAdapter(mAdapter);
    }

}
