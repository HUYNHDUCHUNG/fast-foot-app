package com.example.fastfood_android_java.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood_android_java.Interface.IOnClickOrderItemListener;
import com.example.fastfood_android_java.databinding.ItemOrderHistoryBinding;
import com.example.fastfood_android_java.model.Order;
import com.example.fastfood_android_java.util.Util;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {

    private final IOnClickOrderItemListener iOnClickOrderItemListener;
    Context context;
    List<Order> mListOrder;

    public OrderAdapter(IOnClickOrderItemListener iOnClickOrderItemListener ,Context context) {
        this.iOnClickOrderItemListener = iOnClickOrderItemListener;
        this.context = context;
    }

    public void setData(List<Order> list){
        mListOrder = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemOrderHistoryBinding binding = ItemOrderHistoryBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new OrderViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = mListOrder.get(position);
        holder.tvCodeOrder.setText("Mã hóa đơn: " + order.getCode());
        holder.tvAdminName.setText("Admin: " + order.getAdminName());
        holder.tvCreateAtOrder.setText("Thời gian tạo: " + order.getCreateAt());
        holder.tvStatusOrder.setText("Trạng thái: " + order.getStatus());
        holder.tvTotalPriceOrder.setText("Tổng tiền: " + Util.formatCurrency(Integer.parseInt(order.getTotalPrice())) );
        holder.linearLayout.setOnClickListener(view -> {
            iOnClickOrderItemListener.OnClickListener(order.getCode());
        });
    }

    @Override
    public int getItemCount() {
        return mListOrder.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder{

        CardView linearLayout;
        TextView tvCodeOrder, tvAdminName, tvStatusOrder, tvCreateAtOrder, tvTotalPriceOrder;
        public OrderViewHolder(@NonNull ItemOrderHistoryBinding binding) {
            super(binding.getRoot());


            tvCodeOrder = binding.tvCodeOrder;
            tvAdminName = binding.tvAdminName;
            tvStatusOrder = binding.tvStatusOrder;
            tvCreateAtOrder = binding.tvCreateAtOrder;
            tvTotalPriceOrder = binding.tvTotalPriceOrder;
            linearLayout = binding.layoutOrderItem;
        }
    }
}
