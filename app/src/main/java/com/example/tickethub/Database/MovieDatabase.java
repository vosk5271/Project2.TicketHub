package com.example.tickethub.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tickethub.Interface.MovieDao;
import com.example.tickethub.Interface.UserDao;
import com.example.tickethub.Model.MovieModel;
import com.example.tickethub.Model.UserModel;


@Database(entities = MovieModel.class,exportSchema = false,version = 2)
public abstract class MovieDatabase extends RoomDatabase {
    private static final String DB_NAME= "movie_db";
    private static MovieDatabase instance;
    public static synchronized MovieDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), MovieDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract MovieDao movieDao();
}
