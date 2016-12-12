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
import java.lang.reflect.*;


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
	private Clicker clicker; // the main candy-clicker, doesn't move
	
	
	private TreeMap<Integer, Candy> candies = new TreeMap<Integer, Candy>();
	
	private int numProducers_x = COURT_EXTRA / 50;
	private int numProducers_y = (COURT_HEIGHT - 50) / 50;
	public static final int PRODUCER_SPACER = 50;
	private CandyProducer[][] producerGrid = new CandyProducer[numProducers_x][numProducers_y];
	
	// Set score TODO: is this the right place to put this?
	private int score = 0;

	public boolean playing = false; // whether the game is running
	
	private JLabel status; // Current status text (i.e. Running...)
	private JLabel scoreLabel;
	
	private JButton grandmaButton;
	private static final int GRANDMA_COST = 50;
	private String grandmaST = "";
	
	private JButton chefButton;
	private static final int CHEF_COST = 200;
	private String chefST = "";
	
	private JButton bakeryButton;
	private static final int BAKERY_COST = 1000;
	private String bakeryST = "";
	
	private JButton gigFacButton;
	private static final int GFL_COST = 100000;
	private String gfST = "";
	
	private JButton mineButton;
	private static final int MINE_COST = 10000;
	private String mineST = "";

	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_EXTRA = 400;
	public static final int COURT_HEIGHT = 600;
	// Update interval for timer, in milliseconds
	public static final int INTERVAL = 35;

	public GameCourt(JLabel status) {
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		this.setLayout(null);
		
		System.out.println(numProducers_x);
		System.out.println(numProducers_y);

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
//		addKeyListener(new KeyAdapter() {
//			public void keyPressed(KeyEvent e) {
//				if (e.getKeyCode() == KeyEvent.VK_LEFT)
//					square.v_x = -SQUARE_VELOCITY;
//				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
//					square.v_x = SQUARE_VELOCITY;
//				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
//					square.v_y = SQUARE_VELOCITY;
//				else if (e.getKeyCode() == KeyEvent.VK_UP)
//					square.v_y = -SQUARE_VELOCITY;
//			}
//
//			public void keyReleased(KeyEvent e) {
//				square.v_x = 0;
//				square.v_y = 0;
//			}
//		});

		this.status = status;
		
		this.scoreLabel = new JLabel(Integer.toString(this.score));
		scoreLabel.setBounds(COURT_WIDTH/2 - 50, 80, 300, 100);
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 90));
		this.add(scoreLabel);
		
		this.grandmaButton = new JButton("GRANDMAS" + grandmaST);
		grandmaButton.setBounds(COURT_WIDTH + (50 - 45), 0, 90, 40);
		this.add(grandmaButton);
		grandmaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProducer("Grandma");
			}
		});
		
		this.chefButton = new JButton("CHEFS" + chefST);
		chefButton.setBounds(COURT_WIDTH + (150 - 35), 0, 70, 40);
		this.add(chefButton);
		chefButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProducer("Chef");
			}
		});
		
		this.bakeryButton = new JButton("BAKERIES" + bakeryST);
		bakeryButton.setBounds(COURT_WIDTH + (250 - 50), 0, 100, 40);
		this.add(bakeryButton);
		bakeryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProducer("Bakery");
			}
		});
		
		this.mineButton = new JButton("MINES" + mineST);
		mineButton.setBounds(COURT_WIDTH + 302, 0, 50, 40);
		this.add(mineButton);
		mineButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProducer("Mine");
			}
		});
		
		this.gigFacButton = new JButton("GFL" + gfST);
		gigFacButton.setBounds(COURT_WIDTH + (400 - 48), 0, 50, 40);
		this.add(gigFacButton);
		gigFacButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addProducer("GigFac");
			}
		});
		
		
		//ADD THE MAIN CLICKER
		this.clicker = new Clicker("Clicker");
		clicker.setBounds(COURT_WIDTH/2 - 125, COURT_HEIGHT/2 - 125, 250, 250);
		clicker.setPreferredSize(new Dimension(50, 50));
		this.add(clicker);
		clicker.setOpaque(false);
		clicker.setContentAreaFilled(false);
		clicker.setBorderPainted(false);
		this.clicker.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addCandy(1);
			}
		});
		
		this.setBackground( Color.WHITE );
		
	}

	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
//		poison = new Poison(COURT_WIDTH, COURT_HEIGHT);
		
		playing = true;
		status.setText("Running...");
		initializeProducers();

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
//			square.move();
//			snitch.move();

			// make the snitch bounce off walls...
//			snitch.bounce(snitch.hitWall());
			// ...and the mushroom
//			snitch.bounce(snitch.hitObj(poison));

			// check for the game end conditions
//			if (square.intersects(poison)) {
//				playing = false;
//				status.setText("You lose!");
//
//			}
			
