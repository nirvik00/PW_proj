package gtpw_graph;

import java.util.ArrayList;

public class U02_RayEvalGrid {
    ArrayList<String> finalFigPtList;
    ArrayList<String> gridPtList;
    ArrayList<String>PTList;
    double gridspacing;
    U02_RayEvalGrid(double gridspacing_){
        gridspacing=gridspacing_;
        finalFigPtList=new ArrayList<String>();    
        gridPtList=new ArrayList<String>();    
        PTList=new ArrayList<String>();
    }
    public ArrayList<String> evalPtList(){
        for(int i=0; i<gridPtList.size(); i++){
            double x=(Double.parseDouble(gridPtList.get(i).split(",")[0]));
            double y=(Double.parseDouble(gridPtList.get(i).split(",")[1]));
            int intX=0;
            for(int j=0; j<finalFigPtList.size()-1; j++){
                String s1=finalFigPtList.get(j);
                String s2=finalFigPtList.get(j+1);
                intX+=evaluate(x,y,s1,s2, intX);
            }
            if((intX>0) && (intX%2!=0)){
                PTList.add(x+","+y);
            }
        }
        return PTList;
    }
    public boolean evalSinglePt(double x, double y){
        boolean t;
        int intX=0;
        for(int i=0; i<finalFigPtList.size()-1; i++){
            String s1=finalFigPtList.get(i);
            String s2=finalFigPtList.get(i+1);
            intX+=evaluate(x,y,s1,s2,intX);
        }
        if(intX>0 && intX%2!=0){
           t=true;
        }else{
            t=false;
        }
        return t;
    }
    public int evaluate(double x0, double y0, String s1, String s2, int i){
        double x1=Double.parseDouble(s1.split(",")[0]);
        double y1=Double.parseDouble(s1.split(",")[1]);
        double x2=Double.parseDouble(s2.split(",")[0]);
        double y2=Double.parseDouble(s2.split(",")[1]);
        double fx=0;
        double fy=0;
        if((x1==x2) && (y1 != y2)){
            fx=x1;
            fy=y0;
            if(fx>x0){
                if(y1>y2 && fy>=y2 && fy<y1){
                    return 1;
                }else if(y2>y1 && fy>=y1 && fy<y2){
                    return 1;
                }else{
                    return 0;
                }
            }else{
                return 0;
            }
        }else if(x1 != x2){
            fy=y0;
            double m2=(y2-y1)/(x2-x1);
            double c2=y2-(m2*x2);
            fx=(y0-c2)/m2;
            if(fx>=x0){
                if(y1>y2 && fy>=y2 && fy<y1){
                    return 1;
                }else if((y2>y1 && fy>=y1 && fy<y2)){
                    return 1;
                }else{
                    return 0;
                }
            }else{
                return 0;
            }

        }else{
            return 0;
        }
    }
}
