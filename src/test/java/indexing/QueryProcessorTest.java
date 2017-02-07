package indexing;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by OttkO on 07-Jan-17.
 */

//test the query processor
public class QueryProcessorTest {
    @Test
    public void processArticleQuery() throws Exception {
        ArrayList<KeywordStructure> result = QueryProcessor.processArticleQuery("about");
        ArrayList<Article> articles = Util.getArticlesListFromKeywordsinFiles(result, 0);
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

    @Test
    public void getArticleId() throws Exception {
        String testId;
        testId = "r1534335215";
        long t0, t1, t2;
        ArrayList<Article> articlesListFromKeywordsinFiles; //get articles based on keyword and stPoint
        ArrayList<KeywordStructure> articleById;

        t0 = System.currentTimeMillis();
        articleById = QueryProcessor.getArticleById(testId);
        t1 = System.currentTimeMillis();
        System.out.println("QueryProcessor.getArticleById(testId) [1] took "+(t1-t0)+"ms and returned "+articleById.size()+" results");
        articlesListFromKeywordsinFiles = Util.getArticlesListFromKeywordsinFiles(articleById, 0);
        t2 = System.currentTimeMillis();
        System.out.println("Util.getArticlesListFromKeywordsinFiles(articleById, 0) [1] took "+(t2-t1)+"ms and returned "+articlesListFromKeywordsinFiles.size()+" results");

        testId = "r328776564";
        t0 = System.currentTimeMillis();
        articleById = QueryProcessor.getArticleById(testId);
        t1 = System.currentTimeMillis();
        System.out.println("QueryProcessor.getArticleById(testId) [2] took "+(t1-t0)+"ms and returned "+articleById.size()+" results");
        articlesListFromKeywordsinFiles = Util.getArticlesListFromKeywordsinFiles(articleById, 0);
        t2 = System.currentTimeMillis();
        System.out.println("Util.getArticlesListFromKeywordsinFiles(articleById, 0) [2] took "+(t2-t1)+"ms and returned "+articlesListFromKeywordsinFiles.size()+" results");
    }

    @Test
    public void getTweetId() throws Exception {
        String testId;
        testId = "t786537470007582720";
        long t0 = System.currentTimeMillis();
        ArrayList<KeywordStructure> tweetById = QueryProcessor.getTweetById(testId);
        long t1 = System.currentTimeMillis();
        System.out.println("QueryProcessor.getTweetById(testId) took "+(t1-t0)+"ms and returned "+tweetById.size()+" results");
        ArrayList<Tweet> tweetListFromKeywordsinFiles = Util.getTweetListFromKeywordsinFiles(tweetById, 0);//get articles based on keyword and stPoint
        long t2 = System.currentTimeMillis();
        System.out.println("Util.getTweetListFromKeywordsinFiles(tweetById, 0) took "+(t2-t1)+"ms and returned "+tweetListFromKeywordsinFiles.size()+" results");


        t0 = System.currentTimeMillis();
        tweetById = QueryProcessor.getTweetById(testId);
        t1 = System.currentTimeMillis();
        System.out.println("QueryProcessor.getTweetById(testId) [2] took "+(t1-t0)+"ms and returned "+tweetById.size()+" results");
        tweetListFromKeywordsinFiles = Util.getTweetListFromKeywordsinFiles(tweetById, 0);//get articles based on keyword and stPoint
        t2 = System.currentTimeMillis();
        System.out.println("Util.getTweetListFromKeywordsinFiles(tweetById, 0) [2] took "+(t2-t1)+"ms and returned "+tweetListFromKeywordsinFiles.size()+" results");
    }
}