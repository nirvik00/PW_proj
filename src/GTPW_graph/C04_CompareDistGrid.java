package gtpw_graph;

import java.util.Comparator;

public class C04_CompareDistGrid implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) {
        double d0=Double.parseDouble(o1.split(",")[2]);
        double d1=Double.parseDouble(o2.split(",")[2]);
        if(d0<d1){
            return -1;
        }else if(d0>d1){            
            return 1;
        }else{
            return 0;
        }
    }
}
