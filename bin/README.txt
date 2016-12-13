=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an approprate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D arrays
  	I needed 2D arrays to represent the list of CandyProducers, as they were to be displayed
  	in a 2D fashion on the screen and were of different types. You can see the grid to the right
  	It allows me to traverse the candy producers easily and represent them in an organized way by
  	type of candy producer.

  2. Collections
  	I needed Collections to store the amount of Candy (the little cookies), as the Candies were of
  	class type Candy and could not be stored and easily traversed except by a collection. I used
  	a TreeMap specifically because the Candy id was specific and non repeated and allowed for easy
  	deletion when I needed to purchase a candyproducer as well as easy addition for every new candy
  	created.

  3. Inheritance
  	Inheritance was a core concept in my implementation and defined my entire program. Everything was
  	organized into classes - the candyProducers, the candies, and the other objects in my game such
  	as the big clicker. I used inheritance to define specific-level functionality of types of candy
  	Producers, for example, and also made use of overarching methods for all candy producers. This 
  	applied to all the classes I made that allowed me to have a more structured program and modular
  	design.

  4. Testing
  	Java doesn't have easily implementable GUITests, which would have been amazing for this, but I
  	made good use of JUnit tests to test instantiation and the effects that it would have on the game.
  	I also tested the methods in the GameCourt that were most complex and were core functionality. Without
  	Running the game, I had a good idea that the game was working well by testing these methods.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
	Candy:
		This was the class for the little candies, and was mainly an image and inherited from GameObj.
		I did this inheritance because the candies were then easily drawable and could be placed through
		the super method of GameObj. There were many of these, and so I kept the data storage within each
		one to a minimum.
	CandyProducer (and all of it's extension classes like Grandma, Chef e.t.c.)
		This class allowed for the creation of timers that would incrememnt the candy count every specified
		amount and would interface with the main program. The extended classes used these main method but
		had specific constructors that allowed for differentiation between the candy producers and more
		organization
	GameCourt:
		The main place where things happened. Was a giant JFrame that impelemented all the core functionality
		for the game. Everything took this court as an argument to call its methods.
	Game:
		This was the overarching JFrame that allowed for the creation of smaller JFrames and more organization.
		Also, this is where the runnable main method was implemented.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
  	Not so many - it took a while to figure out the layouts and to organize them well. Also, it was difficult
  	to make sure candies and candy producers had unique id's when getting created and destroyed


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.


