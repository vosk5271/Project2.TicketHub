package com.example.tickethub.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.tickethub.Converters;
import com.example.tickethub.Interface.BookingDao;
import com.example.tickethub.Interface.MovieDao;
import com.example.tickethub.Model.BookingModel;
import com.example.tickethub.Model.MovieModel;


@Database(entities = BookingModel.class,exportSchema = false,version = 8)
@TypeConverters({Converters.class})
public abstract class BookingDatabase extends RoomDatabase {
    private static final String DB_NAME= "booking_db";
    private static BookingDatabase instance;
    public static synchronized BookingDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), BookingDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract BookingDao bookingDao();
}
