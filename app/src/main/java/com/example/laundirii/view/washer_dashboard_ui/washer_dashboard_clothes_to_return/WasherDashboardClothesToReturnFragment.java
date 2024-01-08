package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.WasherDashboardFragmentClothesToReturnBinding;
import com.example.laundirii.model.Washer;

public class WasherDashboardClothesToReturnFragment extends Fragment {
    private WasherDashboardFragmentClothesToReturnBinding binding;

    private RecyclerView recyclerView;
    private DashboardController dashboardController;
    private SharedPreferences washerInfoPreferences;
    private Washer washer;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = WasherDashboardFragmentClothesToReturnBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Initialize your UI components and set up RecyclerView
//        recyclerView = root.findViewById(R.id.);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        dashboardController = new DashboardController();
        washerInfoPreferences = requireActivity().getSharedPreferences("WasherInfoPreferences", 0);
        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
        washer = dashboardController.getWasher(washerUsername, requireActivity());

        // Initialize and set up your RecyclerView adapter

        // Load and display data

        return root;
    }
}

