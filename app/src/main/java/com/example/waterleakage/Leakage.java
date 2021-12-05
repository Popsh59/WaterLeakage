package com.example.waterleakage;

public class Leakage
{
    private String leakType,leakStatus,leakLocation,leakDescription,leakBy,leakPlumber;

    public Leakage(){}

    public Leakage(String leakType, String leakStatus, String leakLocation, String leakDescription, String leakBy, String leakPlumber)
    {
        this.leakType = leakType;
        this.leakStatus = leakStatus;
        this.leakLocation = leakLocation;
        this.leakDescription = leakDescription;
        this.leakBy = leakBy;
        this.leakPlumber = leakPlumber;
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

    public String getLeakLocation() {
        return leakLocation;
    }

    public void setLeakLocation(String leakLocation) {
        this.leakLocation = leakLocation;
    }

    public String getLeakDescription() {
        return leakDescription;
    }

    public void setLeakDescription(String leakDescription) {
        this.leakDescription = leakDescription;
    }

}
