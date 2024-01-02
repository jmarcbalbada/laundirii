package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_receivedclothes;

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
import com.example.laundirii.databinding.WasherDashboardFragmentReceivedClothesBinding;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Washer;

import java.util.List;

public class WasherDashboardReceivedClothesFragment extends Fragment {
    private WasherDashboardFragmentReceivedClothesBinding binding;
    private RecyclerView recyclerView;
    private WasherDashboardReceivedClothesAdapter washerDashboardReceivedClothesAdapter;
    private SharedPreferences washerInfoPreferences;
    private DashboardController dashboardController;
    private Washer washer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        dashboardController = new DashboardController();
        binding = WasherDashboardFragmentReceivedClothesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.washer_receivedclothes_tolist_recyclerviewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // fetch courier
        washerInfoPreferences = this.getActivity().getSharedPreferences("LoginWasherPreferences", 0);
        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
        washer = dashboardController.getWasher(washerUsername, this.getActivity());
        List<Phase1Order> orders = dashboardController.getWasherReceivedClothes(washer.getWasherID(), getContext());

        washerDashboardReceivedClothesAdapter = new WasherDashboardReceivedClothesAdapter(orders,getContext());
        recyclerView.setAdapter(washerDashboardReceivedClothesAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
