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
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;


//public class A09_ForcePanel extends JPanel implements MouseListener, ActionListener{
public class A09_ForcePanel extends JPanel{
    ArrayList<A04_GraphObject>graphObjectList, originalGraphObjectList;
    ArrayList<String>minSpanningTreeStr;
    ArrayList<A04_GraphObject>mstObjectList;
    ArrayList<String>weightList;
    ArrayList<String>splitList;
    //Timer timer;
    Random rnd;
    double xpos, ypos;
    double maxDi,minDi;
    int WIDTH, HEIGHT;
    int timercount=0;
    
    A09_ForcePanel(ArrayList<A04_GraphObject>graphObjectList_, double maxDi_, double minDi_, int WIDTH_, int HEIGHT_, ArrayList<String>weightList_,  ArrayList<String>minSpanningTree_){
        //addMouseListener(this);
        //timer=new Timer(1,this);
        //timer.start();
        rnd=new Random();
        WIDTH=WIDTH_;
        HEIGHT=HEIGHT_;
        xpos=WIDTH/2;
        ypos=HEIGHT/2;
        graphObjectList=new ArrayList<A04_GraphObject>();
        originalGraphObjectList=new ArrayList<A04_GraphObject>();
        graphObjectList.addAll(graphObjectList_);
        originalGraphObjectList.addAll(graphObjectList_);
        maxDi=maxDi_;
        minDi=minDi_;
        weightList=new ArrayList<String>();
        weightList.clear();
        weightList.addAll(weightList_);
        minSpanningTreeStr=new ArrayList<String>();
        minSpanningTreeStr.addAll(minSpanningTree_);
        mstObjectList=new ArrayList<A04_GraphObject>();
    }  
    
    public int weightReturn(A04_GraphObject objA, A04_GraphObject objB){
        int wt=0;
        int idA=objA.getId();
        int idB=objB.getId();
        for(int i=0; i<weightList.size(); i++){
            int id0=Integer.parseInt(weightList.get(i).split(",")[0]);
            int id1=Integer.parseInt(weightList.get(i).split(",")[1]);
            int wtx=(int) Double.parseDouble(weightList.get(i).split(",")[2]);
            if((idA==id0 && idB==id1) || (idA==id1 && idB==id0)){
               wt=wtx;
            break;
            }
        }
        return wt;
    }
    public void moveX250(){
        for(int i=0; i<graphObjectList.size(); i++){
            A04_GraphObject objA=graphObjectList.get(i);
            double x0=objA.getXPos();
            double y0=objA.getYPos();
            double r0=objA.getDim();
            x0+=WIDTH/2;
            objA.setXPos(x0);
            objA.setYPos(y0);
        }
    }
    
