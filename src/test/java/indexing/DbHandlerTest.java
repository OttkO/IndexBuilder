package indexing;

import org.junit.Test;

/**
 * Created by OttkO on 06-Jan-17.
 */
public class DbHandlerTest {
    @Test
    public void setupDatabase() throws Exception {
        DbHandler.setupDatabase();
    }

    @Test
    public void dropFileNameTable() throws Exception {
        DbHandler.dropFileNameTable();
    }

    @Test
    public void dropFileInformationTable() throws Exception {
        DbHandler.dropArticleIndexesTable();
    }

    @org.junit.Test
    public void testCreateFileInfoTable() throws Exception {
        DbHandler.createArticleIndexesTable();
    }
    @org.junit.Test
    public void testCreateFileNameTable() throws Exception{
        DbHandler.createFileNameTable();
    }

}