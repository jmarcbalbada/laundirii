package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_notification;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.WasherDashboardFragmentNotificationBinding;
import com.example.laundirii.model.Notification;
import com.example.laundirii.model.Washer;

import java.util.List;

public class WasherNotificationFragment extends Fragment {

    private WasherDashboardFragmentNotificationBinding binding;
    private ArrayAdapter<Notification> notifications;
    private DashboardController dashboardController;
    private SharedPreferences washerSharedPreferences;
    private Washer washer;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = WasherDashboardFragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // initialize dashboard controller
        dashboardController = new DashboardController();

        // get shared preferences
        washerSharedPreferences = this.getActivity().getSharedPreferences("LoginWasherPreferences", 0);
        String clientUsername = washerSharedPreferences.getString("washerUsername", "");
        washer = dashboardController.getWasher(clientUsername, this.getActivity());

        // display the listview
        ListView listView = root.findViewById(R.id.washer_dashboard_fragment_notification_listview);
        List<Notification> notificationList = dashboardController.getWasherNotification(washer.getWasherID(), getContext());
        notifications = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, notificationList);
        listView.setAdapter(notifications);


        return root;
    }


    private void showNotification(Notification notification) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // This will display the (Title of Notification)
        builder.setTitle(notification.getTitle());

        // This will display the (Message of Notification)
        builder.setMessage(notification.getMessage());
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