//			if (square.intersects(snitch)) {
//				playing = false;
//				status.setText("You win!");
//			}

			// update the display
			repaint();
		}
	}
	
	public void initializeProducers() {
		int index = 0;
		for(int i = 0; i < numProducers_x; i++) {
			for(int j = 0; j < numProducers_y; j++) {
				producerGrid[i][j] = new nullProducer(index, this, j, i);
				this.add(producerGrid[i][j]);
				index++;
			}
		}
	}
	
	public void addCandy(int points) {
		if(this.score >= 14400) {
			playing = false;
			scoreLabel.setText("You won!");
		}
		for(int x = 0; x < points; x++) {
			int id = 0;
			try {
				id = candies.lastKey() + 1;
				Candy can = new Candy(id, COURT_WIDTH, COURT_HEIGHT);
				candies.put(id, can);
			} catch (Exception e) {
				Candy can = new Candy(id, COURT_WIDTH, COURT_HEIGHT);
				candies.put(id, can);
			}
		}
//		System.out.println("points = " + points);
//		System.out.println(this.score);
		this.score = this.score + points;
		scoreLabel.setText(Integer.toString(this.score));
//		System.out.println(this.score);
	}
	
	public void addProducer(String className) {
		try {
			if(className.equals("Grandma")) {
				if(score >= GRANDMA_COST) {
					score = score - GRANDMA_COST;
					scoreLabel.setText(Integer.toString(score));
					for(int i = 0; i < GRANDMA_COST; i++) {
						candies.remove(candies.lastKey());
					}
					boolean setOne = true;
					for(int i = 0; i < 2; i++) {
						for(int j = 0; j < numProducers_y; j++) {
							if(producerGrid[i][j].getName().equals("open") && setOne) {
								this.remove(producerGrid[i][j]);
								int id = producerGrid[i][j].getId();
								producerGrid[i][j] = new Grandma(id, this, j, i);
								this.add(producerGrid[i][j]);
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("Chef")) {
				if(score >= CHEF_COST) {
					score = score - CHEF_COST;
					scoreLabel.setText(Integer.toString(score));
					for(int i = 0; i < CHEF_COST; i++) {
						candies.remove(candies.lastKey());
					}
					boolean setOne = true;
					for(int i = 2; i < 4; i++) {
						for(int j = 0; j < numProducers_y; j++) {
							if(producerGrid[i][j].getName().equals("open") && setOne) {
								this.remove(producerGrid[i][j]);
								int id = producerGrid[i][j].getId();
								producerGrid[i][j] = new Chef(id, this, j, i);
								this.add(producerGrid[i][j]);
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("Bakery")) {
				if(score >= BAKERY_COST) {
					score = score - BAKERY_COST;
					scoreLabel.setText(Integer.toString(score));
					for(int i = 0; i < BAKERY_COST; i++) {
						candies.remove(candies.lastKey());
					}
					boolean setOne = true;
					for(int i = 4; i < 6; i++) {
						for(int j = 0; j < numProducers_y; j++) {
							if(producerGrid[i][j].getName().equals("open") && setOne) {
								this.remove(producerGrid[i][j]);
								int id = producerGrid[i][j].getId();
								producerGrid[i][j] = new Bakery(id, this, j, i);
								this.add(producerGrid[i][j]);
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("Mine")) {
				if(score >= MINE_COST) {
					score = score - MINE_COST;
					scoreLabel.setText(Integer.toString(score));
					for(int i = 0; i < MINE_COST; i++) {
						candies.remove(candies.lastKey());
					}
					boolean setOne = true;
					for(int i = 6; i < 7; i++) {
						for(int j = 0; j < numProducers_y; j++) {
							if(producerGrid[i][j].getName().equals("open") && setOne) {
								this.remove(producerGrid[i][j]);
								int id = producerGrid[i][j].getId();
								producerGrid[i][j] = new Mine(id, this, j, i);
								this.add(producerGrid[i][j]);
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("GigFac")) {
				if(score >= GFL_COST) {
					score = score - GFL_COST;
					scoreLabel.setText(Integer.toString(score));
					for(int i = 0; i < GFL_COST; i++) {
						candies.remove(candies.lastKey());
					}
					boolean setOne = true;
					for(int i = 7; i < 8; i++) {
						for(int j = 0; j < numProducers_y; j++) {
							if(producerGrid[i][j].getName().equals("open") && setOne) {
								this.remove(producerGrid[i][j]);
								int id = producerGrid[i][j].getId();
								producerGrid[i][j] = new GigFac(id, this, j, i);
								this.add(producerGrid[i][j]);
								setOne = false;
							}
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("was a problem");
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
        Line2D lin1 = new Line2D.Float(COURT_WIDTH, 0, COURT_WIDTH, COURT_HEIGHT);
        Line2D lin2 = new Line2D.Float(COURT_WIDTH + 100, 0, COURT_WIDTH + 100, COURT_HEIGHT);
        Line2D lin3 = new Line2D.Float(COURT_WIDTH + 200, 0, COURT_WIDTH + 200, COURT_HEIGHT);
        Line2D lin4 = new Line2D.Float(COURT_WIDTH + 300, 0, COURT_WIDTH + 300, COURT_HEIGHT);
        Line2D lin5 = new Line2D.Float(COURT_WIDTH + 350, 0, COURT_WIDTH + 350, COURT_HEIGHT);
        g2.draw(lin1);
        g2.draw(lin2);
        g2.draw(lin3);
        g2.draw(lin4);
        g2.draw(lin5);
		for(Candy c : candies.values()) {
			c.draw(g);
		}
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension((COURT_WIDTH + COURT_EXTRA), COURT_HEIGHT);
	}
}
