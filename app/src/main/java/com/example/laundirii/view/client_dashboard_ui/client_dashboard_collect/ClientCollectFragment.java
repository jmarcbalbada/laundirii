package com.example.laundirii.view.client_dashboard_ui.client_dashboard_collect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentCollectBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;
import com.example.laundirii.view.client_dashboard_ui.client_dashboard_current.CurrentFragment;

import java.util.Date;
import java.util.List;

public class ClientCollectFragment extends Fragment {
    private DashboardController dashboardController;
    private ClientFragmentCollectBinding binding;
    private Client client;
    private SharedPreferences clientInfoPreferences;
    private ListView lv_CollectOrders;
    private ArrayAdapter CollectClientOrdersAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ClientFragmentCollectBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences", 0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername, this.getActivity());
        Log.e("thisClient", client.toString());
        lv_CollectOrders = root.findViewById(R.id.client_collect_transaction);
//        displayCollectOrders();
        return root;
    }

    private void displayCollectOrders() {
        List<Phase1Order> listPendingOrders = dashboardController.getPendingDeliveryOnClient(client.getCustomerID(), getActivity());
        Log.e("listCollectOrders", listPendingOrders.size() + "");
        CollectClientOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listPendingOrders);
        lv_CollectOrders.setAdapter(CollectClientOrdersAdapter);
    }



}
