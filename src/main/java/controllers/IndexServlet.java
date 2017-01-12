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
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            String tweetsDir = new File(Config.pCloudRoot, "tweets").getAbsolutePath();
            String articleDir = new File(Config.pCloudRoot, "articles").getAbsolutePath();
            PosIndexer.reBuildIndices(articleDir,tweetsDir);
            jsonObject.put("status", "Success");
	    jsonObject.put("tweetsDir", tweetsDir);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonObject.toString());
        resp.setHeader("Content-Type", "application/json");
    }
}
