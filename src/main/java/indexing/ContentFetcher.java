package indexing;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
public abstract class ContentFetcher {

    ArrayList<KeywordStructure> getContent(String filePath) throws IOException {
        ArrayList<KeywordStructure> result = new ArrayList<KeywordStructure>();
        CSVReader reader = new CSVReader(new FileReader(filePath),';');
        String [] nextLine;
        reader.readNext();
        int lineNumber = 1;
        while ((nextLine = reader.readNext()) != null) {
            getKeywords(result, nextLine, lineNumber);
            lineNumber++;
        }
        return result;
    }

    abstract void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber);
}
