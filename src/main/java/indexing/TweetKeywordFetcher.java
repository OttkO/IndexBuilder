package indexing;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by OttkO on 06-Jan-17.
 */
public class TweetKeywordFetcher extends ContentFetcher {
    @Override
    void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber) {
        String processedOutput = nextLine[15].replace("'", "\"");
        String[] keywords = Util.getStringArray(new JSONArray(processedOutput));
        int startPosition = 0;
        int columns = 0;
        for (; columns < 15; columns++) {
            startPosition += nextLine[columns].length();
        }
        startPosition += columns;
        for (String keyword : keywords) {
            result.add(new KeywordStructure(keyword.toLowerCase(), lineNumber, startPosition + 1 + Util.getPosition(nextLine[15], keyword)));
        }

    }
}
