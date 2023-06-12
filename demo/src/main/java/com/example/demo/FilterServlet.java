package com.example.demo;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@WebServlet(name = "filter", value = "/filter-servlet")
public class FilterServlet extends HttpServlet {
    private final DataStorage dataStorage = new DataStorage();
    private Repository repository;
    public void init() {
        repository = Repository.getInstance();
    }
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String latitudeString  = request.getParameter("latitude");
        String longitudeString = request.getParameter("longitude");
        double latitude = Double.parseDouble(latitudeString);
        double longitude = Double.parseDouble(longitudeString);

        try {
            dataStorage.storeData(latitudeString, longitudeString);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        System.out.println("여기 필터 서블릿 실행");

        List<WifiLocation> nearLocationsObjs = repository.findNear(latitude, longitude);
        int limit = 20;
        List<WifiLocation> limitedNearLocations = nearLocationsObjs.subList(0, Math.min(nearLocationsObjs.size(), limit));

        request.setAttribute("nearLocations", limitedNearLocations);
        request.getRequestDispatcher("index.jsp").forward(request, response);


        Gson gson = new Gson();
        String nearLocations2 = gson.toJson(nearLocationsObjs);
        List nearLocations = gson.fromJson(nearLocations2, List.class);

        request.setAttribute("nearLocations", nearLocations);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}
