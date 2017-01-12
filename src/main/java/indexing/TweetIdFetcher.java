package indexing;

import java.util.ArrayList;

/**
 * Created by Dennis on 12-1-2017.
 */
public class TweetIdFetcher extends ContentFetcher {

    @Override
    void getKeywords(ArrayList<KeywordStructure> result, String[] nextLine, int lineNumber) {
        if (nextLine.length >= 1){
            result.add(new KeywordStructure(nextLine[0], lineNumber, 0));
        }
    }
}
