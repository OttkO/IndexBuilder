package indexing;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by OttkO on 05-Jan-17.
 */
public class PosIndexer {
    //Build article index
    public static void makeArticleIndex(String inputDirectory) throws IOException, SQLException {
       // DbHandler.setupDatabase();
        ArticleKeywordFetcher fetcher = new ArticleKeywordFetcher();
        List<String> result = Util.getFilesInDirectory(inputDirectory); // retrieves all files from the director
        for (String file: result) { // process each file
            int fileId = getOrCreateFileId(file); // get or create a file id depending if it exists
            if (fileId != -1) { // if it successed
                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file); // get all keywords from a file
                DbHandler.insertRecordsIntoArticleTable(fileId, keywordStructures);
            }
        }
    }
    public static void makeTwitterIndex(String inputDirectory) throws IOException, SQLException {
        //DbHandler.setupDatabase();
        TweetKeywordFetcher fetcher = new TweetKeywordFetcher();
        List<String> result = Util.getFilesInDirectory(inputDirectory);  // retrieves all files from the director
        for (String file: result) {  // process each file
            int fileId = getOrCreateFileId(file);
            if (fileId != -1) { // if it successed
                long t0 = System.currentTimeMillis();

                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file);

                long t1 = System.currentTimeMillis();
                long time_read = t1 - t0;

                for (KeywordStructure k :
                        keywordStructures) {
                    DbHandler.insertRecordIntoTweetTable(fileId, k.lineNumber, k.position, k.keyword); // insert position, keyword and filename in database
                }

                long time_write = System.currentTimeMillis() - t0;
                System.out.println("time_read = " + time_read + "ms, time_write = " + time_write + "ms");
            }
        }
    }
    public static void makeTwitterIndexBatch(String inputDirectory) throws IOException, SQLException {
        TweetKeywordFetcher fetcher = new TweetKeywordFetcher();
        ContentFetcher fetcherId = new TweetIdFetcher();
        List<String> result = Util.getFilesInDirectory(inputDirectory);
        for (String file: result) {
            int fileId = getOrCreateFileId(file);
            if (fileId != -1) {
                long t0 = System.currentTimeMillis();

                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file);
                ArrayList<KeywordStructure> idStructures = fetcherId.getContent(file);

                long t1 = System.currentTimeMillis();
                long time_read = t1 - t0;
                System.out.println("time_read = " + time_read + "ms");

                DbHandler.insertRecordsIntoTweetIdTable(fileId, idStructures);
                DbHandler.insertRecordsIntoTweetTable(fileId, keywordStructures);

                long time_write = System.currentTimeMillis() - t0;
                System.out.println("time_write = " + time_write + "ms");
            }
        }
    }
    public static void makeArticleIndexBatch(String inputDirectory) throws IOException, SQLException {
        ArticleKeywordFetcher fetcher = new ArticleKeywordFetcher();
        ContentFetcher fetcherId = new TweetIdFetcher();
        List<String> result = Util.getFilesInDirectory(inputDirectory);
        for (String file: result) {
            int fileId = getOrCreateFileId(file);
            if (fileId != -1) {
                long t0 = System.currentTimeMillis();

                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file);
                ArrayList<KeywordStructure> idStructures = fetcherId.getContent(file);

                long t1 = System.currentTimeMillis();
                long time_read = t1 - t0;
                System.out.println("time_read = " + time_read + "ms");

                DbHandler.insertRecordsIntoArticleIdTable(fileId, idStructures);
                DbHandler.insertRecordsIntoArticleTable(fileId, keywordStructures);

                long time_write = System.currentTimeMillis() - t0;
                System.out.println("time_write = " + time_write + "ms");
            }
        }
    }
    public static void reBuildIndices(String articleDir , String tweetsDir) throws IOException, SQLException {
        DbHandler.setupDatabase();

        //Build index
        makeArticleIndexBatch(articleDir);

        //Set up database
        makeTwitterIndexBatch(tweetsDir);
    }
    //Insert filename in database or retrieve it if it exists
    private static int getOrCreateFileId(String filepath) throws SQLException {
        int fileId = -1;
        boolean success = false;
        try {
            fileId = DbHandler.insertFileName(filepath);
            success = true;
        }
        catch(Exception ex) {
            // System.out.printf(ex.getMessage());
        }
        if (!success)
        {
            fileId = DbHandler.findFilenameId(filepath);
        }
        return fileId;
    }



}
