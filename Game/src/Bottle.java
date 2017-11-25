import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Bottle extends GameObj implements Comparable<Bottle> {
    
    public static final String BLUE_BOTTLE = "images/bluebottle.png";
    public static final String PINK_BOTTLE = "images/pinkbottle.png";
    public static final String YELLOW_BOTTLE = "images/yellowbottle.png";
    public static final String GREEN_BOTTLE = "images/greenbottle.png";
    public static final String SODA = "images/soda.png";
    public static final String PINK_CRACKED = "images/pinkbottlecracked.png";
    public static final String YELLOW_CRACKED = "images/yellowbottlecracked.png";
    public static final String EMPTY = "images/empty.png";
    
    public static final int SIZE = 40;
    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    private BufferedImage img;
    private String color;
    private int lives = 3;
    
    public Bottle(int courtWidth, int courtHeight, boolean full) { 
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);

        try {
            if (img == null) {
                if (full) { 
                    //generate a random number from 1 to 10
                    Random rn = new Random();
                    int n = rn.nextInt(9) + 1;
                    
                    if(n == 1 || n == 5) { 
                        img = ImageIO.read(new File(BLUE_BOTTLE)); 
                        color = "blue";
                    } else if (n == 2 || n == 6) { 
                        img = ImageIO.read(new File(PINK_BOTTLE)); 
                        color = "pink";
                    } else if (n == 3 || n == 7) { 
                        img = ImageIO.read(new File(YELLOW_BOTTLE)); 
                        color = "yellow";
                    } else if (n == 4 || n == 8) { 
                        img = ImageIO.read(new File(GREEN_BOTTLE)); 
                        color = "green";
                    } else{
                        Random r = new Random();
                        int x = r.nextInt(7) + 1;
                        
                        if (x == 1) { 
                            img = ImageIO.read(new File(BLUE_BOTTLE)); 
                            color = "blue";
                        } else if (x == 2) { 
                            img = ImageIO.read(new File(PINK_BOTTLE)); 
                            color = "pink";
                        } else if (x == 3) { 
                            img = ImageIO.read(new File(YELLOW_BOTTLE)); 
                            color = "yellow";
                        } else if (x == 4) { 
                            img = ImageIO.read(new File(GREEN_BOTTLE)); 
                            color = "green";
                        } else if (x == 5) { 
                            img = ImageIO.read(new File(PINK_CRACKED)); 
                            color = "PINK";
                        } else if (x == 6){ 
                            img = ImageIO.read(new File(YELLOW_CRACKED)); 
                            color = "YELLOW";
                        } else { 
                            img = ImageIO.read(new File(SODA)); 
                            color = "red";
                        }
                    }   
                } else { 
                    img = ImageIO.read(new File(EMPTY)); 
                    color = "black";
                }
            }
        } catch (IOException e) {
            System.out.println("Internal Error: " + e.getMessage());
        }
    }
    
    public Bottle(int courtWidth, int courtHeight, String imageFile) { 
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        
        try {
            if (img == null) {
                img = ImageIO.read(new File(imageFile));
                if (imageFile.equals(YELLOW_BOTTLE)) { 
                    color = "yellow"; 
                } else if (imageFile.equals(YELLOW_CRACKED)) { 
                    color = "YELLOW";
                } else if (imageFile.equals(PINK_BOTTLE)) { 
                    color = "pink";
                } else if (imageFile.equals(PINK_CRACKED)) { 
                    color = "PINK";
                } else if (imageFile.equals(BLUE_BOTTLE)) { 
                    color = "blue";
                } else if (imageFile.equals(GREEN_BOTTLE)) { 
                    color = "green";
                } else if (imageFile.equals(EMPTY)) { 
                    color = "black";
                } else if (imageFile.equals(SODA)) { 
                    color = "red";
                } else { 
                    color = "none";
                }
            }
        } catch (IOException e){ 
            System.out.println("Internal Error: " + e.getMessage());
        }
            
    }
    
    public int getLives() { 
        int l = lives;
        return l;
    }
    
    public void setLives(int l) { 
        lives = l;
    }
    
    public boolean isCracked() { 
        return color.equals("PINK") || color.equals("YELLOW");
    }
    
    public boolean isSoda() { 
        return color.equals("red");
    }
    
    public boolean isEmpty() { 
        return color.equals("black");
    }
    
    public String getColor() {
        String c = this.color;
        return c;
    }
    
    @Override
    public void draw(Graphics g) {
        g.drawImage(img, this.getPx(), this.getPy(), this.getWidth(), this.getHeight(), null);
    }
    
    @Override
    public int compareTo(Bottle b) {
        return this.color.compareTo(b.color);
    }

}
