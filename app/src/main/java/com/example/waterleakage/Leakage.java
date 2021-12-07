package com.example.waterleakage;

public class Leakage
{
    private String leakType,leakStatus,leakDescription,leakBy,leakPlumber;
    private double lat, lon;

    public Leakage(String leakType, String leakStatus, String leakDescription, String leakBy, String leakPlumber, double lat, double lon) {
        this.leakType = leakType;
        this.leakStatus = leakStatus;
        this.leakDescription = leakDescription;
        this.leakBy = leakBy;
        this.leakPlumber = leakPlumber;
        this.lat = lat;
        this.lon = lon;
    }

    public Leakage(){}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getLeakBy() {
        return leakBy;
    }

    public void setLeakBy(String leakBy) {
        this.leakBy = leakBy;
    }

    public String getLeakPlumber() {
        return leakPlumber;
    }

    public void setLeakPlumber(String leakPlumber) {
        this.leakPlumber = leakPlumber;
    }

    public String getLeakType() {
        return leakType;
    }

    public void setLeakType(String leakType) {
        this.leakType = leakType;
    }

    public String getLeakStatus() {
        return leakStatus;
    }

    public void setLeakStatus(String leakStatus) {
        this.leakStatus = leakStatus;
    }


    public String getLeakDescription() {
        return leakDescription;
    }

    public void setLeakDescription(String leakDescription) {
        this.leakDescription = leakDescription;
    }

}
