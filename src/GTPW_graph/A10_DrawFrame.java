package gtpw_graph;

import java.util.ArrayList;
import javax.swing.JFrame;

public class A10_DrawFrame extends JFrame{
    private static ArrayList<A04_GraphObject> graphObjList;
    A11_DrawPanel pnl;
    int WIDTH=1000;
    int HEIGHT=1000;
    A10_DrawFrame(ArrayList<A04_GraphObject> graphObjList_){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("DRAWING FRAME");
        setSize(WIDTH, HEIGHT);
        setLocation(500,0);
        setVisible(true);
        graphObjList=new ArrayList<A04_GraphObject>();
        graphObjList.clear();
        graphObjList.addAll(graphObjList_);
        pnl=new A11_DrawPanel(WIDTH, HEIGHT,graphObjList);
        add(pnl);
    }    
}
