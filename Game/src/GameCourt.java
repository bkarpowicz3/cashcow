import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;

public class GameCourt extends JPanel {

    public static Grid board = new Grid(400, 480);
    public boolean playing = false; // whether the game is running 
    public boolean winning;
    public boolean cracked_loss;
    
    public static final int INTERVAL = 2000;
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 480;
    
    public GameCourt() { 
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }
    
    public void reset() {
        board = new Grid(COURT_WIDTH, COURT_HEIGHT);
        playing = true;
    }
    
    public static JFrame showLost() { 
        final JFrame lost = new JFrame("You Lose!");
        lost.setLocation(300,300);
        
        ImageIcon im = new ImageIcon("images/losingscreen.png"); 
        Image im2 = im.getImage();  
        Image use = im2.getScaledInstance(600, 300, Image.SCALE_SMOOTH); 
        im = new ImageIcon(use);
        lost.add(new JLabel(im));
        
        lost.pack();
        lost.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        lost.setVisible(true);
        
        return lost;
    }
    
    public static JFrame showCrackedLost() { 
        final JFrame cracked = new JFrame("Your Bottle Cracked!");
        cracked.setLocation(300,300);
        
        ImageIcon im = new ImageIcon("images/crackedscreen.png"); 
        Image im2 = im.getImage();  
        Image use = im2.getScaledInstance(600, 300, Image.SCALE_SMOOTH); 
        im = new ImageIcon(use);
        cracked.add(new JLabel(im));
        
        cracked.pack();
        cracked.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        cracked.setVisible(true);
        
        return cracked;
    }
    
    public static JFrame showWon() { 
        final JFrame won = new JFrame("You Win!");
        won.setLocation(300,300);
        
        ImageIcon im = new ImageIcon("images/winningscreen.png"); 
        Image im2 = im.getImage();  
        Image use = im2.getScaledInstance(600, 300, Image.SCALE_SMOOTH); 
        im = new ImageIcon(use);
        won.add(new JLabel(im));
        
        won.pack();
        won.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        won.setVisible(true);
        
        return won;
    }
    
    public JFrame statusScreen() { 
        if (!playing && !winning && !cracked_loss) { 
            JFrame lost = showLost();
            return lost;
        }
        if (!playing && winning) { 
            JFrame won = showWon();
            return won;
        } 
        if (!playing && !winning && cracked_loss) { 
            JFrame cracked = showCrackedLost();
            return cracked;
        } else { 
            return null;
        }
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        board.draw(g);
    }
    
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
     
}
