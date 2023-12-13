package com.example.tickethub.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tickethub.Model.BookingModel;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.SeatModel;

import java.util.List;

@Dao
public interface BookingDao {

    @Query("Select * from BookingModel")
    List<BookingModel> getBookingList();
    @Query("Select * from BookingModel WHERE userId=:userId")
    List<BookingModel> getUserBookingList(String userId);
    @Query("Select * from BookingModel WHERE movieId=:movieId AND showId=:showId AND theatreId=:theatreId")
    List<BookingModel> getBookingSeatList(String movieId, String showId, String theatreId);
    @Query("Select * from BookingModel WHERE name = :name")
    int isDataExist(String name);

    @Insert
    Long insertBooking(BookingModel roomModel);
    @Update
    void updateBooking(BookingModel roomModel);
    @Delete
    void deleteBooking(BookingModel roomModel);

    @Query("DELETE FROM BookingModel WHERE id = :id")
    void deleteByBookingId(long id);


}
