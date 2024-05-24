package com.example.fastfood_android_java.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.fastfood_android_java.DinnerTableDetailAcvitity;
import com.example.fastfood_android_java.HomeActivity;
import com.example.fastfood_android_java.Interface.IOnClickDinnerTableItemListener;
import com.example.fastfood_android_java.PaymentActivity;
import com.example.fastfood_android_java.R;
import com.example.fastfood_android_java.adapter.DinnerTableAdapter;
import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.FragmentDinnerTableManager2Binding;
import com.example.fastfood_android_java.model.Admin;
import com.example.fastfood_android_java.model.DinnerTable;
import com.example.fastfood_android_java.model.OrderDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DinnerTableManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DinnerTableManagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public DinnerTableManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *

     * @return A new instance of fragment DinnerTableManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DinnerTableManagerFragment newInstance() {
        DinnerTableManagerFragment fragment = new DinnerTableManagerFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private FragmentDinnerTableManager2Binding binding;
    List<DinnerTable> mListTable;
    RecyclerView rcvDinnerTable;
    DinnerTableAdapter dinnerTableAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        binding =  FragmentDinnerTableManager2Binding.inflate(inflater, container, false);
        View view = binding.getRoot();

        mListTable = new ArrayList<>();
        setHasOptionsMenu(true);
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Quản lý bàn");

        rcvDinnerTable = binding.rcvDinnerTable;
        mListTable = AdminDatabase.getInstance(getContext()).dinnerTableDao().getAllDinnerTable();
         dinnerTableAdapter = new DinnerTableAdapter(new IOnClickDinnerTableItemListener() {
             @Override
             public void OnClickListener(DinnerTable dinnerTable) {
                 if(dinnerTable.getStatus().equals("Chưa thanh toán") || !dinnerTable.getCodeOrder().equals("")){
                     Intent i = new Intent(getContext(), PaymentActivity.class);
                     i.putExtra("dinner_table", (Serializable) dinnerTable);
                     startActivity(i);
                 }else{
                     Intent i = new Intent(getContext(), DinnerTableDetailAcvitity.class);
                     i.putExtra("dinner_table", (Serializable) dinnerTable);
                     startActivity(i);
                 }

             }
         }, getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity().getBaseContext(),2,GridLayoutManager.VERTICAL,false);
        dinnerTableAdapter.setData(mListTable);
        rcvDinnerTable.setLayoutManager(gridLayoutManager);
        rcvDinnerTable.setAdapter(dinnerTableAdapter);



        return view;
    }

    private void createDinnerTableDialog() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_insert_dinner_table);


        Window window = dialog.getWindow();
        if(window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAtribute = window.getAttributes();
        windowAtribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAtribute);

        EditText edtTableName = dialog.findViewById(R.id.edt_table_name);
        Button btn_cancel = dialog.findViewById(R.id.btn_cancel);
        Button btn_ok = dialog.findViewById(R.id.btn_ok);


        btn_cancel.setOnClickListener(view -> {
            dialog.dismiss();
        });
        btn_ok.setOnClickListener(view -> {
            String tableName = edtTableName.getText().toString().trim();
            if(!tableName.isEmpty()){
                DinnerTable dinnerTable = new DinnerTable(tableName,"Trống","");
                AdminDatabase.getInstance(getContext()).dinnerTableDao().insertDinnerTable(dinnerTable);
                mListTable.add(dinnerTable);
                dinnerTableAdapter.notifyItemInserted(mListTable.size()-1);
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();


                dialog.dismiss();
            }else{
                Toast.makeText(getActivity(), "Vui lòng nhập tên bàn", Toast.LENGTH_SHORT).show();
            }

        });

        dialog.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mListTable = AdminDatabase.getInstance(getActivity()).dinnerTableDao().getAllDinnerTable();
        dinnerTableAdapter.setData(mListTable);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_action_bar,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.add){
            createDinnerTableDialog();
        }
        return super.onOptionsItemSelected(item);
    }

}