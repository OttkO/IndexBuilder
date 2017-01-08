package indexing;

import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
public class ArticleKeywordFetcher extends ContentFetcher {
    @Override
    void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber) {
        String[] keywords = nextLine[2].split(" ");
        int startPosition = nextLine[0].length() + nextLine[1].length()+3;
        int pos = startPosition;
        for (String keyword: keywords) {
            result.add(new KeywordStructure(keyword.toLowerCase(),lineNumber, pos));
            pos += keyword.length() + 1;
        }
    }
}
