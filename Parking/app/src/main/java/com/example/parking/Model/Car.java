package com.example.parking.Model;

public class Car {
    String id,bienxe,time,date;

    public String getId() {
        return id;
    }

    public String getBienxe() {
        return bienxe;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public Car(String id, String bienxe, String time, String date) {
        this.id = id;
        this.bienxe = bienxe;
        this.time = time;
        this.date = date;
    }
    public Car( String bienxe, String time, String date) {
        this.bienxe = bienxe;
        this.time = time;
        this.date = date;
    }
}
