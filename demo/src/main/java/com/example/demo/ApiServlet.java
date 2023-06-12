package com.example.demo;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "api", value = "/api-get-servlet")
public class ApiServlet extends HttpServlet {
    private Repository repository;
    public void init() {
        repository = Repository.getInstance();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        String key = "5a4a48694e6c636837376275786677";
        int totalDataCount = 23304;
        int batchSize = 1000;
        PrintWriter out = response.getWriter();

        for (int startIndex = 1; startIndex < totalDataCount; startIndex += batchSize) {
            int endIndex = Math.min(startIndex + batchSize - 1, totalDataCount - 1);
            String url = "http://openapi.seoul.go.kr:8088/" + key + "/json/TbPublicWifiInfo/" + startIndex + "/" + endIndex + "/";
            OkHttpClient client = new OkHttpClient();
            Request httpRequest = new Request.Builder().url(url).build();
            Response httpResponse = client.newCall(httpRequest).execute();
            if (httpResponse.isSuccessful()) {
                assert httpResponse.body() != null;
                String responseBody = httpResponse.body().string();
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(responseBody, JsonObject.class);
                JsonObject tbPublicWifiInfo = json.getAsJsonObject("TbPublicWifiInfo");
                JsonArray rowArray = tbPublicWifiInfo.getAsJsonArray("row");
                for (JsonElement element : rowArray) {
                    WifiLocation wifiLocation = gson.fromJson(element, WifiLocation.class);
                    repository.save(wifiLocation);
                }
            }
        }
        out.println("<html><body>");
        out.println("<div style=\"text-align: center;\">");
        out.println("<h1>" + repository.getStoreSize() + "개의 WIFI 정보를 정상적으로 저장하였습니다." + "</h1>");
        out.println("<a href=\"javascript:history.back()\">홈 으로 가기</a>");
        out.println("</div>");
        out.println("</body></html>");


    }

}
