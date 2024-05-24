package com.example.fastfood_android_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.ActivityUpdateFoodBinding;
import com.example.fastfood_android_java.model.Food;

import java.io.Serializable;

public class UpdateFoodActivity extends AppCompatActivity {

    ActivityUpdateFoodBinding binding;
    Food food;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityUpdateFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Thông tin món ăn");
        Intent i = getIntent();
        if(i != null){
            food = (Food) i.getSerializableExtra("food");
        }
        binding.edtFoodName.setText(food.getName());
        binding.edtPathImage.setText(food.getPathImage());
        binding.edtFoodPrice.setText(food.getPrice());

        binding.btnSaveFood.setOnClickListener(view -> {
            String pathImage = binding.edtPathImage.getText().toString();
            String name = binding.edtFoodName.getText().toString();
            String price = binding.edtFoodPrice.getText().toString();

            if(!checkInputData(pathImage) && !checkInputData(name) && !checkInputData(price)){
                food.setPathImage(pathImage);
                food.setName(name);
                food.setPrice(price);

                AdminDatabase.getInstance(this).foodDao().editFood(food);
                finish();
            }else{
                Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }

        });

        binding.btnCancelAddFood.setOnClickListener(view -> {
            finish();
        });
    }

    private boolean checkInputData(String s){
        return s.isEmpty() && s.equals("");
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
                AdminDatabase.getInstance(getApplicationContext()).foodDao().delFood(food);
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