package indexing;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by OttkO on 04-Jan-17.
 */
public interface IContentFetcher {
    public ArrayList<KeywordStructure> GetContent(String filePath) throws IOException;
}
