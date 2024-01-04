package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_home;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.CourierDashboardFragmentHomeBinding;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Order;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.courier_dashboard_ui.CourierDashboardActivity;

import java.util.ArrayList;
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

        binding = CourierDashboardFragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        lv_pendingOrders = (ListView) root.findViewById(R.id.lv_pendingDelivery);

        // fetch courier
        courierInfoPreferences = this.getActivity().getSharedPreferences("LoginCourierPreferences", 0);
        String courierUsername = courierInfoPreferences.getString("courierUsername", "");
        courier = dashboardController.getCourier(courierUsername, this.getActivity());
        boolean hasPendingTransaction = dashboardController.hasActiveTransactionOnPhase1Order(courier.getCourierID(), getContext());
        if(hasPendingTransaction)
        {
            Phase1Order phase1Order = dashboardController.getPendingDeliveryOnCourier(courier.getCourierID(), getContext());
            if(phase1Order.getPhase1OrderStatus() == 1)
            {
                showCustomDialogOnReceivedBooking(phase1Order);
            }
        }
        // display pending orders
        try{
            displayPendingOrders();
        }catch (Exception e)
        {
            Log.e("displayPendingErrors", e.getMessage().toString());
        }

        lv_pendingOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Phase1Order selectedOrder = (Phase1Order) parent.getItemAtPosition(position);
                switch(selectedOrder.getPhase1OrderStatus())
                {
                    case -1:
                        break;
                    case 0:
                        break;
                    case 1: showCustomDialogOnNotifyingClient();
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                }
            }
        });
        return root;
    }

    private void showCustomDialogOnNotifyingClient()
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_notify_client_courier_arrived, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewDismiss = dialogView.findViewById(R.id.textViewCourierArrivedCancelButton);
        TextView textViewNotify = dialogView.findViewById(R.id.CourierReceivedNotifyButton);

        textViewNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = dashboardController.updatePhase1OrderStatusOnDb(courier.getCourierID(),2,getContext());
                displayPendingOrders();
                if(success)
                {
                    Toast.makeText(getContext(), "Notified Client!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });

        textViewDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showCustomDialogOnReceivedBooking(Phase1Order phase1Order)
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_courier_received_booking, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewDismiss = dialogView.findViewById(R.id.textViewCourierReceivedDismissButton);
        TextView textViewContent = dialogView.findViewById(R.id.textViewCourierReceivedContent);

        if(textViewContent != null)
        {
            textViewContent.setText("Order ID: " + phase1Order.getOrderID()
                    + "\nClient: " + phase1Order.getClient().getName()
                    + "\nClient Address: " + phase1Order.getClient().getAddress()
                    + "\nInitial Load: " + phase1Order.getInitialLoad()
                    + "\nWasher: " + phase1Order.getWasher().getShopName()
                    + "\nWasher Address: " + phase1Order.getWasher().getShopLocation()
            );
        }

        textViewDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void displayPendingOrders() {
        // display pending order
//        boolean isActive = courier.getCourierStatusOnDb(courier.getCourierID(),this.getActivity()) == 1 ? true : false;
//        courier.setStatus(isActive);
//        Log.e("Courier Status", courier.getStatus() + "");
        if(!courier.getStatus())
        {
            Phase1Order pendingDelivery = dashboardController.getPendingDeliveryOnCourier(courier.getCourierID(), this.getActivity());
            List<Phase1Order> listPendingDelivery = new ArrayList<Phase1Order>();
            listPendingDelivery.add(pendingDelivery);
            Log.e("LISTPENDINGDELIVERY", listPendingDelivery.size() + "");
            pendingOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listPendingDelivery);
            lv_pendingOrders.setAdapter(pendingOrdersAdapter);
        }
        else
        {
            // display No pending delivery by creating textview
            View root = binding.getRoot();
            lv_pendingOrders.setVisibility(View.GONE);
            TextView textView = new TextView(getContext());

            textView.setText("No pending delivery for now!");
            Log.e("TextView Value", textView.getText().toString());
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24); // Set the text size, adjust as needed
            int currentNightMode = getResources().getConfiguration().uiMode
                    & Configuration.UI_MODE_NIGHT_MASK;
            if(currentNightMode == Configuration.UI_MODE_NIGHT_YES)
            {
                textView.setTextColor(Color.WHITE);
            }else {
                textView.setTextColor(Color.BLACK);
            }

            ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
            );

            params.topToBottom = R.id.text_home;
            params.topMargin = -1700;
            params.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID;
            params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
            params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;

            textView.setLayoutParams(params);

            // Add the TextView to your layout
            ViewGroup rootView = (ViewGroup) root.getRootView();

            if (rootView != null) {
                rootView.addView(textView);
                Log.e("TextViewAdded", "TextView added successfully");
            } else {
                Log.e("RootViewNull", "RootView is null");
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}