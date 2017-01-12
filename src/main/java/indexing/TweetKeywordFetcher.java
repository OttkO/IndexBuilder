package indexing;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by OttkO on 06-Jan-17.
 */
//builds keywords from tweets
public class TweetKeywordFetcher extends ContentFetcher {
    @Override
    void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber) {
        if(nextLine.length <= 15){
            return;
        }
        try {
            String processedOutput = nextLine[15].replace("'", "\"");
            String[] keywords = Util.getStringArray(new JSONArray(processedOutput));
            int startPosition = 0; // initialze start position
            int columns = 0; //initiliaze columns
            for (; columns < 15; columns++) {
                startPosition += nextLine[columns].length();
            }
            startPosition += columns; // calculate start position
            int pos = startPosition;
            for (String keyword : keywords) {
                result.add(new KeywordStructure(keyword.toLowerCase(), lineNumber, pos));
                pos += keyword.length() + 1;
            }
        } catch (Exception e) {
            System.err.println("Could not parse: "+ Arrays.toString(nextLine));
            return;
        }

    }
}
