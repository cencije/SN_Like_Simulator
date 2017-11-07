import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.geom.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

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
    JCheckBox chkShare;
    JTextField tfUsers, tfDays, tfAvgLogin, tfYears, tfSeed;
    JLabel lblUsers, lblDays, dummyLabel;
    Color lightGreen = new Color(204, 255, 153);
    Color aqua = new Color(0, 255, 255);
    int days, users;
    int dayNumber;
    int numberPostsToday = 0;
    int seed = 0;
    Random rand;

    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<Post> postList = new ArrayList<Post>();
    ArrayList<Post> recentPostList = new ArrayList<Post>();
    ArrayList<Integer> loginNumberList;
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

        tfSeed = new JTextField("");
        tfSeed.setBounds(120, 100, 100, 30);
        tfSeed.setEditable(true);
        mainFrame.add(tfSeed);

        chkShare = new JCheckBox("Users can share posts");
        chkShare.setBounds(5, 130, 175, 30);
        chkShare.setForeground(Color.WHITE);
        mainFrame.add(chkShare);

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
    }

    public void setupSimulation() {
        makeUsers();
        runSimulation();
        getStats();
    }

    public void makeUsers() {
        for (int i = 0; i < users; i++) {
            User u = new User(i, (rand.nextInt(8) + 3), (rand.nextInt(8) + 3), this, seed);
            userList.add(u);
        }
    }

    public void runSimulation() {
        loginNumberList = new ArrayList<Integer>();
        for (int i = 0; i < days; i++) {
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

        for (int i = 0; i < users; i++) {
            int loginAmount = userList.get(i).loginAmount;
            float loginPercentage = 0;
            if (loginAmount != 0) {
                loginPercentage = (userList.get(i).loginAmount * 100f) / days;
            }

            System.out.print("User " + userList.get(i).userIDNo + " login %");
            System.out.printf("%.2f", loginPercentage);
            System.out.println();
        }
        System.out.println("--------------------------------");
        for (int i = 0; i < postList.size(); i++) {
            Post currentPost = postList.get(i);
            System.out.println("Post # " + currentPost.postID + " on Day: " + currentPost.dayNumber
                + " Posted by: " + currentPost.originalPoster.userIDNo);
        }
        int viewTotalPost = 0;
        int likeTotalPost = 0;
        int shareTotalPost = 0;
        for (int i = 0; i < postList.size(); i++) {
            Post currentPost = postList.get(i);
            System.out.println("Post # " + currentPost.getID() + " total likes: " + currentPost.getLikes());
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
        if (mostViewed != null) System.out.println("Most viewed = " + mostViewed.getID());
        if (mostLiked != null) System.out.println("Most liked = " + mostLiked.getID());
        if (mostShared != null) System.out.println("Most shared = " + mostShared.getID());

        /*for (int i = 0; i < users; i++) {
        User thisUser = userList.get(i);
        System.out.println();
        System.out.print("User " + thisUser.getUserID() + " list = {");
        for (int j = 0; j < thisUser.getFriendList().size(); j++) {
        System.out.print(thisUser.getFriendList().get(j) + ", ");
        }
        System.out.print("}");
        }*/
        System.out.println();
        for (int i = 0; i < days; i++) {
            System.out.print(loginNumberList.get(i) + "| ");
            System.out.println(totalPostsDaily.get(i));
        }
        System.out.println();
        for (int i = 0; i < days; i++) {
            System.out.println(totalPostsDaily.get(i));
        }
        createGraphFrame();
    }

    public void newPost(User u) {
        Post newPost = new Post(postList.size(), dayNumber, u);
        postList.add(newPost);
        recentPostList.add(newPost);
        numberPostsToday++;
    }

    public void createGraphFrame() {

        JFrame gvFrame = new JFrame("Graphical View");
        Grapher g = new Grapher(loginNumberList, totalPostsDaily, days, users);
        gvFrame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, aqua));
        gvFrame.add(g);
        gvFrame.setSize(400,400);
        gvFrame.setVisible(true);
    }
}
