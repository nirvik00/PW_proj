package gtpw_graph;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class A00_GtPwGraphAlgoFrame extends JFrame{
    A01_GraphAlgoPanel pnl;
    A00_GtPwGraphAlgoFrame(){
        setTitle("Gatech & Perkins+Will");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(500,500);
        setLocation(0,0);
        
        pnl=new A01_GraphAlgoPanel();
        add(pnl);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new A00_GtPwGraphAlgoFrame();
            }
        });
    }
    
}
