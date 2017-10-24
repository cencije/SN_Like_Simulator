import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import javax.swing.plaf.LayerUI;
import javax.swing.text.JTextComponent;

/**
 * Write a description of class GUIClass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIClass extends JPanel implements ActionListener
{
    JFrame mainFrame;
    JButton btnParameters, btnRun;
    JLabel dummyLabel;
    Color lightGreen = new Color(204, 255, 153);
    Color aqua = new Color(0, 255, 255);
    int perfectHP, perfectAtk, perfectDef, perfectSDef, perfectSAtk, perfectSpeed;
    Random rand;
    /**
     * Constructor for objects of class GUIClass
     */
    public GUIClass()
    {
       rand = new Random();
    }

    
    public void makeGUI() {
        mainFrame = new JFrame();
        mainFrame.getContentPane().setBackground(Color.BLACK);
        mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aqua));
        mainFrame.setBounds(0,0,300,300);
        mainFrame.getContentPane().setLayout(new BorderLayout());
        mainFrame.setResizable(false);
        mainFrame.setTitle("Social Network Like Simulator");
        
        btnRun = new JButton("Run");
        btnRun.setBounds(130,1,60,30);
        btnRun.setForeground(Color.BLACK);
        btnRun.addActionListener(this);
        btnRun.setEnabled(true);
        mainFrame.add(btnRun);
        
        btnParameters = new JButton("Set Parameters");
        btnParameters.setBounds(1,1,120,30);
        btnParameters.setForeground(Color.BLACK);
        btnParameters.addActionListener(this);
        btnParameters.setEnabled(true);
        mainFrame.add(btnParameters);
        
        
        dummyLabel = new JLabel("");
        mainFrame.add(dummyLabel);
        
        mainFrame.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent evt) {
        int timesTried = 0;
        if (evt.getActionCommand().equals("Run")) {
            btnRun.setEnabled(false);
        }
        if (evt.getActionCommand().equals("Set Parameters")) {
            btnRun.setEnabled(false);
        }
    }

}
