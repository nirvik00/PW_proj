package gtpw_graph;
public class A04_GraphObject {
    String id;
    String name;
    double qnty;
    double ar_each;
    double dim;
    double xPos;
    double yPos;
    int[] rgb;
    String adj_to;
    boolean selected=false;
    int weight;
    A04_GraphObject(){
    
    }
    A04_GraphObject(double xPos_, double yPos_){
        xPos=xPos_;
        yPos=yPos_;
    }
    A04_GraphObject(String id_, String name_, double qnty_, double ar_each_, double dim_, String adj_to_, int[] rgb_, int wt_){
        id=id_;
        name=name_;
        qnty=qnty_;
        ar_each=ar_each_;
        dim=dim_;
        adj_to=adj_to_;
        rgb=new int[3];
        for(int i=0; i<rgb_.length;i++){
            rgb[i]=rgb_[i];
        }
        weight=wt_;
    }
    public double getXPos(){
        return xPos;
    }
    public double getYPos(){
        return yPos;
    }
    public double setXPos(double xPos_){
        xPos=xPos_;
        return xPos;
    }
    public double setYPos(double yPos_){
        yPos=yPos_;
        return yPos;
    }
    public String getId(){
        return id;
    }
    public String setId(String id_){
        id=id_;
        return id;
    }
    public String getName(){
        return name;
    }
    public String setName(String name_){
        name=name_;
        return name;
    }    
    public double getQnty(){
        return qnty;
    }
    public double setQnty(double qnty_){
        qnty=qnty_;
        return qnty;
    }
    public double getAr_each(){
        return ar_each;
    }
    public double setAr_each(double ar_each_){
        ar_each=ar_each_;
        return ar_each;
    }
    public double getDim(){
        return dim;
    }
    public double setDim(double dim_){
        dim=dim_;
        return dim;
    }
    public int[] getAdj_to(){
        String[] adj_to_str_arr=adj_to.split(",");
        int[] adj_to_int_arr=new int[adj_to_str_arr.length];
        for(int i=0;i<adj_to_str_arr.length;i++){
            adj_to_int_arr[i]=Integer.parseInt(adj_to_str_arr[i]);
        }
        return adj_to_int_arr;
    }
    public String setAdj_to(String adj_to_){
        adj_to=adj_to_;
        return adj_to;
    }
    public int[] getRgb(){
        return rgb;
    }
    public int[] setRgb(int[] rgb_){
        for(int i=0; i<rgb.length; i++){
            rgb[i]=rgb_[i];
        }
        return rgb;
    }
    public boolean getSelected(){
        return selected;
    }
    public boolean setSelected(boolean sel_){
        selected=sel_;
        return selected;
    }
    public int gwtWeight(){
        return weight;
    }
    public int setWeight(int wt_){
        weight=wt_;
        return weight;
    }
}
