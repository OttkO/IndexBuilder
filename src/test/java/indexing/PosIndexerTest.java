package indexing;

import org.junit.Test;

import static controllers.IndexServlet.articleDir;
import static controllers.IndexServlet.tweetsDir;
/**
 * Test the index builder
 * Created by OttkO on 06-Jan-17.
 */
public class PosIndexerTest {

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
    public void testMakeTwitterIndexBatch() throws Exception{
        DbHandler.setupDatabase(); // delete all
        PosIndexer.makeTwitterIndexBatch(tweetsDir);
    }

    @Test
    public void testBuildIndex() throws Exception{
        PosIndexer.reBuildIndices(articleDir,tweetsDir);
    }
}