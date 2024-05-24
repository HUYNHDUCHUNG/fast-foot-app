package com.example.fastfood_android_java.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fastfood_android_java.HomeActivity;
import com.example.fastfood_android_java.Interface.IOnClickOrderItemListener;
import com.example.fastfood_android_java.OrderDetailActivity;
import com.example.fastfood_android_java.R;
import com.example.fastfood_android_java.adapter.OrderAdapter;
import com.example.fastfood_android_java.database.AdminDatabase;
import com.example.fastfood_android_java.databinding.FragmentOrderManagerBinding;
import com.example.fastfood_android_java.model.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link OrderManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OrderManagerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public OrderManagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment OrderManagerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OrderManagerFragment newInstance(String param1, String param2) {
        OrderManagerFragment fragment = new OrderManagerFragment();
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

    FragmentOrderManagerBinding binding;
    RecyclerView rcvOrder;

    OrderAdapter adapter;
    List<Order> mListOrder;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderManagerBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        ((HomeActivity)getActivity()).getSupportActionBar().setTitle("Lịch sử hóa đơn");

        rcvOrder = binding.rcvOrder;
        adapter = new OrderAdapter(new IOnClickOrderItemListener() {
            @Override
            public void OnClickListener(String code) {
                Intent i = new Intent(getActivity(), OrderDetailActivity.class);
                i.putExtra("code",code);
                startActivity(i);
            }
        }, getActivity());
        mListOrder = new ArrayList<>();
        getData();
        adapter.setData(mListOrder);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getActivity(),1,RecyclerView.VERTICAL,false);

        rcvOrder.setAdapter(adapter);
        rcvOrder.setLayoutManager(linearLayoutManager);


        return view;
    }

    private void getData(){
        mListOrder = AdminDatabase.getInstance(getContext()).orderDao().getAllOrder();
    }
}