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
    Timer timer;
    Random rnd;
    double xpos, ypos;
    int WIDTH=1000;
    int HEIGHT=1000;
    double maxDi,minDi;
    A03_GraphDisplayPanel(ArrayList<A04_GraphObject>graphObjectList_, double maxDi_, double minDi_){
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
        treeObjList=new ArrayList<A04_GraphObject>();
        treeObjList.clear();
        
        maxDi=maxDi_;
        minDi=minDi_;
        
    }  
    public void layout3(){
        int n=graphObjectList.size();
        for(int i=0; i<graphObjectList.size();i++){
            A04_GraphObject objA=graphObjectList.get(i);
            double x0=objA.getXPos();
            double y0=objA.getYPos();
            double r0=objA.getDim();
            int[] adjTo=objA.getAdj_to();
            for(int j=0; j<graphObjectList.size(); j++){
                A04_GraphObject objB=graphObjectList.get(j);
                double x1=objB.getXPos();
                double y1=objB.getYPos();
                double r1=objB.getDim();
                double d1=Math.sqrt((x0-x1)*(x0-x1) + (y0-y1)*(y0-y1));
                double dx,dy;
                for(int k=0; k<adjTo.length; k++){
                    if(j==adjTo[k]){
                        if(d1>(r0+r1+maxDi)){
                            dx=x1+(r0*(x0-x1))/(d1);
                            dy=y1+(r0*(y0-y1))/(d1);
                            if(dx<0 || dx>WIDTH){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            if(dy<0 || dy>HEIGHT){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            objB.setXPos(dx);
                            objB.setYPos(dy);
                        }else if(d1<(r0+r1+minDi)){
                            dx=x1+(r0*(x1-x0))/(d1);
                            dy=y1+(r0*(y1-y0))/(d1);
                            if(dx<0 || dx>WIDTH){
                                dx=WIDTH/2;
                                dy=HEIGHT/2;
                            }
                            if(dy<0 || dy>HEIGHT){
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
                    }
                }
            }
        }
    }

    public void paint(Graphics g){
        super.paint(g);
        Graphics2D g2d=(Graphics2D) g.create();
        g2d.setColor(new Color(255,255,255));
        g2d.fillRect(0,0,WIDTH,HEIGHT-150);
        g2d.setColor(new Color(255,0,0));
        for(int i=0; i<graphObjectList.size(); i++){
            A04_GraphObject obj=graphObjectList.get(i);
            double dim=obj.getDim();
        }
        for(int i=0; i<graphObjectList.size(); i++){ 
            // PLOT OBJECTS
            A04_GraphObject objA=graphObjectList.get(i);
            double xA=objA.getXPos();
            double yA=objA.getYPos();
            g2d.setStroke(new BasicStroke(3F));
            // PLOT CONNECTIONS
            int[] adjTo=objA.getAdj_to();
            for(int j=0; j<adjTo.length; j++){
                int k=adjTo[j];
                A04_GraphObject objB=graphObjectList.get(k);
                double xB=objB.getXPos();
                double yB=objB.getYPos();
                g2d.setColor(new Color(0,0,0));
                g2d.setStroke(new BasicStroke(0.1F));
                g2d.draw(new Line2D.Double(xA,yA,xB,yB));
            }
            double qnty=objA.getQnty();
            double dim=objA.getDim()*qnty;    
            if(dim>minDi){
                dim=minDi*1.25;
            }
            String name=objA.getName();
            String idx=objA.getId();
            int[] rgb=objA.getRgb();
            g2d.setColor(new Color(rgb[0],rgb[1],rgb[2],150));
            g2d.fill(new Ellipse2D.Double(xA-dim/2, yA-dim/2, dim, dim));
            g2d.setColor(new Color(0,0,0));
            g2d.drawString(name, (int)xA+20, (int)yA);
            g2d.drawString("id #"+idx, (int)xA+20, (int)yA-15);
        }
        repaint();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //layout3();
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
