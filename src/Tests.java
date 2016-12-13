import static org.junit.Assert.*;
import java.io.*;

import javax.swing.JLabel;
import java.text.DecimalFormat;
import org.junit.Test;
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

public class Tests {
	
	//These two tests check the instantiation of the CandyProducers,
	//...by checking the rate increase
	
	final JLabel status = new JLabel("Running...");
	  
	  DecimalFormat decimalFormat = new DecimalFormat("#");
	  GameCourt court = new GameCourt(status);
	
  @Test public void testCreateGrandmaIncreasesRate() {

	  Grandma g = new Grandma(0, court, 0, 0);
	  assertEquals(decimalFormat.format(court.rate), decimalFormat.format(0.125));
  }
  
  @Test public void testCreateMineIncreasesRate() {

	  Mine m = new Mine(1, court, 0, 0);
	  assertEquals(decimalFormat.format(court.rate), decimalFormat.format(100));
  }
  
  @Test public void testAddCandyMethodAddsCandiesAndScore() {
	  
	  court.addCandy(50);
	  TreeMap<Integer, Candy> candies = court.getCandies();
	  
	  assertEquals(court.getScore(), 50);
	  assertEquals(candies.get(candies.lastKey()).getId(), 49);
	  assertEquals(candies.size(), 50);
  }
  
  @Test public void testBuyGrandmaReducesCandyAndScore() {
	  
	  court.addCandy(100);
	  TreeMap<Integer, Candy> candies = court.getCandies();
	  court.initializeProducers();
	  
	  court.addProducer("Grandma");
	  CandyProducer[][] candyProducers = court.getProducerGrid();
	  
	  assertEquals(candyProducers[0][0].getName(), "Grandma");
	  assertEquals(court.getScore(), 50);
	  
  }
}
  
//  @Test public void testFileCorrectorNullReader() throws IOException, FileCorrector.FormatException {
//    // Here's how to test expecting an exception...
//    try {
//      new FileCorrector(null);
//      fail("Expected an IllegalArgumentException - cannot create FileCorrector with null.");
//    } catch (IllegalArgumentException f) {    
//      //Do nothing. It's supposed to throw an exception
//    }
//  }
//
//  
//  @Test public void testGetCorrection() throws IOException, FileCorrector.FormatException  {
//    Corrector c = FileCorrector.make("smallMisspellings.txt");
//    assertEquals("lyon -> lion", makeSet(new String[]{"lion"}), c.getCorrections("lyon"));
//    TreeSet<String> set2 = new TreeSet<String>();
//    assertEquals("TIGGER -> {Trigger,Tiger}", makeSet(new String[]{"Trigger","Tiger"}), c.getCorrections("TIGGER"));
//  }
//
//
//  
//  @Test public void testInvalidFormat() throws IOException, FileCorrector.FormatException  {
//	 try {
//		  Corrector c = new FileCorrector(new StringReader("no comma in this puppy"));
//	     fail("This is a bad format");
//	 } catch (FileCorrector.FormatException e) {
//	    // do nothing
//	 }
//  }

  // Do NOT add your own tests here. Put your tests in MyTest.java