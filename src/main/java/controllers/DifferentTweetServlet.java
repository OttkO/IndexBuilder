package controllers;

import com.google.gson.Gson;
import indexing.QueryProcessor;
import indexing.Tweet;
import indexing.TweetsMap;
import indexing.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/getTweetsNotRaw")
public class DifferentTweetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonData = "n/a";
        String query = "n/a";
        int startingPoint = 0;
        try {
            query = req.getParameter("query");
            startingPoint = Integer.parseInt(req.getParameter("startingPoint"));
            ArrayList<Tweet> tweetListFromKeywordsinFiles = Util.getTweetListFromKeywordsinFiles(QueryProcessor.processTweetQuery(query), startingPoint);
            jsonData = gson.toJson(tweetListFromKeywordsinFiles);
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonData);
        resp.setHeader("Content-Type", "application/json");
    }
}
