package gtpw_graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class A08_ForceFrame extends JFrame{
    A09_ForcePanel pnl;
    U01_ObjectOperations callsplitobj;
    JTextField jtfTextSplit;
    JLabel jlblTextSplit;
    JButton jbtnCalcSplit, jbtnCalcReset, jbtnStop;
    Random rnd;
    ArrayList<String>dataList, weightList;
    ArrayList<String>minSpanningTree;
    ArrayList<String>dataCompareList;
    ArrayList<gtpw_graph.A04_GraphObject>graphObjList;
    ArrayList<gtpw_graph.A04_GraphObject>oriGraphObjList;
    int WIDTH=1000;
    int HEIGHT=1000;
    A08_ForceFrame(ArrayList<String>dataList_){
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setVisible(true);
        setLocation(1000,0);
        setSize(WIDTH,HEIGHT);
        setTitle("GTPW_MINIMAL_SPANNING_TREE_Algorithm");
        rnd=new Random();
        dataList=new ArrayList<String>();
        dataList.clear();
        dataList.addAll(dataList_);
        graphObjList=new ArrayList<gtpw_graph.A04_GraphObject>();
        graphObjList.clear();
        minSpanningTree=new ArrayList<String>();
        oriGraphObjList=new ArrayList<gtpw_graph.A04_GraphObject>();
        oriGraphObjList.clear();
        
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
            A04_GraphObject g_obj=new gtpw_graph.A04_GraphObject(id,prog_name,qnty,ar_each,dim_each,adj_to,rgb,weight);
            graphObjList.add(g_obj);
        }
        Collections.sort(graphObjList, new C02_CompareNodeWeight());
        for(int i=0; i<graphObjList.size(); i++){
            double x=WIDTH/2;
            double y=HEIGHT/2;
            double r=(x+y)/3-50;
            double theta=i*(360/(dataList.size()));
            double dx=x+r*Math.cos(Math.toRadians(theta));
            double dy=y-50+r*Math.sin(Math.toRadians(theta));
            graphObjList.get(i).setXPos(dx);
            graphObjList.get(i).setYPos(dy);
        }
        oriGraphObjList.addAll(graphObjList);
        weightList=new ArrayList<String>();
        weightList.clear();
        U00_Kruskal kruskal=new U00_Kruskal(graphObjList);
        weightList.addAll(kruskal.findMinWeight());
        jlblTextSplit=new JLabel("Split Nodes? format : split,id,number;");
        jlblTextSplit.setBounds(10,HEIGHT-120,230,40);
        jtfTextSplit =new JTextField("split,2,4;");
        jtfTextSplit.setBounds(250,HEIGHT-120,200,40);
        jbtnCalcSplit=new JButton("Calculate");
        jbtnCalcSplit.setBounds(470,HEIGHT-120,100,40);
        jbtnCalcReset=new JButton("Reset");
        jbtnCalcReset.setBounds(620,HEIGHT-120,100,40);
        jbtnStop=new JButton("Stop");
        jbtnStop.setBounds(750,HEIGHT-120,100,40);
        kruskal.sortNode();
        minSpanningTree.addAll(kruskal.minSpanningTree);
        pnl=new A09_ForcePanel(graphObjList, 300, 70, WIDTH, HEIGHT, weightList,minSpanningTree);
        pnl.setBounds(0,0,WIDTH,HEIGHT-150);
        setLayout(null);
        add(pnl);
        add(jlblTextSplit);
        add(jtfTextSplit);
        add(jbtnCalcSplit);
        add(jbtnCalcReset);
        add(jbtnStop);
        
        jbtnCalcSplit.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               String jtfTxt=jtfTextSplit.getText();
               String[] jtfStrArr=jtfTxt.split(";");
               ArrayList<gtpw_graph.A04_GraphObject>newGraphObjList=new ArrayList<gtpw_graph.A04_GraphObject>();
               newGraphObjList.clear();
               callsplitobj=new U01_ObjectOperations(graphObjList,jtfStrArr);
               newGraphObjList.addAll(callsplitobj.splitObject());
               pnl.graphObjectList.addAll(newGraphObjList);
           }
        });
        jbtnCalcReset.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){               
               ArrayList<gtpw_graph.A04_GraphObject>newGraphObjList=new ArrayList<gtpw_graph.A04_GraphObject>();
               jtfTextSplit.setText("split,2,4;");
               newGraphObjList.clear();
               newGraphObjList.addAll(callsplitobj.mergeObject(oriGraphObjList));               
               pnl.graphObjectList.clear();
               pnl.graphObjectList.addAll(oriGraphObjList);
           }
        });
        jbtnStop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            }
        });
        
    }
}
