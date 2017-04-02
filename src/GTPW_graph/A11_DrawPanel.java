package gtpw_graph;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.Timer;


public class A11_DrawPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener{
    int WIDTH,HEIGHT;
    Timer timer;
    ArrayList<A04_GraphObject> graphObjList;
    ArrayList<String>mosPosList;
    ArrayList<String>nodeList;
    int select=1000;
    A11_DrawPanel(int WIDTH_, int HEIGHT_, ArrayList<A04_GraphObject> graphObjList_){

        WIDTH=WIDTH_;
        HEIGHT=HEIGHT_;
        
        graphObjList=new ArrayList<A04_GraphObject>();
        graphObjList.clear();
        graphObjList.addAll(graphObjList_);
        System.out.println(graphObjList.size());
        mosPosList=new ArrayList<String>();
        nodeList=new ArrayList<String>();
        
        timer=new Timer(100, this);
        timer.start();
        
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
    public void paint(Graphics g){
        Graphics2D g2d=(Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fill(new Rectangle2D.Double(0,0,WIDTH,HEIGHT));
        nodeList.clear();
        int n=graphObjList.size();
        g2d.scale(1,1);
        for(int i=0; i<graphObjList.size(); i++){
            A04_GraphObject objA=graphObjList.get(i);
            g2d.setColor(new Color(0,0,0));
            double dx=objA.getXPos();
            double dy=objA.getYPos();
            double dia=objA.getDim()*objA.getQnty();
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
              g2d.fill(new Ellipse2D.Double(dx-(int)dia/2,dy-(int)dia/2,(int)dia,(int)dia));
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
        mosPosList.clear();
        double mosX=e.getX();
        double mosY=e.getY();
        System.out.println("mouse clicked : "+mosX+","+mosY);
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
        System.out.println("mouse pressed");
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
        System.out.println("mouse released");
        repaint();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mosPosList.clear();
    }
}