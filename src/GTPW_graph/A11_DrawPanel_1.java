package gtpw_graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import javax.swing.JPanel;
import javax.swing.Timer;

public class A11_DrawPanel_1 extends JPanel implements ActionListener, MouseListener{
    U03_CurveSkeletonAlgo crvSkeleton;
    ArrayList<String> gridPtList;
    ArrayList<String> figSegmentList;
    ArrayList<String> finalFigPtList;
    ArrayList<String> internalGridPtList;
    ArrayList<String> seqSegList;
    ArrayList<String> angList;
    ArrayList<String> angBisectorList;
    ArrayList<String> angBisectorSegList;
    ArrayList<String> boundingbox;
    ArrayList<String> intersectionList;
    ArrayList<String> minsortAreaABList;
    ArrayList<String> selectionList;
    ArrayList<A04_GraphObject> graphObjList;
    ArrayList<String>mosPosList;
    ArrayList<String>nodeList;
    ArrayList<String>reqList;
    ArrayList<String>reqPtList;
    ArrayList<String>divPtList;
    ArrayList<String>div2PtList;
    ArrayList<String>perSegList;
    ArrayList<String>per2SegList;
    ArrayList<String>curveSkeletonPtList;
    boolean drawSite=true;
    int select=1000;
    int WIDTH, HEIGHT;
    Timer t;
    double a;
    
