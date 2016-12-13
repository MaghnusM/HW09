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
import java.io.*;
import java.util.Collections;

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
	private static final String HIGHSCORE_FILE = "highscore.txt";
	
	private TreeMap<Integer, Candy> candies = new TreeMap<Integer, Candy>();
	
	private int numProducers_x = COURT_EXTRA / 50;
	private int numProducers_y = (COURT_HEIGHT - 50) / 50;
	public static final int PRODUCER_SPACER = 50;
	private CandyProducer[][] producerGrid = new CandyProducer[numProducers_x][numProducers_y];
	
	private List<Integer> highscores = new LinkedList<Integer>();
	
	// Set score TODO: is this the right place to put this?
	private int score = 0;
	
	public static double rate = 0.0;

	public boolean playing = false; // whether the game is running
	
	private JLabel status; // Current status text (i.e. Running...)
	private JLabel scoreLabel;
	private JLabel rateLabel;
	
	private JButton grandmaButton;
	private static final int GRANDMA_COST = 50;
	private String grandmaST = "";
	
	private JButton chefButton;
	private static final int CHEF_COST = 250;
	private String chefST = "";
	
	private JButton bakeryButton;
	private static final int BAKERY_COST = 2500;
	private String bakeryST = "";
	
	private JButton gigFacButton;
	private static final int GFL_COST = 10000;
	private String gfST = "";
	
	private JButton mineButton;
	private static final int MINE_COST = 14000;
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


		this.status = status;
		
		this.scoreLabel = new JLabel(Integer.toString(this.score));
		scoreLabel.setBounds(COURT_WIDTH/2 - 50, 40, 300, 100);
		scoreLabel.setFont(new Font("Serif", Font.BOLD, 90));
		this.add(scoreLabel);
		
		this.rateLabel = new JLabel((Double.toString(rate) + " candy/sec"));
		rateLabel.setBounds(COURT_WIDTH/2 - 50, 100, 300, 100);
		rateLabel.setFont(new Font("Serif", Font.BOLD, 40));
		this.add(rateLabel);
		
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
	 * This method is called every time the timer defined in the constructor
	 * triggers.
	 */
	void tick() {
		if (playing) {
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

		
		this.score = this.score + points;
		scoreLabel.setText(Integer.toString(this.score));
		rateLabel.setText(Double.toString(rate) + " candy/sec");
	}
	
	public void addProducer(String className) {
		try {
			if(className.equals("Grandma")) {
				if(score >= GRANDMA_COST) {
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
								score = score - GRANDMA_COST;
								scoreLabel.setText(Integer.toString(this.score));
								rateLabel.setText(Double.toString(rate) + " candy/sec");
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("Chef")) {
				if(score >= CHEF_COST) {
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
								score = score - CHEF_COST;
								scoreLabel.setText(Integer.toString(this.score));
								rateLabel.setText(Double.toString(rate) + " candy/sec");
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("Bakery")) {
				if(score >= BAKERY_COST) {
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
								score = score - BAKERY_COST;
								scoreLabel.setText(Integer.toString(this.score));
								rateLabel.setText(Double.toString(rate) + " candy/sec");
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("Mine")) {
				if(score >= MINE_COST) {
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
								score = score - MINE_COST;
								scoreLabel.setText(Integer.toString(this.score));
								rateLabel.setText(Double.toString(rate) + " candy/sec");
								setOne = false;
							}
						}
					}
				}
			} else if(className.equals("GigFac")) {
				if(score >= GFL_COST) {
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
								score = score - GFL_COST;
								scoreLabel.setText(Integer.toString(this.score));
								rateLabel.setText(Double.toString(rate) + " candy/sec");
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
	
	public void winGame() {
		playing = false;
		scoreLabel.setText("You won!");
	}
	
	public void openInstructions() {
		
		JOptionPane.showMessageDialog(this, "Hello! Welcome to Cookie Clicker.\n" +
				"The goal of this game is to fill the entire screen with cookies, all 14,400 places\n" +
				"Of course, you can add cookies by clicking the large cookie button.\n" +
				"However, of course that won't be enough - you'll need to add Cookie Producers to help you\n" +
				"These are:\n" +
				"Grandmas: COST: 50, COOKIE RATE: 1 every 8 seconds\n" +
				"Chefs: COST: 250, COOKIE RATE: 5 every 5 seconds\n" +
				"Bakeries: COST: 2500, COOKIE RATE: 200 every 10 seconds\n" +
				"Mines: COST: 10000, COOKIE RATE: 2000 every 20 seconds\n" +
				"GigFactory LLC: COST: 14000, COOKIE RATE: Immediate Win!\n\n" +
				"Good luck!");
    }
	
	/**
	 * (Re-)set the game to its initial state.
	 */
	public void reset() {
		
		playing = true;
		status.setText("Running...");
		initializeProducers();

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
		
		FileReader fileRead = null;
		BufferedReader bufferRead = null;
		
		try {
			fileRead = new FileReader(HIGHSCORE_FILE);
			bufferRead = new BufferedReader(fileRead);
			
			String thisLine;
			bufferRead = new BufferedReader(new FileReader(HIGHSCORE_FILE));
			
			while ((thisLine = bufferRead.readLine()) != null) {
				try { 
					if (Integer.parseInt(thisLine) > 0) {
						highscores.add(Integer.parseInt(thisLine));
						System.out.println(thisLine);
					}
				} catch (NumberFormatException nfe) {
					System.out.println("not integer");
				}
			}
		} catch(FileNotFoundException ex) {
	          System.out.println(
	        		"Unable to open file '" + 
	        		HIGHSCORE_FILE + "'"); 
			  try {
				  	FileWriter fw = new FileWriter(HIGHSCORE_FILE);
				  	BufferedWriter bw = new BufferedWriter(fw);
				  	
				    bw.close();
			  } catch(IOException exs) {
	                ex.printStackTrace();
	          }
		} catch(IOException exs) {
			exs.printStackTrace();
		}
	}
	
	public void abruptEnd() {
			Writer add;
			try {
				add = new BufferedWriter(new FileWriter(HIGHSCORE_FILE, true));
				add.append("\n");
				add.append(Integer.toString(this.score));
				add.append("\n");
				add.close();
			} catch (Exception e) {
				System.out.println("couldn't add score");
			}
	}
	
	public void openHighscores() {
		
		Collections.sort(highscores);
		Collections.reverse(highscores);
		String highscoreList = "\n";
		for(int e : highscores) {
			highscoreList = highscoreList + Integer.toString(e) + "\n";
		}
		JOptionPane.showMessageDialog(this, "Highscores are" + highscoreList);
		
		
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
