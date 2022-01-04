package com.gnw.test;

public class BmapPoint {
    private double lng;
    private double lat;

    public BmapPoint() {
    }

    public BmapPoint(double lng, double lat) {
        this.lng = lng;
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    @Override
    public String toString() {
        return "BmapPoint{" +
                "lng=" + lng +
                ", lat=" + lat +
                '}';
    }
}
