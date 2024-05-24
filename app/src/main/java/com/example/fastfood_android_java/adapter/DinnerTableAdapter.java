package com.example.fastfood_android_java.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fastfood_android_java.Interface.IOnClickDinnerTableItemListener;
import com.example.fastfood_android_java.R;
import com.example.fastfood_android_java.databinding.ItemDinnerTableBinding;
import com.example.fastfood_android_java.model.DinnerTable;

import java.util.List;

public class DinnerTableAdapter extends RecyclerView.Adapter<DinnerTableAdapter.DinnerTableViewHolder>{


    private final IOnClickDinnerTableItemListener OnClickDinnerTableItemListener;
    private Context context;
    private List<DinnerTable> mListTable;

    public DinnerTableAdapter(IOnClickDinnerTableItemListener iOnClickDinnerTableItemListenerm, Context context) {
        OnClickDinnerTableItemListener = iOnClickDinnerTableItemListenerm;
        this.context = context;
    }

    public void setData(List<DinnerTable> list){
        mListTable = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DinnerTableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemDinnerTableBinding binding = ItemDinnerTableBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new DinnerTableViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(@NonNull DinnerTableViewHolder holder, int position) {
        DinnerTable table = mListTable.get(position);
        if(table == null) return;
        holder.tvTableName.setText(table.getName());
        if(table.getStatus().equals("Chưa thanh toán") || !table.getCodeOrder().equals("")){
            holder.imvTable.setBackgroundResource(R.color.yellow);
        }else{
            holder.imvTable.setBackgroundResource(R.color.button);
        }
        holder.linearLayout_item.setOnClickListener(view -> {
            OnClickDinnerTableItemListener.OnClickListener(table);
        });

    }

    @Override
    public int getItemCount() {
        return mListTable.size();
    }

    public static class DinnerTableViewHolder extends RecyclerView.ViewHolder{


        public ItemDinnerTableBinding binding;
        ImageView imvTable;
        TextView tvTableName;
        LinearLayout linearLayout_item;
        public DinnerTableViewHolder(ItemDinnerTableBinding binding) {
            super(binding.getRoot());

            imvTable = binding.imageView;
            tvTableName = binding.tvTableName;
            linearLayout_item = binding.linerItemTable;

        }
    }
}
