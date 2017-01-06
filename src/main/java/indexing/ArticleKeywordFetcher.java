package indexing;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
public class ArticleKeywordFetcher implements IContentFetcher {
    public ArrayList<KeywordStructure> GetContent(String filePath) throws IOException {
        ArrayList<KeywordStructure> result = new ArrayList<KeywordStructure>();
        CSVReader reader = new CSVReader(new FileReader(filePath),';');
        String [] nextLine;
        reader.readNext();
        int lineNumber = 1;
        while ((nextLine = reader.readNext()) != null) {
            String[] keywords = nextLine[2].split(" ");
            int startPosition = nextLine[0].length() + nextLine[1].length();
            for (String keyword: keywords) {
                result.add(new KeywordStructure(keyword.toLowerCase(),lineNumber,startPosition+2+Util.getPosition(nextLine[2],keyword)));
               // System.out.println(keyword+lineNumber);
            }
            lineNumber++;
        }
        return result;
    }


}
