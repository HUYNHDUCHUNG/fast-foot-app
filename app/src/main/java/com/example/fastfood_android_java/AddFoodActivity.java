package com.example.fastfood_android_java;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.ActivityAddFoodBinding;
import com.example.fastfood_android_java.model.Food;

public class AddFoodActivity extends AppCompatActivity {

    ActivityAddFoodBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddFoodBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().setTitle("Thêm menu");

        binding.btnAddFood.setOnClickListener(view -> {
            AddFood();
            finish();
        });
        binding.btnCancelAddFood.setOnClickListener(view -> {
            finish();
        });
    }

    private void AddFood() {
        String pathImage = binding.edtPathImage.getText().toString().trim();
        String foodName = binding.edtFoodName.getText().toString().trim();
        String foodPrice = binding.edtFoodPrice.getText().toString().trim();
        if(!checkIsEmptyInputData(pathImage) && !checkIsEmptyInputData(foodName) && !checkIsEmptyInputData(foodPrice)){
            Food food = new Food();
            food.setName(foodName);
            food.setPrice(foodPrice);
            food.setPathImage(pathImage);
            AdminDatabase.getInstance(this).foodDao().insertFood(food);
            Toast.makeText(this, "Thêm thành công", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkIsEmptyInputData(String s){
        return s.isEmpty() || s.equals("");
    }
}