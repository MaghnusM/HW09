/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

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


/**
 * GameCourt
 * 
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 * 
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private Square square; // the Black Square, keyboard control
	private Circle snitch; // the Golden Snitch, bounces
//	private Poison poison; // the Poison Mushroom, doesn't move
	private Clicker clicker; // the main candy-clicker, doesn't move
	
	// Set score TODO: is this the right place to put this?
	private int score = 0;

	public boolean playing = false; // whether the game is running
	private JLabel status; // Current status text (i.e. Running...)
	private JLabel scoreLabel;

	// Game constants
	public static final int COURT_WIDTH = 300;
	public static final int COURT_HEIGHT = 300;
	public static final int SQUARE_VELOCITY = 4;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setLayout(new BorderLayout());

		// The timer is an object which triggers an action periodically
		// with the given INTERVAL. One registers an ActionListener with
		// this timer, whose actionPerformed() method will be called
		// each time the timer triggers. We define a helper method
		// called tick() that actually does everything that should
		// be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area.
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// This key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually
		// moves the square.)
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					square.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					square.v_x = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					square.v_y = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					square.v_y = -SQUARE_VELOCITY;
			}

			public void keyReleased(KeyEvent e) {
				square.v_x = 0;
				square.v_y = 0;
			}
		});

		this.status = status;
//		this.clicker = clicker;
		
//		this.scoreLabel = new JLabel(Integer.toString(this.score));
//		scoreLabel.setBounds(0, 100, 100, 100);
////	    scoreLabel.setLocation(10, 50);
////	    scoreLabel.setPreferredSize(new Dimension(100, 100));
//		this.add(scoreLabel);
		
		this.clicker = new Clicker("Clicker", 100, 100, 50, 50, this);
//		clicker.setBounds(100, 100, 100, 100);
		this.clicker.addMouseListener(new MyMouseListener(clicker, clicker));
		this.add(this.clicker);
		
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {

		square = new Square(COURT_WIDTH, COURT_HEIGHT);
//		poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
		snitch = new Circle(COURT_WIDTH, COURT_HEIGHT);
		
		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	/**
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
			// advance the square and snitch in their
			// current direction.
			square.move();
			snitch.move();

			// make the snitch bounce off walls...
			snitch.bounce(snitch.hitWall());
			// ...and the mushroom
//			snitch.bounce(snitch.hitObj(poison));

			// check for the game end conditions
//			if (square.intersects(poison)) {
//				playing = false;
//				status.setText("You lose!");
//
//			}
			
			if (square.intersects(snitch)) {
				playing = false;
				status.setText("You win!");
			}

			// update the display
			repaint();
		}
	}
	
	public void addPoints(boolean isFromClicker) {
		if(isFromClicker) {
			//add clicker upgrade options?
			this.score ++;
			System.out.println(score);
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		square.draw(g);
//		poison.draw(g);
		snitch.draw(g);
		clicker.draw(g);
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(COURT_WIDTH, COURT_HEIGHT);
	}
}
