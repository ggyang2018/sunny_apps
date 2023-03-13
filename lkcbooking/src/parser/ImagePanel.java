package parser;
import java.awt.Color;
/**
 * Image panel for display image
 * beware:  ImageIO is not for animated GIF 
 * @author guang yang (Sunny)
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class ImagePanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	//private BufferedImage image;
	Image img;
	Color navyBlue;

    public ImagePanel() {
   	
    	//setBackground(Color.BLUE.brighter().brighter());
    	navyBlue = new Color(0, 0, 52);
    	setBackground(navyBlue.brighter().brighter());
    	
       try {                
          //image = ImageIO.read(new File("res/jesus-god.gif"));
    	   img = Toolkit.getDefaultToolkit().createImage("res/jesus-god.gif");
    	   //to annimation
    	   MediaTracker mt = new MediaTracker(this);
    	   mt.addImage(img, 0);
    	   mt.waitForAll();
          
       } catch (Exception ex) { ex.printStackTrace();}
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, this); // see javadoc for more info on the parameters
        g.drawImage(img, 400, 20, 300, 400, this);
    }

}