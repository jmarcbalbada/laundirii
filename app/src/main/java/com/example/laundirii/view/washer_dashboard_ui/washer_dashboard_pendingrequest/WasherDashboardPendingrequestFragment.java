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
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Washer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        List<Phase1Order> orders = washerDashboardPendingrequestViewModel.getListofOrdersToReceive(1, getContext());

        List<Phase1Order> testing = new ArrayList<Phase1Order>();
        testing.add(insertDummyPhase1Order());

        testing.add(insertDummyPhase1Order());

        testing.add(insertDummyPhase1Order());

        testing.add(insertDummyPhase1Order());

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
    public Phase1Order insertDummyPhase1Order() {
        Random random = new Random();

        // Generate random values
        int orderID = random.nextInt(1000); // Change the range based on your requirements
        Client client = new Client(/* Generate random client parameters */);
        Washer washer = new Washer();
        washer.setWasherID(3);
        Courier courier = new Courier(/* Generate random courier parameters */);
        int courierStatus = random.nextInt(2); // Assuming courierStatus can be 0 or 1
        double totalCourierAmount = random.nextDouble() * 100.0; // Change the range based on your requirements
        String dateCourier = "2023-12-01 12:00:00"; // Replace with actual random date generation logic
        double totalDue = random.nextDouble() * 50.0; // Change the range based on your requirements
        double totalPaid = random.nextDouble() * 50.0; // Change the range based on your requirements
        int paymentStatus = random.nextInt(2); // Assuming paymentStatus can be 0 or 1
        String dateReceived = "2023-12-02 14:30:00"; // Replace with actual random date generation logic

        // Create and return a new Phase1Order
        return new Phase1Order(orderID, client, washer, courier,
                courierStatus, totalCourierAmount, dateCourier,
                totalDue, totalPaid, paymentStatus, dateReceived);
    }
}