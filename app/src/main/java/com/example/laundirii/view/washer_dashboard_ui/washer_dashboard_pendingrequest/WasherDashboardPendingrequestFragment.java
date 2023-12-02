package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.databinding.WasherDashboardFragmentPendingrequestBinding;
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardPendingrequestFragment extends Fragment {

    private WasherDashboardFragmentPendingrequestBinding binding;
    private RecyclerView recyclerView;
    private WasherDashboardPendingrequestAdapter washerDashboardPendingrequestAdapter;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        WasherDashboardPendingrequestViewModel washerDashboardPendingrequestViewModel =
                new ViewModelProvider(this).get(WasherDashboardPendingrequestViewModel.class);

        binding = WasherDashboardFragmentPendingrequestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        TextView textView = binding.textGallery;
        washerDashboardPendingrequestViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        // Find your RecyclerView in the layout
        recyclerView = root.findViewById(R.id.washer_pendingrequest_tolist_recyclerviewer);

        // Set up the RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Get the list of orders from the ViewModel
        List<Phase1Order> orders = washerDashboardPendingrequestViewModel.getListofOrdersToReceive(2, getContext());

        Log.e("YAWA", orders + "asdfasdf");
//         Create and set the adapter
        washerDashboardPendingrequestAdapter = new WasherDashboardPendingrequestAdapter(orders);
        recyclerView.setAdapter(washerDashboardPendingrequestAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}