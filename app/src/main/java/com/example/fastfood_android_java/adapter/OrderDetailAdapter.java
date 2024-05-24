package com.example.fastfood_android_java.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.ItemOrderDetailBinding;
import com.example.fastfood_android_java.model.Food;
import com.example.fastfood_android_java.model.OrderDetail;

import java.util.List;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder> {


    Context context;
    List<OrderDetail> mListOrderDetail;

    public OrderDetailAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<OrderDetail> list){
        mListOrderDetail = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderDetailBinding binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new OrderDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailViewHolder holder, int position) {
        OrderDetail orderDetail = mListOrderDetail.get(position);
        if(orderDetail == null) return;
        Food food = AdminDatabase.getInstance(context).foodDao().findFoodById(orderDetail.getFoodId());
        Glide.with(this.context).load(food.getPathImage()).into(holder.imvFoodItem);
        holder.tvFoodNameItem.setText(food.getName());
        holder.tvQuantityItem.setText("Số lượng: " + orderDetail.getQuantity());
    }

    @Override
    public int getItemCount() {
        return mListOrderDetail.size();
    }

    public static class OrderDetailViewHolder extends RecyclerView.ViewHolder{


        ImageView imvFoodItem;
        TextView tvFoodNameItem;
        TextView tvQuantityItem;

        public OrderDetailViewHolder(ItemOrderDetailBinding binding) {
            super(binding.getRoot());
            imvFoodItem = binding.imvFoodItem;
            tvFoodNameItem = binding.tvFoodNameItem;
            tvQuantityItem = binding.tvQuantityItem;
        }
    }
}
