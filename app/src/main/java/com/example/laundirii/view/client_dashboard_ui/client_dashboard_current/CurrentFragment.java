package com.example.laundirii.view.client_dashboard_ui.client_dashboard_current;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentCurrentBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Phase1Order;

import java.util.ArrayList;
import java.util.List;

public class CurrentFragment extends Fragment {

    private ClientFragmentCurrentBinding binding;
    private Client client;
    SharedPreferences clientInfoPreferences;
    private ListView lv_pendingOrders;
    private ArrayAdapter pendingClientOrdersAdapter;
    private DashboardController dashboardController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CurrentViewModel currentViewModel =
                new ViewModelProvider(this).get(CurrentViewModel.class);

        binding = ClientFragmentCurrentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences",0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername,this.getActivity());
        lv_pendingOrders = (ListView) root.findViewById(R.id.current_transaction);
        displayPendingOrders();
        return root;
    }

    public void displayPendingOrders() {
        List<Phase1Order> listPendingOrders = new ArrayList<>();
        listPendingOrders = dashboardController.getPendingDeliveryOnClient(client.getCustomerID(), this.getActivity());
        pendingClientOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listPendingOrders);
        lv_pendingOrders.setAdapter(pendingClientOrdersAdapter);

    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}