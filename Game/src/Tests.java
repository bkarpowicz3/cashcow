import static org.junit.Assert.*;

import java.awt.Point;
import java.util.Random;

import javax.swing.JFrame;

import org.junit.Test;

public class Tests {
    public static final int COURT_WIDTH = 400;
    public static final int COURT_HEIGHT = 480;

    ////testing Bottle class
    @Test
    public void testCracked() { 
        Bottle b1 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
        Bottle b2 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottlecracked.png");
        Bottle b3 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/yellowbottlecracked.png");
        
        assertTrue("pink is cracked", b2.isCracked());
        assertTrue("yellow is cracked", b3.isCracked());
        assertFalse("blue is not cracked", b1.isCracked());
    }
    
    @Test
    public void testEmpty() {
        Bottle b1 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
        Bottle b2 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/empty.png");
        
        assertTrue("empty is empty", b2.isEmpty());
        assertFalse("blue is not empty", b1.isEmpty());
    }
    
    @Test 
    public void testGetColor() {
        Bottle b1 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
        Bottle b2 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottlecracked.png");
        Bottle b3 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/yellowbottlecracked.png");
        Bottle b4 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/yellowbottle.png");
        Bottle b5 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
        Bottle b6 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/greenbottle.png");
        Bottle b7 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/empty.png");
        Bottle b8 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/soda.png");
        Bottle b9 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/howtoplay.png");
        
        assertEquals("blue bottle is blue", "blue", b1.getColor());
        assertEquals("pink cracked bottle is PINK", "PINK", b2.getColor());
        assertEquals("yellow cracked bottle is YELLOW", "YELLOW", b3.getColor());
        assertEquals("yellow bottle is yellow", "yellow", b4.getColor());
        assertEquals("pink bottle is pink", "pink", b5.getColor());
        assertEquals("green bottle is green", "green", b6.getColor());
        assertEquals("empty bottle is black", "black", b7.getColor());
        assertEquals("soda is red", "red", b8.getColor());
        assertEquals("non bottle image has none", "none", b9.getColor());
    }
    
    @Test
    public void testSoda() {
        Bottle b1 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
        Bottle b2 = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/soda.png");
        
        assertFalse("blue bottle is not soda", b1.isSoda());
        assertTrue("soda is soda", b2.isSoda());
    }
    
