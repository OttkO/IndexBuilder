package controllers;

import com.google.gson.Gson;
import indexing.Article;
import indexing.KeywordStructure;
import indexing.QueryProcessor;
import indexing.Util;
import org.json.JSONArray;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/getArticles")
public class ReaderServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonData = "n/a";
        String query = "n/a";
        try {
            query = req.getParameter("query");

            ArrayList<Article> articlesListFromKeywordsinFiles = Util.getArticlesListFromKeywordsinFiles(QueryProcessor.ProcessQuery(query));
            jsonData = gson.toJson(articlesListFromKeywordsinFiles);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonData);
        resp.setHeader("Content-Type", "application/json");
    }
}
