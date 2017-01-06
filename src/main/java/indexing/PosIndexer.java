package indexing;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by OttkO on 05-Jan-17.
 */
public class PosIndexer {
    public static void makeIndex(String inputDirectory) throws IOException, SQLException {
        DbHandler.TruncateAll();
        ArticleKeywordFetcher fetcher = new ArticleKeywordFetcher();
        List<String> result = Util.GetFilesInDirectory(inputDirectory);
        for (String file: result) {
            int fileId = getOrCreateFileId(file);
            if (fileId != -1) {
                ArrayList<KeywordStructure> keywordStructures = fetcher.GetContent(file);
                for (KeywordStructure k :
                        keywordStructures) {
                    DbHandler.insertRecordIntoTable(fileId, k.lineNumber, k.position, k.keyword);
                }
            }
        }
    }

    private static int getOrCreateFileId(String filepath) throws SQLException {
        int fileId = -1;
        boolean success = false;
        try {
            fileId = DbHandler.InsertFileName(filepath);
            success = true;
        }
        catch(SQLException ex) {
            // System.out.printf(ex.getMessage());
        }
        if (!success)
        {
            fileId = DbHandler.FindFilenameId(filepath);
        }
        return fileId;
    }



}
