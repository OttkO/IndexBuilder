package controllers;

import com.google.gson.Gson;
import indexing.Article;
import indexing.QueryProcessor;
import indexing.Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/empty")
public class EmptyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Content-Type", "application/json"); // set the type of return message
        resp.setHeader("Access-Control-Allow-Origin","*"); // add headgier to be accessed by JS
        Gson gson = new Gson(); //initialize gson object
        String jsonData = "n/a"; // initialize response string
        String query = "n/a"; //initialize query
        String emptyResponse = "";
        int startingPoint = 0; // initialize first 100 responses
        try {
            System.out.println("yami");
        } catch (Exception e) {
            e.printStackTrace();
        }
        resp.getWriter().write(gson.toJson(emptyResponse)); // put it in the response


    }
}
