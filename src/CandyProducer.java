import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;


public class CandyProducer extends JLabel {
	
	private String name;
	private int id;
	
	private String img_file;
	
	private int time;
	private int amount;
	private static ImageIcon img;
	
	private int pos_x;
	private int pos_y;
	private int row;
	private int col;
	private int spacerSize;
	private int width;
	private int height;
	private int leftPadding;
	private int topPadding;
	
	private GameCourt court;
	
	public CandyProducer(String name, int id, int amount, 
			int time, GameCourt court, String img_file, int width, int height, int row, int col) {
		this.name = name;
		this.id = id;
		this.time = time;
		this.court = court;
		this.img_file = img_file;
		this.width = width;
		this.height = height;
		this.row = row;
		this.col = col;
		this.spacerSize = court.PRODUCER_SPACER;
		this.leftPadding = 10;
		this.topPadding = 50;
		this.amount = amount;
		
		if(amount == -1) {
			court.winGame();
		}
		
		if(time != 0) {
			court.rate = court.rate + ((Double) (amount/(time/1000.0)));
			System.out.println((Double) (amount/(time/1000.0)));
			Timer timer = new Timer(time, new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					makeCandy();
				}
			});
			timer.start();
		}
		
		if(img_file.equals("null")) {
			this.setText(name);
		} else {
			try {
				Image imgF = ImageIO.read(new File(img_file));
				Image imgS = imgF.getScaledInstance(width, height, 0);
				img = new ImageIcon(imgS);
				this.setIcon(img);
			} catch (Exception e) {
				System.out.println("oh no");
			}
		}
		
		this.setLocation();
		
	}
	
	public void setLocation() {
		int base = court.COURT_WIDTH;
		this.pos_x = leftPadding + base + (col * spacerSize);
		if(id != 0) {
			this.pos_y = topPadding + spacerSize * row;
		} else {
			this.pos_y = topPadding;
		}
		this.setBounds(pos_x, pos_y, width, height);
	}
	
	public void makeCandy() {
		court.addCandy(amount);
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getId() {
		return this.id;
	}
	
}   