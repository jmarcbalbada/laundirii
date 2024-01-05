package com.example.laundirii.view.client_dashboard_ui.client_dashboard_notification;

import static com.example.laundirii.view.client_dashboard_ui.ClientDashboardActivity.notification_counter_number;
import static com.example.laundirii.view.client_dashboard_ui.ClientDashboardActivity.showNotificationCounter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentHistoryBinding;
import com.example.laundirii.databinding.ClientFragmentNotificationBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Notification;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;
import com.example.laundirii.view.client_dashboard_ui.client_dashboard_history.HistoryFragment;

import java.util.ArrayList;
import java.util.List;

public class ClientNotificationFragment extends Fragment {
    private ClientFragmentNotificationBinding binding;
    SharedPreferences clientInfoPreferences;
    private Client client;
    private DashboardController dashboardController;
    private ArrayAdapter notificationClientOrdersAdapter;
    private ListView lv_notificationList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = ClientFragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences",0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername,this.getActivity());

        lv_notificationList = root.findViewById(R.id.client_listview_notification);
        dashboardController.markNotificationsAsRead(client.getCustomerID(),0,this.getActivity());
        displayNotification();

        notification_counter_number = 0;
        showNotificationCounter(notification_counter_number);
        return root;
    }

    public void displayNotification()
    {
        List<Notification> listHistory = new ArrayList<>();
        listHistory = dashboardController.getNotificationOnClient(client.getCustomerID(), this.getActivity());
        notificationClientOrdersAdapter = new ArrayAdapter<Notification>(this.getContext(), android.R.layout.simple_list_item_1, listHistory);
        lv_notificationList.setAdapter(notificationClientOrdersAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
