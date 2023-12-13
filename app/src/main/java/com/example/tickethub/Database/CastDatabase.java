package com.example.tickethub.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tickethub.Interface.CastDao;
import com.example.tickethub.Interface.ShowDao;
import com.example.tickethub.Model.CastModel;
import com.example.tickethub.Model.ShowModel;


@Database(entities = CastModel.class,exportSchema = false,version = 5)
public abstract class CastDatabase extends RoomDatabase {
    private static final String DB_NAME= "cast_db";
    private static CastDatabase instance;
    public static synchronized CastDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), CastDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract CastDao castDao();
}
