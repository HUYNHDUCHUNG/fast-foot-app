package com.example.fastfood_android_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.fastfood_android_java.adapter.OrderDetailAdapter;
import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.ActivityPaymentBinding;
import com.example.fastfood_android_java.model.Admin;
import com.example.fastfood_android_java.model.DinnerTable;
import com.example.fastfood_android_java.model.Order;
import com.example.fastfood_android_java.model.OrderDetail;
import com.example.fastfood_android_java.util.Util;

import java.util.List;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;
    DinnerTable dinnerTable;
    Order order;

    List<OrderDetail> orderDetailList;
    OrderDetailAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Thanh toán");
        Intent i = getIntent();
        if(i != null){
            dinnerTable = (DinnerTable) i.getSerializableExtra("dinner_table");
            order = AdminDatabase.getInstance(this).orderDao().fingOrderByCode(dinnerTable.getCodeOrder());
            orderDetailList = AdminDatabase.getInstance(this).orderDetailDao().getOrderDetailByCode(dinnerTable.getCodeOrder());
        }

        binding.tvTotalPayment.setText("Tổng tiền: "+ Util.formatCurrency(Integer.parseInt(order.getTotalPrice())) );
        adapter = new OrderDetailAdapter(this);
        adapter.setData(orderDetailList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rcvPaymentFoodItem.setLayoutManager(linearLayoutManager);
        binding.rcvPaymentFoodItem.setAdapter(adapter);

        binding.btnPayment.setOnClickListener(view -> {
            order.setStatus("Đã thanh toán");
            dinnerTable.setStatus("Trống");
            dinnerTable.setCodeOrder("");
            AdminDatabase.getInstance(this).orderDao().editOrder(order);
            AdminDatabase.getInstance(this).dinnerTableDao().updateDinnerTable(dinnerTable);
            Toast.makeText(this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
            finish();
        });

    }
}