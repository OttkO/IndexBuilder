package indexing;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
//Abstract Content Fetcher. YOu can extend it for different inputs e.g articles, tweeets, rss and so on
public abstract class ContentFetcher {

    ArrayList<KeywordStructure> getContent(String filePath) throws IOException {
        ArrayList<KeywordStructure> result = new ArrayList<KeywordStructure>();
        CSVReader reader = new CSVReader(new FileReader(filePath),';'); // reads the file, file is split on ;
        String [] nextLine;
        reader.readNext(); // skips the first row of the file - the header
        int lineNumber = 1;
        while ((nextLine = reader.readNext()) != null) {
            getKeywords(result, nextLine, lineNumber); // gets keywords
            lineNumber++; // increments the line
        }
        return result;
    }

    abstract void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber);
}
