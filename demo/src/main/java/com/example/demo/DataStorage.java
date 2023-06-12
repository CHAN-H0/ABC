package com.example.demo;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DataStorage {
    private static final String DB_URL = "jdbc:sqlite:C:\\ABC\\demo\\test.db";

    public void deleteData(int id) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String query = "DELETE FROM history WHERE id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void storeData(String latitude, String longitude) throws ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");

        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String createTableQuery = "CREATE TABLE IF NOT EXISTS history (ID INTEGER PRIMARY KEY AUTOINCREMENT, Xcoords TEXT NOT NULL, Ycoords TEXT NOT NULL, DT TEXT NOT NULL)";
            connection.createStatement().execute(createTableQuery);

            String query = "INSERT INTO history (Xcoords, Ycoords, DT) VALUES (?, ?, ?)";
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            String dateTimeString = now.format(formatter);
            System.out.println(dateTimeString);
            System.out.println(latitude);
            System.out.println(longitude);
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, latitude);
            statement.setString(2, longitude);
            statement.setString(3, dateTimeString);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<HistoryData> retrieveData() throws ClassNotFoundException {
        List<HistoryData> historyDataList = new ArrayList<>();
        Class.forName("org.sqlite.JDBC");
        try {
            Connection connection = DriverManager.getConnection(DB_URL);
            String query = "SELECT * FROM history";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String latitude = resultSet.getString("Xcoords");
                String longitude = resultSet.getString("Ycoords");
                String dateTimeString = resultSet.getString("DT");
                HistoryData historyData = new HistoryData(id, latitude, longitude, dateTimeString);
                historyDataList.add(historyData);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(historyDataList);
        return historyDataList;
    }
}
