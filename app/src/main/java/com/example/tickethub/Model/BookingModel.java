package com.example.tickethub.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "BookingModel")
public class BookingModel {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "name")
    private String name;
     @ColumnInfo(name = "userId")
    private String userId;
    @ColumnInfo(name = "show")
    private  String show;
    @ColumnInfo(name = "showId")
    private  String showId;
    @ColumnInfo(name = "movieId")
    private  String movieId;
    @ColumnInfo(name = "movieName")
    private  String movieName;
    @ColumnInfo(name = "theatreId")
    private  String theatreId;
    @ColumnInfo(name = "bookingStatus")
    private String bookingStatus;
    @ColumnInfo(name = "amount")
    private String amount;

    @ColumnInfo(name = "seatModels")
    private List<String> seatModels;
    @ColumnInfo(name = "poster")
    private String poster;

    @Ignore
    public BookingModel(int id, String name,String userId, String show, String showId, String movieId, String movieName, String theatreId, String bookingStatus, String amount,String poster, List<String> seatModels) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.show = show;
        this.showId = showId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.theatreId = theatreId;
        this.bookingStatus = bookingStatus;
        this.amount = amount;
        this.poster = poster;
        this.seatModels = seatModels;
    }

    public BookingModel(String name, String userId, String show, String showId, String movieId, String movieName,  String theatreId, String bookingStatus, String amount, String poster,List<String> seatModels) {
        this.name = name;
        this.userId = userId;
        this.show = show;
        this.showId = showId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.theatreId = theatreId;
        this.bookingStatus = bookingStatus;
        this.amount = amount;
        this.poster = poster;
        this.seatModels = seatModels;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getShow() {
        return show;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public String getAmount() {
        return amount;
    }

    public String getPoster() {
        return poster;
    }

    public String getMovieName() {
        return movieName;
    }

    public List<String> getSeatModels() {
        return seatModels;
    }

    public String getUserId() {
        return userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShowId() {
        return showId;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getTheatreId() {
        return theatreId;
    }
}
