package gtpw_graph;

import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;

public class A10_DrawFrame extends JFrame{
    ArrayList<A04_GraphObject> graphObjList;
    //A11_DrawPanel pnl;
    A11_DrawPanel_1 pnl;
    int WIDTH=1500;
    int HEIGHT=1000;
    A10_DrawFrame(ArrayList<A04_GraphObject> graphObjList_, double gridspacing){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("DRAWING FRAME");
        setSize(WIDTH, HEIGHT);
        setLocation(430,0);
        setVisible(true);
        graphObjList=new ArrayList<A04_GraphObject>();
        graphObjList.clear();
        graphObjList.addAll(graphObjList_);
        //pnl=new A11_DrawPanel(WIDTH, HEIGHT,graphObjList);
        pnl=new A11_DrawPanel_1(WIDTH, HEIGHT,graphObjList, gridspacing);
        add(pnl);
    }    
}
