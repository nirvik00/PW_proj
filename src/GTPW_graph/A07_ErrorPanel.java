package gtpw_graph;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class A07_ErrorPanel extends JPanel{
    String errorMessage="";
    public A07_ErrorPanel(String s){
        errorMessage=s;
    }
    public void paint(Graphics g){
        g.setColor(new Color(245,245,245));
        g.fillRect(0,0,500,500);
        g.setColor(new Color(255,0,0));
        g.drawString(errorMessage, 50,50);
        g.setColor(new Color(0,0,0));
        g.drawRect(30,30,150,40);
    }
}
