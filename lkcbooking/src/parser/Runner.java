package parser;
/**
 * Running and testing as application entry point
 * @author guang yang (sunny)
 */
import java.util.Date;

import javax.swing.JFrame;


public class Runner {
	
	static void displayFrame()
	{
		javax.swing.SwingUtilities.invokeLater(new Runnable() 
		{
			public void run() 
			{
				ParserFrame frm = new ParserFrame();
				frm.setVisible(true);
				frm.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
	}
	
		
	static public void main(String args[])
	{
		Date start = new Date();
		System.out.println("----- start: "+start.toString()+"---------");
		try
		{
			displayFrame();
		}catch(Exception ex) { ex.printStackTrace(); }
	}

}

