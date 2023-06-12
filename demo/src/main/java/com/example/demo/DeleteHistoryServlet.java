package com.example.demo;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "deleteHistory", value = "/delete-history-servlet")
public class DeleteHistoryServlet extends HttpServlet {
    ViewHistory viewHistory = new ViewHistory();
    private DataStorage dataStorage = new DataStorage();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String idString  = request.getParameter("id");
        int id = Integer.parseInt(idString);
        System.out.println(id);
        try {
            dataStorage.deleteData(id);
            viewHistory.doGet(request, response);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
