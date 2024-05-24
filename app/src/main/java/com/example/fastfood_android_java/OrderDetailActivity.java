package com.example.fastfood_android_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.fastfood_android_java.adapter.OrderDetailAdapter;
import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.ActivityOrderDetailBinding;
import com.example.fastfood_android_java.model.OrderDetail;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {

    ActivityOrderDetailBinding binding;
    OrderDetailAdapter adapter;

    List<OrderDetail> orderDetailList;
    String code = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Chi tiết đơn hàng");

        Intent i = getIntent();
        if(i != null){
            code = i.getStringExtra("code");
        }
        orderDetailList = new ArrayList<>();
        getData();
        adapter = new OrderDetailAdapter(this);
        adapter.setData(orderDetailList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvOrderDetail.setLayoutManager(linearLayoutManager);
        binding.rcvOrderDetail.setAdapter(adapter);


    }

    private void getData(){

        orderDetailList = AdminDatabase.getInstance(this).orderDetailDao().getOrderDetailByCode(code);
    }


}