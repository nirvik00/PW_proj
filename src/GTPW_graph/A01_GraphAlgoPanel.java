package gtpw_graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class A01_GraphAlgoPanel extends JPanel {//implements ActionListener{
    A10_DrawFrame drawfrm;
    A08_ForceFrame treefrm;
    U02_RayEvalGrid rayEvalGrid;
    U03_CurveSkeletonAlgo crvSkeleton;
    A05_GraphStreamFrame graphFrm1;
    A02_GraphDisplayFrame graphFrm2;
    U05_SubDivideSegments dev;
                    
    JLabel jLblPath, jLblNAME, jlblGridDim, jlblMinDi;
    JTextField jtfPath, jtfGridDimension, jtfMinDi, jtfCurveSkeletonDim, jtfCurveSkeletonNodeNum, jtfCurveSkeletonNodeDim, jtfSegDim;
    JButton jbtnRead, jbtnDraw, jbtnMakeParallel;
    JButton jbtnGraph, jbtnAccept, jbtnFindGrid, jbtnReset, jbtnCompute, jbtnCrvSkeleton;
    JLabel jLbl, jLblCurveSkeletonDimension, jLblCurveSkeletonNodeDimension, jLblSegDim;
    JSlider jsliderGridDimension, jsliderNodeDim, jsliderSegDim;
    ArrayList<String> dataList;
    ArrayList<String> tempList;
    //Timer timer;
    double gridspacing=50.0;
    
    A01_GraphAlgoPanel(){
        tempList=new ArrayList<String>();
        
        //timer=new Timer(150,this);
        //timer.start();
        
        rayEvalGrid=new U02_RayEvalGrid(gridspacing);
        
        dataList=new ArrayList<String>();
        
        jLblPath=new JLabel("PATH of EXCEL FILE : ");
        jLblPath.setBounds(30,10,350,50);        
        
        jtfPath=new JTextField("c:/users/nirvik saha/desktop/programDocSample.xlsx");
        jtfPath.setBounds(30,70,350,40);        
        
        jbtnRead=new JButton("Read Excel File");
        jbtnRead.setBounds(30,135,150,50);
        
        jbtnGraph=new JButton("Graph from Data");
        jbtnGraph.setBounds(230,135,150,50);
        
        jlblGridDim=new JLabel("GRID DIMENSION");
        jlblGridDim.setBounds(30,260,120,40);
        
        jtfGridDimension=new JTextField("25");
        jtfGridDimension.setBounds(150,265,35,40);

        jbtnDraw=new JButton("DRAW");
        jbtnDraw.setBounds(230,260,150,50);
        
        jLblCurveSkeletonDimension=new JLabel("Slider : Node Dimension w.r.t. curve skeleton");
        jLblCurveSkeletonDimension.setBounds(30,370,300,100);
                
        jtfCurveSkeletonDim=new JTextField("5");
        jtfCurveSkeletonDim.setBounds(330,400,30,30);
        
        jLblSegDim=new JLabel("Slider : Segment Dimension w.r.t. curve skeleton");
        jLblSegDim.setBounds(30,470,300,100);
        
        jtfSegDim=new JTextField("5");
        jtfSegDim.setBounds(330,505,30,30);
        
        jbtnCrvSkeleton=new JButton("CURVE SKELETON");
        jbtnCrvSkeleton.setBounds(30,570,150,50);  
        
        jbtnMakeParallel=new JButton("MAKE PARALLEL");
        jbtnMakeParallel.setBounds(230,570,150,50);        
        
        jbtnReset=new JButton("RESET EVERYTHING");
        jbtnReset.setBounds(30,700,350,50);        
        
        jsliderNodeDim=new JSlider(1,2000,300);
        jsliderNodeDim.setBounds(30,350,350,50);
        
        jsliderSegDim=new JSlider(1,150,70);
        jsliderSegDim.setBounds(30,450,350,50);
        

        
        setLayout(null);
        add(jLblPath);
        add(jtfPath);
        add(jbtnRead);
        add(jbtnGraph);
        add(jlblGridDim);
        add(jtfGridDimension);
        add(jbtnDraw);
        add(jLblCurveSkeletonDimension);
        add(jtfCurveSkeletonDim);
        add(jbtnCrvSkeleton);
        add(jbtnMakeParallel);
        add(jbtnReset);
        add(jsliderNodeDim);
        add(jtfSegDim);
        add(jLblSegDim);
        add(jsliderSegDim);
        
        int x_nodeDim=jsliderNodeDim.getValue();
        double y_nodeDim=(x_nodeDim/100);
        jtfCurveSkeletonDim.setText(String.valueOf(y_nodeDim));
        
        int segDim=jsliderSegDim.getValue();
        double segDim_double=(segDim*10/200);
        jtfSegDim.setText(String.valueOf(segDim_double));
        
        jbtnRead.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                try {
                    dataList.clear();
                    String filePath=jtfPath.getText();
                    FileInputStream fis=new FileInputStream(new File(filePath));
                    XSSFWorkbook workbook=new XSSFWorkbook(fis);
                    XSSFSheet spreadsheet=workbook.getSheetAt(0);
                    Iterator<Row>rowIterator=spreadsheet.iterator();
                    while(rowIterator.hasNext()){
                        XSSFRow row=(XSSFRow) rowIterator.next();
                        Iterator<Cell> cellIterator=row.cellIterator();
                        String s="";
                        while(cellIterator.hasNext()){
                            Cell cell=cellIterator.next();
                            s+=String.valueOf(cell)+";";
                        }
                        dataList.add(s);
                        //System.out.println(s);
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("file not found");
                } catch (IOException ex) {
                    System.out.println("problem with \"class not found \" ...");
                }
                
            }
        });
        jbtnGraph.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(dataList.size()<1){
                    A06_ErrorFrame errFrm=new A06_ErrorFrame();
                    errFrm.setVisible(true);
                }else{  
                    //DefGraphFrame defGraphFrm=new DefGraphFrame(dataList);
                    //defGraphFrm.display();
                    //A05_GraphStreamFrame 
                    graphFrm1=new A05_GraphStreamFrame(dataList);
                    //A02_GraphDisplayFrame 
                    graphFrm2=new A02_GraphDisplayFrame(dataList);
                    treefrm=new A08_ForceFrame(dataList);
                }
            }
        });
        jsliderNodeDim.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                
                int x=jsliderNodeDim.getValue();
                double y=(x/100);
                jtfCurveSkeletonDim.setText(String.valueOf(y));
                clearForCurveSkeleton();
                rayEvalGrid.gridPtList.addAll(drawfrm.pnl.gridPtList);
                rayEvalGrid.finalFigPtList.addAll(drawfrm.pnl.finalFigPtList);
                tempList.clear();
                LinkedHashSet<String>lhs=new LinkedHashSet<String>();
                lhs.clear();
                lhs.addAll(rayEvalGrid.evalPtList());
                tempList.addAll(lhs);
                lhs.clear();
               
                drawfrm.pnl.internalGridPtList.addAll(tempList);
                tempList.clear();
                double dim=Double.parseDouble(jtfCurveSkeletonDim.getText());
                crvSkeleton=new U03_CurveSkeletonAlgo(gridspacing, dim);
                LinkedHashSet<String>lhs1=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs2=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs3=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs4=new LinkedHashSet<String>();
                lhs1.addAll(drawfrm.pnl.figSegmentList);
                lhs2.addAll(drawfrm.pnl.finalFigPtList);
                lhs3.addAll(drawfrm.pnl.internalGridPtList);
                crvSkeleton.figSegmentList.addAll(lhs1);
                crvSkeleton.finalFigPtList.addAll(lhs2);
                crvSkeleton.internalGridPtList.addAll(lhs3);
                lhs1.clear();
                lhs2.clear();
                lhs3.clear();
                ArrayList<String>bbcp=new ArrayList<String>();
                bbcp.addAll(drawfrm.pnl.bound());
                drawfrm.pnl.seqSegList.addAll(crvSkeleton.combineSegments());
                drawfrm.pnl.angBisectorList.addAll(crvSkeleton.newfindAngleBisector(bbcp));
                crvSkeleton.organizeAngBisLocal(crvSkeleton.organizePtList);
                drawfrm.pnl.angBisectorSegList.addAll(crvSkeleton.newAngBisectorSegList);
                drawfrm.pnl.reqPtList.addAll(crvSkeleton.reqPtList);
                
                /*
                ArrayList<String>tempReqPtList=new ArrayList<String>();
                tempReqPtList.clear();
                tempReqPtList.addAll(crvSkeleton.reqPtList);
                drawfrm.pnl.reqPtList.addAll(crvSkeleton.makeParallel(tempReqPtList));
                tempReqPtList.clear();
                */
                
                drawfrm.pnl.divPtList.addAll(organizeSpace(drawfrm.pnl.reqPtList,  crvSkeleton.finalFigPtList));                                
                
                ArrayList<String>divPtList=new ArrayList<String>();
                ArrayList<String>div2PtList=new ArrayList<String>();
                ArrayList<String>perSegList=new ArrayList<String>();
                divPtList.clear();
                div2PtList.clear();
                perSegList.clear();
                int segDim=jsliderSegDim.getValue();
                double segDim_double=(segDim*10/100);
                dev=new U05_SubDivideSegments(drawfrm.pnl.reqPtList, crvSkeleton.finalFigPtList, segDim);
                divPtList.addAll(dev.genPrimarySegments());
                div2PtList.addAll(dev.genSecondarySegments());
                perSegList.addAll(dev.perSegList);

                drawfrm.pnl.divPtList.addAll(divPtList);    
                drawfrm.pnl.div2PtList.addAll(div2PtList); 
                drawfrm.pnl.perSegList.addAll(perSegList);
            
            }
        });
        jsliderSegDim.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e){
                
                clearForCurveSkeleton();
                rayEvalGrid.gridPtList.addAll(drawfrm.pnl.gridPtList);
                rayEvalGrid.finalFigPtList.addAll(drawfrm.pnl.finalFigPtList);
                tempList.clear();
                LinkedHashSet<String>lhs=new LinkedHashSet<String>();
                lhs.clear();
                lhs.addAll(rayEvalGrid.evalPtList());
                tempList.addAll(lhs);
                lhs.clear();
               
                drawfrm.pnl.internalGridPtList.addAll(tempList);
                tempList.clear();
                double dim=Double.parseDouble(jtfCurveSkeletonDim.getText());
                crvSkeleton=new U03_CurveSkeletonAlgo(gridspacing, dim);
                LinkedHashSet<String>lhs1=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs2=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs3=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs4=new LinkedHashSet<String>();
                lhs1.addAll(drawfrm.pnl.figSegmentList);
                lhs2.addAll(drawfrm.pnl.finalFigPtList);
                lhs3.addAll(drawfrm.pnl.internalGridPtList);
                crvSkeleton.figSegmentList.addAll(lhs1);
                crvSkeleton.finalFigPtList.addAll(lhs2);
                crvSkeleton.internalGridPtList.addAll(lhs3);
                lhs1.clear();
                lhs2.clear();
                lhs3.clear();
                ArrayList<String>bbcp=new ArrayList<String>();
                bbcp.addAll(drawfrm.pnl.bound());
                drawfrm.pnl.seqSegList.addAll(crvSkeleton.combineSegments());
                drawfrm.pnl.angBisectorList.addAll(crvSkeleton.newfindAngleBisector(bbcp));
                crvSkeleton.organizeAngBisLocal(crvSkeleton.organizePtList);
                drawfrm.pnl.angBisectorSegList.addAll(crvSkeleton.newAngBisectorSegList);
                drawfrm.pnl.reqPtList.addAll(crvSkeleton.reqPtList);
                
                ArrayList<String>divPtList=new ArrayList<String>();
                ArrayList<String>div2PtList=new ArrayList<String>();
                ArrayList<String>perSegList=new ArrayList<String>();
                divPtList.clear();
                div2PtList.clear();
                perSegList.clear();
                int segDim=jsliderSegDim.getValue();
                double segDim_double=(segDim*10/100);
                
                dev=new U05_SubDivideSegments(drawfrm.pnl.reqPtList, crvSkeleton.finalFigPtList, segDim);
                divPtList.addAll(dev.genPrimarySegments());
                div2PtList.addAll(dev.genSecondarySegments());
                perSegList.addAll(dev.perSegList);
                drawfrm.pnl.divPtList.addAll(divPtList);  
                drawfrm.pnl.div2PtList.addAll(div2PtList); 
                drawfrm.pnl.perSegList.addAll(perSegList);
                
            }
        });
        jbtnDraw.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){  
               gridspacing=Double.parseDouble(jtfGridDimension.getText());
               graphFrm2.setVisible(false);
               graphFrm1.setVisible(false);
               graphFrm1.dispose();
               treefrm.pnl.updateFigures=true;
               treefrm.setVisible(false);
               drawfrm=new A10_DrawFrame(treefrm.pnl.mstObjectList,gridspacing);
           }
        });
        jbtnCrvSkeleton.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
                //treefrm.setVisible(false);
                clearForCurveSkeleton();
                rayEvalGrid.gridPtList.addAll(drawfrm.pnl.gridPtList);
                rayEvalGrid.finalFigPtList.addAll(drawfrm.pnl.finalFigPtList);
                tempList.clear();
                LinkedHashSet<String>lhs=new LinkedHashSet<String>();
                lhs.clear();
                lhs.addAll(rayEvalGrid.evalPtList());
                tempList.addAll(lhs);
                lhs.clear();
               
                drawfrm.pnl.internalGridPtList.addAll(tempList);
                tempList.clear();
                double dim=Double.parseDouble(jtfCurveSkeletonDim.getText());
                crvSkeleton=new U03_CurveSkeletonAlgo(gridspacing, dim);
                LinkedHashSet<String>lhs1=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs2=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs3=new LinkedHashSet<String>();
                LinkedHashSet<String>lhs4=new LinkedHashSet<String>();
                lhs1.addAll(drawfrm.pnl.figSegmentList);
                lhs2.addAll(drawfrm.pnl.finalFigPtList);
                lhs3.addAll(drawfrm.pnl.internalGridPtList);
                crvSkeleton.figSegmentList.addAll(lhs1);
                crvSkeleton.finalFigPtList.addAll(lhs2);
                crvSkeleton.internalGridPtList.addAll(lhs3);
                lhs1.clear();
                lhs2.clear();
                lhs3.clear();
                ArrayList<String>bbcp=new ArrayList<String>();
                
                bbcp.addAll(drawfrm.pnl.bound());
                drawfrm.pnl.seqSegList.addAll(crvSkeleton.combineSegments());
                drawfrm.pnl.angBisectorList.addAll(crvSkeleton.newfindAngleBisector(bbcp));
                crvSkeleton.organizeAngBisLocal(crvSkeleton.organizePtList);
                drawfrm.pnl.angBisectorSegList.addAll(crvSkeleton.newAngBisectorSegList);
                drawfrm.pnl.reqPtList.addAll(crvSkeleton.reqPtList);
                
                ArrayList<String>divPtList=new ArrayList<String>();
                ArrayList<String>div2PtList=new ArrayList<String>();
                ArrayList<String>perSegList=new ArrayList<String>();
                divPtList.clear();
                div2PtList.clear();
                perSegList.clear();
                int segDim=jsliderSegDim.getValue();
                double segDim_double=(segDim*10/100);
                
                dev=new U05_SubDivideSegments(drawfrm.pnl.reqPtList, crvSkeleton.finalFigPtList, segDim);
                divPtList.addAll(dev.genPrimarySegments());
                div2PtList.addAll(dev.genSecondarySegments());
                perSegList.addAll(dev.perSegList);
                drawfrm.pnl.divPtList.addAll(divPtList);         
                drawfrm.pnl.div2PtList.addAll(div2PtList);         
                drawfrm.pnl.perSegList.addAll(perSegList);
                
           }
        });
         jbtnMakeParallel.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
               try{
                drawfrm.pnl.reqPtList.clear();
                drawfrm.pnl.divPtList.clear();
                drawfrm.pnl.div2PtList.clear();
                drawfrm.pnl.perSegList.clear();
               }catch(Exception exc){
                   
               }
               
                ArrayList<String>tempReqPtList=new ArrayList<String>();
                tempReqPtList.clear();
                tempReqPtList.addAll(crvSkeleton.reqPtList);
                drawfrm.pnl.reqPtList.addAll(crvSkeleton.makeParallel(tempReqPtList));
                tempReqPtList.clear();
                
                ArrayList<String>divPtList=new ArrayList<String>();
                ArrayList<String>div2PtList=new ArrayList<String>();
                ArrayList<String>perSegList=new ArrayList<String>();
                divPtList.clear();
                div2PtList.clear();
                perSegList.clear();
                int segDim=jsliderSegDim.getValue();
                double segDim_double=(segDim*10/100);
                
                dev=new U05_SubDivideSegments(drawfrm.pnl.reqPtList, crvSkeleton.finalFigPtList, segDim);
                divPtList.addAll(dev.genPrimarySegments());
                div2PtList.addAll(dev.genSecondarySegments());
                perSegList.addAll(dev.perSegList);
                drawfrm.pnl.divPtList.addAll(divPtList);   
                drawfrm.pnl.div2PtList.addAll(div2PtList);        
                drawfrm.pnl.perSegList.addAll(perSegList);
           }
         });
        jbtnReset.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){
            try{
                drawfrm.pnl.finalFigPtList.clear();
                drawfrm.pnl.figSegmentList.clear();
                drawfrm.pnl.internalGridPtList.clear();
                drawfrm.pnl.seqSegList.clear();
                drawfrm.pnl.angList.clear();
                drawfrm.pnl.boundingbox.clear();
                drawfrm.pnl.angBisectorList.clear();
                drawfrm.pnl.angBisectorSegList.clear();
                drawfrm.pnl.intersectionList.clear();
                drawfrm.pnl.minsortAreaABList.clear();
                drawfrm.pnl.reqList.clear();
                drawfrm.pnl.reqPtList.clear();
                drawfrm.pnl.divPtList.clear();    
                drawfrm.pnl.div2PtList.clear();
                drawfrm.pnl.perSegList.clear();
                drawfrm.pnl.per2SegList.clear();
            }catch(Exception exc){
            }
            try{
                rayEvalGrid.PTList.clear();
                rayEvalGrid.finalFigPtList.clear();
                rayEvalGrid.gridPtList.clear();
            }catch(Exception exc){
            }
            try{
                crvSkeleton.figSegmentList.clear();
                crvSkeleton.finalFigPtList.clear();
                crvSkeleton.internalGridPtList.clear();
                crvSkeleton.seqSegList.clear();
                crvSkeleton.angBisectorList.clear();
                crvSkeleton.angBisectorSegList.clear();
                crvSkeleton.intersectionList.clear();
                crvSkeleton.minsortAreaABList.clear();
                crvSkeleton.reqList.clear();
                crvSkeleton.reqPtList.clear();                    
            }catch(Exception exc){
            }
                drawfrm.pnl.drawSite=true;
            }
        }); 
    }
    public void clearForCurveSkeleton(){
        try{
            drawfrm.pnl.internalGridPtList.clear();
            drawfrm.pnl.seqSegList.clear();
            drawfrm.pnl.angList.clear();
            drawfrm.pnl.boundingbox.clear();
            drawfrm.pnl.angBisectorList.clear();
            drawfrm.pnl.angBisectorSegList.clear();
            drawfrm.pnl.intersectionList.clear();
            drawfrm.pnl.minsortAreaABList.clear();
            drawfrm.pnl.reqList.clear();
            drawfrm.pnl.reqPtList.clear();
            drawfrm.pnl.divPtList.clear();
            drawfrm.pnl.div2PtList.clear();
            drawfrm.pnl.perSegList.clear();
            drawfrm.pnl.per2SegList.clear();
        }catch(Exception exc){
        }
        try{
            rayEvalGrid.PTList.clear();
            rayEvalGrid.finalFigPtList.clear();
            rayEvalGrid.gridPtList.clear();
            }catch(Exception exc){
            }
        try{
             crvSkeleton.figSegmentList.clear();
             crvSkeleton.finalFigPtList.clear();
             crvSkeleton.internalGridPtList.clear();
             crvSkeleton.seqSegList.clear();
             crvSkeleton.angBisectorList.clear();
             crvSkeleton.angBisectorSegList.clear();
             crvSkeleton.intersectionList.clear();
             crvSkeleton.minsortAreaABList.clear();
             crvSkeleton.reqList.clear();
             crvSkeleton.reqPtList.clear();
        }catch(Exception exc){
        }
    }
    public ArrayList<String> organizeSpace(ArrayList<String> tempReqPtList, ArrayList<String> tempFigPtList){
        ArrayList<String>divPtList=new ArrayList<String>();
        ArrayList<String>perSegList=new ArrayList<String>();
        divPtList.clear();
        perSegList.clear();
        //dev=new U05_SubDivideSegments(tempReqPtList,tempFigPtList);
        //divPtList.addAll(dev.genPrimarySegments());
        return divPtList;
    }
}