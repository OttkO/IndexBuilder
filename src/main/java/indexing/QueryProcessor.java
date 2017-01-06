package indexing;
import java.security.Key;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by OttkO on 05-Jan-17.
 */
public class QueryProcessor {

    public static ArrayList<KeywordStructure> ProcessQuery(String query) throws SQLException {
        String[] keywords = Util.extractKeywords(query); // break input query into keywords
        String first = keywords[0]; // take the first keyword
        ArrayList<KeywordStructure> candidates = DbHandler.GetKeywordPositions(first); // retrieve all info from db regarding the first keyword

        for (int i = 1; i < keywords.length; i++) { // go over the keywords
            ArrayList<KeywordStructure> results = new ArrayList<>(candidates.size());
            int offset = keywords[i - 1].length() + 1; // how many characters it should look further and a space
            for (KeywordStructure k : //go over reults from first set
                    candidates) {
                int startPos = k.position + offset; //calculate starting position of not initial keyword
                // Is there a k with same file, and linenumber and position: startPos; and keyword = keywords[i]
                KeywordStructure found = DbHandler.FindKeywordInfo(k.fileName, keywords[i], startPos, k.lineNumber); // look for the info
                if(found != null){
                    results.add(found);
                }
            }
            candidates = results;
        }

        return candidates;
    }
}
