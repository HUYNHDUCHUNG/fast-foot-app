package com.example.fastfood_android_java.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "dinner_table")
public class DinnerTable implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String status;
    private String codeOrder;

    public DinnerTable(){

    }


    public DinnerTable(String name, String status, String codeOrder) {
        this.name = name;
        this.status = status;
        this.codeOrder = codeOrder;
    }

    public String getCodeOrder() {
        return codeOrder;
    }

    public void setCodeOrder(String codeOrder) {
        this.codeOrder = codeOrder;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}

