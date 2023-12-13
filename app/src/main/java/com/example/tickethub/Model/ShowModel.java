package com.example.tickethub.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ShowModel")
public class ShowModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "showTime")
    private String showTime;
@ColumnInfo(name = "duration")
    private String duration;
@ColumnInfo(name = "movie")
    private String movie;
@ColumnInfo(name = "theatre")
    private String theatre;
@ColumnInfo(name = "totalSeat")
    private int totalSeat;
@ColumnInfo(name = "availableSeat")
    private int availableSeat;

@Ignore
    public ShowModel(int id, String showTime, String duration, String movie,String theatre, int totalSeat, int availableSeat) {
        this.id = id;
        this.showTime = showTime;
        this.duration = duration;
        this.movie = movie;
        this.theatre = theatre;
        this.totalSeat = totalSeat;
        this.availableSeat = availableSeat;
    }


    public ShowModel(String showTime, String duration, String movie,String theatre, int totalSeat, int availableSeat) {
        this.showTime = showTime;
        this.duration = duration;
        this.movie = movie;
        this.theatre = theatre;
        this.totalSeat = totalSeat;
        this.availableSeat = availableSeat;

    }

    public int getId() {
        return id;
    }

    public String getShowTime() {
        return showTime;
    }

    public String getDuration() {
        return duration;
    }

    public String getMovie() {
        return movie;
    }

    public int getTotalSeat() {
        return totalSeat;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public String getTheatre() {
        return theatre;
    }

    public void setId(int id) {
        this.id = id;
    }
}
