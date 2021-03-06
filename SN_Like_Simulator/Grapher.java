import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
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
    Color pink = new Color(255, 51, 255);

    Stroke GRAPH_STROKE = new BasicStroke(1f);
    Stroke GRID_STROKE = new BasicStroke(0.25f);
    boolean showGrids = true;
    boolean showLogins = true;
    boolean showPosts = true;
    int numDays = 0;
    int numUsers = 0;
    ArrayList<Integer> loginPoints = new ArrayList<Integer>();
    ArrayList<Integer> postDailyPoints = new ArrayList<Integer>();
    GUIClass guiClass;
    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(GUIClass guiC, ArrayList<Integer> loginList, ArrayList<Integer> postDailyList, int days, 
    int users, boolean showGrid, boolean showLogin, boolean showPost) {
        guiClass = guiC;
        loginPoints = loginList;
        postDailyPoints = postDailyList;
        numDays = days;  
        numUsers = users;
        showGrids = showGrid;
        showLogins = showLogin;
        showPosts = showPost;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        this.setBackground(Color.BLACK);

        g2.setColor(Color.WHITE);
        g2.drawRect(20, 20, 540, 520);

        int highestLoginDay = Collections.max(loginPoints);
        int highestPostDay = Collections.max(postDailyPoints);
        int dayHLD = loginPoints.indexOf(highestLoginDay) + 1;
        int dayHPD = postDailyPoints.indexOf(highestPostDay) + 1;

        String numUsersString = Integer.toString(numUsers);
        String numDaysString = Integer.toString(numDays);
        g2.setFont(new Font("TimesRoman", Font.PLAIN, 10)); 
        g2.drawString(numUsersString, 5, 20);
        g2.drawString("0", 10, 541);
        g2.drawString("1", 17, 550);
        g2.drawString("Number of Days", 260, 550);
        AffineTransform orig = g2.getTransform();
        g2.rotate(Math.toRadians(270));
        g2.drawString("Number of Users",-300,15);
        g2.setTransform(orig);
        g2.drawString("Highest Login Day = " + Integer.toString(dayHLD), 30, 570);
        g2.drawString("Highest Post Day = " + Integer.toString(dayHPD), 150, 570);
        g2.drawString(numDaysString, 560, 550);
        g2.setStroke(GRID_STROKE);
        float scaleDays = (float) 540 / (numDays - 1);
        float scaleUsers = (float) 520 / numUsers;

        if (showGrids) {
            g2.setColor(Color.YELLOW);
            for (int i = 1; i < numDays - 1; i++) {
                g2.draw(new Line2D.Float(20 + (i * scaleDays), 20, 20 + (i * scaleDays), 540)); 
            }
            for (int i = 1; i < numUsers; i++) {
                g2.draw(new Line2D.Float(20, 20 + (i * scaleUsers), 560, 20 + (i * scaleUsers)));
            }
        }
        g2.setStroke(GRAPH_STROKE);
        if (showLogins) {
            for (int i = 0; i < numDays - 1; i++) {
                g2.setColor(lightGreen);
                g2.draw(new Line2D.Float((20 + (i * scaleDays)), 540 - (scaleUsers * loginPoints.get(i)), 
                        (20 + ((i + 1) * scaleDays)), 540 - (scaleUsers * loginPoints.get(i+1))));
                if (i == (dayHLD - 1)) {
                    g2.setColor(pink);
                    Ellipse2D.Double shape = new Ellipse2D.Double((20 + (i * scaleDays)) - 1.5, 
                            (540 - (scaleUsers * loginPoints.get(i))) - 1.5, 3 , 3);
                    g2.draw(shape);
                }
                else {
                    g2.setColor(aqua);
                    Ellipse2D.Double shape = new Ellipse2D.Double((20 + (i * scaleDays)) - 1, 
                            (540 - (scaleUsers * loginPoints.get(i))) - 1, 2 , 2);
                    g2.draw(shape);
                }
            }
            if (numDays == dayHLD) {
                g2.setColor(pink);
                Ellipse2D.Double lastDotLogin = new Ellipse2D.Double((20 + ((numDays-1) * scaleDays)) - 1, 
                        (540 - (scaleUsers * loginPoints.get(numDays-1))) - 1, 2 , 2);
                g2.draw(lastDotLogin);
            }
            else {
                g2.setColor(aqua);
                Ellipse2D.Double lastDotLogin = new Ellipse2D.Double((20 + ((numDays-1) * scaleDays)) - 1, 
                        (540 - (scaleUsers * loginPoints.get(numDays-1))) - 1, 2 , 2);
                g2.draw(lastDotLogin);
            }
        }
        if (showPosts) {
            for (int i = 0; i < numDays - 1; i++) {
                g2.setColor(Color.RED);
                g2.draw(new Line2D.Float((20 + (i * scaleDays)), 540 - (scaleUsers * postDailyPoints.get(i)), 
                        (20 + ((i + 1) * scaleDays)), 540 - (scaleUsers * postDailyPoints.get(i + 1))));

                if (i == (dayHPD - 1)) {
                    g2.setColor(pink);
                    Ellipse2D.Double shape = new Ellipse2D.Double((20 + (i * scaleDays)) - 1.5, 
                            (540 - (scaleUsers * postDailyPoints.get(i))) - 1.5, 3 , 3);
                    g2.draw(shape);
                }
                else {
                    g2.setColor(aqua);
                    Ellipse2D.Double shape = new Ellipse2D.Double((20 + (i * scaleDays)) - 1, 
                            (540 - (scaleUsers * postDailyPoints.get(i))) - 1, 2 , 2);
                    g2.draw(shape);
                }
            }
            if (numDays == dayHPD) {
                g2.setColor(pink);
                Ellipse2D.Double lastDotPost = new Ellipse2D.Double((20 + ((numDays-1) * scaleDays)) - 1, 
                        (540 - (scaleUsers * postDailyPoints.get(numDays-1))) - 1, 2 , 2);
                g2.draw(lastDotPost);
            }
            else {
                g2.setColor(aqua);
                Ellipse2D.Double lastDotPost = new Ellipse2D.Double((20 + ((numDays-1) * scaleDays)) - 1, 
                        (540 - (scaleUsers * postDailyPoints.get(numDays-1))) - 1, 2 , 2);
                g2.draw(lastDotPost);
            }
        }
    }

}
