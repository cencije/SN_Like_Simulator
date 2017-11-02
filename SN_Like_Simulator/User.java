import java.util.Random;
import java.util.ArrayList;
/**
 * Write a description of class User here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class User
{
    int userIDNo;
    int loginAmount = 0;
    int likeAmount = 0;
    int shareAmount = 0;

    int loginPossibility = 0;
    int outgoingLevel = 0;
    Random rand;
    ArrayList<Integer> friendList = new ArrayList<Integer>();
    GUIClass main;
    /**
     * Constructor for objects of class User
     */
    public User()
    {

    }

    public User(int id, int loginPos, int outgoingLVL, GUIClass mainGui) {
        main = mainGui;
        userIDNo = id;
        loginPossibility = loginPos;
        outgoingLevel = outgoingLVL;
        rand = new Random();
    } 

    public void checkActivity(ArrayList<Post> postList, ArrayList<User> userList) {
        int generatedChance = rand.nextInt(loginPossibility);
        if (generatedChance >= 2) { login(postList, userList); }
        else { 
            //System.out.println("User # " + userIDNo + " didnt login."); 
        }
    }

    public void login(ArrayList<Post> postList, ArrayList<User> userList) {
        loginAmount++;
        //System.out.println("User # " + userIDNo + " logged in.");
        for (int i = 0; i < postList.size(); i++) {
            int likePost = rand.nextInt(outgoingLevel);
            if (likePost >= 2) {
                if (!postList.get(i).alreadyLiked(userIDNo)
                    && postList.get(i).originalPoster.getUserID() != userIDNo) {
                    likeAmount++;
                    postList.get(i).liked(userIDNo);
                }
                //System.out.println("User # " + userIDNo + " liked Post # " + i);
            }
        }
        int genPostChance = rand.nextInt(outgoingLevel);
        if (genPostChance >= 2) {
            main.newPost(this);
        }

        for (int i = 0; i < userList.size(); i++) {

            User otherUser = userList.get(i);
            int ouID = otherUser.getUserID();
            if (ouID != userIDNo) {
                boolean alreadyFriended = false;
                for (int j = 0; j < friendList.size(); j++) {
                    if (ouID == friendList.get(j)) {
                        alreadyFriended = true;
                        break;    
                    }
                }
                if (alreadyFriended == false) {
                    int friendRequestChance = rand.nextInt(outgoingLevel);  
                    if (friendRequestChance >= 2) {
                        otherUser.receiveFriendRequest(this);
                    }
                }
            }
        }
    }

    public void receiveFriendRequest(User otherUser) {
        int acceptChance = rand.nextInt(outgoingLevel);
        if (acceptChance >= 2) {
            friendList.add(otherUser.getUserID());
            otherUser.linkFriend(this);
        }
    }

    public void linkFriend(User requestedFriend) {
        friendList.add(requestedFriend.getUserID());
    }
    public int getUserID() { return userIDNo; }
    public ArrayList<Integer> getFriendList() { return friendList; }
}
