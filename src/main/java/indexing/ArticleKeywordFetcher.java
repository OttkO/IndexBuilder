package indexing;

import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
///Builds keywords from articles
public class ArticleKeywordFetcher extends ContentFetcher {
    @Override
    void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber) {
        if (nextLine.length < 6){
            // Cannot parse, to small
            return;
        }
        String[] keywords = nextLine[5].split(" "); // split the third column on space
        // calculate start position
        int startPosition = nextLine[0].length();
        for (int i = 1; i <= 4; i++) {
            startPosition += nextLine[i].length() + 3;
        }
        int pos = startPosition;
        for (String keyword: keywords) { // go through all split words
            result.add(new KeywordStructure(keyword.toLowerCase(),lineNumber, pos)); // add them to result
            pos += keyword.length() + 1; // update position
        }
    }
}
