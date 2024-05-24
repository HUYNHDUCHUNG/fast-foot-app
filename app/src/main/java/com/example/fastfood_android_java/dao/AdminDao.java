package com.example.fastfood_android_java.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.fastfood_android_java.model.Admin;

import java.util.List;

@Dao
public interface AdminDao {
    @Insert
    void insertAdmin(Admin admin);

    @Query("SELECT * FROM admin WHERE email = :email and password = :password ")
    List<Admin> checkLoginAdmin(String email, String password);


}
