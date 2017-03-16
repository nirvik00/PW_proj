package gtpw_graph;

import java.util.ArrayList;

public class U01_ObjectOperations {
    String[] jtfStrArr;
    ArrayList<String>changedObject;
    ArrayList<gtpw_graph.A04_GraphObject>graphObjList;
    ArrayList<gtpw_graph.A04_GraphObject>newGraphObjList;
    
    U01_ObjectOperations(ArrayList<gtpw_graph.A04_GraphObject>graphObjList_, String[] jtfStrArr_){
        changedObject=new ArrayList<String>();
        graphObjList=new ArrayList<gtpw_graph.A04_GraphObject>();
        newGraphObjList=new ArrayList<gtpw_graph.A04_GraphObject>();
        graphObjList.addAll(graphObjList_);
        jtfStrArr=new String[jtfStrArr_.length];
        for(int i=0; i<jtfStrArr_.length; i++){
            jtfStrArr[i]=jtfStrArr_[i];
        }
    }   
    
    public ArrayList<gtpw_graph.A04_GraphObject> splitObject(){
        //System.out.println("before split");
        for(int i=0; i<graphObjList.size(); i++){
            //System.out.println(i+">"+graphObjList.get(i).getId()+","+graphObjList.get(i).getName());
        }
        for(int i=0; i<jtfStrArr.length; i++){
            String[] sArr=jtfStrArr[i].split(",");
            if (sArr[0].toLowerCase().equals("split")){
                int id=Integer.parseInt(sArr[1]);
                //System.out.println("required "+id);
                int number=Integer.parseInt(sArr[2]);
                for(int j=0; j<graphObjList.size(); j++){
                    A04_GraphObject objA=graphObjList.get(j);
                    int idA=objA.getId();
                    //System.out.println("check "+id+"--"+idA);
                    if(id==idA){
                        //System.out.println("found "+id+","+idA);
                        double qnty_ini=objA.getQnty();
                        changedObject.add(id+","+qnty_ini);
                        double qnty_req=qnty_ini/number;
                        objA.setQnty(qnty_req);
                        objA.setRelId(0+"");
                        for(int k=0; k<number-1; k++){                        
                            A04_GraphObject objB=new A04_GraphObject();
                            objA.copy(objB);
                            objB.setRelId(k+1+"");
                            newGraphObjList.add(objB);
                            //System.out.println("..... add "+k);
                        }
                    }
                }
            }
        }
        graphObjList.addAll(newGraphObjList);
        //System.out.println("after split");
        for(int i=0; i<graphObjList.size(); i++){
            //System.out.println(i+">"+graphObjList.get(i).getId()+","+graphObjList.get(i).getName());
        }
        return newGraphObjList;
    }
    public ArrayList<gtpw_graph.A04_GraphObject> mergeObject(ArrayList<gtpw_graph.A04_GraphObject>oriGraphObjList){
        newGraphObjList.clear();
        System.out.println("in function");
        for(int i=0; i<changedObject.size(); i++){
            String s=changedObject.get(i);
            int id=Integer.parseInt(s.split(",")[0]);
            double qnty_req=Double.parseDouble(s.split(",")[1]);
            for(int j=0; j<graphObjList.size(); j++){
                A04_GraphObject objA=graphObjList.get(j);
                int idA=objA.getId();
                double qntyA=objA.getQnty();
                System.out.println(idA+","+qntyA+"=>"+id+","+qnty_req);
                if(idA==id){
                    objA.setQnty(qnty_req);
                    newGraphObjList.add(objA);
                    System.out.println("ok");
                }
            }
        }
        for(int i=0; i<newGraphObjList.size(); i++){
            System.out.println(i+">"+newGraphObjList.get(i).getId()+","+newGraphObjList.get(i).getName());
        }
        return newGraphObjList;
    }
}
// "split,#2,4"