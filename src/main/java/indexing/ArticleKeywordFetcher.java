package indexing;

import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
///Builds keywords from articles
public class ArticleKeywordFetcher extends ContentFetcher {
    @Override
    void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber) {
        String[] keywords = nextLine[2].split(" "); // split the third column on space
        int startPosition = nextLine[0].length() + nextLine[1].length()+3; // calculate start position
        int pos = startPosition;
        for (String keyword: keywords) { // go through all split words
            result.add(new KeywordStructure(keyword.toLowerCase(),lineNumber, pos)); // add them to result
            pos += keyword.length() + 1; // update position
        }
    }
}
