package com.example.tickethub.Model;

public class ScreenModel {
    String id,name,noSeats;


    public ScreenModel(String id, String name, String noSeats) {
        this.id = id;
        this.name = name;
        this.noSeats = noSeats;
    }

    public String getId() {
        return id;
    }


    public String getNoSeats() {
        return noSeats;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
