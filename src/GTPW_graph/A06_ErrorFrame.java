package gtpw_graph;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class A06_ErrorFrame extends JFrame{
    A07_ErrorPanel errPnl;
    public A06_ErrorFrame(){
     setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
     setTitle("Message FRAME");
     setLocation(50,50);
     setSize(250,150);
     errPnl=new A07_ErrorPanel("Error - NO DATA");
     errPnl.setBounds(0,0,250,100);
     add(errPnl);
    }    
}
