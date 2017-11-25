
public class Coordinate implements Comparable<Coordinate> {
    private int x;
    private int y;
    
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public int getX() { 
        return this.x; 
    }
    
    public int getY() { 
        return this.y; 
    }

    public void setX(int x) { 
        this.x = x; 
    }
    
    public void setY(int y) { 
        this.y = y; 
    }

    @Override
    public int compareTo(Coordinate c) {
        if (y < c.y) {
            return -1;
        }
        if (y > c.y) {
            return 1;
        }
        if (x < c.x) {
            return -1;
        }
        if (x > c.x) {
            return 1;
        }
        return 0;
    }

}
