package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_history;

import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Washer;

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
        List<Phase1Order> orders = dashboardController.getWasherHistory(washer.getWasherID(),getContext());
//        List<Phase1Order> orders = dashboardController.getWaherHistory(washer.getWasherID(), getContext());

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