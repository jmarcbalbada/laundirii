package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

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
import com.example.laundirii.databinding.WasherDashboardFragmentClothesToReturnBinding;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.model.Washer;

import java.util.List;

public class WasherDashboardClothesToReturnFragment extends Fragment{
    private WasherDashboardFragmentClothesToReturnBinding binding;
    private RecyclerView recyclerView;
    private SharedPreferences washerInfoPreferences;
    private Washer washer;
    private DashboardController dashboardController;
    private WasherDashboardClothesToReturnAdapter washerDashboardClothesToReturnAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = WasherDashboardFragmentClothesToReturnBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        dashboardController = new DashboardController();

        recyclerView = root.findViewById(R.id.washer_clothes_to_return_tolist_recyclerviewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // fetch courier
        washerInfoPreferences = this.getActivity().getSharedPreferences("LoginWasherPreferences", 0);
        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
         washer = dashboardController.getWasher(washerUsername,getContext());

         Log.e("Waher Username","washer username: " + washerUsername );
//        List<Phase2Order> phase2OrderList = dashboardController.getWasherPhase2ClohtesToReturn(washer.getWasherID(),getContext());
        List<Phase2Order> phase2OrderList = dashboardController.getWasherPhase2ClohtesToReturn(washer.getWasherID(),getContext());

//        List<Phase2Order> phase2OrdersList = dashboardController.getWasherPhase2ClohtesToReturns(washer.getWasherID(), getActivity().getBaseContext());

////        // Initialize and set up your RecyclerView adapter
//        List<Phase1Order> orders = dashboardController.getPendingDeliveriesOnWasher(washer.getWasherID(), getContext());
//        washerDashboardClothesToReturnAdapter = new WasherDashboardClothesToReturnAdapter(phase,getContext());
        washerDashboardClothesToReturnAdapter = new WasherDashboardClothesToReturnAdapter(phase2OrderList,getContext());
        recyclerView.setAdapter(washerDashboardClothesToReturnAdapter);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}