package com.example.tickethub.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity(tableName = "TheatreModel")
public class TheatreModel {
    @PrimaryKey(autoGenerate = true)
    private int  id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "movieId")
    private String movieId;

    public TheatreModel(String name, String address, String movieId) {
        this.name = name;
        this.address = address;
        this.movieId = movieId;
    }
@Ignore
    public TheatreModel(int id, String name, String address, String movieId) {

        this.id = id;
        this.name = name;
        this.address = address;
        this.movieId = movieId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMovieId() {
        return movieId;
    }

    @Override
    public String toString() {
        return name ;
    }
}
