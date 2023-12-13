package com.example.tickethub.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tickethub.Interface.ShowDao;
import com.example.tickethub.Interface.TheatreDao;
import com.example.tickethub.Model.ShowModel;
import com.example.tickethub.Model.TheatreModel;


@Database(entities = TheatreModel.class,exportSchema = false,version = 6)
public abstract class TheatreDatabase extends RoomDatabase {
    private static final String DB_NAME= "theatre_db";
    private static TheatreDatabase instance;
    public static synchronized TheatreDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), TheatreDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract TheatreDao theatreDao();
}
