package com.example.fastfood_android_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.fastfood_android_java.Interface.IOnClickDinnerTableItemListener;
import com.example.fastfood_android_java.adapter.DinnerTableAdapter;
import com.example.fastfood_android_java.adapter.ViewPagerAdapter;
import com.example.fastfood_android_java.databinding.ActivityHomeBinding;
import com.example.fastfood_android_java.databinding.FragmentDinnerTableManager2Binding;
import com.example.fastfood_android_java.fragment.DinnerTableManagerFragment;
import com.example.fastfood_android_java.fragment.FoodManagerFragment;
import com.example.fastfood_android_java.fragment.OrderManagerFragment;
import com.example.fastfood_android_java.model.DinnerTable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    List<DinnerTable> mListTable;
    RecyclerView rcvDinnerTable;
    private ActivityHomeBinding binding;
    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    DinnerTableAdapter dinnerTableAdapter;

    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        viewPager = binding.viewpagerHome;
        bottomNavigationView = binding.bottomNav;
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fram_show_fragment,new DinnerTableManagerFragment(),"DinnerTableManager");
        fragmentTransaction.commit();
        bottomNavigationView.getMenu().findItem(R.id.item_nav_home).setChecked(true);
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
//        viewPager.setAdapter(adapter);
//
//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                switch (position){
//                    case 0:
//                        bottomNavigationView.getMenu().findItem(R.id.item_nav_home).setChecked(true);
//                        break;
//                    case 1:
//                        bottomNavigationView.getMenu().findItem(R.id.item_nav_food).setChecked(true);
//                        break;
//                    case 2:
//                        bottomNavigationView.getMenu().findItem(R.id.item_nav_order).setChecked(true);
//                        break;
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });



      bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
          @Override
          public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              if(item.getItemId() == R.id.item_nav_home){
//                  viewPager.setCurrentItem(0);
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.fram_show_fragment,new DinnerTableManagerFragment(),"DinnerTableManager");
                  fragmentTransaction.commit();

              }else if(item.getItemId() == R.id.item_nav_food){
//                  viewPager.setCurrentItem(1);
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.fram_show_fragment,new FoodManagerFragment(),"FoodManager");
                  fragmentTransaction.commit();
              }else{
//                  viewPager.setCurrentItem(2);
                  FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                  fragmentTransaction.replace(R.id.fram_show_fragment,new OrderManagerFragment(),"OrderManager");
                  fragmentTransaction.commit();
              }
              return true;
          }

          public void reloadFragment(){
              Fragment frg = null;
              frg = getSupportFragmentManager().findFragmentByTag("DinnerTableManager");
              final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
              ft.detach(frg);
              ft.attach(frg);
              ft.commit();
          }

      });

//        mListTable = new ArrayList<>();
//
//
//        rcvDinnerTable = binding.rcvDinnerTable;
//        getData();
//         dinnerTableAdapter = new DinnerTableAdapter(new IOnClickDinnerTableItemListener() {
//             @Override
//             public void OnClickListener(String nameTable) {
//                 Intent i = new Intent(HomeActivity.this, DinnerTableDetailAcvitity.class);
//                 i.putExtra("table_name",nameTable);
//                 startActivity(i);
//             }
//         }, this);
//        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
//        dinnerTableAdapter.setData(mListTable);
//        rcvDinnerTable.setLayoutManager(gridLayoutManager);
//        rcvDinnerTable.setAdapter(dinnerTableAdapter);
//
//
//        binding.btnAddTableDinner.setOnClickListener(view -> {
//
//        });
    }


//    private void getData() {
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("dinnertable");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot child : snapshot.getChildren()) {
//                    DinnerTable table = child.getValue(DinnerTable.class);
//                    mListTable.add(table);
//                    Log.d("--firebase", String.valueOf(child.getValue(DinnerTable.class)));
//                }
//                dinnerTableAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
}
