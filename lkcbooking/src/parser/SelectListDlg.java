package parser;
/**
 * A dialgo for selection of group to generate contract in PDF. 
 * A requirement, file name need to be import by user
 * @author guangyang
 */
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Dialog for select item from list and show detals, then select for generated contract form in pdf format
 * @author guangyang
 */
import javax.swing.*;
import java.util.*;

import guikit.gui.JTextFieldRegularPopupMenu;
import guikit.util.IdentityKey;
import guikit.widget.GUIWidgetAdaptor;
import guikit.widget.MessageBox;
import guikit.widget.XYLayout;

public class SelectListDlg extends JDialog implements ActionListener
{
	private static final long serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	
	int wid, hgt;
	ParserFrame frame;
	//component
	ListPane listPane;
	JTextArea detailTxt;
	JTextField nameFd, nbrFd;
	JButton pdfBtn, closeBtn;
	JCheckBox videoCheck, audeoCheck, tableCheck;
	
	Map<String, List<MapRow>> titleGroup;
	String workingTitle = null;
	int selectIndex = -1;
	
	
	public SelectListDlg(ParserFrame dlg, String title, String iconPath)
	{
		super(dlg);
		frame = dlg;
		setModal(true);
		setTitle(title);
		if(iconPath!=null )
			setIconImage(Toolkit.getDefaultToolkit().getImage(iconPath));
		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent we)
			{  
				dispose(); 
				setVisible(false);
			}
				 
			public void windowDeiconified(WindowEvent e)
			{
				Dimension dim = getSize();
				Double w = dim.getWidth();
				Double h = dim.getHeight();
				setSize(w.intValue()+1, h.intValue()+1);
				setSize(w.intValue(), h.intValue());
			}
		});
		getContentPane().setLayout(new XYLayout());	
		
	}
	
	public void setFavourBounds(int width, int height)
	{
		wid = width; hgt = height;
		Dimension dim = Toolkit.getDefaultToolkit( ).getScreenSize();
		float fx = (float)dim.getWidth( )/2;
		float fy = (float)dim.getHeight( )/2;
		int x = Math.round(fx - (float)(width/2));
		int y = Math.round(fy - (float)(height/2))-15;
		setBounds(x, y, width, height);
	}
	
	public void displayDlg() {
		this.setVisible(true);
	}
	
	void closeDlg() {
		this.dispose();
		this.setVisible(false);
	}
	
	//-- implement panel
	void setDataPane(Map<String, List<MapRow>> group)
	{
		titleGroup = group;
		Vector<String> data_v = new Vector<String>(group.keySet());
		// 560 x 400
		int x=10, y=10;
		int w = 360, h=25;
		JLabel titLb = new JLabel("Title List for Selection", SwingConstants.CENTER);		
		titLb.setBounds(x, y, w, h);
		getContentPane().add(titLb);
		y+=h; h=200;			
		listPane = new ListPane(this, data_v);
		
		JScrollPane js = listPane.createScrollPane();
		js.setBounds(x, y, w, h);
		getContentPane().add(js);
		
		x=370; y=50; w=160; h=20;
		JLabel reqLb = new JLabel("Requirements", SwingConstants.CENTER);
		reqLb.setBounds(x, y, w, h);
		getContentPane().add(reqLb);
		y+=h;
		videoCheck = new JCheckBox("Video Project/Screen", false);
		videoCheck.setBounds(x, y, w, h);
		getContentPane().add(videoCheck);
		y+=h;
		audeoCheck = new JCheckBox("Public Address System", false);
		audeoCheck.setBounds(x, y, w, h);
		getContentPane().add(audeoCheck);
		y+=h;
		tableCheck = new JCheckBox("Tables and Chairs", false);
		tableCheck.setBounds(x, y, w, h);
		getContentPane().add(tableCheck);
		
		x=400; w=100; h=25; y=205; 	
		pdfBtn = new JButton("Create PDF");
		pdfBtn.addActionListener(this);
		pdfBtn.setBounds(x, y, w, h);
		getContentPane().add(pdfBtn);
		pdfBtn.setEnabled(false);
		y+=h+5;
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		closeBtn.setBounds(x, y, w, h);
		getContentPane().add(closeBtn);
				
		x=10; y=235; h = 25; w=80; 
		JLabel flb = new JLabel("File Name:");
		flb.setBounds(x, y, w, h);
		getContentPane().add(flb);
		x=x+w; w=280;
		String ikey = IdentityKey.getDateKey("con", 1);
		nameFd = new JTextField(ikey+".pdf");
		nameFd.setBounds(x, y, w, h);
		getContentPane().add(nameFd);
		JTextFieldRegularPopupMenu.addTo(nameFd);
		x=10; w=80; y+=h;
		JLabel inlb = new JLabel("Invoice No:");
		inlb.setBounds(x, y, w, h);
		getContentPane().add(inlb);
		x+=w; w=150;
		nbrFd = new JTextField();
		nbrFd.setBounds(x, y, w, h);
		JTextFieldRegularPopupMenu.addTo(nbrFd);
		getContentPane().add(nbrFd);
		
		x=10; y+=h; h=80; w=560;
		detailTxt = new JTextArea();
		detailTxt.setLineWrap(true);
		JScrollPane jsp = new JScrollPane(detailTxt);
		jsp.setBounds(x, y, w, h);
		getContentPane().add(jsp);
												
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		if(cmd.equals("Close")) closeDlg();				
		else if(cmd.equals("Create PDF")) processPdf();
				
	}
	
	void processPdf() {
		if(workingTitle == null) return;
		try 
		{
			String file_name = nameFd.getText();
			if(file_name==null || file_name.trim().length()<2)
			{
				String tit = "Warning to Produce Contract";	
				MessageBox box = new MessageBox("Please add the pdf file name for processing", MessageBox.WARNING, 300, 200);
				box.showMsg(tit);
				return;
			}
			
		    String file_path = frame.pdfMaker.checkFile(file_name);
		    if(file_path == null)
		    {
		    	String tit = "Warning to Produce Contract";	
				MessageBox box = new MessageBox("The file exists already, please change another name", MessageBox.ERROR, 300, 200);
				box.showMsg(tit);
				return;
		    }	
					   
		    List<MapRow> lstx1 = titleGroup.get(workingTitle);
			if(lstx1 == null || lstx1.size()<1) return;
		    
			MediaData md = createMediaData(lstx1);
			
			if(!md.checkEmpty())
			{
				frame.pdfMaker.makeContract(md, file_path);
				MessageBox box1 = new MessageBox("Successful create PDF: "+file_path, MessageBox.SUCCESS, 300, 200);
				box1.showMsg("Success Create Contract PDF file");
			}else {
				MessageBox box2 = new MessageBox("Something wrong to create PDF: "+file_path+". Please contact Admin", MessageBox.ERROR, 300, 200);
				box2.showMsg("Fail to create contract pdf file");
			}
			//listPane.updateUI();			
		}catch(Exception ex) { ex.printStackTrace(); }
	}
	
	MediaData createMediaData(List<MapRow> list) {		
		MediaData md = new MediaData();
		md.extractData(list);
		if (videoCheck.isSelected())
			md.addRequire("V. P.");
		if(audeoCheck.isSelected())
			md.addRequire("P. A. ,");		
		if(tableCheck.isSelected())
			md.addRequire("Tables/Chairs");				
		return md;
	}
}
