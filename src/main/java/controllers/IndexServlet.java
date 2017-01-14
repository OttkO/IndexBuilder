package controllers;

import indexing.Config;
import indexing.PosIndexer;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/makeindex")
public class IndexServlet extends HttpServlet {

    public static File articleDir = new File(new File(Config.pCloudRoot, "preprocessed_articles"), "sander_results");
    public static File tweetsDir = new File(Config.pCloudRoot, "tweets");

    {
        assert articleDir.exists();
        assert tweetsDir.exists();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject(); // initialize JSON object
        try {
            jsonObject.put("status", "Success"); // put success ar response
            jsonObject.put("tweetsDir", tweetsDir);
            //Setup directories to read tweets and articles from, note this is platform independent
            PosIndexer.reBuildIndices(articleDir,tweetsDir); // build the indexes
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonObject.toString()); // put it in the response
        resp.setHeader("Content-Type", "application/json"); // set the type of return message
        resp.setHeader("Access-Control-Allow-Origin","*"); // add header to be accessed by JS
    }
}
