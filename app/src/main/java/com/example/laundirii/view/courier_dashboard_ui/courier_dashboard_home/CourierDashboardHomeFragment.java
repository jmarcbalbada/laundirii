package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.example.laundirii.databinding.CourierDashboardFragmentHomeBinding;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Order;

import java.util.List;

public class CourierDashboardHomeFragment extends Fragment {

    private CourierDashboardFragmentHomeBinding binding;

    private ArrayAdapter pendingOrdersAdapter;
    private ListView lv_pendingOrders;
    public DashboardController dashboardController;
    SharedPreferences courierInfoPreferences;
    private Courier courier;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CourierDashboardHomeViewModel homeViewModel =
                new ViewModelProvider(this).get(CourierDashboardHomeViewModel.class);

        binding = CourierDashboardFragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        lv_pendingOrders = (ListView) root.findViewById(R.id.lv_pendingDelivery);
        courierInfoPreferences = this.getActivity().getSharedPreferences("LoginCourierPreferences", 0);

        int courierID = courierInfoPreferences.getInt("courierID", -1);
        String courierUsername = courierInfoPreferences.getString("courierUsername", "");
        String courierPassword = courierInfoPreferences.getString("courierPassword", "");
        String courierName = courierInfoPreferences.getString("courierName", "");
        String courierContactNo = courierInfoPreferences.getString("courierContactNo", "");
        String courierPlateNo = courierInfoPreferences.getString("courierPlateNo", "");
        boolean courierStatus = courierInfoPreferences.getBoolean("courierStatus", false);

//         Now, you can use these values as needed in CourierDashboardActivity
        courier = new Courier(courierID,courierUsername,courierPassword,courierName,courierContactNo,courierPlateNo,courierStatus);
        Log.e("COURIER FRAGMENT", courier.getCourierID() + ", " + courier.getUsername() + ","
                + courier.getPassword() + ", " + courier.getName() + ", " + courier.getContactNo() + ","
                + courier.getPlateNo() + "," + courier.getStatus());
        try{
            displayPendingOrders();
        }catch (Exception e)
        {
            Log.e("ATERROR", e.getMessage().toString());
        }
        return root;
    }

    public void displayPendingOrders() {
        List<Order> orderList = dashboardController.getPendingDeliveriesOnCourier(courier.getCourierID(), this.getActivity());

        Log.e("OrderListSize", String.valueOf(orderList.size()));

        // Create an ArrayAdapter for your Order objects
        pendingOrdersAdapter = new ArrayAdapter<Order>(this.getContext(), android.R.layout.simple_list_item_1, orderList);

        // Set the adapter to your ListView
        lv_pendingOrders.setAdapter(pendingOrdersAdapter);
        Log.e("AdapterItemValue", pendingOrdersAdapter.getItem(1).toString());
        Log.e("AdapterItemCount", String.valueOf(pendingOrdersAdapter.getCount()));

        // Optional: If you want to handle item clicks, you can set an OnItemClickListener
//        lv_pendingOrders.setOnItemClickListener((parent, view, position, id) -> {
//            // Handle item click here
//            Order selectedOrder = (Order) parent.getItemAtPosition(position);
//            // Do something with the selected order
//        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}