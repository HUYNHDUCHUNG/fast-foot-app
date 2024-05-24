package com.example.fastfood_android_java.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.fastfood_android_java.AddFoodActivity;
import com.example.fastfood_android_java.HomeActivity;
import com.example.fastfood_android_java.Interface.IOnClickFoodItemListener;
import com.example.fastfood_android_java.R;
import com.example.fastfood_android_java.UpdateFoodActivity;
import com.example.fastfood_android_java.adapter.FoodAdapter;
import com.example.fastfood_android_java.dao.FoodDao;
import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.FragmentFoodManagerBinding;
import com.example.fastfood_android_java.model.Food;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FoodManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FoodManagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FoodManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FoodManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FoodManagerFragment newInstance(String param1, String param2) {
        FoodManagerFragment fragment = new FoodManagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    FoodAdapter foodAdapter;
    List<Food> mListFood;
    private FragmentFoodManagerBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFoodManagerBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý menu");

        mListFood = new ArrayList<>();
        getDataFood();
        foodAdapter = new FoodAdapter(new IOnClickFoodItemListener() {
            @Override
            public void onClickListener(Food food) {
                Intent i = new Intent(getActivity(), UpdateFoodActivity.class);
                i.putExtra("food",(Serializable) food);
                startActivity(i);

            }
        },getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2,GridLayoutManager.VERTICAL,false);
        foodAdapter.setData(mListFood);
        binding.rcvFood.setLayoutManager(gridLayoutManager);
        binding.rcvFood.setAdapter(foodAdapter);


        return view;
    }

    private void getDataFood() {
        mListFood = AdminDatabase.getInstance(getContext()).foodDao().getAllFood();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListFood = AdminDatabase.getInstance(getActivity()).foodDao().getAllFood();
        foodAdapter.setData(mListFood);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_action_bar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            Intent i = new Intent(getActivity(), AddFoodActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}