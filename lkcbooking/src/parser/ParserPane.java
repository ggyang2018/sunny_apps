package parser;
/**
 * Work with frame to display data
 * @author guangyang
 */

import guikit.gui.MainGuiFrame;
import guikit.widget.GTabbedPane;
import guikit.widget.PanelAdapter;
import guikit.widget.XYLayout;

//import guikit.widget.GStyledTextPane;
import guikit.widget.GTableModel;
import guikit.widget.GTablePane;

import javax.swing.JPanel;

//import java.awt.Color;
import java.util.*;

public class ParserPane extends JPanel implements PanelAdapter
{
	private static final long serialVersionUID = MainGuiFrame.serialVersionUID;
	
	ParserFrame frm;
	GTabbedPane tabPane;
	GTablePane dataPane;
	ImagePanel welcomePane;
	
	int wid,  hgt;
	
	ArrayList<String> tableHeaders;
	List<List<String>> tableData;
	
	public ParserPane(ParserFrame frm, int wid, int hgt)
	{
		this.frm = frm;
		//setBackground(Color.MAGENTA);
		this.wid = wid; this.hgt=hgt;
		init();
	}
	
	
	void init()
	{
		setLayout(new XYLayout());
		tabPane = new GTabbedPane();
		welcomePane = new ImagePanel();
		dataPane = new GTablePane(1);
		int x=5, y=5;
		tabPane.setBounds(x, y, wid-2*x, hgt-2*y);
		dataPane.setBounds(x, y, wid-2*x, hgt-2*y);
		tabPane.add("Welcome", welcomePane);
		tabPane.add("Working", dataPane);		
		add(tabPane);
		
	}
			
	
	public void renderData(List<List<String>> tdata, List<String> theaders)
	{
		    
	    GTableModel tm = new GTableModel(tdata, theaders);
	    List<Integer> csz = new ArrayList<Integer>();
		csz.add(150);//start date
		csz.add(150);//end date
		csz.add(80);//duration
		csz.add(80); //space count 
		csz.add(200); //title
		csz.add(50);//price
		csz.add(80); //payment status
		csz.add(100);//first namme
		csz.add(100);//last name
		csz.add(200);//organisation
		csz.add(150); //telephone
		csz.add(150); //email
		csz.add(150); //space
		csz.add(100);  //category
		csz.add(100); //for row id
		tm.setColumnSizes(csz);
		dataPane.setMainTable(tm, false);
		dataPane.updateUI();
		tabPane.setSelectedIndex(1);				
	}

	@Override
	public void doAction(int componentId, Object row) {
		System.out.println("doction: "+componentId);
		
	}

	@Override
	public void doAction(int commentId, String key, String value) {
		System.out.println("doction: "+commentId+", Key:"+key);
		
	}
	
	void checkRowInfo(int rowIndex) {		
		System.out.println("*****************************");
		System.out.println(tableHeaders);
		System.out.println("-------------------------------------------------");
		List<String> rw = tableData.get(rowIndex);
		System.out.println(rw);
		System.out.println("*****************************");
		System.out.println(tableHeaders.size()+", data size:"+tableData.get(rowIndex).size()+", original size:");				
	}
		

}
