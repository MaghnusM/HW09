import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Candy extends GameObj{
	private int id;
	
	public static final int SIZE = 5;
	public static final int INIT_POS_X = 0;
	public static final int INIT_POS_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	
	public static final String img_file = "cookie.png";
	private static BufferedImage img;
	
	private int courtDim;
	
	public Candy(int id, int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE,
				courtWidth, courtHeight);
		this.id = id;
		this.courtDim = courtWidth;
		this.setLocation();
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
		}
	}
	
	public void setLocation() {
		int row = (int) Math.floor((id * SIZE) / courtDim);
		this.pos_x = (id * SIZE) - (row * courtDim);
		if(id != 0) {
//			System.out.println("pos_y: " + ( SIZE * Math.floor(id/(courtDim/SIZE)) ));
			this.pos_y = (int) ( SIZE * Math.floor(id/(courtDim/SIZE)) );
			
		} else {
			this.pos_y = 0;
		}
	}
	
	@Override
	public void draw(Graphics g) {
		g.drawImage(img, pos_x, pos_y, width, height, null);
	}
}