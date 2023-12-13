package com.example.tickethub.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "CastModel")
public class CastModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "role")
    private String role;
    @ColumnInfo(name = "movieId")
    private String movieId;


    @Ignore
    public CastModel(int id, String name, String role,String movieId) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.movieId = movieId;
    }

    public CastModel(String name, String role, String movieId) {
        this.name = name;
        this.role = role;
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public String getRole() {
        return role;
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

    public void setId(int id) {
        this.id = id;
    }
}
