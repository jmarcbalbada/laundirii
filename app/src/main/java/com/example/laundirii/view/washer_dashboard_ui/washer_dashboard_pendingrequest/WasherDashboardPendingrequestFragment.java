package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

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
import com.example.laundirii.databinding.WasherDashboardFragmentPendingrequestBinding;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Washer;

import java.util.List;

public class WasherDashboardPendingrequestFragment extends Fragment {

    private WasherDashboardFragmentPendingrequestBinding binding;
    private RecyclerView recyclerView;
    private WasherDashboardPendingrequestAdapter washerDashboardPendingrequestAdapter;
    private Washer washer;


    private SharedPreferences washerInfoPreferences;

    private DashboardController dashboardController = new DashboardController();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = WasherDashboardFragmentPendingrequestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.washer_pendingrequest_tolist_recyclerviewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // fetch courier
        washerInfoPreferences = this.getActivity().getSharedPreferences("LoginWasherPreferences", 0);
        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
        washer = dashboardController.getWasher(washerUsername, this.getActivity());
        List<Phase1Order> orders = dashboardController.getPendingDeliveriesOnWasher(washer.getWasherID(), getContext());

         washerDashboardPendingrequestAdapter = new WasherDashboardPendingrequestAdapter(orders,getContext());
        recyclerView.setAdapter(washerDashboardPendingrequestAdapter);
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}