/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.MouseListener;
import java.awt.event.*;


/**
 * A game object displayed using an image.
 * 
 * Note that the image is read from the file when the object is constructed, and
 * that all objects created by this constructor share the same image data (i.e.
 * img is static). This important for efficiency: your program will go very
 * slowing if you try to create a new BufferedImage every time the draw method
 * is invoked.
 */
public class Clicker extends JComponent {
	
	public static final String img_file = "poison.png";
//	public static final int SIZE = 40;
//	public static final int INIT_X = 100;
//	public static final int INIT_Y = 100;
//	public static final int INIT_VEL_X = 0;
//	public static final int INIT_VEL_Y = 0;
	
	public int pos_x; 
	public int pos_y;

	/** Size of object, in pixels */
	public int width;
	public int height;
	
	private static BufferedImage img;
	
	GameCourt mainCourt;
	
	public Clicker(String name, int pos_x, int pos_y, int width, int height, GameCourt mainCourt) {
//		super(INIT_VEL_X, INIT_VEL_Y, INIT_X, INIT_Y, SIZE, SIZE, courtWidth,
//				courtHeight);
		
		this.setName(name);
		this.setPreferredSize(new Dimension(width, height));
		this.setLocation(pos_x, pos_y);
		this.pos_x = pos_x;
		this.pos_y = pos_y;
		this.width = width;
		this.height = height;
		
		this.mainCourt = mainCourt;
		
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	
//	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}
	
	public void addPoints() {
		System.out.println("add points");
		mainCourt.addPoints(true);
	}

}
