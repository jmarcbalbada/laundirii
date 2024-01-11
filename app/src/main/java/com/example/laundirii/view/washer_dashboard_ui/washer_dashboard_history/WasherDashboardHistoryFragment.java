package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.WasherDashboardFragmentHistoryBinding;
import com.example.laundirii.model.Orders;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.model.Washer;

import java.util.ArrayList;
import java.util.List;

public class WasherDashboardHistoryFragment extends Fragment {

    private WasherDashboardFragmentHistoryBinding binding;
    private RecyclerView recyclerView;
    private DashboardController dashboardController;
    private SharedPreferences washerInfoPreferences;
    private WasherDashboardHistoryAdapter washerDashboardHistoryAdapter;
    private Washer washer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = WasherDashboardFragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.washer_history_tolist_recyclerviewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dashboardController = new DashboardController();
        binding = WasherDashboardFragmentHistoryBinding.inflate(inflater, container, false);
        washerInfoPreferences = this.getActivity().getSharedPreferences("LoginWasherPreferences", 0);
        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
        washer = dashboardController.getWasher(washerUsername, this.getActivity());


        // TODO implement History
        List<Phase1Order> phase1orders = dashboardController.getWasherPhase1OrderHistory(washer.getWasherID(),getContext());
        List<Phase2Order> phase2orders = dashboardController.getWasherPhase2OrderHistory(washer.getWasherID(),getContext());

        List<Orders> orders = new ArrayList<>();
        orders.addAll(phase1orders);
        orders.addAll(phase2orders);
//        List<Phase1Order> orders = dashboardController.getWaherHistory(washer.getWasherID(), getContext());
        Log.e("Phase1Order",""+phase1orders.size());
        Log.e("Phase2Order",""+phase2orders.size());

        phase1orders.forEach(order->{
            Log.e("Phase1Order",""+order);
        });
        orders.forEach(order->{
            if(order instanceof Phase2Order){
                Log.e("Phase2Order",""+order);
            }
            if(order instanceof Phase1Order){
                Log.e("Phase1Order",""+order);

            }
        });

        washerDashboardHistoryAdapter = new WasherDashboardHistoryAdapter(orders,getContext());
        recyclerView.setAdapter(washerDashboardHistoryAdapter);


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}