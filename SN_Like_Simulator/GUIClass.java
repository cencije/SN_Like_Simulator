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
    JButton btnParameters, btnRun, btnRepeatRun, btnExportTable, btnClearTable;
    JCheckBox chkGrids, chkLogins, chkPosts;
    JTextField tfUsers, tfDays, tfAvgLogin, tfYears, tfSeed;
    JLabel lblUsers, lblDays, dummyLabel;
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

        chkGrids = new JCheckBox("Show gridlines on graph");
        chkGrids.setBounds(5, 100, 200, 30);
        chkGrids.setForeground(Color.WHITE);
        mainFrame.add(chkGrids);

        chkLogins = new JCheckBox("Show logins line.");
        chkLogins.setBounds(5, 130, 200, 30);
        chkLogins.setForeground(Color.WHITE);
        mainFrame.add(chkLogins);

        chkPosts = new JCheckBox("Show posts line.");
        chkPosts.setBounds(5, 160, 200, 30);
        chkPosts.setForeground(Color.WHITE);
        mainFrame.add(chkPosts);

        String[] columnNames = {"User ID", "Logins", "Login %", "Likes"};
        Object[][] data = {};

        model = new DefaultTableModel(columnNames, 0) {
            public boolean isCellEditable(int row, int column) { return false; }

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
        scrollPane.setBounds(5, 230, 285, 340); 
        mainFrame.add(scrollPane);

        btnExportTable = new JButton("Export to CSV");
        btnExportTable.setBounds(1,200,130,30);
        btnExportTable.setForeground(Color.BLACK);
        btnExportTable.addActionListener(this);
        btnExportTable.setEnabled(false);
        mainFrame.add(btnExportTable);
        
        btnClearTable = new JButton("Clear Table");
        btnClearTable.setBounds(190, 200, 100, 30);
        btnClearTable.setForeground(Color.BLACK);
        btnClearTable.addActionListener(this);
        btnClearTable.setEnabled(true);
        mainFrame.add(btnClearTable);
        
        dummyLabel = new JLabel("");
        mainFrame.add(dummyLabel);

        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent evt) {
        int timesTried = 0;
        if (evt.getActionCommand().equals("Run")) {
            if (chkGrids.isSelected()) showGridLines = true;
            else showGridLines = false;
            if (chkLogins.isSelected())  showLoginsLine = true;
            else showLoginsLine = false;
            if (chkPosts.isSelected())  showPostsLine = true;
            else showPostsLine = false;
            setupSimulation();
            btnRun.setEnabled(false);
            btnRepeatRun.setEnabled(true);
            btnExportTable.setEnabled(true);
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
            rand = new Random(System.currentTimeMillis());
            if (usersString.equals("")) validUsersNumber = false; 
            else if (daysString.equals("")) validDaysNumber = false;
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
            if (chkGrids.isSelected()) showGridLines = true;
            else showGridLines = false;
            if (chkLogins.isSelected())  showLoginsLine = true;
            else showLoginsLine = false;
            if (chkPosts.isSelected())  showPostsLine = true;
            else showPostsLine = false;
            createGraphFrame();
        }
         if (evt.getActionCommand().equals("Export to CSV")) {
            try {
                FileWriter csv = new FileWriter(new File("./ExperimentData.csv"));
        
                for (int i = 0; i < model.getColumnCount(); i++) {
                    csv.write(model.getColumnName(i) + ",");
                }
                csv.write("\n");
        
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        csv.write(model.getValueAt(i, j).toString() + ",");
                    }
                    csv.write("\n");
                }
                csv.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (evt.getActionCommand().equals("Clear Table")) {
            model.setRowCount(0);
            btnExportTable.setEnabled(false);
        }
    }

    public void setupSimulation() {
        makeUsers();
        runSimulation();
        getStats();
    }

    public void makeUsers() {
        for (int i = 0; i < users; i++) {
            User u = new User(i+1, (rand.nextInt(8) + 3), (rand.nextInt(8) + 3), this);
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
                    recentPostList.remove(j);
                    j--;

                }
                if ((j + 1) > recentPostList.size()) break;
            }
            int todaysLogins = 0;

            while(todaysList.size() > 0) {

                int checkedUser = rand.nextInt(todaysList.size());
                boolean loggedIn = todaysList.get(checkedUser).checkActivity(recentPostList, userList);
                if (loggedIn) todaysLogins++;
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
        int viewTotalPost = 0;
        int likeTotalPost = 0;
        int shareTotalPost = 0;
        for (int i = 0; i < postList.size(); i++) {
            Post currentPost = postList.get(i);
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
        ArrayList<User> peopleList = new ArrayList<User>(userList);
        Collections.sort(peopleList);

        df2.setRoundingMode(RoundingMode.UP);
        for (int i = 0; i < userList.size(); i++) {
            User thisUser = userList.get(i);
            int logAmount = thisUser.loginAmount;
            float loginPercentage = 0;
            if (logAmount != 0)  loginPercentage = (logAmount * 100f) / days;
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
        gvFrame.setLocation(300,0);
        gvFrame.setSize(600, 600);
        gvFrame.setVisible(true);
        gvFrame.setResizable(false);
    }


}