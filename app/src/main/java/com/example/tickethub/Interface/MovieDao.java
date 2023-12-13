package com.example.tickethub.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.UserModel;

import java.util.List;

@Dao
public interface MovieDao {

    @Query("Select * from MovieModel")
    List<MovieModel> getMovieList();
    @Query("Select * from MovieModel WHERE name = :name")
    int isDataExist(String name);

    @Insert
    Long insertMovie(MovieModel roomModel);
    @Update
    void updateMovie(MovieModel roomModel);
    @Delete
    void deleteMovie(MovieModel roomModel);

    @Query("DELETE FROM MovieModel WHERE id = :id")
    void deleteByMovieId(long id);


}
