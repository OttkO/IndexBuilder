package indexing;


import org.junit.Test;

/**
 * Created by OttkO on 06-Jan-17.
 */
public class PosIndexerTest {

    @Test
    public void testMakeArticleIndex() throws Exception {
        DbHandler.setupDatabase();
        PosIndexer.makeArticleIndex(Config.articleDir);
    }

    @Test
    public void testMakeTwitterIndex() throws Exception {
        DbHandler.setupDatabase();
        PosIndexer.makeTwitterIndex(Config.tweetsDir);
    }

    @Test
    public void testBuildIndex() throws Exception{
        PosIndexer.buildIndexes(Config.articleDir,Config.tweetsDir);
    }
}