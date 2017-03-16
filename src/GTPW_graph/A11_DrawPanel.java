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
        //graphObjList.clear();
        graphObjList.addAll(graphObjList_);
        
        mosPosList=new ArrayList<String>();
        nodeList=new ArrayList<String>();
        
        timer=new Timer(100, this);
        timer.start();
        
        addMouseListener(this);
    }
    public void paint(Graphics g){
        Graphics2D g2d=(Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fill(new Rectangle2D.Double(0,0,WIDTH,HEIGHT));
        nodeList.clear();
        for(int i=0; i<graphObjList.size(); i++){
            A04_GraphObject objA=graphObjList.get(i);
            double dim=objA.getDim();
            int id=objA.getId();
            double x=objA.getXPos();
            double y=objA.getYPos();
            double area=objA.getAr_each();
            double qnty=objA.getQnty();
            nodeList.add(x+","+y+","+area+","+qnty);
            g2d.setColor(new Color(0,0,0));
            double r=15;
            g2d.fill(new Ellipse2D.Double(x-r/2,y-r/2,r,r));
            g2d.drawString("#"+id, (int)x, (int)y+20);
        }
        for(int i=0;i<mosPosList.size()-1; i++){
            double x0=Double.parseDouble(mosPosList.get(i).split(",")[0]);
            double y0=Double.parseDouble(mosPosList.get(i).split(",")[1]);
            double x1=Double.parseDouble(mosPosList.get(i+1).split(",")[0]);
            double y1=Double.parseDouble(mosPosList.get(i+1).split(",")[1]);
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
        }
        
        for(int i=0; i<nodeList.size()-1; i++){
            double x0=Double.parseDouble(nodeList.get(i).split(",")[0]);
            double y0=Double.parseDouble(nodeList.get(i).split(",")[1]);
            double x1=Double.parseDouble(nodeList.get(i+1).split(",")[0]);
            double y1=Double.parseDouble(nodeList.get(i+1).split(",")[1]);
            g2d.draw(new Line2D.Double(x0,y0,x1,y1));
            double slope;
            if(x0-x1==0){
                slope=90;
            }else{
                slope=(y0-y1)/(x0-x1);
            }
            double theta=Math.atan(slope);
            double r=100;
            g2d.translate(x0,y0);
            g2d.rotate(theta);
            g2d.draw(new Rectangle2D.Double(-r/2,-r/2,r,r));
            g2d.rotate(-theta);
            g2d.translate(-x0,-y0);
            
        }
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {        
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mosPosList.clear();
        double mosX=e.getX();
        double mosY=e.getY();
        System.out.println("mouse pressed : "+mosX+","+mosY);
        for(int i=0; i<graphObjList.size(); i++){
            double dx=graphObjList.get(i).getXPos();
            double dy=graphObjList.get(i).getYPos();
            double cx=Math.abs(dx-mosX);
            double cy=Math.abs(dy-mosY);
            double di=Math.sqrt(cx*cx + cy*cy);
            if(di<20){
                System.out.println("selection get : "+i);
                graphObjList.get(i).setSelected(true);
            }
        }
        mosPosList.add(mosX+","+mosY);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        double mosX=e.getX();
        double mosY=e.getY();
        System.out.println("mouse released : "+mosX+","+mosY);
        for(int i=0; i<graphObjList.size(); i++){
            if(graphObjList.get(i).getSelected()==true){
                System.out.println("selection set : "+i);
                graphObjList.get(i).setXPos(mosX);
                graphObjList.get(i).setYPos(mosY);
                graphObjList.get(i).setSelected(false);
            }
        }
        mosPosList.add(mosX+","+mosY);
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