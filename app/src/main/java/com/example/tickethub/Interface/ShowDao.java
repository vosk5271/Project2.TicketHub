package com.example.tickethub.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.ShowModel;

import java.util.List;

@Dao
public interface ShowDao {

    @Query("Select * from ShowModel")
    List<ShowModel> getShowList();
    @Query("Select * from ShowModel WHERE movie = :movieId AND theatre = :theatreId ")
    List<ShowModel> getMovieShowList(String movieId,String theatreId);
    @Query("Select availableSeat from ShowModel WHERE id = :showId  ")
    int getavailableSeat(int showId);
    @Query("Select * from ShowModel WHERE showTime = :showTime AND movie = :movieId AND theatre = :theatreId " )
    int isDataExist(String showTime,String movieId,String theatreId);

    @Insert
    Long insertShow(ShowModel roomModel);
    @Query("UPDATE ShowModel SET availableSeat = :seat WHERE id = :showId AND movie = :movieId AND theatre = :theatreId " )
    int updateSeat(String showId,String movieId,String theatreId,int seat);
    @Update
    void updateShow(ShowModel roomModel);
    @Delete
    void deleteShow(ShowModel roomModel);

    @Query("DELETE FROM ShowModel WHERE id = :id")
    void deleteByShowId(long id);


}
