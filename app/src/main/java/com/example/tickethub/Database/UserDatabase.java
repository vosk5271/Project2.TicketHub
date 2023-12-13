package com.example.tickethub.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tickethub.Interface.UserDao;
import com.example.tickethub.Model.UserModel;


@Database(entities = UserModel.class,exportSchema = false,version = 1)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DB_NAME= "user_db";
    private static UserDatabase instance;
    public static synchronized UserDatabase getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),UserDatabase.class,DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract UserDao userDao();
}