    public void moveY250(){
        for(int i=0; i<graphObjectList.size(); i++){
            A04_GraphObject objA=graphObjectList.get(i);
            double x0=objA.getXPos();
            double y0=objA.getYPos();
            double r0=objA.getDim();
            y0-=HEIGHT/2;
            objA.setXPos(x0);
            objA.setYPos(y0);
        }
    }
    
    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d=(Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fillRect(0,0,WIDTH,HEIGHT);
        g2d.setColor(new Color(255,0,0));
        for(int i=0; i<graphObjectList.size(); i++){
            A04_GraphObject obj=graphObjectList.get(i);
            double dim=obj.getDim();
        }
        g2d.translate(50,100);
        double XAXIS=0;
        ArrayList<String> mp_line=new ArrayList<String>();
        mstObjectList.clear();
        if(minSpanningTreeStr.size()>0){
            for(int i=0; i<minSpanningTreeStr.size();i++){
                int id=Integer.parseInt(minSpanningTreeStr.get(i));
                for(int j=0; j<graphObjectList.size(); j++){
                    A04_GraphObject objA=graphObjectList.get(j);
                    int idA=objA.getId();
                    String name=objA.getName();
                    if(id==idA){
                        mstObjectList.add(objA);
                        double qnty=objA.getQnty();
                        double dim=objA.getDim();
                        double size=qnty*dim;
                        XAXIS+=size;
                        g2d.setColor(new Color(0,0,0));
                        g2d.draw(new Ellipse2D.Double(XAXIS-size,500-size/2,size,size));
                        mp_line.add((XAXIS-size/2)+","+(500)+","+idA);
                        if(i%2==0){
                            g2d.drawString("#"+id,(int)(XAXIS-size), (int)(HEIGHT/2+50));
                        }else{
                            g2d.drawString("#"+id,(int)(XAXIS-size), (int)(HEIGHT/2-50));
                        }
                    }
                }
            }
        }
        //plot the nodes on a line
        g2d.translate(0,-300);
        for(int i=0; i<mp_line.size()-1; i++){
            double x0=Double.parseDouble(mp_line.get(i).split(",")[0]);
            double y0=Double.parseDouble(mp_line.get(i).split(",")[1]);
            double x1=Double.parseDouble(mp_line.get(i+1).split(",")[0]);
            double y1=Double.parseDouble(mp_line.get(i+1).split(",")[1]);
            g2d.setColor(new Color(0,0,0));
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
        }
        //plot node of the line & id
        for(int i=0; i<mp_line.size(); i++){
            double x0=Double.parseDouble(mp_line.get(i).split(",")[0]);
            double y0=Double.parseDouble(mp_line.get(i).split(",")[1]);
            int id=(int)Double.parseDouble(mp_line.get(i).split(",")[2]);
            double r=5;
            g2d.setColor(new Color(255,0,0));
            g2d.fill(new Ellipse2D.Double(x0-r/2,y0-r/2,r,r));
            g2d.setColor(new Color(0,0,0));
            if(i%2==0){
                g2d.drawString("#"+id,(int)(x0-r),(int)y0+20);
            }else{
                g2d.drawString("#"+id,(int)(x0-r),(int)y0-15);
            }
        }
        g2d.translate(-130,130);
        //draw out the minimum spanning tree as a radial sequence
        ArrayList<String> cp_chain=new ArrayList<String>();
        cp_chain.clear();
        for(int i=0; i<mstObjectList.size(); i++){
            A04_GraphObject objA=mstObjectList.get(i);
            int id=objA.getId();
            double x=WIDTH/4;
            double y=HEIGHT/4;
            double r=(x+y)/3-50;
            double theta=i*(360/(mstObjectList.size()));
            double dx=x+r*Math.cos(Math.toRadians(theta));
            double dy=y-50+r*Math.sin(Math.toRadians(theta));
            //
            //
            //
            //TWO LINE OF CODE BELOW CAUSED A PROBLEM IN SUBSEQUNT VALUES BECAUSE 
            //THEY ARE IN REPAINT AND THEY SET THE VALUE OF THE OBJECT
            //SO THE OBJECT IN A DIFFERENT FRAME WILL ALSO BE UPDATED!!!!
            //
            //
            //objA.setXPos(dx);
            //objA.setYPos(dy);
            //-
            //
            //TWO LINE OF CODE ABOVE CAUSED A PROBLEM IN SUBSEQUNT VALUES BECAUSE 
            //THEY ARE IN REPAINT AND THEY SET THE VALUE OF THE OBJECT
            //SO THE OBJECT IN A DIFFERENT FRAME WILL ALSO BE UPDATED!!!!
            //
            //
            //
            cp_chain.add(dx+","+dy);
            g2d.setColor(new Color(255,0,0));
            double rx=10.0;
            g2d.fill(new Ellipse2D.Double(dx-rx/2,dy-rx/2,rx,rx));
            g2d.setColor(new Color(0,0,0));
            g2d.drawString("#"+id,(int) dx,(int) dy);
        }
        for(int i=0; i<cp_chain.size()-1; i++){ 
            double x0=Double.parseDouble(cp_chain.get(i).split(",")[0]);
            double y0=Double.parseDouble(cp_chain.get(i).split(",")[1]);
            double x1=Double.parseDouble(cp_chain.get(i+1).split(",")[0]);
            double y1=Double.parseDouble(cp_chain.get(i+1).split(",")[1]);
            g2d.setColor(new Color(0,0,0,50));
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
        }
        g2d.translate(130,70);
        for(int i=0; i<mstObjectList.size(); i++){
            A04_GraphObject objA=mstObjectList.get(i);
            String name=objA.getName();
            int id=objA.getId();       
            String qnty=String.valueOf(objA.getQnty());       
            String area=String.valueOf(objA.getAr_each());       
            g2d.setColor(new Color(0,0,0));
            double xA=WIDTH-500;
            double yA=20+i*15;
            g2d.drawString("id #"+id, (int)xA, (int)yA);
            g2d.drawString(name, (int)xA+50, (int)yA);
            g2d.drawString(qnty, (int)xA+220, (int)yA);
            g2d.drawString(area, (int)xA+270, (int)yA);
        }
        repaint();
    }
}
    /*
    @Override
    public void actionPerformed(ActionEvent e) {
        if(minSpanningTreeStr.size()>0){
            //System.out.println(minSpanningTreeStr);
        }
        repaint();
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        double mosX=e.getX();
        double mosY=e.getY();
        for(int i=0; i<graphObjectList.size(); i++){
            double dx=graphObjectList.get(i).getXPos();
            double dy=graphObjectList.get(i).getYPos();
            double cx=Math.abs(dx-mosX);
            double cy=Math.abs(dy-mosY);
            double di=Math.sqrt(cx*cx + cy*cy);
            if(di<100){
                graphObjectList.get(i).setSelected(true);
            }
        }
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double mosX=e.getX();
        double mosY=e.getY();
        for(int i=0; i<graphObjectList.size(); i++){
            if(graphObjectList.get(i).getSelected()==true){
                graphObjectList.get(i).setXPos(mosX);
                graphObjectList.get(i).setYPos(mosY);
                graphObjectList.get(i).setSelected(false);
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
}*/