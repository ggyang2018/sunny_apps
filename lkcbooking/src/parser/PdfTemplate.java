package parser;
/**
 * Not in use just reference for development testing. 
 * PDF tempalte as Kirk and as some code reference. 
 * It is independent reference class and would not be packaged
 * @author guangyang
 *
 */
import java.awt.Color;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.pdmodel.common.PDRectangle;


import java.util.*;

public class PdfTemplate {

	PDFont fontPlain = PDType1Font.HELVETICA;
	PDFont fontBold = PDType1Font.HELVETICA_BOLD;
	PDFont fontItalic = PDType1Font.HELVETICA_OBLIQUE;
	PDFont fontMono = PDType1Font.COURIER;
	PDFont fontItalicBold = PDType1Font.HELVETICA_BOLD_OBLIQUE;
	    
	final String info1 = "A period 15 mins will be allowed before and after hires for setting up/cleaning up, extra times will be included in booing";
	final String header1="LIBERTON KIRK-CHURCH OF SCOTLAND(Scottish Charity No. SC011602)28-30 Kirkgate EH16 6RY"; 
	final String lkTel = "07478111407";
	final String lkEmail = "facilities@libertonkirk.net";
	final String info2=" -*Payment must be made prior to the hire period commencing unless agreed otherwise";
	final String bankName="Bank of Scotland, Newington Branch, 51 Sourth Clerk Branch, Edinburgh EH8 9PP";
	final String bankTrans="A/C Name: Liberton Kirk; Sort Code: 80.02.73; Account No: 00492471";

	
	public PdfTemplate() {
		super();
	}
	
	
	public void makeContractForm(String filePath) throws IOException
	{
		PDDocument document = new PDDocument();
		PDPage page1 = new PDPage(PDRectangle.A4);
		PDRectangle rect = page1.getMediaBox();	//w=595.27563, h=841.8898			
		document.addPage(page1);
		
		//create content of page
		PDPageContentStream cos = new PDPageContentStream(document, page1);
		try {
            PDImageXObject ximage = PDImageXObject.createFromFile("res/kirk.jpg", document);
            float scale = 0.25f; // alter this value to set the image size
            cos.drawImage(ximage, 20, rect.getHeight()- 50, ximage.getWidth()*scale, ximage.getHeight()*scale);
        } catch (IOException ioex) {
            System.out.println("No image for you");
            ioex.printStackTrace();
        }

		
		addText(cos, fontBold, 16, 200, rect.getHeight() - 40, "Contract for Hire of Halls");
		addText(cos, fontBold, 11, 15, rect.getHeight() - 70, header1);
		//data
		String tit1 = "hire title";
		String dat1 = "hire date";
		String name1 ="firs surname";
		String userTel = "user tel";
		String userEmail = "user eamil";
		String userAddress ="user address";
		String hireFee = "£30";
		
		List<String> timeList= new ArrayList<String>();
		List<String> spaceList = new ArrayList<String>();
		List<String> priceList = new ArrayList<String>();
		for(int i=0; i<3; i++) {
			timeList.add("time="+i);
			spaceList.add("space="+i);
			priceList.add("price"+i);
		}
		
		//contract section
		float x=15, w1=100, w2=200, h=20;  
		float y=rect.getHeight()-35*3, y1=y+5; 
		addRectText(cos, fontBold, 11, x, y, w1, h, "Hire agreement"); 
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, tit1);		
		x+=w2; w1=60;
		addRectText(cos, fontBold, 11, x, y, w1, h, "Date");
		cos.addRect(x, y, w1, h);
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, dat1);
		
		//contact section
		x=30; y=rect.getHeight()-32*4; 
		addText(cos, fontBold, 11, x, y, "Representative at Liberton Kirk");
		x=330;
		addText(cos, fontBold, 11, x, y, "User/Group Contact Details");	
		x=15; y=rect.getHeight()-155; y1=y+5;
		w1=80; w2=200;
		addRectText(cos, fontBold, 11, x, y, w1, h, "L.K. Rep.");		
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, "Edna Matthews");		
		x+=w2;
		addRectText(cos, fontBold, 11, x, y, w1, h, "Name:");
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, name1);
		
		x=15; y-=h; y1=y+5;
		addRectText(cos, fontBold, 11, x, y, w1, h, "Contact Tel:");		
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, lkTel);
		x+=w2;
		addRectText(cos, fontBold, 11, x, y, w1, h, "Contact Tel:");		
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, userTel);		
		x=15; y-=h; y1=y+5;
		addRectText(cos, fontBold, 11, x, y, w1, h, "Email:");		
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, lkEmail);
		x+=w2;
		addRectText(cos, fontBold, 11, x, y, w1, h, "Email:");
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, userEmail);
		x=15; y-=h; y1=y+5; w1=110; w2=450;
		addRectText(cos, fontBold, 11, x, y, w1, h, "Customer Address:");		
		x+=w1;
		addRectText(cos, fontPlain, 11, x, y, w2, h, userAddress);
				
		//payment section
		x=15; y=rect.getHeight()-240; h=15; 
		addText(cos, fontBold, 11, x, y, "Hire Fee: ");
		x+=80;
		addText(cos, fontPlain, 11, x, y, hireFee);
		x+=30;
		addText(cos, fontItalic, 10, x, y, info2);
		y-=h;
		x=15;
		addText(cos, fontBold, 11, x, y, "Pay be BACS: ");
		x+=80;
		addText(cos, fontPlain, 11, x, y, bankName);
		y-=h;
		x=15;
		addText(cos, fontBold, 11, x, y, "Bank Details: ");
		x+=80;
		addText(cos, fontPlain, 11, x, y, this.bankTrans);
	
	    //book detail section
		x=150; y=rect.getHeight()-300; h=20;
		addText(cos, fontBold, 11, x, y, "           Details of Hire ");				
		x=15; y-=30; w1=135; w2=80; 
		addRectText(cos, fontBold, 11, x, y, w1, h, "         Date Time");
		x+=w1;
		addRectText(cos, fontBold, 11, x, y, w1, h, "         Location");
		x+=w1;
		addRectText(cos, fontBold, 11, x, y, w1, h, "         Price (£)");
		x+=w1;
		addRectText(cos, fontBold, 11, x, y, w1, h, "         Requirements");
		x=15; y-=h;
		for(int i=0; i<timeList.size(); i++) {
			addRectText(cos, fontPlain, 11, x, y, w1, h, timeList.get(i));
			x+=w1;
			addRectText(cos, fontPlain, 11, x, y, w1, h, spaceList.get(i));
			x+=w1;
			addRectText(cos, fontPlain, 11, x, y, w1, h, priceList.get(i));
			x+=w1;
			addRectText(cos, fontPlain, 11, x, y, w1, h, " ");
			x=15;
			y-=h;
		}
		addRectText(cos, fontPlain, 11, x, y, 4*w1, h, "Recurring: ");
		h=15; y-=h; 
		addText(cos, fontPlain, 11, x, y, "Requirements include:  1. P.A = Public Address System,");
		y-=h;
		addText(cos, fontPlain, 11, x, y, "                                    2. V.P = Video projector and/or screen,");
		y-=h;
		addText(cos, fontPlain, 11, x, y, "                                    3. Tabbles/Chair = table/chairs available for you to setup");
		
		//waster section as final heighjt (no change from here 
		x=40; y=rect.getHeight()-500;;
		addText(cos, fontBold, 11, x, y, "**PLEASE ALSO TAKE ALL WASTE HOME AS WE DON'T HAVE ENOUGH BIN SPACES**");
		x=20; y-=h;		
		addText(cos, fontPlain, 11, x, y, "The large bin at side of Anderson Hall does not belong to use so please don not put your waste there");
		
		//term sections, height won't be changed from here
		x=50; y=rect.getHeight()-550; h=20;
		String tm1 = "* Please read the full version of our Terms and Conditions of which there are only a part(be provided at the time of booking)"; 
	    addText(cos, fontPlain, 9, x, y, tm1);
	    y-=h;
	    String tm2= "* The user must inform Libertion Kirk of their intention to have any \"third pary\" entertainment in the halls and must ensure if so";
	    addText(cos, fontPlain, 9, x, y, tm2);
	    y-=10;
	    String tm2_1 = "     1. they have their own adeuate Public Liability Insurance";
	    addText(cos, fontPlain, 9, x, y, tm2_1);
		y-=10;
	  	String tm2_2 ="     2. some activities will require to hava in place their own Public Liability Insurence";  
	  	addText(cos, fontPlain, 9, x, y, tm2_2);		
	    y-=h;  
	    String tm3 = "* Any breakage and damage to either equipments or fabric will be met by the user";		
	    addText(cos, fontPlain, 9, x, y, tm3);	    
	    y-=h;
	    String tm4 = "* A period 15 mins will be allowed before and after hires for setting up/cleaning up, extra times will be included in booking";
	    addText(cos, fontPlain, 9, x, y, tm4);	    
	    y-=h;
	    String tm5="* Recurring hire arrangement must be renewed by 1st September for the following year.(Failure to do this may loss hall use)";  
	    addText(cos, fontPlain, 9, x, y, tm5);
	    
	    //declaration section
	    x=100; y=rect.getHeight()-700; h=20;
	    String decStr = "I declare that I have read and accept the Terms and Conditions.";
	    String clientName ="sunny yang";
	    addText(cos, fontBold, 11, x, y, decStr);
	       
	    x=20; y-=20; w1=240; h=20;
	    addText(cos, fontPlain, 11, x, y, "LK Representative: Edna Matthews");   
		x+=w1;
		addText(cos, fontPlain, 11, x, y, "Client: "+clientName);			   
		x=20; y-=h; 
		addText(cos, fontPlain, 11, x, y, "Signature: ________________________");	    
		x+=w1;
		addText(cos, fontPlain, 11, x, y, "Signature: ________________________");
		x=20; y-=h; 
		addText(cos, fontPlain, 11, x, y, "Date: ________________________");	    
		x+=w1;
		addText(cos, fontPlain, 11, x, y, "Date: ________________________");
				    
		cos.stroke();
		cos.close();	
		document.save(filePath);	
		document.close();		
	}
	
	public void makeInvoiceForm(String filePath) throws IOException
	{
		//data
		String ref_nbr ="ch2333";
		String tit = "titles??";
		String nm = "name for leader";
		
		List<String> timeList= new ArrayList<String>();
		List<String> spaceList = new ArrayList<String>();
		List<String> priceList = new ArrayList<String>();
		for(int i=0; i<3; i++) {
			timeList.add("time="+i);
			spaceList.add("space="+i);
			priceList.add("price"+i);
		}
		
		PDDocument document = new PDDocument();
		PDPage page1 = new PDPage(PDRectangle.A4);
		PDRectangle rect = page1.getMediaBox();	//w=595.27563, h=841.8898			
		document.addPage(page1);
		
		//create content of page
		PDPageContentStream cos = new PDPageContentStream(document, page1);
		try {
            PDImageXObject ximage = PDImageXObject.createFromFile("res/kirk.jpg", document);
            float scale = 0.5f; // alter this value to set the image size
            cos.drawImage(ximage, 30, rect.getHeight()- 120, ximage.getWidth()*scale, ximage.getHeight()*scale);
        } catch (IOException ioex) {
            System.out.println("No image for you");
            ioex.printStackTrace();
        }
		float x=380, y=rect.getHeight()-70, h=20;  
		addText(cos, fontBold, 16, x, y, "Liberton Kirk Office");
		y-=h;
		addText(cos, fontBold, 16, x, y, "28/30 Kirkgate");
		y-=h;
		addText(cos, fontBold, 16, x, y, "Edinburgh, EH16 6RY");
		
		x=200; y=rect.getHeight()-150; 
		int font_size = 12;
		float x1= 320;
		addText(cos, fontBold, font_size, x, y, "Our Reference: ");
		addText(cos, fontPlain, font_size, x1, y, ref_nbr);
		y-=15;
		addText(cos, fontItalic, font_size, x, y, "Please quote the reference when contact us");
		
		x=50;
		x1=250; y=rect.getHeight()-220; h=25;
		addText(cos, fontBold, font_size, x, y, "Invoice to: ");
		addText(cos, fontPlain, font_size, x1, y, tit);
		y-=h;
		addText(cos, fontBold, font_size, x, y, "Title of Event/Class: ");
		addText(cos, fontPlain, font_size, x1, y, nm);
		y-=h;
		addText(cos, fontBold, font_size, x, y, "Email: ");
		addText(cos, fontPlain, font_size, x1, y, nm);
		y-=h;
		addText(cos, fontBold, font_size, x, y, "Mobile Number: ");
		addText(cos, fontPlain, font_size, x1, y, nm);
		y-=h;
		addText(cos, fontBold, font_size, x, y, "Invoice Date: ");
		addText(cos, fontPlain, font_size, x1, y, nm);
		y-=h;
		addText(cos, fontBold, font_size, x, y, "Halls being used: ");
		addText(cos, fontPlain, font_size, x1, y, nm);
		
		x=50; y-=50; 
		float w1=150; 
		addRectText(cos, fontBold, 11, x, y, w1, h, "         Date Time");
		x+=w1;
		addRectText(cos, fontBold, 11, x, y, w1, h, "         Location");
		x+=w1;
		addRectText(cos, fontBold, 11, x, y, w1, h, "         Price (£)");
		x=50; y-=h;
		for(int i=0; i<timeList.size(); i++) {
			addRectText(cos, fontPlain, 11, x, y, w1, h, timeList.get(i));
			x+=w1;
			addRectText(cos, fontPlain, 11, x, y, w1, h, spaceList.get(i));
			x+=w1;
			addRectText(cos, fontPlain, 11, x, y, w1, h, priceList.get(i));
			x=50;
			y-=h;
		}
		y-=10; 
		addText(cos, fontBold, font_size, x, y, "Total Hours: ");
		addText(cos, fontPlain, font_size, x1, y, nm);
		y-=h;
		addText(cos, fontBold, font_size, x, y, "Amount Due: ");
		addText(cos, fontPlain, font_size, x1, y, nm);
			
		cos.stroke();
		cos.close();	
		document.save(filePath);	
		document.close();
		
	}
	
	//reference test
		public void testReference() throws IOException {
			PDDocument document = new PDDocument();
		    PDPage page = new PDPage();
		    document.addPage(page);

		    int pageWidth = (int)page.getTrimBox().getWidth(); //get width of the page
		    int pageHeight = (int)page.getTrimBox().getHeight(); //get height of the page

		    PDPageContentStream contentStream = new PDPageContentStream(document,page);
		    contentStream.setStrokingColor(Color.DARK_GRAY);
		    contentStream.setLineWidth(1);

		    int initX = 50;
		    int initY = pageHeight-50;
		    int cellHeight = 20;
		    int cellWidth = 100;

		    int colCount = 3;
		    int rowCount = 3;

		    for(int i = 1; i<=rowCount;i++){
		        for(int j = 1; j<=colCount;j++){
		            if(j == 2){
		                contentStream.addRect(initX,initY,cellWidth+30,-cellHeight);

		                contentStream.beginText();
		                contentStream.newLineAtOffset(initX+30,initY-cellHeight+10);
		                contentStream.setFont(PDType1Font.TIMES_ROMAN,10);
		                contentStream.showText("Dinuka");
		                contentStream.endText();

		                initX+=cellWidth+30;
		            }else{
		                contentStream.addRect(initX,initY,cellWidth,-cellHeight);

		                contentStream.beginText();
		                contentStream.newLineAtOffset(initX+10,initY-cellHeight+10);
		                contentStream.setFont(PDType1Font.TIMES_ROMAN,10);
		                contentStream.showText("Dinuka");
		                contentStream.endText();

		                initX+=cellWidth;
		            }
		        }
		        initX = 50;
		        initY -=cellHeight;
		    }
		    	    
		    contentStream.close();
		    document.save("pdf_fiels/table.pdf");
		    document.close();
		    System.out.println("table pdf created");
		  }

	
	//---- suppot methods
	/*
	 * Add text to PDF file, postion x, y (up) 
	 */
	private void addText(PDPageContentStream cos, PDFont pdFont, int fontSize, float x, float y, String txt) throws IOException
	{
		cos.beginText();
		cos.setFont(pdFont, fontSize);
		cos.newLineAtOffset(x, y); 
		cos.showText(txt);
		cos.endText();
		
	}
	
	//add text with a rectangle box
	private void addRectText(PDPageContentStream cos, PDFont pdFont, int fontSize, float x, float y, float w, float h, String txt) 
	throws IOException{
		float x1 = x+2;
		float y1 = y+5;
		cos.addRect(x, y, w, h);
		cos.beginText();
		cos.setFont(pdFont, fontSize);
		cos.newLineAtOffset(x1, y1);
		cos.showText(txt);
		cos.endText();
		
	}
}
