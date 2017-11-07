import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
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
    
    ArrayList<Integer> loginPoints = new ArrayList<Integer>();
    /**
     * Constructor for objects of class Grapher
     */
    public Grapher(ArrayList<Integer> pointList, int days) {
        loginPoints = pointList;
        numDays = days;  
    }

    public void createGUI() {

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.setBackground(Color.WHITE);
        
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 400, 400);
        
        g.setColor(lightGreen);
        int scale = 400 / numDays;
        for (int i = 0; i < numDays - 1; i++) {
            g.drawLine(i * scale, 400 - loginPoints.get(i), (i + 1) * scale, 400 - loginPoints.get(i+1));
        }

        
   
    }

}
