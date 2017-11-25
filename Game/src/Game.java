/**
 * CIS 120 Game HW
 * (c) University of Pennsylvania
 * @version 2.1, Apr 2017
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
    public void run() {
        // NOTE : recall that the 'final' keyword notes immutability even for local variables.

        // Top-level frame in which game components live
        // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Cash Cow");
        frame.setLocation(300, 300);
        frame.getContentPane().setPreferredSize(new Dimension(400, 600));

        // create count up timer as label on bottom of game
        // return label 
        final JLabel text = CountUp.createCounter(frame);

        // Main playing area
        final GameCourt court = new GameCourt();
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        control_panel.setLayout(new BorderLayout());
        frame.add(control_panel, BorderLayout.NORTH);

        //Add a progress bar that shows the score
        final JPanel progress_panel = new JPanel();
        final JProgressBar progressBar = new JProgressBar();
        progressBar.setValue(GameCourt.board.getScore());
        progressBar.setStringPainted(true);
        Border border = BorderFactory.createTitledBorder("Collecting Milk...");
        progressBar.setBorder(border);
        progress_panel.add(progressBar);
        control_panel.add(progress_panel, BorderLayout.SOUTH);
        
        // Note here that when we add an action listener to the reset button, we define it as an
        // anonymous inner class that is an instance of ActionListener with its actionPerformed()
        // method overridden. When the button is pressed, actionPerformed() will be called.
        final JPanel reset_panel = new JPanel();
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                court.reset();
                CountUp.resetTimer(text, frame);
                GameCourt.board.setScore(0);
                progressBar.setValue(GameCourt.board.getScore());
            }
        });
        reset_panel.add(reset);
        control_panel.add(reset_panel);
        
        // Add the instructional screen
        final JPanel help_panel = new JPanel();
        final JButton howTo = new JButton("How To Play");
        howTo.addActionListener(new ActionListener () { 
            public void actionPerformed(ActionEvent e) { 
                final JFrame help = new JFrame("How To Play");
                help.setLocation(300,300);
                
                ImageIcon im = new ImageIcon("images/howtoplay.png"); 
                Image im2 = im.getImage();  
                Image use = im2.getScaledInstance(600, 300, Image.SCALE_SMOOTH); 
                im = new ImageIcon(use);
                help.add(new JLabel(im));
                
                help.pack();
                help.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                help.setVisible(true);
            }
        });
        help_panel.add(howTo);
        control_panel.add(help_panel, BorderLayout.WEST);
        
        //Add the high score feature
        final JPanel score_panel = new JPanel();
        final JButton scoreButton = new JButton("Submit Score");
        //Prepare the milk icon
        final ImageIcon icon = new ImageIcon("images/milkicon.png");
        Image img = icon.getImage() ;  
        Image img2 = img.getScaledInstance(40, 80, Image.SCALE_SMOOTH ) ;  
        final ImageIcon icon2 = new ImageIcon(img2);
        
        scoreButton.addActionListener(new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                //if you win, you can submit your score
                if(court.winning) { 
                    String time = CountUp.timeElapsed();

                    String user = (String) JOptionPane.showInputDialog(frame, 
                            "Input your username: ", "Submit Score", 
                            JOptionPane.PLAIN_MESSAGE, icon2, null, null);
                    
                    try {
                        HighScores hs = new HighScores();
                        hs.submitScore(user, time);
                        
                        JTextArea displayScore = new JTextArea();
                        BufferedReader in = new BufferedReader(new FileReader("images/highscores.txt"));
                        String line = in.readLine();
                        while(line != null){
                          displayScore.append(line + "\n");
                          line = in.readLine();
                        }
                        in.close();
                        
                        displayScore.setEditable(false);
                        displayScore.setVisible(true);
                        
                        final JFrame scoreFrame = new JFrame("High Scores");
                        scoreFrame.add(displayScore);
                        scoreFrame.pack();
                        scoreFrame.setLocationRelativeTo(null); 
                        scoreFrame.setVisible(true);
                        scoreFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
                    } catch (Exception e1) { 
                        System.out.println("Internal Error: " + e1.getMessage());
                    }
                //otherwise you are warned that you must win to submit your score
                } else { 
                    JOptionPane.showMessageDialog(frame, 
                            "You must win the game to submit your score.", "Sorry!",
                            JOptionPane.WARNING_MESSAGE, icon2);
                }
            }
        });
        score_panel.add(scoreButton);
        control_panel.add(score_panel, BorderLayout.EAST);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        class Mouse extends MouseAdapter implements MouseListener { 
            private Point p;
        
            @Override
            public void mouseReleased(MouseEvent e) {
                if(court.playing) { 
                    p = e.getPoint();
                    GameCourt.board.deleteChunk(p, 400, 480);
                    court.repaint(); 
                    progressBar.setValue(GameCourt.board.getScore());
                }
                
                if (GameCourt.board.gameLost()) { 
                    court.playing = false; 
                    court.winning = false; }
                
                if(GameCourt.board.gameLostCracked()) { 
                    court.cracked_loss = true;
                }
          
                if(GameCourt.board.gameWon()) { 
                    court.playing = false;
                    court.winning = true; }
                
                court.statusScreen();
            }
        }
        
        court.addMouseListener(new Mouse());

        // Start game
        court.reset();
    }

    /**
     * Main method run to start and run the game. Initializes the GUI elements specified in Game and
     * runs it. IMPORTANT: Do NOT delete! You MUST include this in your final submission.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Game());
    }
}