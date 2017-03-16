package gtpw_graph;

import java.util.Comparator;

public class C03_CompareStringWeight implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        int w1=Integer.parseInt(o1.split(",")[2]);
        int w2=Integer.parseInt(o2.split(",")[2]);
        if(w1>w2){
            return 1;
        }else if(w1<w2){
            return -1;
        }else{
            return 0;
        }
    }
}
