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
        Gson gson = new Gson(); //initialize gson object
        String jsonData = "n/a"; // initialize response string
        String query = "n/a"; //initialize query
        int startingPoint = 0; // initialize first 100 responses
        try {
            query = req.getParameter("query"); // receive the query
            startingPoint = Integer.parseInt(req.getParameter("startingPoint")); // assign starting point a value
            ArrayList<Tweet> tweetListFromKeywordsinFiles = Util.getTweetListFromKeywordsinFiles(QueryProcessor.processTweetQuery(query), startingPoint); //get tweets based on query and starting point
            jsonData = gson.toJson(tweetListFromKeywordsinFiles); //convert result to json
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonData); // put it in the response
        resp.setHeader("Content-Type", "application/json"); // set the type of return message
        resp.setHeader("Access-Control-Allow-Origin","*"); // add header to be accessed by JS
    }
}
