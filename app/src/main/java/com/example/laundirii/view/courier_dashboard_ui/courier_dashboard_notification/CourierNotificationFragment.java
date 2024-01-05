package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_notification;

import static com.example.laundirii.view.courier_dashboard_ui.CourierDashboardActivity.notification_counter_number;
import static com.example.laundirii.view.courier_dashboard_ui.CourierDashboardActivity.showNotificationCounter;

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
import com.example.laundirii.databinding.CourierDashboardFragmentNotificationBinding;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class CourierNotificationFragment extends Fragment {
    private CourierDashboardFragmentNotificationBinding binding;
    private Courier courier;
    SharedPreferences courierInfoPreferences;
    private ListView lv_notificationList;
    private ArrayAdapter notificationCourierOrdersAdapter;
    private DashboardController dashboardController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = CourierDashboardFragmentNotificationBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        courierInfoPreferences = this.getActivity().getSharedPreferences("LoginCourierPreferences", 0);
        String courierUsername = courierInfoPreferences.getString("courierUsername", "");
        courier = dashboardController.getCourier(courierUsername,this.getActivity());
        lv_notificationList = root.findViewById(R.id.courier_listview_notification);
        dashboardController.markNotificationsAsRead(courier.getCourierID(),1,this.getActivity());
        displayNotification();

        notification_counter_number = 0;
        showNotificationCounter(notification_counter_number);
        return root;
    }

    public void displayNotification()
    {
        List<Notification> listHistory = new ArrayList<>();
        listHistory = dashboardController.getNotificationOnCourier(courier.getCourierID(), this.getActivity());
        notificationCourierOrdersAdapter = new ArrayAdapter<Notification>(this.getContext(), android.R.layout.simple_list_item_1, listHistory);
        lv_notificationList.setAdapter(notificationCourierOrdersAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
