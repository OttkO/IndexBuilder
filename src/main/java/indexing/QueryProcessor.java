package indexing;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by OttkO on 05-Jan-17.
 */
public class QueryProcessor {

    public static ArrayList<KeywordStructure> processArticleQuery(String query) throws SQLException {
        String[] keywords = Util.extractKeywords(query); // break input query into keywords
        String first = keywords[0]; // take the first keyword
        ArrayList<KeywordStructure> candidates = DbHandler.getKeywordPositionsArticleIndexes(first); // retrieve all info from db regarding the first keyword

        for (int i = 1; i < keywords.length; i++) { // go over the keywords
            ArrayList<KeywordStructure> results = new ArrayList<>(candidates.size());
            int offset = keywords[i - 1].length() + 1; // how many characters it should look further and a space
            for (KeywordStructure k : //go over reults from first set
                    candidates) {
                int startPos = k.position + offset; //calculate starting position of not initial keyword
                // Is there a k with same file, and linenumber and position: startPos; and keyword = keywords[i]
                KeywordStructure found = DbHandler.findKeywordInfoArticleIndexes(k.fileName, keywords[i], startPos, k.lineNumber); // look for the info
                if(found != null){
                    results.add(found);
                }
            }
            candidates = results;
        }

        return candidates;
    }
    public static ArrayList<KeywordStructure> processTweetQuery(String query) throws SQLException {
        String[] keywords = Util.extractKeywords(query); // break input query into keywords
        String first = keywords[0]; // take the first keyword
        ArrayList<KeywordStructure> candidates = DbHandler.getKeywordPositionsTweetIndexes(first); // retrieve all info from db regarding the first keyword

        for (int i = 1; i < keywords.length; i++) { // go over the keywords
            ArrayList<KeywordStructure> results = new ArrayList<>(candidates.size());
            int offset = keywords[i - 1].length() + 1; // how many characters it should look further and a space
            for (KeywordStructure k : //go over reults from first set
                    candidates) {
                int startPos = k.position + offset; //calculate starting position of not initial keyword
                // Is there a k with same file, and linenumber and position: startPos; and keyword = keywords[i]
                KeywordStructure found = DbHandler.findKeywordInfoTweetIndexes(k.fileName, keywords[i], startPos, k.lineNumber); // look for the info
                if(found != null){
                    results.add(found);
                }
            }
            candidates = results;
        }

        return candidates;
    }
    public static ArrayList<KeywordStructure> getTweetById(String id) throws SQLException {
        ArrayList<KeywordStructure> tweetIds = DbHandler.getKeywordPositionsTweetIds(id);

        ArrayList<KeywordStructure> results = new ArrayList<>();
        for (KeywordStructure k :
                tweetIds) {
            KeywordStructure found = DbHandler.findKeywordInfoTweetIndexes(k.fileName, id, 0, k.lineNumber); // look in the db
            if (found != null) {
                results.add(found);
            }
        }

        return results;
    }
}
