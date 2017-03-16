package gtpw_graph;

import java.util.ArrayList;
import java.util.Collections;

public class U00_Kruskal {
    ArrayList<String>nodeIniList, nodeWeightList, nodeRemList;
    ArrayList<A04_GraphObject> graphObjectList;
    ArrayList<String> weightList;
    ArrayList<String>minSpanningTree;
    
    U00_Kruskal(ArrayList<A04_GraphObject> graphObjectList_){
        graphObjectList=new ArrayList<A04_GraphObject>();
        graphObjectList.addAll(graphObjectList_);
        weightList=new ArrayList<String>();        
        nodeIniList=new ArrayList<String>();
        nodeWeightList=new ArrayList<String>();
        nodeRemList=new ArrayList<String>();
        minSpanningTree=new ArrayList<String>();
    }
    
    //classfication function ; bot related to kruskal
    public ArrayList<String> findMinWeight(){
        weightList.clear();
        for(int i=0; i<graphObjectList.size(); i++){
            A04_GraphObject objA=graphObjectList.get(i);
            int idA=objA.getId();
            double wtA=objA.getWeight();
            int[] adj=objA.getAdj_to();
            for(int j=0; j<adj.length;j++){
                for(int k=0; k<graphObjectList.size(); k++){
                    A04_GraphObject objB=graphObjectList.get(k);
                    int idB=objB.getId();
                    if(idB==adj[j]){
                        double wtB=objB.getWeight();
                        double wt=wtA+wtB;
                        weightList.add(idA+","+idB+","+wt);
                    }
                }
            }
        }
        Collections.sort(weightList, new C01_CompareEdgeWeight());
        double minWt=Double.parseDouble(weightList.get(0).split(",")[2]);
        double maxWt=Double.parseDouble(weightList.get(weightList.size()-1).split(",")[2]);
        for(int i=0; i<weightList.size(); i++){
            String id0=weightList.get(i).split(",")[0];
            String id1=weightList.get(i).split(",")[1];
            double wt=Double.parseDouble(weightList.get(i).split(",")[2]);
            int normal=(int)(wt/minWt);
            String s=id0+","+id1+","+normal;
            weightList.set(i, s);
        }
        return weightList;
    }
    
    public int weightReturn(String idA, String idB){
        int wt=0;
        for(int i=0; i<weightList.size(); i++){
            String id0=weightList.get(i).split(",")[0];
            String id1=weightList.get(i).split(",")[1];
            int wtx=(int) Double.parseDouble(weightList.get(i).split(",")[2]);
            if((idA.equals(id0) && idB.equals(id1)) || (idA.equals(id1) && idB.equals(id0))){
                wt=wtx;
                //System.out.println("wt found :- "+idA+"-"+idB+":"+wt);
                break;
            }
        }
        if(wt==0){
            wt=10000;
        }
        return wt;
    }
    
    //public void sortNode(String[] nodeStrArr){
    public void sortNode(){
        String allnodes="";
        //IF WE WANT SPECIFIC NODES
        //the array of nodes is added to nodeList
        /*
        int[] nodearr=new int[nodeStrArr.length];
        for(int i=0; i<nodeStrArr.length; i++){
            nodeIniList.add(nodeStrArr[i]);
        }
        */
        //HERE WE WILL CONSTRUCT A SPANNING TREE OF ALL NODES
        nodeIniList.clear();
        nodeWeightList.clear();
        for(int i=0; i<graphObjectList.size(); i++){
            int id=graphObjectList.get(i).getId();
            nodeIniList.add(""+id);
        }
        //start with first element in array 
        //and reference it to others to find weights
        String wrt=nodeIniList.get(0);
        nodeWeightList.add(wrt);
        nodeRemList.clear();
        int k=0;
        int n=nodeIniList.size();
        nodeIniList.remove(0);
        sortNodeIterate(wrt, nodeIniList, k, n);
    }
    
    public void sortNodeIterate(String wrt,ArrayList<String>nodeIniList, int k, int n){
        //here wrt is the reference node
        //every other node in the list will be checked against this and weight noted
        String s="";
        ArrayList<String>temp=new ArrayList<String>();
        temp.clear();
        for(int i=0; i<nodeIniList.size(); i++){
            String id=nodeIniList.get(i);
            if(wrt.equals(id)){
            //do nothing 
            }else{
            int wt=weightReturn(wrt,id);
                if(wt!= 10000){
                    String str=wrt+","+id+","+wt;
                    temp.add(str);
                }
            }
        }
        k++;
        //if an id among list is found, it will add the minimum found noted id
        if(temp.size()>0){
            Collections.sort(temp,new C03_CompareStringWeight());
            String req_id=temp.get(0).split(",")[1];
            wrt=req_id;            
            nodeWeightList.add(req_id);
        }else{//else it will move to next, that is the element after index 0 or first elemetn
            wrt=nodeIniList.get(1);
        }
        //this node is added to nodeRemList

        //current wrt will removed from iniList
        for(int i=0; i<nodeWeightList.size();i++){
            String s_wt=nodeWeightList.get(i);
            nodeIniList.remove(s_wt);
        }
        //System.out.println("#"+k + " this gen weightList : "+nodeWeightList);
        //System.out.println("#"+k+" next generation's node : "+wrt);
        //System.out.println("#"+k+" elements going to next ite : "+nodeIniList);
        if(nodeIniList.size()>1){
           sortNodeIterate(wrt, nodeIniList, k, n); 
        }else{
            nodeWeightList.add(nodeIniList.get(0));
            //System.out.println("final sorted weighted list : "+nodeWeightList);
            minSpanningTree.clear();
            minSpanningTree.addAll(nodeWeightList);
        }
    }
}