package com.example.fastfood_android_java.dao;

import androidx.room.Dao;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fastfood_android_java.model.OrderDetail;

import java.util.List;

@Dao
public interface OrderDetailDao {

    @Insert
    void insertOrderDetail(OrderDetail orderDetail);

    @Query("SELECT * FROM order_detail WHERE code = :code")
    List<OrderDetail> getOrderDetailByCode(String code);
}
