package com.example.tickethub.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tickethub.Model.ShowModel;
import com.example.tickethub.Model.TheatreModel;

import java.util.List;

@Dao
public interface TheatreDao {

    @Query("Select * from TheatreModel")
    List<TheatreModel> getTheatreList();
     @Query("Select * from TheatreModel WHERE  movieId = :movieId")
    List<TheatreModel> getMovieTheatreList(String movieId);
    @Query("Select * from TheatreModel WHERE name = :name AND movieId = :movieId")
    int isDataExist(String name,String movieId);

    @Insert
    Long insertTheatre(TheatreModel roomModel);
    @Update
    void updateTheatre(TheatreModel roomModel);
    @Delete
    void deleteTheatre(TheatreModel roomModel);

    @Query("DELETE FROM TheatreModel WHERE id = :id")
    void deleteByTheatreId(long id);


}
