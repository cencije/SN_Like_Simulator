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
    Stroke GRID_STROKE = new BasicStroke(0.25f);
    boolean showGrids = true;
    int numDays = 0;
    int numUsers = 0;
    ArrayList<Integer> loginPoints = new ArrayList<Integer>();
    ArrayList<Integer> postDailyPoints = new ArrayList<Integer>();
    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(ArrayList<Integer> loginList, ArrayList<Integer> postDailyList, int days, 
                   int users, boolean showGrid) {
        loginPoints = loginList;
        postDailyPoints = postDailyList;
        numDays = days;  
        numUsers = users;
        showGrids = showGrid;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(Color.BLACK);

        g2.setColor(Color.WHITE);
        g2.drawRect(20, 20, 340, 320);

        String numUsersString = Integer.toString(numUsers);
        String numDaysString = Integer.toString(numDays);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
        g2.drawString(numUsersString, 5, 20);
        g2.drawString("0", 10, 341);
        g2.drawString("0", 17, 350);
        g2.drawString(numDaysString, 360, 350);
        g2.setStroke(GRID_STROKE);
        float scaleDays = (float) 340 / (numDays - 1);
        float scaleUsers = (float) 320 / numUsers;
        //int scalePosts = 400 / numPosts;
        
        if (showGrids) {
            g2.setColor(Color.YELLOW);
            for (int i = 1; i < numDays - 1; i++) {
                g2.draw(new Line2D.Float(20 + (i * scaleDays), 20, 20 + (i * scaleDays), 340)); 

            }
            for (int i = 1; i < numUsers - 1; i++) {
                g2.draw(new Line2D.Float(20, 20 + (i * scaleUsers), 360, 20 + (i * scaleUsers)));
            }
        }
        g2.setStroke(GRAPH_STROKE);
        for (int i = 0; i < numDays - 1; i++) {
            g2.setColor(lightGreen);
            g2.draw(new Line2D.Float((20 + (i * scaleDays)), 340 - (scaleUsers * loginPoints.get(i)), 
                    (20 + ((i + 1) * scaleDays)), 340 - (scaleUsers * loginPoints.get(i+1))));
            g2.setColor(aqua);
            Ellipse2D.Double shape = new Ellipse2D.Double((20 + (i * scaleDays)) - 1, 
                    (340 - (scaleUsers * loginPoints.get(i))) - 1, 2 , 2);
            g2.draw(shape);
        }
        Ellipse2D.Double lastDotLogin = new Ellipse2D.Double((20 + ((numDays-1) * scaleDays)) - 1, 
                (340 - (scaleUsers * loginPoints.get(numDays-1))) - 1, 2 , 2);
        g2.draw(lastDotLogin);
        for (int i = 0; i < numDays - 1; i++) {
            g2.setColor(Color.RED);
            g2.draw(new Line2D.Float((20 + (i * scaleDays)), 340 - (scaleUsers * postDailyPoints.get(i)), 
                    (20 + ((i + 1) * scaleDays)), 340 - (scaleUsers * postDailyPoints.get(i + 1))));
            g2.setColor(aqua);
            Ellipse2D.Double shape = new Ellipse2D.Double((20 + (i * scaleDays)) - 1, 
                    (340 - (scaleUsers * postDailyPoints.get(i))) - 1, 2 , 2);
            g2.draw(shape);
        }

        Ellipse2D.Double lastDotPost = new Ellipse2D.Double((20 + ((numDays-1) * scaleDays)) - 1, 
                (340 - (scaleUsers * postDailyPoints.get(numDays-1))) - 1, 2 , 2);
        g2.draw(lastDotPost);
        /*for (int i = 0; i < numDays - 1; i++) {
        g.drawLine(i * scale, 400 - loginPoints.get(i), (i + 1) * scale, 400 - loginPoints.get(i+1));
        }*/
    }

}
