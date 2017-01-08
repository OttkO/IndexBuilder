package controllers;

import com.google.gson.Gson;
import indexing.QueryProcessor;
import indexing.Tweet;
import indexing.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/getTweets")
public class TweetServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Gson gson = new Gson();
        String jsonData = "n/a";
        String query = "n/a";
        int startingPoint = 0;
        try {
            query = req.getParameter("query");
            startingPoint = Integer.parseInt(req.getParameter("startingPoint"));
            Tweet tweets = Util.getTweets(QueryProcessor.processTweetQuery(query),startingPoint);
            jsonData = gson.toJson(tweets);
        /* TODO */

        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonData);
        resp.setHeader("Content-Type", "application/json");
    }
}
