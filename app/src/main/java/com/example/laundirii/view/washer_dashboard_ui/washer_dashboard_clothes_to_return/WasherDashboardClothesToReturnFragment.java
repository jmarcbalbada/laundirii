package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.WasherDashboardFragmentClothesToReturnBinding;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.model.Washer;

import java.util.List;

public class WasherDashboardClothesToReturnFragment extends Fragment {
    private WasherDashboardFragmentClothesToReturnBinding binding;
    private RecyclerView recyclerView;
    private DashboardController dashboardController;
    private WasherDashboardClothesToReturnAdapter washerDashboardClothesToReturnAdapter;
    private SharedPreferences washerInfoPreferences;
    private Washer washer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WasherDashboardFragmentClothesToReturnBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize your UI components and set up RecyclerView
        recyclerView = root.findViewById(R.id.washer_clothes_to_return_tolist_recyclerviewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize Dashboard Controller
        dashboardController = new DashboardController();

        // Get the object washer
        washerInfoPreferences = requireActivity().getSharedPreferences("WasherInfoPreferences", 0);
        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
        washer = dashboardController.getWasher(washerUsername, requireActivity());

        List<Phase2Order> phase2OrderList = dashboardController.getWasherPhase2PendingOrder(washer.getWasherID(),getContext());

        // Initialize and set up your RecyclerView adapter
        washerDashboardClothesToReturnAdapter = new WasherDashboardClothesToReturnAdapter(phase2OrderList,getContext());
        recyclerView.setAdapter(washerDashboardClothesToReturnAdapter);

        // Load and display data

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}

