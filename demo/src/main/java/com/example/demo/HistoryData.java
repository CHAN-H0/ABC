package com.example.demo;
import lombok.Data;
@Data
public class HistoryData {
    private String id;
    private String latitude;
    private String longitude;
    private String dateTimeString;

    public HistoryData(String id, String latitude, String longitude, String dateTimeString) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.dateTimeString = dateTimeString;
    }

}
