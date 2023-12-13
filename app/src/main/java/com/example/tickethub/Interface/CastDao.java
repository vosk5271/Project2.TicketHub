package com.example.tickethub.Interface;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.tickethub.Model.CastModel;
import com.example.tickethub.Model.ShowModel;

import java.util.List;

@Dao
public interface CastDao {

    @Query("Select * from CastModel WHERE movieId = :movieId")
    List<CastModel> getCastList(String movieId);
    @Query("Select * from CastModel WHERE name = :name AND movieId = :movieId")
    int isDataExist(String name,String movieId);

    @Insert
    Long insertCast(CastModel roomModel);
    @Update
    void updateCast(CastModel roomModel);
    @Delete
    void deleteCast(CastModel roomModel);

    @Query("DELETE FROM CastModel WHERE id = :id")
    void deleteByCastId(long id);


}
