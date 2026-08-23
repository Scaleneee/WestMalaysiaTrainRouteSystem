package com.trainsystem.model;

public class Station {

    // properties
    private String stationCode;
    private String stationName;

    // constructors
    public Station(String stationCode, String stationName) {
        this.stationCode = stationCode.trim().toUpperCase();
        this.stationName = stationName.trim();
    }

    public Station() {
    }

    // getter and setter methods
    public String getStationCode() {
        return stationCode;
    }

    public void setStationCode(String stationCode) {
        this.stationCode = stationCode;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    // to string method
    @Override
    public String toString() {
        return stationCode + " - " + stationName;
    }

    // equals method
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Station)) return false;

        if (this == obj) return true;

        Station other = (Station) obj;
        // if both of the station has the same stationCode, means they are same
        return other.getStationCode().equals(this.stationCode);
    }

     // get the hashCode of the station
    @Override
    public int hashCode() {
        return stationCode.hashCode();
    }
}
