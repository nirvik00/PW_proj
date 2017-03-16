package gtpw_graph;

import java.util.Comparator;

class C02_CompareNodeWeight implements Comparator<A04_GraphObject>{
    public int compare(A04_GraphObject o1, A04_GraphObject o2) {
        double w1=o1.getWeight();
        double w2=o2.getWeight();
        if(w1>w2){
            return 1;
        }else if(w1<w2){
            return -1;
        }else{
            return 0;
        }
    }

}