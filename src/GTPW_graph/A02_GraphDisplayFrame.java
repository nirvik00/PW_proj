package gtpw_graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JFrame;

public class A02_GraphDisplayFrame extends JFrame{
    A03_GraphDisplayPanel pnl;    
    
    ArrayList<String>dataList;
    ArrayList<String>weightList;
    ArrayList<A04_GraphObject>graphObjList;
    ArrayList<String>minSpanningTree;
    
    int WIDTH=500;
    int HEIGHT=1000;
            
    Random rnd;
    
    A02_GraphDisplayFrame(ArrayList<String>dataList_){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(WIDTH,HEIGHT);
        setVisible(true);
        setLocation(500,0);
        setTitle("GTPW_Layout_Algorithm");
        rnd=new Random();
        dataList=new ArrayList<String>();
        dataList.clear();
        dataList.addAll(dataList_);
        graphObjList=new ArrayList<A04_GraphObject>();
        minSpanningTree=new ArrayList<String>();
        graphObjList.clear();
        for(int i=1; i<dataList.size(); i++){
            int id=i-1;
            String prog_name=dataList.get(i).split(";")[1];
            double qnty=Double.parseDouble(dataList.get(i).split(";")[3]);
            double ar_each=Double.parseDouble(dataList.get(i).split(";")[4]);
            double dim_each=Math.sqrt(ar_each);
            String adj_to=dataList.get(i).split(";")[8];
            int red=rnd.nextInt(255);
            int grn=rnd.nextInt(255);
            int blu=rnd.nextInt(255);
            int[] rgb={red,grn,blu};
            int weight=(int)Double.parseDouble(dataList.get(i).split(";")[6]);
            A04_GraphObject g_obj=new A04_GraphObject(id,prog_name,qnty,ar_each,dim_each,adj_to,rgb,weight);
            graphObjList.add(g_obj);
            
        }
        Collections.sort(graphObjList, new C02_CompareNodeWeight());
        for(int i=0; i<graphObjList.size(); i++){
            double x=WIDTH/2;
            double y=HEIGHT/2;
            double r=(x+y)/3-50;
            double theta=(i+1)*(360/(dataList.size()));
            double dx=x-20+r*Math.cos(Math.toRadians(theta));
            double dy=y+100+r*Math.sin(Math.toRadians(theta));
            graphObjList.get(i).setXPos(dx);
            graphObjList.get(i).setYPos(dy);
        }
        weightList=new ArrayList<String>();
        weightList.clear();
        U00_Kruskal kruskal=new U00_Kruskal(graphObjList);
        weightList.addAll(kruskal.findMinWeight());
        // String[] nodeStrArr=nodes.split(",");
        kruskal.sortNode();
        minSpanningTree.addAll(kruskal.minSpanningTree);
        
        pnl=new A03_GraphDisplayPanel(graphObjList, 300, 70, WIDTH, HEIGHT, weightList, minSpanningTree);
        pnl.setBounds(0,0,1000,1000);
        setLayout(null);
        add(pnl);
    }
}