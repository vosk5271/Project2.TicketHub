package com.example.tickethub.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tickethub.Model.UserModel;


import java.util.List;

@Dao
public interface UserDao {

    @Query("Select * from UserModel ")
    List<UserModel> getUserList();
    @Query("Select * from UserModel WHERE name = :name")
    int isDataExist(String name);

    @Query("Select * from UserModel WHERE name = :name AND name = :pass")
    int  Login(String name,String pass);

    @Query("Select * from UserModel WHERE name = :name AND name = :pass")
    List<UserModel>  LoginData(String name,String pass);
    @Insert
    Long insertUser(UserModel roomModel);
    @Update
    void updateUser(UserModel roomModel);
    @Delete
    void deleteUser(UserModel roomModel);

    @Query("DELETE FROM UserModel WHERE userId = :userId")
    int deleteByUserId(long userId);


}
