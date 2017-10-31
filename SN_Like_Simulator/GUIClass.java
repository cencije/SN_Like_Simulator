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
    JTextField tfUsers, tfDays, tfAvgLogin, tfYears;
    JLabel lblUsers, lblDays, dummyLabel;
    Color lightGreen = new Color(204, 255, 153);
    Color aqua = new Color(0, 255, 255);
    int days, users;
    Random rand;

    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<Post> postList = new ArrayList<Post>();

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

        btnParameters = new JButton("Set Parameters");
        btnParameters.setBounds(1,1,120,30);
        btnParameters.setForeground(Color.BLACK);
        btnParameters.addActionListener(this);
        btnParameters.setEnabled(true);
        mainFrame.add(btnParameters);

        btnRun = new JButton("Run");
        btnRun.setBounds(130,1,60,30);
        btnRun.setForeground(Color.BLACK);
        btnRun.addActionListener(this);
        btnRun.setEnabled(false);
        mainFrame.add(btnRun);

        lblUsers = new JLabel("Number of Users:");
        lblUsers.setBounds(5, 40, 120, 30);
        lblUsers.setForeground(Color.WHITE);
        mainFrame.add(lblUsers);

        tfUsers = new JTextField("");
        tfUsers.setBounds(120, 40, 100, 30);
        tfUsers.setEditable(true);
        mainFrame.add(tfUsers);

        lblDays = new JLabel("Number of Days:");
        lblDays.setBounds(5, 70, 120, 30);
        lblDays.setForeground(Color.WHITE);
        mainFrame.add(lblDays);

        tfDays = new JTextField("");
        tfDays.setBounds(120, 70, 100, 30);
        tfDays.setEditable(true);
        mainFrame.add(tfDays);

        dummyLabel = new JLabel("");
        mainFrame.add(dummyLabel);

        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        int timesTried = 0;
        if (evt.getActionCommand().equals("Run")) {
            setupSimulation();
            btnRun.setEnabled(false);
            btnParameters.setEnabled(true);
        }
        if (evt.getActionCommand().equals("Set Parameters")) {
            userList.clear();
            postList.clear();
            boolean validUsersNumber = true;
            boolean validDaysNumber = true;
            String usersString = tfUsers.getText();
            String daysString = tfDays.getText();

            if (usersString.equals("")) {
                System.out.println("Invalid Users Value");
                validUsersNumber = false; 
            }
            else if (daysString.equals("")) {
                System.out.println("Invalid Days Value");
                validDaysNumber = false;
            }
            else {
                for (int i = 0; i < usersString.length(); i++) {
                    if (Character.isDigit(tfUsers.getText().charAt(i)) == false) {
                        System.out.println("Invalid Users Value");
                        validUsersNumber = false;
                        break;
                    }
                }
                for (int i = 0; i < daysString.length(); i++) {
                    if (Character.isDigit(tfDays.getText().charAt(i)) == false) {
                        System.out.println("Invalid Days Value");
                        validDaysNumber = false;
                        break;
                    }
                }
            }
            if (validUsersNumber && validDaysNumber) {
                users = Integer.parseInt(usersString);
                days = Integer.parseInt(daysString);
                btnRun.setEnabled(true);
            }
        }
    }

    public void setupSimulation() {
        makeUsers();
        runSimulation();
        getStats();
    }

    public void makeUsers() {
        for (int i = 0; i < users; i++) {
            User u = new User(i, (rand.nextInt(9) + 2), (rand.nextInt(9) + 2));
            userList.add(u);
        }
    }

    public void runSimulation() {
        for (int i = 0; i < days; i++) {
            for (int j = 0; j < users; j++) {
                userList.get(j).checkActivity(postList);
            }
        }
    }

    public void getStats() {
        int totalLikes;
        int totalShares;
        Post mostViewed;
        Post mostLiked;
        Post mostShared;
        User mostActive;
        User mostFriends;
        User leastFriends;
        
        for (int i = 0; i < users; i++) {
            float loginPercentage = (userList.get(i).loginAmount * 100f) / days;
            
            System.out.print("User " + userList.get(i).userIDNo + " login %");
            System.out.printf("%.2f", loginPercentage);
            System.out.println();
        }
    }
}
