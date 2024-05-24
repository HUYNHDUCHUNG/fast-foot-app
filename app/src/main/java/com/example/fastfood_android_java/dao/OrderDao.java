package com.example.fastfood_android_java.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.fastfood_android_java.model.Order;

import java.util.List;

@Dao
public interface OrderDao {

    @Insert
    void insertOrder(Order order);

    @Query("SELECT * FROM `order` ORDER BY id DESC")
    List<Order> getAllOrder();

    @Query("SELECT * FROM `order` WHERE code= :code ")
    Order fingOrderByCode(String code);

    @Update
    void editOrder(Order order);
}
