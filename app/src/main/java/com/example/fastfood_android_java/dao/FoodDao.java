package com.example.fastfood_android_java.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fastfood_android_java.model.Food;

import java.util.List;

@Dao
public interface FoodDao {

    @Insert
    void insertFood(Food food);

    @Delete
    void delFood(Food food);

    @Query("SELECT * FROM food")
    List<Food> getAllFood();

    @Query("SELECT * FROM food WHERE id = :id")
    Food findFoodById(int id);


    @Update
    void editFood(Food food);
}
