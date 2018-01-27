package com.example.mami.myapplication.utility;

import android.util.Log;

import com.example.mami.myapplication.adapter.CustomTreeNode;
import com.example.mami.myapplication.bean.ContentNode;
import com.example.mami.myapplication.database.DatabaseAccess;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Mami on 11/29/2017.
 */

public class Utility {
    public static List<CustomTreeNode> generateTree(List<ContentNode> nodeList ){
        List<CustomTreeNode> nodes = new ArrayList<>();
        List<ContentNode> nodeWithZero=getNodeListWithParenID(nodeList,0);
        for(int i=0;i<nodeWithZero.size();i++){
            CustomTreeNode<ContentNode> part=new CustomTreeNode<>(nodeWithZero.get(i));
            nodeList.remove(nodeWithZero.get(i));
            nodes.add(part);
        }
        generateTreeFromHeightSecondCall(nodeList,nodes);
       /* while(nodeList.){

        }*/
        return nodes;
    }



    public static List<CustomTreeNode> generateArticleTree(List<ContentNode> nodeList ){
        List<CustomTreeNode> nodes = new ArrayList<>();
        Log.d("rticleTree","Started   ");
        for(int i=0;i<nodeList.size();i++){
            if(nodeList.get(i).getArticleId()>0){

                Log.d("rticleTree","test  "+nodeList.size()+"    "+nodeList.get(i).getId());
                CustomTreeNode<ContentNode> part=new CustomTreeNode<>(nodeList.get(i));
                int j=i+1;
                while(j<nodeList.size()){
                   if(nodeList.get(j).getParentID()==nodeList.get(i).getId()){
                       CustomTreeNode<ContentNode> childpart=new CustomTreeNode<>(nodeList.get(j));
                       part.addChild(childpart);
                   }
                   else{
                       break;
                   }
                    j++;
                }
                Log.d("rticleTree","nodes  inner "+nodes.size());
                nodes.add(part);
            }
            Log.d("rticleTree","nodes outer  "+nodes.size());
        }
       /* while(nodeList.){

        }*/
        return nodes;
    }
    public static List<CustomTreeNode> generateOnlyArticle(List<ContentNode> nodeList ){
        List<CustomTreeNode> nodes = new ArrayList<>();
        List<ContentNode> nodeWithZero=getNodeListWithParenID(nodeList,0);
        for(int i=0;i<nodeWithZero.size();i++){
            CustomTreeNode<ContentNode> part=new CustomTreeNode<>(nodeWithZero.get(i));
            nodeList.remove(nodeWithZero.get(i));
            nodes.add(part);
        }
        generateTreeFromHeightSecondCall(nodeList,nodes);
       /* while(nodeList.){

        }*/
        return nodes;
    }
//    private Node search(String name, Node node){
//        if(node != null){
//            if(node.name().equals(name)){
//                return node;
//            } else {
//                Node foundNode = search(name, node.left);
//                if(foundNode == null) {
//                    foundNode = search(name, node.right);
//                }
//                return foundNode;
//            }
//        } else {
//            return null;
//        }

//    }
    public static CustomTreeNode treeSearch(CustomTreeNode node, ContentNode contentNode){
        if(node != null){
            ContentNode visitingNode=(ContentNode)node.getContent();
            if(visitingNode.getId()==contentNode.getParentID()){
                return node;
            }
            else{

                List<CustomTreeNode> childNode= node.getChildList();
                if(childNode!=null){
                    for(int i=0;i<childNode.size();i++){
                        CustomTreeNode returningNode=   treeSearch(childNode.get(i),contentNode);
                        if(returningNode!=null){
                            return  returningNode;
                        }
                    }
                }

                return null;
            }
        }
        else{
            return null;
        }
    }
    public static void generateTreeFromHeightSecondCall( List<ContentNode> nodeList,List<CustomTreeNode> nodes){
        long millis=System.currentTimeMillis();
        Log.d("FromHeightSec","Size  "+nodeList.size());
        for(int i=0;i<nodeList.size();i++){
           if(nodeList.get(i)==null){
               Log.d("NullFound","Null found ");
           }
        }
        while(nodeList.size()>0){
            ContentNode node=nodeList.get(0);
            Log.d("undeNullFound","sdfgfdghgfhfh Size  "+nodeList.size()+"    "+node.getId());
            boolean isFounded=false;
            for(CustomTreeNode treeNode:nodes){
                CustomTreeNode foundedNode= treeSearch(treeNode,node);
                if(foundedNode!=null){
                    CustomTreeNode<ContentNode> part=new CustomTreeNode<>(node);

                    foundedNode.addChild(part);
                    nodeList.remove(node);
                    isFounded=true;
                  break;
                }

            }

            if(isFounded==false){
                Log.d("isFounded","bbbv  regretgtrgtrgtrg "+node.getId());
            }
            else{
                Log.d("isFounded","dddk "+node.getId());
            }


        }
        Log.d("FromHeightSec","Size  "+nodeList.size());

    }
    public static List<ContentNode>  getNodeListWithParenID(List<ContentNode> nodeList,int id){
        List<ContentNode> nodeChildList=new ArrayList<>();
        for(ContentNode node:nodeList){
            if(node.getParentID()==id){
                nodeChildList.add(node);
            }

        }
        return nodeChildList;
    }

}
