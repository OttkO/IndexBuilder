package indexing;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by OttkO on 06-Jan-17.
 */
public class Tweet{
    public String tweetID;
    public String userID;
    public String timestamp;
    public String fullText;

    public Tweet(String tweetID, String userID, String timestamp, String fullText) {
        this.tweetID = tweetID;
        this.userID = userID;
        this.timestamp = timestamp;
        this.fullText = fullText;
    }
    public Tweet() {

    }

}
