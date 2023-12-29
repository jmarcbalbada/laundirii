package com.example.laundirii.view.client_dashboard_ui.client_dashboard_notification;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentHistoryBinding;
import com.example.laundirii.databinding.ClientFragmentNotificationBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;
import com.example.laundirii.view.client_dashboard_ui.client_dashboard_history.HistoryFragment;

public class ClientNotificationFragment extends Fragment {
    private ClientFragmentNotificationBinding binding;
    SharedPreferences clientInfoPreferences;
    private Client client;
    private DashboardController dashboardController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = ClientFragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences",0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername,this.getActivity());
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