    A11_DrawPanel_1(int WIDTH_, int HEIGHT_, ArrayList<gtpw_graph.A04_GraphObject> graphObjList_, double gridspacing){
        a=gridspacing;
        WIDTH=WIDTH_;
        HEIGHT=HEIGHT_;
        gridPtList=new ArrayList<String>();
        figSegmentList=new ArrayList<String>();
        finalFigPtList=new ArrayList<String>();
        internalGridPtList=new ArrayList<String>();  
        seqSegList=new ArrayList<String>();  
        angList=new ArrayList<String>();  
        boundingbox=new ArrayList<String>();  
        angBisectorList=new ArrayList<String>();
        angBisectorSegList=new ArrayList<String>();
        intersectionList=new ArrayList<String>();
        minsortAreaABList=new ArrayList<String>();
        graphObjList=new ArrayList<A04_GraphObject>();
        graphObjList.clear();
        //graphObjList.addAll(graphObjList_);
        //System.out.println(graphObjList.size());
        mosPosList=new ArrayList<String>();
        nodeList=new ArrayList<String>();
        reqList=new ArrayList<String>();
        reqPtList=new ArrayList<String>();
        divPtList=new ArrayList<String>();
        div2PtList=new ArrayList<String>();
        perSegList=new ArrayList<String>();
        per2SegList=new ArrayList<String>();
        curveSkeletonPtList=new ArrayList<String>();
        divPtList.clear();
        div2PtList.clear();
        perSegList.clear();
        per2SegList.clear();
        for(int i=0; i<WIDTH; i+=a){
            for(int j=0; j<HEIGHT; j+=a){
                gridPtList.add(i+","+j);
            }
        }
        
        t=new Timer(150,this);
        t.start();
        addMouseListener(this);
        setdefaultPosition();
    }
    public void setdefaultPosition(){
        int n=graphObjList.size();
        for(int i=0; i<graphObjList.size(); i++){
            A04_GraphObject objA=graphObjList.get(i);
            double r=300;  
            double dx=400+r*Math.cos(Math.toRadians(i*360/n));
            double dy=500+r*Math.sin(Math.toRadians(i*360/n));
            objA.setXPos(dx);
            objA.setYPos(dy);
        }
    }
    public ArrayList<String> bound(){
        boundingbox.clear();
        //LinkedHashSet<String>lhs=new LinkedHashSet<String>();
        ArrayList<String>lhs=new ArrayList<String>();
        lhs.addAll(finalFigPtList);
        if(lhs.size()>0){
            Collections.sort(lhs, new C05_CompareMinX());
            double x0=Double.parseDouble(lhs.get(0).split(",")[0]);
            double x1=Double.parseDouble(lhs.get(lhs.size()-1).split(",")[0]);
            Collections.sort(lhs, new C06_CompareMinY());
            double y0=Double.parseDouble(lhs.get(0).split(",")[1]);
            double y1=Double.parseDouble(lhs.get(lhs.size()-1).split(",")[1]);
            boundingbox.add(x0+","+y0+","+x1+","+y1);
        }
        return boundingbox;
    }
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d=(Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fill(new Rectangle2D.Double(0,0,1500,1000));
        for(int i=0; i<gridPtList.size(); i++){
            double x=Double.parseDouble(gridPtList.get(i).split(",")[0]);
            double y=Double.parseDouble(gridPtList.get(i).split(",")[1]);
            g2d.setColor(new Color(200,200,200));
            g2d.draw(new Rectangle2D.Double(x,y,a,a));
        }                
        for(int i=0;i<finalFigPtList.size(); i++){
            Double x0=Double.parseDouble(finalFigPtList.get(i).split(",")[0]);
            Double y0=Double.parseDouble(finalFigPtList.get(i).split(",")[1]);
            g2d.setColor(new Color(0,0,0));
            g2d.setStroke(new BasicStroke(1.0F));
            g2d.fill(new Ellipse2D.Double(x0-3.5,y0-3.5,7,7));
            if(i<finalFigPtList.size()-1){
                g2d.drawString("#"+i,(int)(x0-20), (int)(y0-10));
            }
            if(drawSite==true){
                g2d.setColor(new Color(170,170,0,50));
                g2d.setStroke(new BasicStroke(15.0f));
                g2d.draw(new Line2D.Double(0,y0,1500,y0));
                g2d.draw(new Line2D.Double(x0,0,x0,1500));
            }
        }
        for(int i=1;i<finalFigPtList.size(); i++){
           Double x0=Double.parseDouble(finalFigPtList.get(i-1).split(",")[0]);
           Double y0=Double.parseDouble(finalFigPtList.get(i-1).split(",")[1]);
           Double x1=Double.parseDouble(finalFigPtList.get(i).split(",")[0]);
           Double y1=Double.parseDouble(finalFigPtList.get(i).split(",")[1]);
           figSegmentList.add(x0+","+y0+","+x1+","+y1);
           g2d.setColor(new Color(0,0,0));
           g2d.setStroke(new BasicStroke(3.5F));
           g2d.draw(new Line2D.Double(x0,y0,x1,y1));
        }
        
        for(int i=0;i<internalGridPtList.size(); i++){
            double x=Double.parseDouble(internalGridPtList.get(i).split(",")[0]);
            double y=Double.parseDouble(internalGridPtList.get(i).split(",")[1]);
            g2d.setColor(new Color(255,255,255,50));
            g2d.fill(new Rectangle2D.Double(x,y,a,a));
        }
        
        if(drawSite==false){
            nodeList.clear();
            int n=graphObjList.size();
            g2d.scale(1,1);
            for(int i=0; i<graphObjList.size(); i++){
                A04_GraphObject objA=graphObjList.get(i);
                g2d.setColor(new Color(0,0,0));
                double dx=objA.getXPos();
                double dy=objA.getYPos();
                double dia=objA.getDim()*objA.getQnty();
                g2d.setStroke(new BasicStroke(1.0F));
                g2d.draw(new Ellipse2D.Double(dx-(int)dia/2,dy-(int)dia/2,(int)dia,(int)dia));
                g2d.fill(new Ellipse2D.Double(dx-5,dy-5,(int)10,(int)10));
                g2d.drawString("#"+objA.getId()+"-"+objA.getRelId(),(int)(dx), (int)(dy));
            }
            
            for(int i=0; i<graphObjList.size(); i++){
              A04_GraphObject objA=graphObjList.get(i); 
              if(objA.getSelected()==true){
                  double dx=objA.getXPos();
                  double dy=objA.getYPos();
                  double dia=objA.getDim()*objA.getQnty();
                  g2d.setStroke(new BasicStroke(1.0F));
                  g2d.fill(new Ellipse2D.Double(dx-(int)dia/2,dy-(int)dia/2,(int)dia,(int)dia));
              }
            }

            for(int i=0; i<angBisectorList.size(); i++){
                double x1=Double.parseDouble(angBisectorList.get(i).split(",")[0]);
                double y1=Double.parseDouble(angBisectorList.get(i).split(",")[1]);
                double x_Positive1=Double.parseDouble(angBisectorList.get(i).split(",")[2]);
                double y_Positive1=Double.parseDouble(angBisectorList.get(i).split(",")[3]);
                double x_Positive2=Double.parseDouble(angBisectorList.get(i).split(",")[4]);
                double y_Positive2=Double.parseDouble(angBisectorList.get(i).split(",")[5]);
                double x_Negative1=Double.parseDouble(angBisectorList.get(i).split(",")[6]);
                double y_Negative1=Double.parseDouble(angBisectorList.get(i).split(",")[7]);
                double x_Negative2=Double.parseDouble(angBisectorList.get(i).split(",")[8]);
                double y_Negative2=Double.parseDouble(angBisectorList.get(i).split(",")[9]);
                g2d.setStroke(new BasicStroke(1.0F));
                g2d.setColor(new Color(100,100,100));
                g2d.draw(new Line2D.Double(x1,y1,x_Positive1,y_Positive1));
                //g2d.setColor(new Color(0,250,250,50));
                g2d.draw(new Line2D.Double(x1,y1,x_Positive2,y_Positive2));
                //g2d.setColor(new Color(250,0,250,50));
                g2d.draw(new Line2D.Double(x1,y1,x_Negative1,y_Negative1));
                //g2d.setColor(new Color(100,100,100,50));
                g2d.draw(new Line2D.Double(x1,y1,x_Negative2,y_Negative2));
            }
            if(angBisectorSegList.size()>0){
                for(int i=0; i<angBisectorSegList.size(); i++){
                    double x0=Double.parseDouble(angBisectorSegList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(angBisectorSegList.get(i).split(",")[1]);
                    double x1=Double.parseDouble(angBisectorSegList.get(i).split(",")[2]);
                    double y1=Double.parseDouble(angBisectorSegList.get(i).split(",")[3]);
                    g2d.setStroke(new BasicStroke(1.5F));
                    g2d.setColor(new Color(0,0,0));
                    //g2d.draw(new Line2D.Double(x0,y0,x1,y1));
                    g2d.fill(new Ellipse2D.Double(x1-5,y1-5,10,10));
                    g2d.drawString("@"+i,(int)x1+10,(int)y1+10);
                }
                }

            if(boundingbox.size()>0){
                double bbx0=Double.parseDouble(boundingbox.get(0).split(",")[0]);
                double bbx1=Double.parseDouble(boundingbox.get(0).split(",")[1]);
                double bbx2=Double.parseDouble(boundingbox.get(0).split(",")[2]);
                double bbx3=Double.parseDouble(boundingbox.get(0).split(",")[3]);
                g2d.setStroke(new BasicStroke(1.0F));
                g2d.setColor(new Color(0,0,255,150));
                g2d.draw(new Rectangle2D.Double(bbx0,bbx1,bbx2-bbx0,bbx3-bbx1));
                g2d.fill(new Ellipse2D.Double((bbx0+bbx2)/2 - 5, (bbx1+bbx3)/2 - 5, 10,10));
            }
            
            if(reqPtList.size()>0){                
                for(int i=0; i<reqPtList.size()-1; i++){
                    double x0=Double.parseDouble(reqPtList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(reqPtList.get(i).split(",")[1]);
                    double ax0=Double.parseDouble(reqPtList.get(i).split(",")[2]);
                    double ay0=Double.parseDouble(reqPtList.get(i).split(",")[3]);
                    double x1=Double.parseDouble(reqPtList.get(i+1).split(",")[0]);
                    double y1=Double.parseDouble(reqPtList.get(i+1).split(",")[1]);
                    double ax1=Double.parseDouble(reqPtList.get(i).split(",")[2]);
                    double ay1=Double.parseDouble(reqPtList.get(i).split(",")[3]);
                    g2d.setStroke(new BasicStroke(2.5F));
                    g2d.setColor(new Color(0,0,0));
                    g2d.fill(new Ellipse2D.Double(x1-5,y1-5,10,10));
                    g2d.draw(new Line2D.Double(x0,y0,x1,y1));
                    g2d.drawString("o"+i,(int)x0+10,(int)y0+25);  
                    g2d.draw(new Line2D.Double(x0,y0,ax0,ay0));
                }
            }
            if(reqPtList.size()>0){        
                ArrayList<String>tempList=new ArrayList<String>();
                tempList.clear();
                curveSkeletonPtList.clear();
                tempList.addAll(reqPtList);
                for(int i=0; i<tempList.size(); i++){
                    double x0=Double.parseDouble(tempList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(tempList.get(i).split(",")[1]);
                    double ax0=Double.parseDouble(tempList.get(i).split(",")[2]);
                    double ay0=Double.parseDouble(tempList.get(i).split(",")[3]);                    
                    tempList.set(i,(x0+","+y0+","+x0+","+ax0+","+ay0));                            
                }
                Collections.sort(tempList, new C04_CompareDistGrid());
                for(int i=0; i<tempList.size()-2; i+=2){
                    double x0=Double.parseDouble(tempList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(tempList.get(i).split(",")[1]);
                    double ax0=Double.parseDouble(tempList.get(i).split(",")[3]);
                    double ay0=Double.parseDouble(tempList.get(i).split(",")[4]);
                    
                    double x1=Double.parseDouble(tempList.get(i+1).split(",")[0]);
                    double y1=Double.parseDouble(tempList.get(i+1).split(",")[1]);
                    double ax1=Double.parseDouble(tempList.get(i+1).split(",")[3]);
                    double ay1=Double.parseDouble(tempList.get(i+1).split(",")[4]);
                    if(Math.abs(ax0-x0)<0.000001){
                        ax0+=0.000001;
                    }
                    if(Math.abs(ax1-x1)<0.000001){
                        ax1+=0.000001;
                    }
                    double m0=(ay0-y0)/(ax0-x0);
                    double c0=(ay0)-(m0*ax0);
                    double m1=(ay1-y1)/(ax1-x1);
                    double c1=(ay1)-(m1*ax1);
                    //double x=(c1-c0)/(m0-m1);
                    //double y= m1*x+c1;
                    double x=(x0+x1)/2;
                    double y=(y0+y1)/2;
                    curveSkeletonPtList.add(x+","+y);
                }
                for(int i=0; i<curveSkeletonPtList.size()-1; i++){
                    double x0=Double.parseDouble(curveSkeletonPtList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(curveSkeletonPtList.get(i).split(",")[1]);
                    double x1=Double.parseDouble(curveSkeletonPtList.get(i+1).split(",")[0]);
                    double y1=Double.parseDouble(curveSkeletonPtList.get(i+1).split(",")[1]);
                    g2d.setStroke(new BasicStroke(0.5F));
                    g2d.setColor(new Color(200,200,200));
                    //g2d.draw(new Line2D.Double(x0,y0,x1,y1));
                    g2d.fill(new Ellipse2D.Double(x0-7.5,y0-7.5,15,15));
                }
            }
            if(divPtList.size()>0){
                for(int i=0; i<divPtList.size(); i++){
                    double x0=Double.parseDouble(divPtList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(divPtList.get(i).split(",")[1]);
                    g2d.setColor(new Color(0,0,255));
                    g2d.fill(new Ellipse2D.Double(x0-2.5,y0-2.5,5,5));
                }
            }
            if(div2PtList.size()>0){
                for(int i=0; i<div2PtList.size(); i++){
                    double x0=Double.parseDouble(div2PtList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(div2PtList.get(i).split(",")[1]);
                    g2d.setColor(new Color(0,0,255));
                    g2d.fill(new Ellipse2D.Double(x0-5,y0-5,10,10));
                }
            }
            if(perSegList.size()>0){
                for(int i=0; i<perSegList.size(); i++){
                    double x0=Double.parseDouble(perSegList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(perSegList.get(i).split(",")[1]);
                    double x1=Double.parseDouble(perSegList.get(i).split(",")[3]);
                    double y1=Double.parseDouble(perSegList.get(i).split(",")[4]);
                    g2d.setColor(new Color(255,0,0));
                    g2d.setStroke(new BasicStroke(0.5F));
                    g2d.draw(new Line2D.Double(x0,y0,x1,y1));
                }
            }
            if(per2SegList.size()>0){
                for(int i=0; i<per2SegList.size(); i++){
                    double x0=Double.parseDouble(per2SegList.get(i).split(",")[0]);
                    double y0=Double.parseDouble(per2SegList.get(i).split(",")[1]);
                    double x1=Double.parseDouble(per2SegList.get(i).split(",")[3]);
                    double y1=Double.parseDouble(per2SegList.get(i).split(",")[4]);
                    g2d.setColor(new Color(255,0,0));
                    g2d.setStroke(new BasicStroke(0.5F));
                    g2d.draw(new Line2D.Double(x0,y0,x1,y1));
                }
            }
        }
    repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
    @Override
    public void mouseClicked(MouseEvent e) {   
        if(drawSite==true){
            ArrayList<String>tempList=new ArrayList<String>();
            double mosX=e.getX();
            double mosY=e.getY();
            tempList.clear();
            if(e.getButton()==MouseEvent.BUTTON1){
                for(int i=0; i<gridPtList.size(); i++){
                    double x=Double.parseDouble(gridPtList.get(i).split(",")[0]);
                    double y=Double.parseDouble(gridPtList.get(i).split(",")[1]);
                    double dist=Math.sqrt(Math.pow((mosX-x),2)+ Math.pow((mosY-y),2));
                    tempList.add(x+","+y+","+dist);
                }
            }
            if(tempList.size()>0){
                Collections.sort(tempList,new C04_CompareDistGrid());
                finalFigPtList.add(tempList.get(0).split(",")[0]+","+tempList.get(0).split(",")[1]);
                tempList.clear();
                LinkedHashSet<String>lhs=new LinkedHashSet<String>();
                lhs.addAll(finalFigPtList);
                finalFigPtList.clear();
                finalFigPtList.addAll(lhs);
                lhs.clear();
            }
            if(e.getButton()==MouseEvent.BUTTON3){
                finalFigPtList.add(finalFigPtList.get(0));
                drawSite=false;
            }
        }
        mosPosList.clear();
        if(drawSite==false){
            double mosX=e.getX();
            double mosY=e.getY();
             for(int i=0; i<graphObjList.size(); i++){
                double dx=graphObjList.get(i).getXPos();
                double dy=graphObjList.get(i).getYPos();
                double cx=Math.abs(dx-mosX);
                double cy=Math.abs(dy-mosY);
                double r=graphObjList.get(i).getDim()*graphObjList.get(i).getQnty();
                if(cx<100 && cy<100){
                    graphObjList.get(i).setSelected(true);
                    System.out.println("object selected : "+graphObjList.get(i).getId());
                }
            }
            mosPosList.add(mosX+","+mosY); 
        }
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mosPosList.clear();
        double mosX=e.getX();
        double mosY=e.getY();
         for(int i=0; i<graphObjList.size(); i++){
            double dx=graphObjList.get(i).getXPos();
            double dy=graphObjList.get(i).getYPos();
            double cx=Math.abs(dx-mosX);
            double cy=Math.abs(dy-mosY);
            double di=Math.sqrt(cx*cx + cy*cy);
            if(di<100){
                graphObjList.get(i).setSelected(true);
                System.out.println("object selected : "+graphObjList.get(i).getId());
            }
        }
        mosPosList.add(mosX+","+mosY);        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double mosX=e.getX();
        double mosY=e.getY();
        for(int i=0; i<graphObjList.size(); i++){
            if(graphObjList.get(i).getSelected()==true){
                graphObjList.get(i).setXPos(mosX);
                graphObjList.get(i).setYPos(mosY);
                graphObjList.get(i).setSelected(false);
            }
        }
        repaint();        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
