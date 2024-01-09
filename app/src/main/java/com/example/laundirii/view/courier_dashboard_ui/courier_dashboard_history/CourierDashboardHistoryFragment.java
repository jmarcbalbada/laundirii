package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_history;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.example.laundirii.R;

import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.CourierDashboardFragmentHistoryBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Orders;
import com.example.laundirii.model.Phase1Order;

import java.util.ArrayList;
import java.util.List;

public class CourierDashboardHistoryFragment extends Fragment {

    private CourierDashboardFragmentHistoryBinding binding;
    private Courier courier;
    SharedPreferences courierInfoPreferences;
    private ListView lv_historyList;
    private ArrayAdapter historyOrdersAdapter;
    private DashboardController dashboardController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = CourierDashboardFragmentHistoryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        courierInfoPreferences = this.getActivity().getSharedPreferences("LoginCourierPreferences", 0);
        String courierUsername = courierInfoPreferences.getString("courierUsername", "");
        courier = dashboardController.getCourier(courierUsername,this.getActivity());
        lv_historyList = (ListView) root.findViewById(R.id.lv_historyList);
        displayHistory();
        return root;
    }

    public void displayHistory()
    {
        List<Orders> listHistory = new ArrayList<>();
        listHistory.addAll(dashboardController.getHistoryList(courier.getUsername(), this.getActivity()));
        listHistory.addAll(dashboardController.getHistoryListOnPhase2Order(courier.getUsername(), this.getActivity()));

        ArrayAdapter<Orders> historyClientOrdersAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listHistory);
        lv_historyList.setAdapter(historyClientOrdersAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}