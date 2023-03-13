package parser;
/**
 * For display selected rows from main table for view details the generated the pdf. 
 * @author guang yang (sunny)
 *
 */

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;
import java.util.*;

import guikit.gui.JTextFieldRegularPopupMenu;
import guikit.util.IdentityKey;
import guikit.widget.GUIWidgetAdaptor;
import guikit.widget.MessageBox;
import guikit.widget.XYLayout;

public class InvoiceDlg extends JDialog implements ActionListener
{
	private static final long serialVersionUID = GUIWidgetAdaptor.serialVersionUID;
	
	int wid, hgt;
	ParserFrame frame;
	//component
	ListPane1 listPane;
	JTextArea detailTxt;
	JTextField nameFd, nbrFd, orderFd;	
	JButton pdfBtn, closeBtn;
	JCheckBox payCheck;
	
	Map<String, List<MapRow>> titleGroup;
	String workingTitle = null;
	int selectIndex = -1;
	
	
	public InvoiceDlg(ParserFrame dlg, String title, String iconPath)
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
		// 400 x 400
		int x=10, y=10;
		int w = 180, h=25;
		JLabel titLb = new JLabel("Title List for Selection", SwingConstants.CENTER);		
		titLb.setBounds(x, y, w, h);
		getContentPane().add(titLb);
		x+=w; w=100;
		pdfBtn = new JButton("Create PDF");
		pdfBtn.addActionListener(this);
		pdfBtn.setBounds(x, y, w, h);
		getContentPane().add(pdfBtn);
		pdfBtn.setEnabled(false);
		x+=w;
		closeBtn = new JButton("Close");
		closeBtn.addActionListener(this);
		closeBtn.setBounds(x, y, w, h);
		getContentPane().add(closeBtn);
		
		y+=h; h=200; x=10; w=380;			
		listPane = new ListPane1(this, data_v);		
		JScrollPane js = listPane.createScrollPane();
		js.setBounds(x, y, w, h);
		getContentPane().add(js);							
		x=10; y=235; h = 25; w=70; 
		JLabel flb = new JLabel("File Name:");
		flb.setBounds(x, y, w, h);
		getContentPane().add(flb);
		x+=w; w=200;
		String ikey = IdentityKey.getDateKey("Inv", 1);
		nameFd = new JTextField(ikey+".pdf");
		nameFd.setBounds(x, y, w, h);
		getContentPane().add(nameFd);
		JTextFieldRegularPopupMenu.addTo(nameFd);
		x=10; y+=h; w=70;
		JLabel inlb = new JLabel("Invoice No:");
		inlb.setBounds(x, y, w, h);
		getContentPane().add(inlb);
		x+=w; w=150;
		nbrFd = new JTextField();
		nbrFd.setBounds(x, y, w, h);
		JTextFieldRegularPopupMenu.addTo(nbrFd);
		getContentPane().add(nbrFd);
		x+=w;
		payCheck = new JCheckBox("Paid", false);
		payCheck.setBounds(x, y, w, h);
		getContentPane().add(payCheck);
		x=10; y+=h; w=100;
		JLabel purLb = new JLabel("Purchase Order:");
		purLb.setBounds(x, y, w, h);
		getContentPane().add(purLb);
		x+=w; w=200;
		orderFd = new JTextField();
		orderFd.setBounds(x, y, w, h);
		getContentPane().add(orderFd);
		
		x=10; y+=h; h=80; w=380;
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
				String tit = "Warning to Produce Invoice";	
				MessageBox box = new MessageBox("Please add the pdf file name for processing", MessageBox.WARNING, 300, 200);
				box.showMsg(tit);
				return;
			}
			
			String invNbr = nbrFd.getText();
			if(invNbr==null || invNbr.trim().length()<2)
			{
				String tit = "Warning to Produce Invoice";	
				MessageBox box = new MessageBox("Please add the invoice number for processing", MessageBox.ERROR, 300, 200);
				box.showMsg(tit);
				return;
				
			}
			
		    String file_path = frame.pdfMaker.checkFile(file_name);
		    if(file_path == null)
		    {
		    	String tit = "Warning to Produce Invoice";	
				MessageBox box = new MessageBox("The file exists already, please change another name", MessageBox.WARNING, 300, 200);
				box.showMsg(tit);
				return;
		    }	
					   
		    List<MapRow> lstx1 = titleGroup.get(workingTitle);
			if(lstx1 == null || lstx1.size()<1) return;
		    
			MediaData md = createMediaData(lstx1);
			
			if(!md.checkEmpty())
			{
				frame.pdfMaker.makeInvoice(md, file_path);
				MessageBox box1 = new MessageBox("Successful create PDF in: "+file_path, MessageBox.SUCCESS, 300, 200);
				box1.showMsg("Success Create Invoice PDF file");
			}else {
				MessageBox box2 = new MessageBox("Something wrong to create PDF: "+file_path+". Please contact Admin", MessageBox.ERROR, 300, 200);
				box2.showMsg("Fail to create invoice pdf file");
			}
			//listPane.updateUI();			
		}catch(Exception ex) { ex.printStackTrace(); }
	}
	
	MediaData createMediaData(List<MapRow> list) {		
		MediaData md = new MediaData();
		md.extractData(list);
		md.setInvoiceNo(nbrFd.getText());
		md.setPurchaseOrderNo(orderFd.getText());
		if(payCheck.isSelected())
			md.hireFee +="  -*(paid with thanks)";
		return md;
	}
}
