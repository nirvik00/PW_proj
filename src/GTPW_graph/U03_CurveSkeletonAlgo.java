package gtpw_graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;

public class U03_CurveSkeletonAlgo {
    U02_RayEvalGrid EvalGrid;
    ArrayList<String> figSegmentList;
    ArrayList<String> finalFigPtList;
    ArrayList<String> internalGridPtList;
    ArrayList<String> seqSegList;
    ArrayList<String> angBisectorList;
    ArrayList<String> angBisectorSegList;
    ArrayList<String> newAngBisectorSegList;
    ArrayList<String[]>organizePtList;
    ArrayList<String> minAreaABList;
    ArrayList<String> minsortAreaABList;
    ArrayList<String> angList;
    ArrayList<String> gridPtList;
    ArrayList<String> intersectionList;
    ArrayList<String> centroidList;
    ArrayList<String>reqList;
    ArrayList<String>reqPtList;
    double gridspacing;
    double dimension_node;
    String internalPt;
    
    U03_CurveSkeletonAlgo(double gridspacing_, double dimension_node_){
        EvalGrid=new U02_RayEvalGrid(gridspacing);
        gridspacing=gridspacing_;
        figSegmentList=new ArrayList<String>();
        finalFigPtList=new ArrayList<String>();
        internalGridPtList=new ArrayList<String>();  
        seqSegList=new ArrayList<String>();  
        angBisectorList=new ArrayList<String>();  
        angBisectorSegList=new ArrayList<String>();  
        newAngBisectorSegList=new ArrayList<String>();
        organizePtList=new ArrayList<String[]>();
        minAreaABList=new ArrayList<String>();  
        minsortAreaABList=new ArrayList<String>();  
        angList=new ArrayList<String>();  
        centroidList=new ArrayList<String>();  
        finalFigPtList.clear();
        figSegmentList.clear();
        centroidList.clear();
        intersectionList=new ArrayList<String>();
        gridPtList=new ArrayList<String>();
        reqList=new ArrayList<String>();
        reqPtList=new ArrayList<String>();
        dimension_node=dimension_node_;
    }
    public ArrayList<String> combineSegments(){
        seqSegList.clear();
        for(int i=0; i<figSegmentList.size()-1; i++){
            double x0=Double.parseDouble(figSegmentList.get(i).split(",")[0]);
            double y0=Double.parseDouble(figSegmentList.get(i).split(",")[1]);
            double x1=Double.parseDouble(figSegmentList.get(i).split(",")[2]);
            double y1=Double.parseDouble(figSegmentList.get(i).split(",")[3]);
            double x2=Double.parseDouble(figSegmentList.get(i+1).split(",")[2]);
            double y2=Double.parseDouble(figSegmentList.get(i+1).split(",")[3]);
            seqSegList.add(x0+","+y0+","+x1+","+y1+","+x2+","+y2);
        }
        if(seqSegList.size()>0){
            //String s0=figSegmentList.get(figSegmentList.size()-2);
            String s1=figSegmentList.get(figSegmentList.size()-1);
            String s2=figSegmentList.get(0).split(",")[2]+","+figSegmentList.get(0).split(",")[3];
            seqSegList.add(s1+","+s2);
        }
        for(int i=0; i<seqSegList.size(); i++){
        }
        return seqSegList;
    }
    public ArrayList<String> findAngleBetweenSegments(ArrayList<String> seqSegList){
        angList.clear();
        for(int i=0; i<seqSegList.size(); i++){
            double x0=Double.parseDouble(seqSegList.get(i).split(",")[0]);
            double y0=Double.parseDouble(seqSegList.get(i).split(",")[1]);
            double dx=Double.parseDouble(seqSegList.get(i).split(",")[2]);
            double dy=Double.parseDouble(seqSegList.get(i).split(",")[3]);
            double x1=Double.parseDouble(seqSegList.get(i).split(",")[4]);
            double y1=Double.parseDouble(seqSegList.get(i).split(",")[5]);
            //norms
            double norm0=norm(x0-dx,y0-dy);
            double norm1=norm(x1-dx,y1-dy);
            double dp=dotproduct(x0-dx,y0-dy,x1-dx,y1-dy);
            double degrees=findAngle(dp, norm0, norm1);
            angList.add(x0+","+y0+","+dx+","+dy+","+x1+","+y1+","+degrees);
        }
        return angList;
    }
    public ArrayList<String> newfindAngleBisector(ArrayList<String>bb){
        ArrayList<String>tempBoundaryList=new ArrayList<String>();
        ArrayList<String>tempTriangleList=new ArrayList<String>();
        
        String s=bb.get(0);
        double bbx0=Double.parseDouble(s.split(",")[0]);
        double bby0=Double.parseDouble(s.split(",")[1]);
        double bbx1=Double.parseDouble(s.split(",")[2]);
        double bby1=Double.parseDouble(s.split(",")[3]);
        double bbx=(bbx0+bbx1)/2;
        double bby=(bby0+bby1)/2;
        
        angBisectorList.clear();
        angBisectorSegList.clear();
        centroidList.clear();
        double tolerance=5;
        reqPtList.clear();
        for(int i=0; i<seqSegList.size(); i++){
            double x0=Double.parseDouble(seqSegList.get(i).split(",")[0]);
            double y0=Double.parseDouble(seqSegList.get(i).split(",")[1]);
            double x1=Double.parseDouble(seqSegList.get(i).split(",")[2]);
            double y1=Double.parseDouble(seqSegList.get(i).split(",")[3]);
            double x2=Double.parseDouble(seqSegList.get(i).split(",")[4]);
            double y2=Double.parseDouble(seqSegList.get(i).split(",")[5]);
            //find angles with dot product
            double degrees=determineAngle(x0,y0,x1,y1,x2,y2);
            String strAng=seqSegList.get(i).split(",")[0];
            double x_P1, y_P1, x_P2, y_P2, x_N1, y_N1, x_N2, y_N2;
            if(x0==x1){ 
                x0=x0+Math.pow(10, -7);
                x1=x1-Math.pow(10, -7);
            }else if(x1==x2){
                x1=x1+Math.pow(10, -7);
            }else if(x2==x0){
                x2=x2+Math.pow(10, -7);
            }
            String centroid=findCentroid(x0,y0,x1,y1,x2,y2);
            double cen_x=Double.parseDouble(centroid.split(",")[0]);
            double cen_y=Double.parseDouble(centroid.split(",")[1]);
            boolean t_cen=insideGrid(cen_x,cen_y, gridspacing);
            
            double m01=(y1-y0)/(x1-x0);
            double c01=(y0)-(m01)*x0;
            double m12=(y2-y1)/(x2-x1);
            double c12=(y1)-(m12)*x1;
            double m20=(y0-y2)/(x0-x2);
            double c20=(y2)-(m20)*x2;
            
            //find angles with coordinate geometry
            double angle_degrees1=Math.toDegrees(Math.atan(-(m01-m12)/(1+(m01*m12))));
            
            double den=Math.sqrt(m01*m01 + 1)/Math.sqrt(m12*m12 + 1);
            x_P1=(((-c20)*(1-den) + c01 - (c12*den))/(m12*den - m01 - (1-den)*(-m20)));
            y_P1=(m20*x_P1 + c20);
            x_N1=((c01 + c12*den - c20*(den+1))/(-m12*den - m01 + (den+1)*(m20)));
            y_N1=(m20*x_N1+c20);
            double rx=(x_P1-x1);
            double ry=(y_P1-y1);
            x_P2=(rx*Math.cos(Math.PI)-ry*Math.sin(Math.PI))+x1;
            y_P2=(rx*Math.sin(Math.PI)+ry*Math.cos(Math.PI))+y1;
            double qx=(x_N1-x1);
            double qy=(y_N1-y1);
            x_N2=(qx*Math.cos(Math.PI)-qy*Math.sin(Math.PI))+x1;
            y_N2=(qx*Math.sin(Math.PI)+qy*Math.cos(Math.PI))+y1;
            double a=gridspacing*dimension_node;
            double sc_x_P1=a/Math.sqrt((x_P1-x1)*(x_P1-x1)+(y_P1-y1)*(y_P1-y1));
            double sc_x_P2=a/Math.sqrt((x_P2-x1)*(x_P2-x1)+(y_P2-y1)*(y_P2-y1));
            double sc_x_N1=a/Math.sqrt((x_N1-x1)*(x_N1-x1)+(y_N1-y1)*(y_N1-y1));
            double sc_x_N2=a/Math.sqrt((x_N2-x1)*(x_N2-x1)+(y_N2-y1)*(y_N2-y1));
            x_P1=(x_P1-x1)*sc_x_P1 + x1;
            y_P1=(y_P1-y1)*sc_x_P1 + y1;
            x_P2=(x_P2-x1)*sc_x_P2 + x1;
            y_P2=(y_P2-y1)*sc_x_P2 + y1;
            x_N1=(x_N1-x1)*sc_x_N1 + x1;
            y_N1=(y_N1-y1)*sc_x_N1 + y1;
            x_N2=(x_N2-x1)*sc_x_N2 + x1;
            y_N2=(y_N2-y1)*sc_x_N2 + y1;
            double b=gridspacing*0.5;
            boolean t0=insideGrid(x_P1,y_P1, b);
            boolean t1=insideGrid(x_P2,y_P2, b);
            boolean t2=insideGrid(x_N1,y_N1, b);
            boolean t3=insideGrid(x_N2,y_N2, b);

            double d0=Math.sqrt((x_P1-cen_x)*(x_P1-cen_x) + (y_P1-cen_y)*(y_P1-cen_y));
            double d1=Math.sqrt((x_P2-cen_x*(x_P2-cen_x) + (y_P2-cen_y)*y_P2-cen_y));
            double d2=Math.sqrt((x_N1-cen_x)*(x_N1-cen_x) + (y_N1-cen_y)*(y_N1-cen_y));
            double d3=Math.sqrt((x_N2-cen_x)*(x_N2-cen_x) + (y_N2-cen_y)*(y_N2-cen_y));

            tempTriangleList.clear();
            String str="";
            if(t0==true){
                tempTriangleList.add(x_P1+","+y_P1+","+d0);
                str+=x_P1+","+y_P1+";";
            }
            if(t1==true){
                tempTriangleList.add(x_P2+","+y_P2+","+d1);
                str+=x_P2+","+y_P2+";";
            }
            if(t2==true){
                tempTriangleList.add(x_N1+","+y_N1+","+d2);
                str+=x_N1+","+y_N1+";";
            }
            if(t3==true){
                tempTriangleList.add(x_N2+","+y_N2+","+d3);
                str+=x_N2+","+y_N2+";";
            }
            
            Collections.sort(tempTriangleList, new C04_CompareDistGrid()); 
           
            for(int j=0; j<tempTriangleList.size(); j++){
                newAngBisectorSegList.add(x1+","+y1+","+tempTriangleList.get(j));
            }
            
            angBisectorSegList.add(str);  
            LinkedHashSet<String>lhs=new LinkedHashSet<String>();
            lhs.clear();
            lhs.addAll(newAngBisectorSegList);
            newAngBisectorSegList.clear();
            newAngBisectorSegList.addAll(lhs);
            angBisectorList.add(x1+","+y1+","+x_P1+","+y_P1+","+x_P2+","+y_P2+","+x_N1+","+y_N1+","+x_N2+","+y_N2);  
            
            String[] strtemp=new String[tempTriangleList.size()+3];
            strtemp[0]=x0+","+y0;
            strtemp[1]=x1+","+y1;
            strtemp[2]=x2+","+y2;
            int jk=3;
            for(int j=0; j<tempTriangleList.size(); j++){
                strtemp[jk]=tempTriangleList.get(j);
                jk++;
            }
            organizePtList.add(strtemp);
        }
        return angBisectorList;       
    }
    public double findAngleAnalytical(String inList){
        double x0=Double.parseDouble(inList.split(",")[0]);
        double y0=Double.parseDouble(inList.split(",")[1]);
        double x1=Double.parseDouble(inList.split(",")[2]);
        double y1=Double.parseDouble(inList.split(",")[3]);
        double x2=Double.parseDouble(inList.split(",")[4]);
        double y2=Double.parseDouble(inList.split(",")[5]);
        if(x0==x1){ 
            x0=x0+Math.pow(10, -3);
            x1=x1-Math.pow(10, -3);
        }else if(x1==x2){
            x1=x1+Math.pow(10, -3);
        }else if(x2==x0){
            x2=x2+Math.pow(10, -3);
        }            
        double m01=(y1-y0)/(x1-x0);
        double c01=(y0)-(m01)*x0;
        double m12=(y2-y1)/(x2-x1);
        double c12=(y1)-(m12)*x1;
        double m20=(y0-y2)/(x0-x2);
        double c20=(y2)-(m20)*x2;
        double angle_degrees1=Math.toDegrees(Math.atan(-(m01-m12)/(1+(m01*m12))));
        System.out.println(angle_degrees1);
        return angle_degrees1;
    }
    public double HeronArea(double x1, double y1, double x2, double y2){
        //System.out.println(internalGridPtList.size());
        String str=internalGridPtList.get((int)internalGridPtList.size()/2);
        internalPt=(str.split(",")[0]+","+str.split(",")[1]);
        double x0=Double.parseDouble(str.split(",")[0]);
        double y0=Double.parseDouble(str.split(",")[1]);
        double a=distance(x0,y0,x1,y1);
        double b=distance(x1,y1,x2,y2);
        double c=distance(x2,y2,x0,y0);
        double s=(a+b+c)/2;
        double area=Math.sqrt(s*(s-a)*(s-b)*(s-c))/1000;
        //System.out.println("u03-412 :"+area);
        return area;
    }
    public double norm(double x0, double y0){
        double z=Math.sqrt(Math.pow(x0,2)+Math.pow(y0,2));
        return z;
    }
    public double distance(double x0, double y0, double x1, double y1){
        double z=Math.sqrt((x0-x1)*(x0-x1)+(y0-y1)*(y0-y1));
        return z;
    }
    public double dotproduct(double x0, double y0, double x1, double y1){
         double z=(x0*x1+y0*y1);
         return z;
    }
    public double findAngle(double dp, double norm0, double norm1){
        double z=dp/(norm0*norm1);
        double degrees=Math.toDegrees(Math.acos(z));
        return degrees;
    }
    public double determineAngle(double x0, double y0, double x1, double y1, double x2, double y2){
        double norm0=norm(x0-x1,y0-y1);
        double norm1=norm(x2-x1,y2-y1);
        double dp=dotproduct(x0-x1,y0-y1,x2-x1,y2-y1);
        double degrees=findAngle(dp, norm0, norm1);
        return degrees;
    }
    public boolean insideGrid(double x, double y, double tolerance){
        int sum=0;
        double a=tolerance;                
        //double a=gridspacing*1.05;
        for(int i=0; i<internalGridPtList.size(); i++){
            double x0=Double.parseDouble(internalGridPtList.get(i).split(",")[0]);
            double y0=Double.parseDouble(internalGridPtList.get(i).split(",")[1]);
            if(x>=x0-a && x<=x0+a && y>=y0-a && y<=y0+a){
                sum++;
            }
        }
        if(sum>0){
            return true;
        }else{
            return false;
        }
    }
    public String findCentroid(double x0, double y0, double x1, double y1, double x2, double y2){
        String temp="";

        double ptX0=(x1+x2)/2;
        double ptY0=(y1+y2)/2;
        double m0=(y0-ptY0)/(x0-ptX0);
        double c0=y0-m0*x0;
        
        double ptX1=(x0+x2)/2;
        double ptY1=(y0+y2)/2;
        double m1=(y1-ptY1)/(x1-ptX1);
        double c1=y1-m1*x1;
        
        double ptX=(c1-c0)/(m0-m1);
        double ptY=ptX*m0 +c0;
        
        temp=ptX+","+ptY;
        return temp;
    }
    public void organizeAngBisLocal(ArrayList<String[]> tempList){
        ArrayList<String>temp=new ArrayList<String>();
        ArrayList<String>ftemp=new ArrayList<String>();
        reqPtList.clear();
        temp.clear();
        for(int i=0; i<tempList.size(); i++){
            String[] tempstr=tempList.get(i);
            double x0=Double.parseDouble(tempstr[0].split(",")[0]);
            double y0=Double.parseDouble(tempstr[0].split(",")[1]);
            double x1=Double.parseDouble(tempstr[1].split(",")[0]);
            double y1=Double.parseDouble(tempstr[1].split(",")[1]);
            double x2=Double.parseDouble(tempstr[2].split(",")[0]);
            double y2=Double.parseDouble(tempstr[2].split(",")[1]);
            double X,Y;
            if(((x1>=x0 && x1<=x2)||(x1>=x2 && x1<=x0))&&(y1>=y0 && y1>=y2)|| (y1<=y0 && y1<=y2)){
                X=x1+500;
                Y=y1;
            }else if(((x1<x0 && x1<x2)||(x1>x0 && x1>x2))&&((y1>y0 && y1<y2)||(y1>y2 && y1<y0))){
                X=x1;
                Y=y1+500;
            }else if(((x1>x0 && x1<x2)||(x1>x0 && x1<x2))&&((y1>y0 && y1<y2)||(y1>y2 && y1<y0))){
                //System.out.println("get condition y");
                X=x1;
                Y=y1+500;
            }else if(Math.abs(x1-x0)<0.01 && Math.abs(y2-y1)<0.01){
                X=x0;
                Y=y0;
            }else if(Math.abs(x1-x2)<0.01 && Math.abs(y0-y1)<0.01  && (y2>y1)){
                X=x0;
                Y=y0;
            }else if(Math.abs(x1-x0)<0.01 && Math.abs(y2-y1)<0.01  && (y0<y1)){
                X=x0;
                Y=y0;
            }else if(Math.abs(x1-x2)<0.01 && Math.abs(y0-y1)<0.01 && (y2<y1)){
                X=x0;
                Y=y0;
            }else{                
                X=x0;
                Y=y0;                    
            }
            if(tempstr.length>3){
                for(int j=3; j<tempstr.length; j++){
                    try{
                        double x3=Double.parseDouble(tempstr[j].split(",")[0]);
                        double y3=Double.parseDouble(tempstr[j].split(",")[1]);
                        double ang=determineAngle(X,Y,x1,y1,x3,y3);
                        temp.add(x3+","+y3+","+ang+","+x1+","+y1);
                    }catch(Exception exc){
                    }
                }
            }else{
                double x3=Double.parseDouble(tempstr[3].split(",")[0]);
                double y3=Double.parseDouble(tempstr[3].split(",")[1]);
                double ang=determineAngle(X,Y,x1,y1,x3,y3);
                temp.add(x3+","+y3+","+ang+","+x1+","+y1);
            }
            Collections.sort(temp, new C04_CompareDistGrid());
            if(temp.size()==3){
                double a0=Double.parseDouble(temp.get(0).split(",")[0]);
                double b0=Double.parseDouble(temp.get(0).split(",")[1]);
                double c0=Double.parseDouble(temp.get(0).split(",")[2]);
                
                double a1=Double.parseDouble(temp.get(1).split(",")[0]);
                double b1=Double.parseDouble(temp.get(1).split(",")[1]);
                double c1=Double.parseDouble(temp.get(1).split(",")[2]);
                
                double a2=Double.parseDouble(temp.get(2).split(",")[0]);
                double b2=Double.parseDouble(temp.get(2).split(",")[1]);
                double c2=Double.parseDouble(temp.get(2).split(",")[2]);

                if(((x1>x0 && x1<x2)||(x1>x2 && x1<x0))&&(y1>y0 && y1>y2)|| (y1<y0 && y1<y2)){
                    //System.out.println("condition x");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }else if(((x1<x0 && x1<x2)||(x1>x0 && x1>x2))&&((y1>y0 && y1<y2)||(y1>y2 && y1<y0))){
                    //System.out.println("condition x");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }else if(((x1>x0 && x1<x2))&&(y1>y0 && y1<y2)){
                    //highly obtuse angle 1
                    //System.out.println("condition x1");
                    reqPtList.add(a0+","+b0+","+x1+","+y1+","+x2+","+y2);
                }else if(((x1>x0 && x1<x2))&&(y1>y0 && y1<y2)){
                    //highly obtuse angle 1
                    //System.out.println("condition x2");
                    reqPtList.add(a2+","+b2+","+x1+","+y1+","+x2+","+y2);
                }else if((x1<x0 && x1>x2)&&(y1>y2 && y1<y0)){
                    //highly obtuse angle 1
                    //System.out.println("condition x3");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }else if((x1>x0 && x1<x2)&&(y0>y1 && y1>y2)){
                    //highly obtuse angle 1
                    //System.out.println("condition x4");
                    reqPtList.add(a2+","+b2+","+x1+","+y1+","+x2+","+y2);
                }else if(Math.abs(x1-x0)<0.01 && Math.abs(y2-y1)<0.01 && (y0>y1) && (x2>x1)){
                    //System.out.println("condition 1a");
                    reqPtList.add(a2+","+b2+","+x1+","+y1+","+x2+","+y2);                    
                }else if(Math.abs(x1-x2)<0.01 && Math.abs(y0-y1)<0.01 && (y2>y1) && (x0>x1)){
                    //System.out.println("condition 1b");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);                    
                }else if(Math.abs(x1-x2)<0.01 && Math.abs(y0-y1)<0.01 && (y2<y1) && (x0>x1)){
                    //System.out.println("condition 2a");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }else if(Math.abs(x1-x0)<0.01 && Math.abs(y2-y1)<0.01 && (y0<y1) && (x2>x1)){
                    //System.out.println("condition 2b");
                    reqPtList.add(a2+","+b2+","+x1+","+y1+","+x2+","+y2);
                }else if(Math.abs(x1-x2)<0.01 && Math.abs(y0-y1)<0.01 && (y1<y2) && (x0<x1)){
                    //System.out.println("condition 3a");
                    reqPtList.add(a0+","+b0+","+x1+","+y1+","+x2+","+y2);
                }else if(Math.abs(x1-x0)<0.01 && Math.abs(y2-y1)<0.01 && (y1<y0) && (x2<x1)){
                    //System.out.println("condition 3b");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }else if(Math.abs(x1-x2)<0.01 && Math.abs(y0-y1)<0.01 && (y2<y1) && (x1>x0)){
                    //System.out.println("condition 4a");
                    reqPtList.add(a2+","+b2+","+x1+","+y1+","+x2+","+y2);
                }else if(Math.abs(x1-x0)<0.01 && Math.abs(y2-y1)<0.01 && (y0<y1) && (x1>x2)){
                    //System.out.println("condition 4b");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }else if(((x1>=x0 && x1<x2)||(x1>=x2 && x1<=x0))&&((y1>=y0 && y1<y2)||(y1>=y2 && y1<y0))){
                    //System.out.println("set condition ya");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }else if((x1>x0 && x1>x2)&&(y1>=y2 && y0<y1)){
                    //System.out.println("set condition yb");
                    reqPtList.add(a2+","+b2+","+x1+","+y1+","+x2+","+y2);
                }else{
                    System.out.println("set condition z");
                    reqPtList.add(a1+","+b1+","+x1+","+y1+","+x2+","+y2);
                }                    
            }else{
                double nx=Double.parseDouble(temp.get(0).split(",")[0]);
                double ny=Double.parseDouble(temp.get(0).split(",")[1]);    
                reqPtList.add(nx+","+ny+","+x1+","+y1+","+x2+","+y2);
            }
            temp.clear();
        }
        reqPtList.add(reqPtList.get(0));
    }
    public ArrayList<String> makeParallel(ArrayList<String>reqPtList){
        ArrayList<String>freqPtList=new ArrayList<String>();
        freqPtList.clear();
        //reqPtList.add(reqPtList.get(0));
        for(int i=0; i<reqPtList.size()-1; i++){
            double nx1=Double.parseDouble(reqPtList.get(i).split(",")[0]);
            double ny1=Double.parseDouble(reqPtList.get(i).split(",")[1]);
            double nx2=Double.parseDouble(reqPtList.get(i+1).split(",")[0]);
            double ny2=Double.parseDouble(reqPtList.get(i+1).split(",")[1]);
            double x1=Double.parseDouble(reqPtList.get(i).split(",")[2]);
            double y1=Double.parseDouble(reqPtList.get(i).split(",")[3]);
            double x2=Double.parseDouble(reqPtList.get(i).split(",")[4]);
            double y2=Double.parseDouble(reqPtList.get(i).split(",")[5]);
            double x3=Double.parseDouble(reqPtList.get(i+1).split(",")[4]);
            double y3=Double.parseDouble(reqPtList.get(i+1).split(",")[5]);
            if(Math.abs(x2-x1)<0.00001){
                x2=x2+0.00001;
            }
            if(Math.abs(nx2-x2)<0.00001){
                nx2=nx2+0.00001;
            }
            if(Math.abs(nx2-nx1)<0.00001){
                nx2=nx2+0.00001;
            }
            double m2=(y2-y1)/(x2-x1);
            double c2=y2-(m2*x2);
            double m1=(ny2-y2)/(nx2-x2);
            double c1=ny2-(m1*nx2);
            double m3=(ny2-ny1)/(nx2-nx1);            
            double c3=ny2-m3*nx2;
            String s="";
            if(Math.abs(m3-m2)<0.00001){
               s=nx2+","+ny2+","+x2+","+y2+","+x3+","+y3;
            }else{
                //System.out.println("change");
                double nx=(m2*nx1-ny1+c1)/(m2-m1);
                double ny=m1*nx+c1;
                s=nx+","+ny+","+x2+","+y2+","+x3+","+y3;
            }
            reqPtList.set(i+1,s);
        }
        return reqPtList;
    }
}