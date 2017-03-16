package gtpw_graph;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class A03_GraphDisplayPanel extends JPanel implements MouseListener, ActionListener{
    JButton jbtnReset, jbtnRun;
    JLabel jlblMaxDi, jlblMinDi;
    JTextField jtfMaxDi, jtfMinDi;
    ArrayList<A04_GraphObject>graphObjectList, originalGraphObjectList;
    ArrayList<A04_GraphObject>treeObjList;
    ArrayList<String>weightList;
    ArrayList<String>minSpanningTree;
    Timer timer;
    Random rnd;
    double xpos, ypos;
    int WIDTH;
    int HEIGHT;
    double maxDi,minDi;
    
    
    A03_GraphDisplayPanel(ArrayList<A04_GraphObject>graphObjectList_, double maxDi_, double minDi_, int WIDTH_, int HEIGHT_, ArrayList<String>weightList_,ArrayList<String>minSpanningTree_){
        addMouseListener(this);
        timer=new Timer(1,this);
        timer.start();
        rnd=new Random();
        xpos=WIDTH/2;
        ypos=HEIGHT/2;
        graphObjectList=new ArrayList<A04_GraphObject>();
        originalGraphObjectList=new ArrayList<A04_GraphObject>();
        graphObjectList.addAll(graphObjectList_);
        originalGraphObjectList.addAll(graphObjectList_);
        minSpanningTree=new ArrayList<String>();
        minSpanningTree.addAll(minSpanningTree_);
        
        maxDi=maxDi_;
        minDi=minDi_;
        
        WIDTH=WIDTH_;
        HEIGHT=HEIGHT_;
        
        weightList=new ArrayList<String>();
        weightList.addAll(weightList_);
    }  
    
    public void layout3(){
        int n=graphObjectList.size();
        for(int i=0; i<graphObjectList.size();i++){
            A04_GraphObject objA=graphObjectList.get(i);
            double x0=objA.getXPos();
            double y0=objA.getYPos();
            double r0=objA.getDim();
            int[] adjTo=objA.getAdj_to();
            for(int j=0; j<adjTo.length; j++){
                for(int k=0; k<graphObjectList.size(); k++){
                    A04_GraphObject objB=graphObjectList.get(k);
                    int idB=objB.getId();
                    double x1=objB.getXPos();
                    double y1=objB.getYPos();
                    double r1=objB.getDim();
                    double d1=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
                    double dx,dy;
                    if(idB==adjTo[j]){
                        double p=Math.pow(d1,2);
                        if(d1>(r0+r1+maxDi)){
                            dx=x1+(r0*(x0-x1))/(p);
                            dy=y1+(r0*(y0-y1))/(p);
                            if(dx<=20 || dx>WIDTH){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            if(dy<=20 || dy>HEIGHT){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            objB.setXPos(dx);
                            objB.setYPos(dy);
                        }else if(d1<(r0+r1+minDi)){
                            dx=x1+(r0*(x1-x0))/(p);
                            dy=y1+(r0*(y1-y0))/(p);
                            if(dx<=20 || dx>WIDTH){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }if(dy<=20 || dy>HEIGHT){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            objB.setXPos(dx);
                            objB.setYPos(dy);
                        }else{
                            //DO NOTHING
                        }
                    }
                    //NOT ADJACENT 
                    else{                        
                        double p=Math.pow(d1,1);
                        if(p>0 && d1<(minDi)){
                            dx=x1+(r0*(x1-x0))/(p);
                            dy=y1+(r0*(y1-y0))/(p);
                            if(dx<0 || dx>WIDTH){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            if(dy<0 || dy>HEIGHT){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            //objB.setXPos(dx);
                            //objB.setYPos(dy);
                        }
                        
                    }
                }
            }
        }
    }
    
    public void layout_force(){  
        for(int i=0; i<graphObjectList.size(); i++){
            A04_GraphObject objA=graphObjectList.get(i);
            double x0=objA.getXPos();
            double y0=objA.getYPos();
            double r0=objA.getDim();
            for(int j=0; j<graphObjectList.size(); j++){
                A04_GraphObject objB=graphObjectList.get(j);
                double x1=objB.getXPos();
                double y1=objB.getYPos();
                double r1=objB.getDim();				
                double di=Math.sqrt(Math.pow(x0-x1, 2)+Math.pow(y0-y1,2));
                if(di>r0+r1+200 && r0>r1){
                    double dx=x1+(x0-x1)*(r0-r1)/(Math.pow(di,2));
                    double dy=y1+(y0-y1)*(r0-r1)/(Math.pow(di,2));
                    if(dx<0 || dx>1200){
                            moveX250();
                    }
                    if(dy<0 || dy>1000){
                            moveY250();
                    }
                    objB.setXPos(dx);
                    objB.setYPos(dy);
                }else if(di<r0+r1+50){
                    double p=Math.pow(di,2);
                    if(p>0){
                        double dx=x1+(x1-x0)/(Math.pow(di,2));
                        double dy=y1+(y1-y0)/(Math.pow(di,2));
                        if(dx<0 || dx>1200){
                                moveX250();
                        }
                        if(dy<0 || dy>1000){
                                moveY250();
                        }
                        objB.setXPos(dx);
                        objB.setYPos(dy);
                    }
                    }
                }
            }
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
            x0+=250;
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
            y0-=250;
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
            // PLOT OBJECTS
            A04_GraphObject objA=graphObjectList.get(i);
            double xA=objA.getXPos();
            double yA=objA.getYPos();
            g2d.setStroke(new BasicStroke(3F));
            // PLOT CONNECTIONS
            int[] adjTo=objA.getAdj_to();
            String name=objA.getName();
            String ck="";
            ck+=name+" => ";
            for(int j=0; j<adjTo.length; j++){
                for(int k=0; k<graphObjectList.size(); k++){
                    A04_GraphObject objB=graphObjectList.get(k);
                    int idB=objB.getId();
                    if(idB==adjTo[j]){
                        double xB=objB.getXPos();
                        double yB=objB.getYPos();
                        g2d.setColor(new Color(100,100,100));
                        g2d.setStroke(new BasicStroke(0.1F));
                        g2d.draw(new Line2D.Double(xA,yA,xB,yB));
                        double midptX=(xA+xB)/2;
                        double midptY=(yA+yB)/2;
                        int wt=weightReturn(objA, objB);
                        g2d.drawString("@"+wt, (int)midptX,(int)midptY);
                        String nameB=objB.getName();
                        ck+=nameB+";";
                    }
                }
            }
            //System.out.println("check : "+ck);
            double qnty=objA.getQnty();
            double dim=objA.getDim();    
            if(dim>minDi){
                dim=minDi*1.25;
            }
            int idx=objA.getId();
            int[] rgb=objA.getRgb();
            g2d.setColor(new Color(rgb[0],rgb[1],rgb[2],150));
            g2d.fill(new Ellipse2D.Double(xA-dim/2, yA-dim/2, dim, dim));
            g2d.setColor(new Color(0,0,0));
            g2d.drawString("id #"+idx, (int)xA+5, (int)yA);
        }
        for(int i=0; i<graphObjectList.size(); i++){
            String name=graphObjectList.get(i).getName();
            int[] adj=graphObjectList.get(i).getAdj_to();
            String adjStr="";
            for(int j=0; j<adj.length; j++){
                adjStr+=adj[j]+",";
            }
            int id=graphObjectList.get(i).getId();            
            g2d.setColor(new Color(0,0,0));
            double xA=WIDTH-350;
            double yA=20+i*15;
            g2d.drawString("id #"+id, (int)xA, (int)yA);
            g2d.drawString(name, (int)xA+50, (int)yA);
            g2d.drawString(adjStr, (int)xA+220, (int)yA);
        }
        String coordinates_mst="";
        for(int i=0; i<minSpanningTree.size(); i++){
            int id_ini=Integer.parseInt(minSpanningTree.get(i));
            for(int j=0; j<graphObjectList.size(); j++){
                A04_GraphObject objA=graphObjectList.get(j);
                int idx=objA.getId();
                if(id_ini==idx){
                    double dx=objA.getXPos();
                    double dy=objA.getYPos();
                    coordinates_mst+=dx+","+dy+";";
                }
            }
        }
        String[] coordinates_mst_arr=coordinates_mst.split(";");
        for(int i=0; i<coordinates_mst_arr.length-1; i++){
            String s0=coordinates_mst_arr[i];
            double x0=Double.parseDouble(s0.split(",")[0]);
            double y0=Double.parseDouble(s0.split(",")[1]);
            String s1=coordinates_mst_arr[i+1];
            double x1=Double.parseDouble(s1.split(",")[0]);
            double y1=Double.parseDouble(s1.split(",")[1]);
            g2d.setStroke(new BasicStroke(7f));
            g2d.setColor(new Color(255,0,0,50));
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
        }
        repaint();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //layout3();
        //layout_force();
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
            if(di<10){
                graphObjectList.get(i).setSelected(true);
            }
        }
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
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
