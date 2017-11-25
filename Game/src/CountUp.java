import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CountUp {
    private static long start;
    
    public CountUp() { 
        start = System.currentTimeMillis(); // time right now
    }
    
    public static String timeElapsed() { 
        long now = System.currentTimeMillis();
        long elapsed = now - start;
        
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        return sdf.format(elapsed);
    }
    
    public static JLabel createCounter(JFrame frame) {
     // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel();
    // Timer as label    
        final CountUp counter = new CountUp();
        Timer timer = new Timer(1, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String t = timeElapsed();
                status.setText(t);
                status_panel.add(status);
            }
        });
        timer.start(); 
        return status;
    }
    
    public static JLabel resetTimer(JLabel label, JFrame frame) { 
       label = createCounter(frame);
       return label;
    }
}
