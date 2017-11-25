
public class NDT {
    private String name;
    private String date;
    private String time;
    
    public NDT(String n, String d, String t) { 
        this.name = n;
        this.date = d;
        this.time = t;
    }
    
    public String getName() { 
        String n = name;
        return n;
    }
    
    public String getDate() { 
        String d = date;
        return d;
    }
    
    public String getTime() { 
        String t = time;
        return t;
    }
    
    public void setName(String n) { 
        this.name = n;
    }
    
    public void setDate(String d) { 
        this.date = d;
    }
    
    public void setTime(String t) {
        this.time = t;
    }
}
