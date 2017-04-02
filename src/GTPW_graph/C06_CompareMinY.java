package gtpw_graph;

import java.util.Comparator;

public class C06_CompareMinY implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        double x0=Double.parseDouble(o1.split(",")[1]);
        double x1=Double.parseDouble(o2.split(",")[1]);
        if(x0<x1){
            return -1;
        }else{
            return 1;
        }
    }
    
    
}
