package com.example.fastfood_android_java.database;

import android.content.Context;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fastfood_android_java.dao.AdminDao;
import com.example.fastfood_android_java.dao.DinnerTableDao;
import com.example.fastfood_android_java.dao.FoodDao;
import com.example.fastfood_android_java.dao.OrderDao;
import com.example.fastfood_android_java.dao.OrderDetailDao;
import com.example.fastfood_android_java.model.Admin;
import com.example.fastfood_android_java.model.DinnerTable;
import com.example.fastfood_android_java.model.Food;
import com.example.fastfood_android_java.model.Order;
import com.example.fastfood_android_java.model.OrderDetail;

@Database(entities = {Admin.class,DinnerTable.class, Food.class, Order.class, OrderDetail.class},version = 9)
public abstract class AdminDatabase extends RoomDatabase     {
    private static final String DATABASE_NAME = "admin.db";

    private static AdminDatabase instance;
    public static synchronized AdminDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),AdminDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
    public abstract AdminDao adminDao();
    public abstract DinnerTableDao dinnerTableDao();

    public abstract FoodDao foodDao();

    public abstract OrderDao orderDao();

    public abstract OrderDetailDao orderDetailDao();
}
