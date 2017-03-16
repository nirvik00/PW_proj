package gtpw_graph;

import java.util.Comparator;

public class C01_CompareEdgeWeight implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        double w1=Double.parseDouble(o1.split(",")[2]);
        double w2=Double.parseDouble(o2.split(",")[2]);
        if(w1>w2){
            return 1;
        }else if(w1<w2){
            return -1;
        }else{
            return 0;
        }
    }
}
