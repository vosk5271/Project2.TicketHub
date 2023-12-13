package com.example.tickethub.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "MovieModel")

public class MovieModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "genre")
    private String genre;
    @ColumnInfo(name = "duration")
    private  String duration;
    @ColumnInfo(name = "date")
    private String date;
    @ColumnInfo(name = "poster")
    private String poster;
    @ColumnInfo(name = "two_d")
    private boolean two_d;
    @ColumnInfo(name = "three_d")
    private boolean three_d ;
    @Ignore
    public MovieModel(int id, String name, String genre, String duration, String date, String poster, boolean two_d, boolean three_d) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.poster = poster;
        this.two_d = two_d;
        this.three_d = three_d;
    }

    public MovieModel(String name, String genre, String duration, String date, String poster, boolean two_d, boolean three_d) {
        this.name = name;
        this.genre = genre;
        this.duration = duration;
        this.date = date;
        this.poster = poster;
        this.two_d = two_d;
        this.three_d = three_d;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public String getDuration() {
        return duration;
    }

    public String getDate() {
        return date;
    }

    public String getPoster() {
        return poster;
    }

    public boolean getTwo_d() {
        return two_d;
    }

    public boolean getThree_d() {
        return three_d;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name ;
    }
}
