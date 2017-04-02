package gtpw_graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

public class U05_SubDivideSegments {
    ArrayList<String>segList;
    ArrayList<String>divPtList;
    ArrayList<String>div2PtList;
    ArrayList<String>figPtList;
    ArrayList<String>perSegList;
    ArrayList<String[]>perSegListArr;
    double min_di;
    U05_SubDivideSegments(ArrayList<String>arr_, ArrayList<String>arr2_, double min_di_){
        segList=new ArrayList<String>();
        divPtList=new ArrayList<String>();
        div2PtList=new ArrayList<String>();
        perSegList=new ArrayList<String>();
        divPtList.clear();
        div2PtList.clear();
        segList.clear();
        segList.addAll(arr_);
        perSegList.clear();
        figPtList=new ArrayList<String>();
        figPtList.clear();
        figPtList.addAll(arr2_);
        perSegListArr=new ArrayList<String[]>();
        perSegListArr.clear();
        min_di=min_di_;
        genPrimarySegments();
    }
    public ArrayList<String> genPrimarySegments(){
        divPtList.clear();
        perSegList.clear();
        for(int i=0; i<segList.size()-1; i++){
            double fig_x0=Double.parseDouble(segList.get(i).split(",")[2]);
            double fig_y0=Double.parseDouble(segList.get(i).split(",")[3]);
            
            double fig_x1=Double.parseDouble(segList.get(i).split(",")[4]);
            double fig_y1=Double.parseDouble(segList.get(i).split(",")[5]);
            
            double x0=Double.parseDouble(segList.get(i).split(",")[0]);
            double y0=Double.parseDouble(segList.get(i).split(",")[1]);
            
            double x1=Double.parseDouble(segList.get(i+1).split(",")[0]);
            double y1=Double.parseDouble(segList.get(i+1).split(",")[1]);
            
            double d=di(x0,y0,x1,y1);
            for(int j=0; j<d; j+=min_di){
                double m1=j;
                double m2=d-m1;
                double x=(m1*x1 + m2*x0)/(m1+m2);
                double y=(m1*y1 + m2*y0)/(m1+m2);
                divPtList.add(x+","+y);
                double dx=Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
                newDrawPerSegments(x,y,i,fig_x0, fig_y0, fig_x1, fig_y1);                
            }
        } 
        return divPtList;
    }   
    public ArrayList<String> genSecondarySegments(){
        System.out.println("----------------new figure was drawn--------------------");
        div2PtList.clear();
        for(int i=0; i<segList.size(); i++){
            double fig_x0,fig_y0;
            double cs_x0, cs_y0;
            double cs_x2, cs_y2;
            if(i>0){
                fig_x0=Double.parseDouble(segList.get(i-1).split(",")[2]);
                fig_y0=Double.parseDouble(segList.get(i-1).split(",")[3]);
                cs_x0=Double.parseDouble(segList.get(i-1).split(",")[0]);
                cs_y0=Double.parseDouble(segList.get(i-1).split(",")[1]);
                //System.out.println("taking @ 0 "+i);
            }else{
                fig_x0=Double.parseDouble(segList.get(segList.size()-1).split(",")[2]);
                fig_y0=Double.parseDouble(segList.get(segList.size()-1).split(",")[3]);                
                cs_x0=Double.parseDouble(segList.get(segList.size()-1).split(",")[0]);
                cs_y0=Double.parseDouble(segList.get(segList.size()-1).split(",")[1]);                
                //System.out.println("taking @ max "+i);
            }
            if(i==0 || i==1){
                cs_x2=Double.parseDouble(segList.get(1).split(",")[0]);
                cs_y2=Double.parseDouble(segList.get(1).split(",")[1]);
            }else if(i>0 && i<segList.size()-2){
                cs_x2=Double.parseDouble(segList.get(i+1).split(",")[0]);
                cs_y2=Double.parseDouble(segList.get(i+1).split(",")[1]);
            }else{
                cs_x2=Double.parseDouble(segList.get(0).split(",")[0]);
                cs_y2=Double.parseDouble(segList.get(0).split(",")[1]);               
            }
            double x0=Double.parseDouble(segList.get(i).split(",")[0]);
            double y0=Double.parseDouble(segList.get(i).split(",")[1]);
            
            double x1=Double.parseDouble(segList.get(i).split(",")[2]);
            double y1=Double.parseDouble(segList.get(i).split(",")[3]);
            
            double fig_x2=Double.parseDouble(segList.get(i).split(",")[4]);
            double fig_y2=Double.parseDouble(segList.get(i).split(",")[5]);
            
            if(Math.abs(x0-x1)<0.00001){
                x1+=0.000001;
            }
            if(Math.abs(fig_x0-x1)<0.00001){
                x1+=0.000001;
            }
            double d=di(x0,y0,x1,y1);
            double mx1=(y0-y1)/(x0-x1);
            double mx2=(fig_y0-y1)/(fig_x0-x1);
            double angle=Math.toDegrees(Math.atan((mx1-mx2)/(1+(mx1*mx2))));
            double req_di=min_di/Math.sqrt(Math.atan((y0-y1)/(x0-x1)));
            if(angle>0){
                System.out.println("plot obtuse angles : "+angle+"---"+cs_x0+","+cs_y0+";"+x0+","+y0+";"+cs_x2+","+cs_y2);
                for(int j=0; j<d; j+=min_di){                    
                    double m1=j;
                    double m2=d-m1;
                    double x=(m1*x1 + m2*x0)/(m1+m2);
                    double y=(m1*y1 + m2*y0)/(m1+m2);
                    div2PtList.add(x+","+y);
                    double dx=Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
                    newDrawPerSegments(x, y, i, cs_x0, cs_y0, x0, y0);
                    newDrawPerSegments(x, y, i, cs_x2, cs_y2, x0, y0);
                }
            }else{
                //System.out.println("plot acute angles : "+angle);
                for(int j=0; j<d; j+=min_di){
                    double m1=j;
                    double m2=d-m1;
                    double x=(m1*x1 + m2*x0)/(m1+m2);
                    double y=(m1*y1 + m2*y0)/(m1+m2);
                    div2PtList.add(x+","+y);
                    double dx=Math.sqrt((x-x1)*(x-x1)+(y-y1)*(y-y1));
                    newDrawPerSegments(x, y, i, fig_x2, fig_y2, x1, y1);                
                    newDrawPerSegments(x, y, i, fig_x0, fig_y0, x1, y1);  
                }
            }
        }
        return div2PtList;
    }
    public void newDrawPerSegments(double x, double y, int index, double ax1, double ay1, double ax2, double ay2){
        if(Math.abs(ax2-ax1)==0){
            ax1=ax1+0.001;
        }
        if(Math.abs(ay2-ay1)==0){
            ay1=ay1+0.001;
        }
        double am2=(ay2-ay1)/(ax2-ax1);
        double ac2=ay2-am2*ax2;
        double am1=-1/am2;
        double ac1=y-am1*x;
        double a=(ac1-ac2)/(am2-am1);
        double b=am1*a+ac1;
        double d=di(a,b,x,y);
        double mpx=(ax1+ax2)/2;
        double mpy=(ay1+ay2)/2;
        double mpDi=di(mpx, mpy, ax1,ay1);
        double cDi=di(mpx,mpy,a,b);
        if(cDi<mpDi){
            perSegList.add(a+","+b+","+d+","+x+","+y+","+index+","+am1);
        }
    }    
    public double di(double x0,double y0,double x1,double y1){
        double d=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
        return d;
    }
}
