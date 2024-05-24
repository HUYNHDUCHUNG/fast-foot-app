package com.example.fastfood_android_java.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfood_android_java.Interface.IOnClickFoodItemListener;
import com.example.fastfood_android_java.databinding.ItemFoodBinding;
import com.example.fastfood_android_java.model.Food;
import com.example.fastfood_android_java.util.Util;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {


    private final IOnClickFoodItemListener iOnClickFoodItemListener;
    private Context context;
    private List<Food> mListFood;

    public FoodAdapter(IOnClickFoodItemListener iOnClickFoodItemListener, Context context) {
        this.iOnClickFoodItemListener = iOnClickFoodItemListener;
        this.context = context;
    }

    public void setData(List<Food> list){
        this.mListFood = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemFoodBinding binding = ItemFoodBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new FoodViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = mListFood.get(position);
        if(food == null) return;
        Glide.with(this.context).load(food.getPathImage()).into(holder.imv_food);
        holder.tvName.setText(food.getName());
        holder.tvPrice.setText("Giá: " + Util.formatCurrency(Integer.parseInt(food.getPrice())));
        holder.linearLayout_item_food.setOnClickListener(view -> {
            iOnClickFoodItemListener.onClickListener(food);
        });
    }

    @Override
    public int getItemCount() {
        return mListFood.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder{

        private ItemFoodBinding binding;
        ImageView imv_food;
        TextView tvPrice;
        TextView tvName;
        LinearLayout linearLayout_item_food;
        public FoodViewHolder(ItemFoodBinding binding) {
            super(binding.getRoot());

            imv_food = binding.imvFood;
            tvName = binding.tvFoodName;
            tvPrice = binding.tvFoodPrice;
            linearLayout_item_food = binding.linearItemFood;
        }
    }
}
