package com.example.fastfood_android_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.ActivityMainBinding;
import com.example.fastfood_android_java.model.Admin;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();
        binding.logIn.setOnClickListener(view -> {
            showLayoutLogin();
        });

        binding.singUp.setOnClickListener(view -> {
            binding.layoutLogin.setVisibility(View.GONE);
            binding.layoutRegister.setVisibility(View.VISIBLE);
            binding.logIn.setBackgroundResource(R.color.tranparent);
            binding.singUp.setBackgroundResource(R.drawable.switch_trcks);
            binding.logIn.setTextColor(getResources().getColor(R.color.button));
            binding.singUp.setTextColor(getResources().getColor(R.color.white));


        });





        binding.btnLogin.setOnClickListener(view -> {


            loginAdmin();

        });

        binding.btnRegister.setOnClickListener(view -> {
            registerAdmin();
        });

    }

    private void showLayoutLogin(){
        binding.layoutLogin.setVisibility(View.VISIBLE);
        binding.layoutRegister.setVisibility(View.GONE);
        binding.logIn.setBackgroundResource(R.drawable.switch_trcks);
        binding.singUp.setBackgroundResource(R.color.tranparent);
        binding.logIn.setTextColor(getResources().getColor(R.color.white));
        binding.singUp.setTextColor(getResources().getColor(R.color.button));
    }

    private void registerAdmin(){
        if(checkInputDataNotEmpty()){
            String email = binding.edtEmailRegister.getText().toString().trim();
            String fullName = binding.edtFullNameRegister.getText().toString().trim();
            String password = binding.edtPasswordRegister.getText().toString().trim();
            String phoneNumber = binding.edtPhoneNumberRegister.getText().toString().trim();
            Admin admin = new Admin(fullName,email,password,phoneNumber);
            AdminDatabase.getInstance(this).adminDao().insertAdmin(admin);
            showLayoutLogin();
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

        }else{
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkInputDataNotEmpty(){
        return !binding.edtFullNameRegister.getText().toString().isEmpty()
                && !binding.edtEmailRegister.getText().toString().isEmpty()
                && !binding.edtPasswordRegister.getText().toString().isEmpty()
                && !binding.edtPhoneNumberRegister.getText().toString().isEmpty();
    }

    private void loginAdmin() {
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();


        List<Admin> admins =  AdminDatabase.getInstance(this).adminDao().checkLoginAdmin(email,password);

        if(admins != null && !admins.isEmpty()){
            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
             intent.putExtra("key_email",email);
             startActivity(intent);

            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefAdmin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("adminName", admins.get(0).getFullName());
            editor.apply();
            finish();
        }else{
            Toast.makeText(this, "Kiểm Tra Lại Thông Tin Đăng Nhập", Toast.LENGTH_SHORT).show();
        }
    }












//    private void loginAdmin() {
//        String email = binding.edtEmail.getText().toString().trim();
//        String password = binding.edtPassword.getText().toString().trim();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("admin");
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    if (child.getValue(Admin.class).getEmail().equals(email)){
//                        Admin admin = child.getValue(Admin.class);
//                        Toast.makeText(MainActivity.this, admin.getEmail(), Toast.LENGTH_SHORT).show();
//                        if(admin.getPassword().equals(password)){
//                            Intent intent = new Intent(MainActivity.this,HomeActivity.class);
//                            intent.putExtra("key_email",email);
//                            startActivity(intent);
//                        }
//                        else {
//                            Toast.makeText(MainActivity.this, "Kiem Tra Lai Thong Tin", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}