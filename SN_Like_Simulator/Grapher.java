import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;


/**
 * Write a description of class Grapher here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Grapher extends JPanel {

    JFrame graphPanel;
    JLabel dummyLabel;
    Color lightGreen = new Color(204, 255, 153);
    Color aqua = new Color(0, 255, 255);

    Stroke GRAPH_STROKE = new BasicStroke(1f);

    int numDays = 0;
    int numUsers = 0;
    ArrayList<Integer> loginPoints = new ArrayList<Integer>();
    ArrayList<Integer> postDailyPoints = new ArrayList<Integer>();
    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(ArrayList<Integer> loginList, ArrayList<Integer> postDailyList, int days, int users) {
        loginPoints = loginList;
        postDailyPoints = postDailyList;
        numDays = days;  
        numUsers = users;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(Color.WHITE);

        g2.setColor(Color.BLACK);
        g2.fillRect(0, 0, 400, 400);
        
        //g2.setColor(Color.WHITE);
        //g2.drawRect(10, 10, 390, 390)

        g2.setStroke(GRAPH_STROKE);
        float scaleDays = (float) 400 / (numDays - 1);
        float scaleUsers = (float) 400 / numUsers;
        //int scalePosts = 400 / numPosts;
        for (int i = 0; i < numDays - 1; i++) {
            g2.setColor(lightGreen);
            g2.draw(new Line2D.Float(i * scaleDays, 400 - (scaleUsers * loginPoints.get(i)), 
                    (i + 1) * scaleDays, 400 - (scaleUsers * loginPoints.get(i+1))));
            g2.setColor(aqua);
            Ellipse2D.Double shape = new Ellipse2D.Double((i * scaleDays) - 1, 
                    (400 - (scaleUsers * loginPoints.get(i))) - 1, 2 , 2);
            g2.draw(shape);
        }

        for (int i = 0; i < numDays - 1; i++) {
            g2.setColor(Color.RED);
            g2.draw(new Line2D.Float(i * scaleDays, 400 - (scaleUsers * postDailyPoints.get(i)), 
                    (i + 1) * scaleDays, 400 - (scaleUsers * postDailyPoints.get(i + 1))));
            g2.setColor(aqua);
            Ellipse2D.Double shape = new Ellipse2D.Double((i * scaleDays) - 1, 
                    (400 - (scaleUsers * postDailyPoints.get(i))) - 1, 2 , 2);
            g2.draw(shape);
        }
        /*for (int i = 0; i < numDays - 1; i++) {
        g.drawLine(i * scale, 400 - loginPoints.get(i), (i + 1) * scale, 400 - loginPoints.get(i+1));
        }*/
    }

}
