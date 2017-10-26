
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
    public void liked() { likes++; }
    public void shared() { shares++; }
    public void viewed() { views++; }
    public int getID() { return postID; }
    public int getLikes() { return likes; }
    public int getShares() { return shares; }
    public int getViews() { return views; }
}
