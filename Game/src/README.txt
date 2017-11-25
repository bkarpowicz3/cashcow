=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: bkarp
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. 2D Arrays 
  I used a 2D array that is 10 blocks wide by 12 blocks high to represent the game board.  
  At the start of the game, the array is randomized to hold a different Bottle object.
  Upon a mouse click, a recursive function searches through the array to collect the indices of 
  bottles that are the same color in all four directions.  It then removes all of these bottles by 
  replacing them with empty black spaces and iterating through the array to shift the black spaces 
  to the top of the game board. Since the board is visually an array, it makes sense to represent 
  this game component as an array programmatically as well.  It offers the ability to store 
  individual objects in each array component and iterate through them rather than creating one 
  cohesive image with less convenient methods of access.

  2. I/O
  I used I/O to offer a "submit score" feature to users who win the game.  The game is won when 100 
  bottles are cleared, so scores were sorted by the amount of time taken to reach this marker.  A 
  BufferedReader is first used to collect the lines of data, which contain the name, date, and 
  time/score.  These components are separated and stored in an ArrayList of NDT objects (for easy 
  access to the Strings).  Then, in a separate method, an additional NDT object is added to this list,
  the list is sorted in ascending order by time, and a BufferedWriter is used to write the ArrayList 
  back into the same text file (so that old scores can be loaded in again the next time a user 
  plays). Finally, another BufferedReader is used to add the text to a JTextArea, which appears in 
  another JFrame after a user submits their score.

  3. JUnit Testing
  Since it is very difficult to ensure that all possible scenarios are handled using simple game
  play and a randomly-generated board, I used JUnit Testing to ensure that the Bottle and Grid class
  logic would act as expected in various scenarios.  I implemented a number of functions specifically
  useful for testing purposes including:  getters and setters for the game board and game score; 
  a getter for the "chunk" of coordinates to be removed; and checkers for empty bottles, bottle 
  color, and status of cracked bottles.  Using these functions, I wrote test cases for the Bottle 
  class that ensured that cracked bottles, empty bottles, soda cans, and the color of bottles could
  be properly identified.  For the Grid class, I modeled scenarios including:  the deletion of the 
  entire board (to ensure that the recursion worked properly), the deletion of the bottom half of 
  the board (to ensure that the shifting of bottles worked properly), the deletion of a row via a 
  soda can, the deletion of a chunk including a cracked bottle (to make sure that it was identified
  as being the same color), the attainment of a the winning state, the attainment of the losing state
  by running out of possible moves, and the attainment of the losing state by running out of turns 
  on a cracked bottle.

  4. Recursion
  In order to identify the bottles of the same color surrounding the bottle that is clicked on 
  (the chunk of bottles to be deleted), a recursive algorithm was necessary.  The algorithm takes 
  in the row and column indices of the point that is clicked on as well as the color of the bottle 
  that is clicked on for comparison.  The algorithm is designed such that it returns if the 
  point it is passed is out of bounds, if the bottle it reaches is empty, if the bottle is a soda
  can (these are handled separately, since they delete the row they are contained in regardless of
  color), if the coordinate has already been checked, or if the color does not match.  If the color 
  does match, the coordinate is added to a set and it is marked as checked.  The algorithm is called 
  on the positions above, below, to the left of, and to the right of each of the index it is passed
  such that it fans out from the original point to get the chunk that needs to be removed from the 
  board.  A recursive algorithm is necessary here because since the board is randomized, it is 
  impossible to otherwise determine the exact size in any direction that the chunk needs to extend. 
  This function is called by a parent function on every mouseclick the user makes.


