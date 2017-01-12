package indexing;

import org.junit.Test;

/**
 * Created by OttkO on 06-Jan-17.
 */
public class TestDBSetup {
    @Test
    public void setupDatabase() throws Exception {
        DbHandler.setupDatabase();
    }

    @Test
    public void testTweetTable() throws Exception {
        DbHandler.createTweetIndexesTable();
        DbHandler.dropTweetIndexesTable();
        DbHandler.createTweetIndexesTable();
    }

    @Test
    public void testArticleTable() throws Exception {
        DbHandler.createArticleIndexesTable();
        DbHandler.dropArticleIndexesTable();
        DbHandler.createArticleIndexesTable();
    }

    @Test
    public void testFileNameTable() throws Exception{
        DbHandler.createFileNameTable();
        DbHandler.dropFileNameTable();
        DbHandler.createFileNameTable();
    }

}