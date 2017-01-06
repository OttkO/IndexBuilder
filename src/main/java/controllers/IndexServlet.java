package controllers;

import indexing.KeywordStructure;
import indexing.PosIndexer;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Path;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/makeindex")
public class IndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        try {
            PosIndexer.makeIndex("G:\\InputFiles\\debug");
            jsonObject.put("status", "Success");
            // ArrayList<KeywordStructure> keywordStructures = QueryProcessor.ProcessQuery("video and audio");
            //jsonObject.put("test",keywordStructures.get(0).fileName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        resp.getWriter().write(jsonObject.toString());
        resp.setHeader("Content-Type", "application/json");
    }
}
