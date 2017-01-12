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
            String tweetsDir = new File(new File(Config.pCloudRoot, "testAPIdata"), "tweets").getAbsolutePath();
            String articleDir = new File(new File(Config.pCloudRoot, "testAPIdata"), "articles").getAbsolutePath();
            PosIndexer.reBuildIndexes(articleDir,tweetsDir);
            jsonObject.put("status", "Success");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonObject.toString());
        resp.setHeader("Content-Type", "application/json");
    }
}
