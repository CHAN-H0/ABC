package com.example.demo;
import java.util.*;

public class Repository {
    private static Repository instance;

    private List<WifiLocation> store = new ArrayList<>();
    private List<WifiLocation> nearLocations = new ArrayList<>();
    public static synchronized Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public List<WifiLocation> findNear(double latitude, double longitude){
        nearLocations.clear();
        System.out.println("FindNear 실행");
        System.out.println("store is" + store);
        for (WifiLocation location : store) {
            double latitude2 = Double.parseDouble(location.getLNT());
            double longitude2 = Double.parseDouble(location.getLAT());
            double distance = DistanceCalculator.calculateDistance(latitude, longitude, latitude2, longitude2);
//            System.out.println(latitude + " latitude in findNear");
//            System.out.println(longitude + " longitude in findNear");
//            System.out.println(latitude2 + " latitude2 in findNear");
//            System.out.println(longitude2 + " longitude2 in findNear");
//            System.out.println(distance + " distant in findNear");
            String distanceString = Double.toString(distance);
            location.setDistance(distanceString);
            if(distance < 1){
                nearLocations.add(location);
            }
            nearLocations.sort(Comparator.comparingDouble(implication -> Double.parseDouble(implication.getDistance())));
        }
        return nearLocations;
    }
    public void save(WifiLocation wifiLocation) {
        if (!store.contains(wifiLocation)) {
            store.add(wifiLocation);
        }
    }

    public int getStoreSize() {
        return store.size();
    }
}
