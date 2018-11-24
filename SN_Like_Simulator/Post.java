import java.util.ArrayList;
/**
 * Write a description of class Post here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Post
{
    int postID;
    int likes;
    int shares;
    int views;
    User originalPoster;
    int dayNumber;
    ArrayList<Integer> userIDLikers = new ArrayList<Integer>();
    /**
     * Constructor for objects of class Post
     */
    public Post()
    {
        
    }

    public Post (int id, int dayNo, User ogPoster) {
        postID = id;
        originalPoster = ogPoster;
        dayNumber = dayNo;
    }
    public void liked(int userID) { 
        likes++; 
        userIDLikers.add(userID);
    }
    public boolean alreadyLiked(int userID) {
        for (int i = 0; i < userIDLikers.size(); i++) {
            if (userIDLikers.get(i) == userID) return true;
        }
        return false;
    }
    public void shared() { shares++; }
    public void viewed() { views++; }
    public int getID() { return postID; }
    public int getLikes() { return likes; }
    public int getShares() { return shares; }
    public int getViews() { return views; }
    public int getOriginalDay() { return dayNumber; }
    public User getOriginalPoster() { return originalPoster; }
}
