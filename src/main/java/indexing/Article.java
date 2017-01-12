package indexing;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by OttkO on 06-Jan-17.
 */
//Constructor
public class Article {
    public Article(String id, String[] author_ids, String description, String html, String published_date, String title, String link, String domain) {
        this.id = id;
        this.author_ids = author_ids;
        this.description = description;
        this.html = html;
        this.published_date = published_date;
        this.title = title;
        this.link = link;
        this.domain = domain;
    }

    public Article()
    {}
// public fields
    public String id;
    public String[] author_ids;
    public String description;
    public String html;
    public String published_date;
    public String title;
    public String link;
    public String domain;
}
