import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;
import java.util.Collections;
import javax.swing.table.DefaultTableModel;

import java.text.DecimalFormat;
import java.math.RoundingMode;

/**
 * Write a description of class GUIClass here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GUIClass extends JPanel implements ActionListener
{
    JFrame mainFrame;
    JButton btnParameters, btnRun, btnRepeatRun;
    JCheckBox chkShare, chkGrids, chkLogins, chkPosts;
    JTextField tfUsers, tfDays, tfAvgLogin, tfYears, tfSeed;
    JLabel lblUsers, lblDays, lblRunning, dummyLabel;
    JTable table;
    Color lightGreen = new Color(204, 255, 153);
    Color aqua = new Color(0, 255, 255);
    int days, users;
    int dayNumber;
    int numberPostsToday = 0;
    int seed = 0;
    boolean showGridLines, showLoginsLine, showPostsLine;
    Random rand;

    DefaultTableModel model;
    
    DecimalFormat df2 = new DecimalFormat(".##");
    
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<Post> postList = new ArrayList<Post>();
    ArrayList<Post> recentPostList = new ArrayList<Post>();
    ArrayList<Integer> loginNumberList = new ArrayList<Integer>();
    ArrayList<Integer> totalPostsDaily = new ArrayList<Integer>();
    /**
     * Constructor for objects of class GUIClass
     */
    public GUIClass()
    {

    }

    public void makeGUI() {
        mainFrame = new JFrame();
        mainFrame.getContentPane().setBackground(Color.BLACK);
        mainFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aqua));
        mainFrame.setBounds(0,0,300,600);
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

        btnRepeatRun = new JButton("Repeat Run");
        btnRepeatRun.setBounds(190,1,100,30);
        btnRepeatRun.setForeground(Color.BLACK);
        btnRepeatRun.addActionListener(this);
        btnRepeatRun.setEnabled(false);
        mainFrame.add(btnRepeatRun);

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

        tfSeed = new JTextField("");
        tfSeed.setBounds(120, 100, 100, 30);
        tfSeed.setEditable(true);
        mainFrame.add(tfSeed);

        chkShare = new JCheckBox("Users can share posts");
        chkShare.setBounds(5, 130, 175, 30);
        chkShare.setForeground(Color.WHITE);
        mainFrame.add(chkShare);

        chkGrids = new JCheckBox("Show gridlines on graph");
        chkGrids.setBounds(5, 160, 200, 30);
        chkGrids.setForeground(Color.WHITE);
        mainFrame.add(chkGrids);

        chkLogins = new JCheckBox("Show logins line.");
        chkLogins.setBounds(5, 190, 200, 30);
        chkLogins.setForeground(Color.WHITE);
        mainFrame.add(chkLogins);

        chkPosts = new JCheckBox("Show posts line.");
        chkPosts.setBounds(5, 220, 200, 30);
        chkPosts.setForeground(Color.WHITE);
        mainFrame.add(chkPosts);

        lblUsers = new JLabel("Program running, please wait...");
        lblUsers.setBounds(5, 250, 120, 30);
        lblUsers.setForeground(Color.WHITE);
        mainFrame.add(lblUsers);
        lblUsers.setVisible(true);

        String[] columnNames = {"User ID", "Logins", "Login %", "Likes"};
        Object[][] data = {};

        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Integer.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return Float.class;
                    default:
                        return Integer.class;
                }
            }
        };
        table = new JTable();
        table.setModel(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        scrollPane.setBounds(5, 280, 285, 290); 
        mainFrame.add(scrollPane);
        
        dummyLabel = new JLabel("");
        mainFrame.add(dummyLabel);

        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        int timesTried = 0;
        if (evt.getActionCommand().equals("Run")) {
            lblUsers.setVisible(true);
            if (chkGrids.isSelected()) {
                showGridLines = true;
            }
            else showGridLines = false;
            if (chkLogins.isSelected()) {
                showLoginsLine = true;
            }
            else showLoginsLine = false;
            if (chkPosts.isSelected()) {
                showPostsLine = true;
            }
            else showPostsLine = false;
            setupSimulation();
            btnRun.setEnabled(false);
            btnRepeatRun.setEnabled(true);

        }
        if (evt.getActionCommand().equals("Set Parameters")) {
            btnRepeatRun.setEnabled(false);
            userList.clear();
            postList.clear();
            loginNumberList.clear();
            totalPostsDaily.clear();
            boolean validUsersNumber = true;
            boolean validDaysNumber = true;
            String usersString = tfUsers.getText();
            String daysString = tfDays.getText();
            String seedString = tfSeed.getText();

            rand = new Random();    
            if (seedString.equals("")) {
                rand = new Random(10);
            }
            else {
                int seed = Integer.parseInt(seedString);
                rand = new Random(seed);
            }
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
        if (evt.getActionCommand().equals("Repeat Run")) {
            if (chkGrids.isSelected()) {
                showGridLines = true;
            }
            else showGridLines = false;
            if (chkLogins.isSelected()) {
                showLoginsLine = true;
            }
            else showLoginsLine = false;
            if (chkPosts.isSelected()) {
                showPostsLine = true;
            }
            else showPostsLine = false;
            createGraphFrame();
        }
    }

    public void setupSimulation() {
        makeUsers();
        runSimulation();
        getStats();
    }

    public void makeUsers() {
        for (int i = 0; i < users; i++) {
            User u = new User(i+1, (rand.nextInt(8) + 3), (rand.nextInt(8) + 3), this);//, seed);
            userList.add(u);
        }
    }

    public void runSimulation() {
        for (int i = 1; i <= days; i++) {
            numberPostsToday = 0;
            dayNumber = i;
            ArrayList<User> todaysList = new ArrayList<User>(userList);
            for (int j = 0; j < recentPostList.size(); j++) {
                Post thisPost = recentPostList.get(j);
                int dayNo = thisPost.getOriginalDay();
                if ((i - dayNo) > 3) {
                    //System.out.println("Removing Post # " + recentPostList.get(j).getID());
                    recentPostList.remove(j);
                    j--;

                }
                if ((j + 1) > recentPostList.size()) break;
            }
            int todaysLogins = 0;

            while(todaysList.size() > 0) {

                int checkedUser = rand.nextInt(todaysList.size());
                boolean loggedIn = todaysList.get(checkedUser).checkActivity(recentPostList, userList);
                if (loggedIn) {
                    todaysLogins++;
                }
                todaysList.remove(checkedUser);
            }
            loginNumberList.add(todaysLogins);
            totalPostsDaily.add(numberPostsToday);
        }
    }

    public void getStats() {
        int totalLikes;
        int totalShares;
        Post mostViewed = null;
        Post mostLiked = null;
        Post mostShared = null;
        User mostActive = null;
        User mostFriends = null;
        User leastFriends = null;
        
        /*for (int i = 0; i < users; i++) {
        System.out.println("--------------------------------");
        for (int i = 0; i < postList.size(); i++) {
        Post currentPost = postList.get(i);
        System.out.println("Post # " + currentPost.postID + " on Day: " + currentPost.dayNumber
        + " Posted by: " + currentPost.originalPoster.userIDNo);
        }*/
        int viewTotalPost = 0;
        int likeTotalPost = 0;
        int shareTotalPost = 0;
        for (int i = 0; i < postList.size(); i++) {
            Post currentPost = postList.get(i);
            //System.out.println("Post # " + currentPost.getID() + " total likes: " + currentPost.getLikes());
            if (currentPost.getViews() > viewTotalPost) {
                mostViewed = currentPost;
                viewTotalPost = currentPost.getViews();
            }
            if (currentPost.getLikes() > likeTotalPost) {
                mostLiked = currentPost;
                likeTotalPost = currentPost.getLikes();
            }
            if (currentPost.getShares() > shareTotalPost) {
                mostShared = currentPost;
                shareTotalPost = currentPost.getShares();
            }
        }
        /*if (mostViewed != null) System.out.println("Most viewed = " + mostViewed.getID());
        if (mostLiked != null) System.out.println("Most liked = " + mostLiked.getID());
        if (mostShared != null) System.out.println("Most shared = " + mostShared.getID());*/
        ArrayList<User> peopleList = new ArrayList<User>(userList);
        Collections.sort(peopleList);

        df2.setRoundingMode(RoundingMode.UP);
        for (int i = 0; i < userList.size(); i++) {
            User thisUser = userList.get(i);
            int logAmount = thisUser.loginAmount;
            float loginPercentage = 0;
            if (logAmount != 0) {
                loginPercentage = (logAmount * 100f) / days;
            }
            Object[] insertingUserRow = {new Integer(thisUser.getUserID()), 
                                         new Integer(thisUser.loginAmount), 
                                         new Float(df2.format(loginPercentage)), 
                                         new Integer(thisUser.likeAmount)};
            model.addRow(insertingUserRow);
        }   
        createGraphFrame();
    }

    public void newPost(User u) {
        numberPostsToday++;
        Post newPost = new Post(postList.size(), dayNumber, u);
        postList.add(newPost);
        recentPostList.add(newPost);
    }

    public void createGraphFrame() {
        JFrame gvFrame = new JFrame("Graphical View");
        Grapher g = new Grapher(this, loginNumberList, totalPostsDaily, days, users, 
                showGridLines, showLoginsLine, showPostsLine);
        gvFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aqua));
        gvFrame.add(g);
        gvFrame.setSize(600, 600);
        gvFrame.setVisible(true);
    }

    public void doneRunning() { lblUsers.setVisible(false); } 
}