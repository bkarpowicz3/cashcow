import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class Grid extends GameObj {
    
    public static final int SIZE_X = 400;
    public static final int SIZE_Y = 480;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;
    
    private Bottle[][] board;
    private boolean[][] checked; 
    private static final int rows = 10;
    private static final int cols = 12;
    private Set<Coordinate> chunk;
    private int score;
    
    public Grid(int courtWidth, int courtHeight) { 
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE_X, SIZE_Y, 
                courtWidth, courtHeight);
        
        board = new Bottle[rows][cols];
        checked = new boolean[rows][cols];
        chunk = new TreeSet<Coordinate>();
        
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < cols; j++) {
                board[i][j] = new Bottle(courtWidth, courtHeight, true);
            }
        }
    }
    
    @Override
    public void draw(Graphics g) {
        for(int i = 0; i < rows; i++) { 
            for(int j = 0; j < cols; j++) { 
                Bottle b = board[i][j];
                b.setPx(i * b.getWidth());
                b.setPy(j * b.getHeight());
                b.draw(g);
            }
        }
    }
    
    public synchronized void checkFour(int row, int col, String color) {    
        
        //check to make sure the coordinate is in the image
        if (row < 0) { return; } 
        if (col < 0) { return; } 
        if (row >= rows ) { return; } 
        if (col >= cols) { return; } 
        
        //check to make sure square isn't empty
        if (board[row][col].isEmpty()) { return; }
        
        //soda bottles are handled separately
        if (color.equals("red")) { return; }
        
        //make sure we haven't checked this coordinate yet 
        if (checked[row][col]) { return; }
        
        //make sure this coordinate is the right color
        if(!board[row][col].getColor().toLowerCase().equals(color)) { return; }
        
        //add coordinate to set         
        Coordinate c = new Coordinate(row, col);
        chunk.add(c);
        checked[row][col] = true;
        
        //recursively check surrounding pixels
        checkFour(row+1, col, color);
        checkFour(row-1, col, color);
        checkFour(row, col+1, color);
        checkFour(row, col-1, color);
    }
    
    public synchronized void getChunk(Point p) {
        
        double x = p.getX();
        double y = p.getY();
        int row = (int) (x / 40);
        int col = (int) (y / 40);
        
        if (row > 9) { row = 9; } 
        if (col > 11) { col = 11; } 
        
        //clear any previous data stored in chunk and checked
        chunk = new TreeSet<Coordinate>();
        checked = new boolean[rows][cols];
        
        String c = board[row][col].getColor().toLowerCase();
        
        if(c.equals("red")) { 
            for(int i = 0; i < rows; i++) { 
                Coordinate coord = new Coordinate(i, col);
                chunk.add(coord);
            }
        } else {
            checkFour(row, col, c);
        }
    }
    
    public synchronized void deleteChunk(Point p, int courtWidth, int courtHeight) {
        getChunk(p);
        
        for (Coordinate b : chunk) { 
            int first = b.getX();
            int last = b.getY();

            //if chunk >= 2, replace all the bottles in the chunk with the blank space 
            if (chunk.size() >= 2) { 
                board[first][last] = new Bottle(courtWidth, courtHeight, false);
                score += 1;
            }
            
            //move the bottles above the empty spaces down
            for(int i = rows - 1; i >= 0; i--) { 
                for(int j = cols - 1; j >= 0; j--) { 
                    int minY = Math.max(0, j-1);
                    if(board[i][j].isEmpty()) { 
                        Bottle temp = board[i][minY];
                        board[i][minY] = board[i][j];
                        board[i][minY].setLives(board[i][j].getLives());
                        board[i][j] = temp;    
                        board[i][j].setLives(board[i][minY].getLives());
                    }
                }
            }
            //decrement the cracked bottles if they're in the same column as a bottle in the
            //chunk to be deleted
            decrementCracked(p);
        }
    }
    
    public void decrementCracked(Point p) { 
        double x = p.getX();
        double y = p.getY();
        int row = (int) (x / 40);
        int col = (int) (y / 40);
        
        loop1:
        for(int i = 0; i < cols; i++) { 
            if (board[row][i].isCracked() && i < col) { 
                int lives = board[row][col].getLives();
                board[row][col].setLives(lives - 1);
                System.out.println(lives);
                break loop1;
            }
        }
    }
    
    public boolean gameWon() { 
        if (score >= 100) { 
            return true;
        } else { return false; }
    }
    
    public synchronized boolean gameLost() { 
        boolean answer = false;
        for (int i = 0; i < rows; i++) { 
            for (int j = 0; j < cols; j++) { 
                if (!board[i][j].isEmpty()) { 
                    //check if there are any neighboring matches left - possible moves
                    //if immediate neighbors is not empty, possible moves - game not lost
                    checkFour(i, j, board[i][j].getColor().toLowerCase());
                    if (chunk.size() <= 1) { 
                        answer = true;
                    }
                }
                //check if there are any cracked bottles with zero moves left
                int l = board[i][j].getLives();
                if (l <= 0) { answer = true; } 
            }
        }
        return answer;
    }
    
    public synchronized boolean gameLostCracked() { 
        for (int i = 0; i < rows; i++) { 
            for (int j = 0; j < cols; j++) { 
                int l = board[i][j].getLives();
                if (l <= 0) { return true; } 
            }
        }
        return false;
    }
    
    //----------------------------TESTING FUNCTIONS------------------------------------------------
    
    ///function provided for testing purposes
    public void setBoard(Bottle[][] b) { 
        board = b;
    }
    
    ///function provided for testing purposes
    public Bottle[][] getBoard () { 
        return Arrays.copyOf(board, board.length);
    }
    
    ///function provided for testing purposes 
    public Set<Coordinate> getChunkSet() { 
        Set<Coordinate> s = new TreeSet<Coordinate>();
        s.addAll(chunk);
        return s;
    }
    
    ///function for testing purposes
    public static boolean checkEmpty(Bottle[][] b) { 
        for (int i = 0; i < b.length; i++) { 
            for (int j = 0; j < b[1].length; j++) { 
                if (!b[i][j].isEmpty()) { 
                    return false;
                }
            }  
        }
        return true;
    }
    
    ///function for testing purposes
    public static boolean checkColor(Bottle[][] b, String color) { 
        for (int i = 0; i < b.length; i++) { 
            for (int j = 0; j < b[1].length; j++) { 
                if (!b[i][j].getColor().equals(color)) { 
                    return false;
                }
            }  
        }
        return true;
    }
    
    ///function for testing purposes
     public boolean crackedMapZero() { 
         for (int i = 0; i < rows; i++) { 
             for (int j = 0; j < cols; j++) { 
                 int l = board[i][j].getLives();
                 if (l <= 0) { return true; } 
             }
         }
         return false;
     }
     
     //function for testing purposes
     public int getScore() { 
         int s = this.score;
         return s;
     }
     
     public void setScore(int s) { 
         this.score = s;
     }
    
}
