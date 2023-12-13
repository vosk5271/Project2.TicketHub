package com.example.tickethub.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tickethub.Interface.MovieDao;
import com.example.tickethub.Interface.ShowDao;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.ShowModel;


@Database(entities = ShowModel.class,exportSchema = false,version = 9)
public abstract class ShowDatabase extends RoomDatabase {
    private static final String DB_NAME= "show_db";
    private static ShowDatabase instance;
    public static synchronized ShowDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), ShowDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract ShowDao showDao();
}
