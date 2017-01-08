package indexing;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OttkO on 05-Jan-17.
 */
public class PosIndexer {
    public static void makeArticleIndex(String inputDirectory) throws IOException, SQLException {
       // DbHandler.setupDatabase();
        ArticleKeywordFetcher fetcher = new ArticleKeywordFetcher();
        List<String> result = Util.getFilesInDirectory(inputDirectory);
        for (String file: result) {
            int fileId = getOrCreateFileId(file);
            if (fileId != -1) {
                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file);
                for (KeywordStructure k :
                        keywordStructures) {
                    DbHandler.insertRecordIntoArticleTable(fileId, k.lineNumber, k.position, k.keyword);
                }
            }
        }
    }
    public static void makeTwitterIndex(String inputDirectory) throws IOException, SQLException {
        //DbHandler.setupDatabase();
        TweetKeywordFetcher fetcher = new TweetKeywordFetcher();
        List<String> result = Util.getFilesInDirectory(inputDirectory);
        for (String file: result) {
            int fileId = getOrCreateFileId(file);
            if (fileId != -1) {
                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file);
                for (KeywordStructure k :
                        keywordStructures) {
                    DbHandler.insertRecordIntoTweetTable(fileId, k.lineNumber, k.position, k.keyword);
                }
            }
        }
    }
    public static void buildIndexes(String articleDir, String tweetsDir) throws IOException, SQLException {
        DbHandler.setupDatabase();
        makeArticleIndex(articleDir);
        makeTwitterIndex(tweetsDir);
    }

    private static int getOrCreateFileId(String filepath) throws SQLException {
        int fileId = -1;
        boolean success = false;
        try {
            fileId = DbHandler.insertFileName(filepath);
            success = true;
        }
        catch(SQLException ex) {
            // System.out.printf(ex.getMessage());
        }
        if (!success)
        {
            fileId = DbHandler.findFilenameId(filepath);
        }
        return fileId;
    }



}
