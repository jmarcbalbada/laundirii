package com.example.laundirii.view.client_dashboard_ui.client_dashboard_history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentHistoryBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Phase1Order;

import java.util.ArrayList;
import java.util.List;

public class HistoryFragment extends Fragment {

    private ClientFragmentHistoryBinding binding;
    private Client client;
    SharedPreferences clientInfoPreferences;
    private ListView lv_pendingOrders;
    private ArrayAdapter pendingClientOrdersAdapter;
    private DashboardController dashboardController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HistoryViewModel historyViewModel =
                new ViewModelProvider(this).get(HistoryViewModel.class);

        binding = ClientFragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences",0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername,this.getActivity());
        lv_pendingOrders = (ListView) root.findViewById(R.id.history_transaction);
        displayHistory();
        return root;
    }

    public void displayHistory()
    {
        List<Phase1Order> listHistory = new ArrayList<>();
        listHistory = dashboardController.getHistoryList(client.getCustomerID(), this.getActivity());
        pendingClientOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listHistory);
        lv_pendingOrders.setAdapter(pendingClientOrdersAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
