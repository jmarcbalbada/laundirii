package com.example.laundirii.view.client_dashboard_ui.client_dashboard_current;

import android.content.Context;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import com.example.laundirii.view.LoginActivity;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;
import com.example.laundirii.view.client_dashboard_ui.ClientDashboardActivity;

import java.util.ArrayList;
import java.util.List;

public class CurrentFragment extends Fragment {

    private ClientFragmentCurrentBinding binding;
    private Client client;
    SharedPreferences clientInfoPreferences;
    private ListView lv_pendingOrders;
    private ArrayAdapter pendingClientOrdersAdapter;
    private DashboardController dashboardController;
    private Button bookServiceButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = ClientFragmentCurrentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences",0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername,this.getActivity());
        Log.e("thisClient", client.toString());
        lv_pendingOrders = (ListView) root.findViewById(R.id.current_transaction);
        bookServiceButton = root.findViewById(R.id.btn_book_service);
        displayPendingOrders();

        bookServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Book Service", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CurrentFragment.this.getContext(), BookServiceActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    public void displayPendingOrders() {
        List<Phase1Order> listPendingOrders = new ArrayList<>();
        listPendingOrders = dashboardController.getPendingDeliveryOnClient(client.getCustomerID(), this.getActivity());
        Log.e("listPendingOrders", listPendingOrders.size() + "");
        pendingClientOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listPendingOrders);
        lv_pendingOrders.setAdapter(pendingClientOrdersAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}