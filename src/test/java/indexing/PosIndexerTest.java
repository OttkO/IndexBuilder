package indexing;


import org.junit.Test;

import java.io.File;

/**
 * Created by OttkO on 06-Jan-17.
 */
public class PosIndexerTest {
    String tweetsDir = new File(new File(Config.pCloudRoot, "testAPIdata"), "tweets").getAbsolutePath();
    String articleDir = new File(new File(Config.pCloudRoot, "testAPIdata"), "articles").getAbsolutePath();
    @Test
    public void testMakeArticleIndex() throws Exception {
        DbHandler.setupDatabase();
        PosIndexer.makeArticleIndex(articleDir);
    }

    @Test
    public void testMakeTwitterIndex() throws Exception {
        DbHandler.setupDatabase();
        PosIndexer.makeTwitterIndex(tweetsDir);
    }

    @Test
    public void testBuildIndex() throws Exception{
        PosIndexer.buildIndexes(articleDir,tweetsDir);
    }
}