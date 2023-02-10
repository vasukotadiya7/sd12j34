package com.example.authentication;

public class HistoryModel {
    String area,time,transactionID,PNP,level;

    public HistoryModel(){}
    public HistoryModel(String area, String time, String transactionID, String PNP,String level) {
        this.area = area;
        this.time = time;
        this.transactionID = transactionID;
        this.PNP = PNP;
        this.level=level;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTransactionID(String transactionID) {
        this.transactionID = transactionID;
    }

    public void setPNP(String PNP) {
        this.PNP = PNP;
    }

    public void setLevel(String level) {this.level = level;
    }

    public String getArea() {
        return area;
    }

    public String getTime() {
        return time;
    }

    public String getTransactionID() {
        return transactionID;
    }
    public String getLevel(){
        return level;
    }

    public String getPNP() {
        return PNP;
    }

}
