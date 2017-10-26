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
    /**
     * Constructor for objects of class User
     */
    public User()
    {
        
    }
    
    public User(int id, int loginPos, int outgoingLVL) {
        userIDNo = id;
        loginPossibility = loginPos;
        outgoingLevel = outgoingLVL;
        rand = new Random();
    } 
    
    public void login(ArrayList<Post> postList) {
        loginAmount++;
        for (int i = 0; i < postList.size(); i++) {
            int likePost = rand.nextInt(outgoingLevel);
            if (likePost >= 1) {
                likeAmount++;
                postList.get(i).liked();
            }
        }
    }
    
    public void newFriend(int otherUserID) {
        
    }
}
