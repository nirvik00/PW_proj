package gtpw_graph;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class A01_GraphAlgoPanel extends JPanel{
    A10_DrawFrame frm;
    A08_ForceFrame treefrm;
    
    JLabel jLblPath, jLblNAME, jlblMaxDi, jlblMinDi;
    JTextField jtfPath, jtfMaxDi, jtfMinDi;
    JButton jbtnRead, jbtnDraw;
    JButton jbtnGraph;
    JLabel jLbl;
    ArrayList<String> dataList;
    A01_GraphAlgoPanel(){
        dataList=new ArrayList<String>();
        
        jLblPath=new JLabel("PATH of EXCEL FILE : ");
        jLblPath.setBounds(30,10,350,40);        
        
        jtfPath=new JTextField("c:/users/nirvik saha/desktop/programDocSample.xlsx");
        jtfPath.setBounds(30,70,350,40);        
        
        jbtnRead=new JButton("Read Excel File");
        jbtnRead.setBounds(30,135,350,40);
        
        jbtnGraph=new JButton("Graph from Data");
        jbtnGraph.setBounds(30,290,350,40);
        
        //jLblNAME=new JLabel("Georgia Institute of Technology & Perkins+Will");
        //jLblNAME.setBounds(30,375,350,40);        
        
        jlblMaxDi=new JLabel("Maximum Distance");
        jlblMaxDi.setBounds(30,230,120,40);
        
        jtfMaxDi=new JTextField("300");
        jtfMaxDi.setBounds(150,230,35,40);
        
        jlblMinDi=new JLabel("Minimum Distance");
        jlblMinDi.setBounds(220,230,120,40);
        
        jtfMinDi=new JTextField("50");
        jtfMinDi.setBounds(340,230,30,40);

        jbtnDraw=new JButton("DRAW");
        jbtnDraw.setBounds(30,375,350,40);
        
        setLayout(null);
        add(jLblPath);
        add(jtfPath);
        add(jbtnRead);
        add(jbtnGraph);
        //add(jLblNAME);
        add(jlblMaxDi);
        add(jlblMinDi);
        add(jtfMaxDi);
        add(jtfMinDi);
        add(jbtnDraw);
                
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
                    A05_GraphStreamFrame graphFrm=new A05_GraphStreamFrame(dataList);
                    A02_GraphDisplayFrame graphfrm=new A02_GraphDisplayFrame(dataList);
                    treefrm=new A08_ForceFrame(dataList);
                    
                }
            }
        });
        jbtnDraw.addActionListener(new ActionListener(){
           public void actionPerformed(ActionEvent e){            
               frm=new A10_DrawFrame(treefrm.pnl.graphObjectList);
           }
        });
    }
}
