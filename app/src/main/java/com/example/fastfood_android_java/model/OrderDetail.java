package com.example.fastfood_android_java.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "order_detail")
public class OrderDetail {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String code;
    private int foodId;
    private String quantity;

    public OrderDetail(){

    }
    public OrderDetail(String  code,int foodId ,String quantity) {
        this.code = code;
        this.foodId = foodId;
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
