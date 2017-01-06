package indexing;

import java.security.Key;

/**
 * Created by OttkO on 04-Jan-17.
 */
public class KeywordStructure {
    public String keyword;
    public int lineNumber;
    public int position;
    public String fileName;

    public KeywordStructure()
    {

    }

    public KeywordStructure(String key, int lineNr, int pos) {
        this.keyword = key;
        this.lineNumber = lineNr;
        this.position = pos;
    }
    public KeywordStructure(String key, int lineNr, int pos, String file) {
        this.keyword = key;
        this.lineNumber = lineNr;
        this.position = pos;
        this.fileName = file;
    }
    @Override
    public String toString() {
        return "KeywordStructure{" +
                "keyword='" + keyword + '\'' +
                ", lineNumber=" + lineNumber +
                ", position=" + position +
                '}';
    }
}
