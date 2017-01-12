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
                long t0 = System.currentTimeMillis();

                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file);

                long t1 = System.currentTimeMillis();
                long time_read = t1 - t0;

                for (KeywordStructure k :
                        keywordStructures) {
                    DbHandler.insertRecordIntoTweetTable(fileId, k.lineNumber, k.position, k.keyword);
                }

                long time_write = System.currentTimeMillis() - t0;
                System.out.println("time_read = " + time_read + "ms, time_write = " + time_write + "ms");
            }
        }
    }
    public static void makeTwitterIndexBatch(String inputDirectory) throws IOException, SQLException {
        TweetKeywordFetcher fetcher = new TweetKeywordFetcher();
        List<String> result = Util.getFilesInDirectory(inputDirectory);
        for (String file: result) {
            int fileId = getOrCreateFileId(file);
            if (fileId != -1) {
                long t0 = System.currentTimeMillis();

                ArrayList<KeywordStructure> keywordStructures = fetcher.getContent(file);

                long t1 = System.currentTimeMillis();
                long time_read = t1 - t0;
                System.out.println("time_read = " + time_read + "ms");

                List<Integer> lineNumbers = keywordStructures.stream().map(kws -> kws.lineNumber).collect(Collectors.toList());
                List<Integer> positions = keywordStructures.stream().map(kws -> kws.position).collect(Collectors.toList());
                List<String> keywords = keywordStructures.stream().map(kws -> kws.keyword).collect(Collectors.toList());
                DbHandler.insertRecordsIntoTweetTable(fileId, lineNumbers, positions, keywords);

                long time_write = System.currentTimeMillis() - t0;
                System.out.println("time_write = " + time_write + "ms");
            }
        }
    }
    public static void reBuildIndexes(String articleDir , String tweetsDir) throws IOException, SQLException {
        tweetsDir = new File(new File(Config.pCloudRoot, "testAPIdata"), "tweets").getAbsolutePath();
        articleDir = new File(new File(Config.pCloudRoot, "testAPIdata"), "articles").getAbsolutePath();
        DbHandler.setupDatabase();
        makeArticleIndex(articleDir);
        makeTwitterIndexBatch(tweetsDir);
    }

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
