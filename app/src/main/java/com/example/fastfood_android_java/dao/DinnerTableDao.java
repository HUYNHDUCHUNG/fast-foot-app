package com.example.fastfood_android_java.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fastfood_android_java.model.DinnerTable;

import java.util.List;

@Dao
public interface DinnerTableDao {


    @Query("SELECT * FROM dinner_table")
    List<DinnerTable> getAllDinnerTable();

    @Insert
    void insertDinnerTable(DinnerTable dinnerTable);

    @Delete
    void delDinnerTable(DinnerTable dinnerTable);


    @Update
    void updateDinnerTable(DinnerTable dinnerTable);

}
