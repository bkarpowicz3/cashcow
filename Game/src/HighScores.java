import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class HighScores {
    private static List<NDT> scores = new LinkedList<NDT>();
    
    public HighScores() throws IOException {
        Reader r = new FileReader("images/highscores.txt");
        BufferedReader br = new BufferedReader(r);
        
        String line = br.readLine();
        
        while(line != null) { 
            if(!line.isEmpty()) { 
                //get text before first comma - user name
                int comma = line.indexOf(",");
                String user = line.substring(0, comma).trim();
                
                //get text between commas - date 
                int comma2 = line.lastIndexOf(",");
                String date = line.substring(comma+1, comma2).trim();
                
                //get text after last comma - time 
                String time = line.substring(comma2+1, line.length()).trim();
                
                //create new NDT object with this info
                NDT current = new NDT(user, date, time);
                scores.add(current);
                
                line = br.readLine();
            }
        }
        br.close();
        
    }

    public void submitScore(String user, String time) throws IOException, ParseException{ 
        //get today's date
        DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
        String today = dateFormat.format(Calendar.getInstance().getTime());
        
        //create a new NDT object with this information
        NDT current = new NDT(user, today, time);
        
        //create new list 
        BufferedWriter out = new BufferedWriter(new FileWriter("images/highscores.txt"));
        
        //add new element to scores
        scores.add(current);

        //sort the list by overriding the compare method for the NDT class
        Collections.sort(scores, new Comparator<NDT>() {
            @Override
            public int compare(NDT n1, NDT n2) {
                return n1.getTime().compareTo(n2.getTime());
            }
        });
        
        for (NDT n : scores) { 
            String name = n.getName();
            String date = n.getDate();
            String t = n.getTime();
            out.write(name + ", " + date + ", " + t);
            out.newLine();
        }
        out.close();
    }
    
}
