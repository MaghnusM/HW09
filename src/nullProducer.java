import java.awt.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import java.awt.event.*;
import javax.swing.*;
import java.awt.geom.*;

public class nullProducer extends CandyProducer {
	
	private String name;
	private int id;
	private int price;
	
	private String candyType;
	private int time;
	
		
	public nullProducer(int id, GameCourt court, int row, int col) {
		super("open", id, 0, 0, court, "null", 50, 50, row, col);
	} 
}