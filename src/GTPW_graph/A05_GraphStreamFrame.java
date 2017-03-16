package gtpw_graph;

import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.ProxyPipe;
import org.graphstream.stream.thread.ThreadProxyPipe;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerListener;
import org.graphstream.ui.view.ViewerPipe;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePosition;

public class A05_GraphStreamFrame extends JFrame{
    JFrame frm;
    
    ArrayList<String>dataList;
    ArrayList<String>objDataList;
    ArrayList<Node>nodeList;
    
    Random rnd;
    @SuppressWarnings("LeakingThisInConstructor")
    A05_GraphStreamFrame(ArrayList<String> dataList_){        
        dataList=new ArrayList<String>();
        dataList.addAll(dataList_);        
        objDataList=new ArrayList<String>();
        objDataList.clear();
        nodeList=new ArrayList<Node>();
        
        rnd=new Random();
        frm=new JFrame("GTPW_Layout_Algo_Final");
        frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frm.setSize(500,500);
        frm.setVisible(true);
        frm.setLocation(0,500);
        
        String css="node {text-size:10px; fill-color:rgba(200,0,0,150); size-mode:dyn-size;}";
        Graph graph=new SingleGraph("nirvik saha");
        graph.addAttribute("ui.stylesheet",css);
        graph.setAutoCreate(true);
        
        for(int i=1; i<dataList.size(); i++){
            double id=Double.parseDouble(dataList.get(i).split(";")[0]);
            String prog_name=dataList.get(i).split(";")[1];
            double qnty=Double.parseDouble(dataList.get(i).split(";")[3]);
            double ar_each=Double.parseDouble(dataList.get(i).split(";")[4]);
            double dim_each=Math.sqrt(ar_each);
            String adj_to=dataList.get(i).split(";")[8];
            //System.out.println(id+","+prog_name+","+qnty+","+ar_each+","+adj_to);
            objDataList.add(id+";"+prog_name+";"+qnty+";"+ar_each+";"+adj_to);
            Node node=graph.addNode(prog_name);
            nodeList.add(node);            
            node.addAttribute("ui.label", node.getId());
            node.addAttribute("ui.size", dim_each);
        }
        int  k=0;
        for(int i=0; i<nodeList.size(); i++){   
            Node nodeA=nodeList.get(i);
            String idA=nodeA.getId();
            String s=dataList.get(k+1);
            String[] adjToStrArr=s.split(";")[8].split(",");
            for(int j=0; j<adjToStrArr.length; j++){
                Node nodeB=nodeList.get(Integer.parseInt(adjToStrArr[j]));
                String idB=nodeB.getId();
                try{
                    graph.addEdge("edge"+rnd.nextInt(10000),idA,idB);
                }catch(Exception e){
                    //do nothing
                }
            }
          k++;
        }
        Viewer viewer=graph.display();
        ViewPanel view=viewer.addDefaultView(false);
        ViewerPipe pipe=viewer.newViewerPipe();
        pipe.addAttributeSink(graph);
        try{
            Thread.sleep(100);
            pipe.pump();
            for(int i=0; i<nodeList.size(); i++){
                String nodeId=nodeList.get(i).getId();
                //System.out.println(nodeId);
                double[] pos=Toolkit.nodePosition(graph,nodeId);
                for(int j=0; j<pos.length; j++){
                    //System.out.println(i+"> "+pos[j]);
                }
                //System.out.println("---------------------");
            }
        }catch(Exception e){
           // System.out.println("error on print coordinates");
        }
        frm.add(view);
    }
}
