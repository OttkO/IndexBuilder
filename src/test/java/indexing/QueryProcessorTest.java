package indexing;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by OttkO on 07-Jan-17.
 */
public class QueryProcessorTest {
    @Test
    public void processArticleQuery() throws Exception {
        ArrayList<KeywordStructure> video = QueryProcessor.processArticleQuery("about");
        Util.getArticlesListFromKeywordsinFiles(video, 0);
    }

    @Test
    public void processTweetQuery() throws Exception {
        ArrayList<KeywordStructure> trump = QueryProcessor.processTweetQuery("trump");
        TweetsMap tweets = Util.getTweets(trump, 1);
        System.out.println("done");
    }
    @Test
    public void processTweetNonRawQuery() throws Exception {
        ArrayList<KeywordStructure> trump = QueryProcessor.processTweetQuery("trump");
        ArrayList<Tweet> tweetListFromKeywordsinFiles = Util.getTweetListFromKeywordsinFiles(trump, 1);
        System.out.println("Done");
    }

}