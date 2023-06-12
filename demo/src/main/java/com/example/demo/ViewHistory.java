package com.example.demo;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "history", value = "/view-history-servlet")
public class ViewHistory extends HttpServlet {
    private DataStorage dataStorage = new DataStorage();
    private List<HistoryData> historyDataList = new ArrayList<>();
    List<Map<String, String>> historyList = new ArrayList<>();

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            historyDataList = dataStorage.retrieveData();
            historyList.clear();
            for (HistoryData data : historyDataList) {
                Map<String, String> historyMap = new HashMap<>();
                historyMap.put("id", data.getId());
                historyMap.put("latitude", data.getLatitude());
                historyMap.put("longitude", data.getLongitude());
                historyMap.put("dateTimeString", data.getDateTimeString());
                historyList.add(historyMap);
            }
            request.setAttribute("includedPage", "history");
            request.setAttribute("result", historyList);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