=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.
  
  Bottle - Creates the crucial game object from a BufferedImage.  Also stores a string as a field
  so that colors can be recorded.  Cracked bottles contain strings in the same color in capital letters
  so that they can be both compared and differentiated.  Soda cans and empty images held distinct
  colors as strings so that they would stand out.  In the constructor of the Bottle, the image that 
  is selected is randomly chosen using a number generated by the Random class. This is actually done
  twice so that the cracked bottles and soda bottles show up less frequently than the regular 
  bottles.  The function also contains an alternative constructor for testing purposes as well as 
  functions that return booleans for cracked bottles, soda bottles, and empty spaces and a getter
  for the color of the bottle.a
  
  Coordinate - Used by the Grid class to store the indices of the array that need to be deleted and 
  the positions of the cracked bottles. Has both an x and y field that refers to the parts of these 
  indices. Includes getters and setters for the x and y positions and implements Comparable.
  
  CountUp - Creates the count up timer that is displayed as the JLabel at the bottom of the game
  window.  Also provides a function for obtaining the time displayed by the timer for use in scoring
  and for resetting the timer back to zero.
  
  Direction - Provided class - Used in GameObj.  Necessary so that extension of GameObj can be 
  used here, but not directly used in game implementation.
  
  Game - Responsible for the complete design of the GUI and for calling the functions that are 
  implemented when buttons are clicked (How To Play, Reset, Submit Score).  Also calls the function
  that creates the CountUp timer and the progress bar.  Finally, adds the MouseListener to the court
  area and calls all logical functions from Grid and GameCourt (as well as updating the public 
  booleans of the GameCourt class) upon each mouse click after obtaining the point of the click.
  
  GameCourt - Holds the game board and creates the game court playing area.  Also responsible for 
  the reset function as well as multiple booleans that model game state.  Holds the functions that 
  display the won/lost windows as well as the function that decides which of these windows to display.
  
  GameObj - Provided class - both Bottle and Grid extend GameObj.  This was used mostly for drawing
  purposes so that the position and size of each could be easily obtained, since none of the 
  components have any velocity.  The provided getters and setters were helpful here.
  
  Grid - Created the game board and implemented all involved logic - recursively searching the board 
  for bottles of a matching color, deleting these bottles from the board and adjusting the board
  appropriately, keeping track of cracked bottles and soda cans, incrementing a score, 
  and modeling game state by checking whether the game is won (goal score [100] reached) or lost 
  (no more moves left or a cracked bottle has been decremented to 0 - "burst").
  
  HighScores - Used I/O to read/write high score data to a text file upon command.
  
  NDT - Stored user name, date, and time in one object for easy setting/retrieval for high score 
  feature.
  
  Tests - Utilized JUnit testing to simulate various game scenarios and ensure that algorithms/logic 
  functioned as desired.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

  One substantial stumbling block that I encountered when implementing my game was the logic 
  required to manipulate the game board.  Identifying the chunks of bottles to be deleted required
  a recursive algorithm for which many cases would cause the method to return.  Eventually, it was 
  discovered that it was important to keep a record of whether or not this index had already been 
  checked by the method - this was done using another 2D array of booleans where true was 
  checked and false was unchecked.  This array had to be cleared every time the function was called.
  Another stumbling block I encountered was the ability to decrement the lives of the cracked 
  bottles.  I originally did this using a map storing coordinates to integers, but since the 
  coordinates could not be updated easily, this generated many "false alarms" of cracked bottles 
  exploding.  Instead, I added a parameter to the bottle class and decremented the lives of only 
  the cracked bottles each time the bottle fell.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I believe that in my logical classes, my functionality is separated smoothly between Bottle, Grid, 
  Coordinate, HighScores, and NDT.  The getters and setters that I implement in these classes 
  do implement private state encapsulation.  When I begin to get into the components of the GUI - 
  CountUp, Game, GameCourt - the necessity to pass many of the parameters between the three 
  required a lot of public variables and the returning of JComponents that does not demonstrate 
  complete separation or encapsulation.  If given the chance, I would refactor the GameCourt 
  and Game classes because it is entirely possible that without my implementation of a timer, 
  the GameCourt class is unnecessary or could be implemented separately with some of its methods 
  contained elsewhere - this would allow the variables to be private rather than public and 
  ensure better encapsulation.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
  
    Images:
    Game images (bottle tiles and How to Play image) taken from original Cash Cow 2 app.
    Losing screen - https://alpha.wallhaven.cc/wallpaper/290295
    Winning screen - https://www.freevector.com/cartoon-cow-pattern-vector-18636
    Cracked milk bottle screen - http://vectorgoods.com/milk-vector/
    Icon png - https://clipartfest.com/categories/view/bd8f6e195454ec9b81d86cdb8c0f4be099f27212/
                        png-milk-clipart.html
                        
    Libraries:
    JavaDocs for Swing components - JFrame, JLabel, JPanel, JProgressBar, JButton, ImageIcon, 
        JTextArea, & JOptionPane 
        via https://docs.oracle.com/javase/tutorial/uiswing/components/index.html
    JavaDocs for Random (to randomize game board), Timer (to implement a count-up timer), and
    DateFormat + SimpleDateFormat + ParseException (to go between dates/times and strings)
    

