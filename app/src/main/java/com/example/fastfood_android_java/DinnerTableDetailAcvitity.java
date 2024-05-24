package com.example.fastfood_android_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfood_android_java.Interface.IOnClickFoodItemListener;
import com.example.fastfood_android_java.adapter.FoodAdapter;
import com.example.fastfood_android_java.adapter.OrderDetailAdapter;
import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.ActivityDinnerTableDetailAcvitityBinding;
import com.example.fastfood_android_java.model.DinnerTable;
import com.example.fastfood_android_java.model.Food;
import com.example.fastfood_android_java.model.Order;
import com.example.fastfood_android_java.model.OrderDetail;
import com.example.fastfood_android_java.util.Util;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DinnerTableDetailAcvitity extends AppCompatActivity {

    private List<Food> mListFood;
    private RecyclerView rcvMenu;
    private FoodAdapter foodAdapter;
    DinnerTable dinnerTable;
    private OrderDetailAdapter orderDetailAdapter;
    int totalPrice = 0;

    String code = "";
    List<OrderDetail> orderDetailList = new ArrayList<>();
    private ActivityDinnerTableDetailAcvitityBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_table_detail_acvitity);
        binding = ActivityDinnerTableDetailAcvitityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent i = getIntent();
        if(i != null){
            dinnerTable = (DinnerTable) i.getSerializableExtra("dinner_table");
            getSupportActionBar().setTitle(dinnerTable.getName());
        }

        createCode();


        rcvMenu = binding.rcvMenu;
        mListFood = new ArrayList<>();
        getDataFood();
        foodAdapter = new FoodAdapter(new IOnClickFoodItemListener() {
            @Override
            public void onClickListener(Food food) {

                showDialogFoodItem(food);
            }
        }, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        foodAdapter.setData(mListFood);
        rcvMenu.setLayoutManager(gridLayoutManager);
        rcvMenu.setAdapter(foodAdapter);





        orderDetailAdapter = new OrderDetailAdapter(this);
        orderDetailAdapter.setData(orderDetailList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,RecyclerView.VERTICAL,false);
        binding.rcvOrdering.setAdapter(orderDetailAdapter);
        binding.rcvOrdering.setLayoutManager(linearLayoutManager);



        binding.btnOrder.setOnClickListener(view -> {
            createOrder();
        });
    }

    private void createOrder() {
        if(orderDetailList.size() == 0 || orderDetailList.isEmpty()){
            Toast.makeText(this, "Vui lòng chọn món", Toast.LENGTH_SHORT).show();
        }else{
            Date currentDate = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy", Locale.getDefault());
            String currentDateTime = dateFormat.format(currentDate);

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefAdmin", Context.MODE_PRIVATE);
            String adminName = sharedPreferences.getString("adminName", "");
            String status = "Chưa thanh toán";
            Order order = new Order(code,String.valueOf(totalPrice),adminName,currentDateTime,status);
            AdminDatabase.getInstance(this).orderDao().insertOrder(order);

            for(OrderDetail o : orderDetailList){
                AdminDatabase.getInstance(this).orderDetailDao().insertOrderDetail(o);
            }

            dinnerTable.setStatus(status);
            dinnerTable.setCodeOrder(code);
            AdminDatabase.getInstance(this).dinnerTableDao().updateDinnerTable(dinnerTable);

            finish();
        }

    }

    private void createCode(){
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HHmmssddMMyyyy");
        code = "ORDER-" +  dateFormat.format(currentDate);
    }



    private void showDialogFoodItem(Food food) {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_order);


        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        windowAtribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAtribute);

        TextView tvFoodName = dialog.findViewById(R.id.tv_dialog_food_name);
        EditText edtQuantity = dialog.findViewById(R.id.edt_quantity);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);

        tvFoodName.setText(food.getName());
        btn_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        btn_ok.setOnClickListener(view -> {
            if(edtQuantity.getText().toString().equals("")){
                Toast.makeText(this, "Vui lòng nhập số lượng món ăn", Toast.LENGTH_SHORT).show();
            }else{
                boolean isOrderDetailExist = false;
                int index = 0;
                for(OrderDetail orderDetail : orderDetailList){
                    if(orderDetail.getFoodId() == food.getId()){
                        int tmp = 0;
                        tmp = Integer.parseInt(orderDetail.getQuantity()) + Integer.parseInt(edtQuantity.getText().toString());
                        orderDetail.setQuantity(tmp + "");
                        orderDetailAdapter.notifyItemChanged(index);
                        isOrderDetailExist = true;
                        break;
                    }
                    index++;
                }
                if(!isOrderDetailExist){
                    orderDetailList.add(new OrderDetail(code,food.getId(),edtQuantity.getText().toString()));
                    orderDetailAdapter.notifyItemChanged(orderDetailList.size() - 1);
                }
                totalPrice +=  Integer.parseInt(food.getPrice()) * Integer.parseInt(edtQuantity.getText().toString());
                binding.tvTotal.setText("Tổng tiền: " + Util.formatCurrency(totalPrice));
                dialog.dismiss();
            }

        });

        dialog.show();
    }

    private void getDataFood() {
        mListFood = AdminDatabase.getInstance(this).foodDao().getAllFood();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_action_bar, menu);
        MenuItem del = menu.findItem(R.id.add);
        del.setIcon(R.drawable.baseline_delete_outline_24);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            showConfirm();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xác nhận");
        builder.setMessage("Bạn có chắc chắn muốn xoá?");

        builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AdminDatabase.getInstance(getApplicationContext()).dinnerTableDao().delDinnerTable(dinnerTable);
                finish();
            }
        });

        builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}