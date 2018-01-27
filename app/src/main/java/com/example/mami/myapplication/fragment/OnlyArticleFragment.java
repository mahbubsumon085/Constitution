//package com.example.mami.myapplication.fragment;
//
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.example.mami.myapplication.R;
//import com.example.mami.myapplication.database.DatabaseAccess;
//import com.example.mami.myapplication.lisenar.SpanClickLisenar;
//import com.example.mami.myapplication.utility.Utility;
//import com.example.mami.myapplication.viewbinder.DirectoryNodeBinder;
//import com.example.mami.myapplication.viewbinder.FileNodeBinder;
//
//import java.util.Arrays;
//import java.util.List;
//
//import tellh.com.recyclertreeview_lib.TreeNode;
//import tellh.com.recyclertreeview_lib.TreeViewAdapter;
//
//
//public class OnlyArticleFragment extends Fragment implements SpanClickLisenar {
//  private String title;
//    private int page;
//    List<TreeNode> nodes;
//    DatabaseAccess databaseAccess;
//    private TreeViewAdapter adapter;
//
//    public static OnlyArticleFragment newInstance(int page, String title, List<TreeNode> nodes,
//                                                      DatabaseAccess databaseAccess) {
//        OnlyArticleFragment treeViewDetails = new OnlyArticleFragment();
//        Bundle args = new Bundle();
//        args.putInt("someInt", page);
//        args.putString("someTitle", title);
//        treeViewDetails.setArguments(args);
//        treeViewDetails.nodes=nodes;
//        treeViewDetails.databaseAccess=databaseAccess;
//        return treeViewDetails;
//    }
//
//
//    // Store instance variables based on arguments passed
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        page = getArguments().getInt("someInt", 0);
//        title = getArguments().getString("someTitle");
//    }
//
//    // Inflate the view for the fragment based on layout XML
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.only_articel_fragment, container, false);
//        //initView(view);
////        if(nodes!=null){
////            initData();
////        }
//        return view;
//    }
//    private void initData() {
//        if(this.getActivity()!=null){
//            rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
//            adapter = new TreeViewAdapter(nodes, Arrays.asList(new FileNodeBinder(), new DirectoryNodeBinder(databaseAccess,false,this)));
//            adapter.setOnTreeNodeListener(new TreeViewAdapter.OnTreeNodeListener() {
//                @Override
//                public boolean onClick(TreeNode node, RecyclerView.ViewHolder holder) {
//                    if (!node.isLeaf()) {
//                        //Update and toggle the node.
//                        onToggle(!node.isExpand(), holder);
////                    if (!node.isExpand())
////                        adapter.collapseBrotherNode(node);
//                    }
//                    return false;
//                }
//
//                @Override
//                public void onToggle(boolean isExpand, RecyclerView.ViewHolder holder) {
//                    DirectoryNodeBinder.ViewHolder dirViewHolder = (DirectoryNodeBinder.ViewHolder) holder;
//                    int rotateDegree = isExpand ? 90 : -90;
//
//                }
//            });
//            rv.setAdapter(adapter);
//        }
//
//    }
//    RecyclerView rv;
//    private void initView(View view) {
//        rv = (RecyclerView) view.findViewById(R.id.rv);
//    }
//
//    @Override
//    public void onSpanClickLisenar(String span) {
//
//    }
//
//    @Override
//    public void onSpanExpandOrRemoveLisenar(TreeNode node) {
//
//    }
//}
