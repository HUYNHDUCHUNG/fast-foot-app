package com.example.fastfood_android_java.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.fastfood_android_java.fragment.DinnerTableManagerFragment;
import com.example.fastfood_android_java.fragment.FoodManagerFragment;
import com.example.fastfood_android_java.fragment.OrderManagerFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DinnerTableManagerFragment();
            case 1:
                return new FoodManagerFragment();
            case 2:
                return new OrderManagerFragment();
            case 3:
                return new DinnerTableManagerFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }


}