    ////testing Grid class
    @Test 
    public void testAllBlueBecomesEmpty() { 
        Bottle[][] b = new Bottle[10][12];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 12; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
            }
        }
        
        Grid g = new Grid(COURT_WIDTH, COURT_HEIGHT);
        g.setBoard(b);
        
        Point p = new Point(120, 120);

        g.deleteChunk(p, COURT_WIDTH, COURT_HEIGHT);
        int size = g.getChunkSet().size();
        assertEquals("chunk is entire board", 120, size);
        
        Bottle[][] b2 = g.getBoard();
        assertTrue("entire board empty after delete", Grid.checkEmpty(b2));
    }
    
    @Test
    public void testHalfBluePinkBottomShifts() { 
        Bottle[][] b = new Bottle[10][12];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 6; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
            }
            for(int j = 6; j < 12; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
            }
        }
        
        Grid g = new Grid(COURT_WIDTH, COURT_HEIGHT);
        g.setBoard(b);
        
        //click in the bottom half of the screen, where it is pink
        Point p = new Point(211, 423);
        
        g.deleteChunk(p, COURT_WIDTH, COURT_HEIGHT);
        int size = g.getChunkSet().size();
        assertEquals("chunk is half the board", 60, size);
        
        Bottle[][] b2 = g.getBoard();
        Bottle[][] toCheck = new Bottle[10][6];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 6; j++) { 
                toCheck[i][j] = b2[i][j];
            }
        }
        
        assertTrue("top half of board is empty", Grid.checkEmpty(toCheck));
        
        Bottle[][] colorCheck = new Bottle[10][6];
        for (int i = 0; i < 10; i++) { 
            for (int j = 6; j < 12; j++) { 
                colorCheck[i][j-6] = b2[i][j];
            }
        }
        
        assertTrue("bottom half is blue", Grid.checkColor(colorCheck, "blue"));
    }
    
    @Test
    public void testSodaBottle() { 
        Bottle[][] b = new Bottle[10][12];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 12; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/greenbottle.png");
            }
        }
        //insert a soda bottle in the position (0,1)
        b[0][1] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/soda.png");
        
        Grid g = new Grid(COURT_WIDTH, COURT_HEIGHT);
        g.setBoard(b);
        
        //click in the position (0,1)
        Point p = new Point(17, 63);
        
        g.deleteChunk(p, COURT_WIDTH, COURT_HEIGHT);
        int size = g.getChunkSet().size();
        assertEquals("chunk is one row", 10, size);
        
        Bottle[][] b2 = g.getBoard();
        Bottle[][] toCheck = new Bottle[10][1];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 1; j++) { 
                toCheck[i][j] = b2[i][j];
            }
        }
        
        assertTrue("top row of board is empty", Grid.checkEmpty(toCheck));
    }
    
    public void testCrackedBottleDeletes() { 
        Bottle[][] b = new Bottle[10][12];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 12; j++) { 
                Random rn = new Random();
                int n = rn.nextInt(1) + 1;
                if (n == 1) { 
                    b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
                } else { 
                    b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottlecracked.png");
                }
            }
        }
        
        Grid g = new Grid(COURT_WIDTH, COURT_HEIGHT);
        g.setBoard(b);
        
        Point p = new Point(120, 120);

        g.deleteChunk(p, COURT_WIDTH, COURT_HEIGHT);
        int size = g.getChunkSet().size();
        assertEquals("chunk is entire board", 120, size);
        
        Bottle[][] b2 = g.getBoard();
        assertTrue("entire board empty after delete", Grid.checkEmpty(b2));
    }
    
    public void testGameWon() { 
        Bottle[][] b = new Bottle[10][12];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 12; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/empty.png");
            }
        }
        
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 7; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
            }
        }
        
        Grid g = new Grid(COURT_WIDTH, COURT_HEIGHT);
        g.setBoard(b);
        
        //click in (0, 1)
        Point p = new Point(17, 63);
        
        g.deleteChunk(p, COURT_WIDTH, COURT_HEIGHT);
        int size = g.getChunkSet().size();
        assertEquals("chunk is 70 parts", 70, size);
        assertEquals("score is 70", 70, g.getScore());
        
        Bottle[][] b2 = g.getBoard();
        assertTrue("entire board empty after delete", Grid.checkEmpty(b2));
        assertTrue("game won", g.gameWon());
        assertFalse("game lost", g.gameLost());
    }
    
    public void testGameLostNoMoves() { 
        Bottle[][] b = new Bottle[10][12];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 12; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/empty.png");
            }
        }
        
        b[4][5] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/greenbottle.png");
        b[8][10] = new  Bottle(COURT_WIDTH, COURT_HEIGHT, "images/yellowbottle.png");
        b[11][11] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/yellowbottlecracked.png");
        
        Grid g = new Grid(COURT_WIDTH, COURT_HEIGHT);
        g.setBoard(b);
        Bottle[][] b2 = g.getBoard();
        
        assertFalse("entire board empty after delete", Grid.checkEmpty(b2));
        assertFalse("game won", g.gameWon());
        assertTrue("game lost", g.gameLost());
        
        assertFalse("cracked bottle has more than 0 lives", g.crackedMapZero());
    }
    
    public void testGameLostCracked() { 
        Bottle[][] b = new Bottle[10][12];
        for (int i = 0; i < 10; i ++) { 
            for (int j = 0; j < 12; j++) { 
                b[i][j] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/empty.png");
            }
        }
        
        //add a cracked bottle with three chunks below it 
        b[0][0] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/yellowbottlecracked.png");
        b[0][1] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/yellowbottle.png");
        b[0][2] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/greenbottle.png");
        b[0][3] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/greenbottle.png");
        b[0][4] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/greenbottle.png");
        b[0][5] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
        b[0][6] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
        b[0][7] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/bluebottle.png");
        b[0][8] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
        b[0][9] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
        b[0][10] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
        b[0][11] = new Bottle(COURT_WIDTH, COURT_HEIGHT, "images/pinkbottle.png");
        
        Grid g = new Grid(COURT_WIDTH, COURT_HEIGHT);
        g.setBoard(b);
        
        //click in the position (0,11)
        Point p = new Point(14, 463);
        g.deleteChunk(p, COURT_WIDTH, COURT_HEIGHT);
        int size = g.getChunkSet().size();
        assertEquals("chunk is 4 bottles", 4, size);
        
        //click in the position (0,11)
        Point p2 = new Point(14, 463);
        g.deleteChunk(p2, COURT_WIDTH, COURT_HEIGHT);
        int size2 = g.getChunkSet().size();
        assertEquals("chunk is 3 bottles", 3, size2);
        
        //click in the position (0,11)
        Point p3 = new Point(14, 463);
        g.deleteChunk(p3, COURT_WIDTH, COURT_HEIGHT);
        int size3 = g.getChunkSet().size();
        assertEquals("chunk is 3 bottles", 3, size3);
        
        Bottle[][] b2 = g.getBoard();
        
        assertFalse("bottle is in position (11, 11)", b2[11][11].isEmpty());
        assertEquals("bottle in position (11, 11) is yellow cracked", "YELLOW", 
                b2[11][11].getColor());
        assertFalse("entire board empty after delete", Grid.checkEmpty(b2));
        assertFalse("game won", g.gameWon());
        assertTrue("game lost", g.gameLost());
        assertTrue("game lost due to cracked bottle", g.gameLostCracked());
        
        assertTrue("one of the cracked bottles has 0 lives left", g.crackedMapZero());
    }
    
}
