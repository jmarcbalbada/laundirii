package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_pending_request;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.CourierDashboardFragmentPendingRequestBinding;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.LoginActivity;
import com.example.laundirii.view.RegisterActivity;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;
import com.example.laundirii.view.client_dashboard_ui.client_dashboard_current.CurrentFragment;
import com.example.laundirii.view.courier_dashboard_ui.CourierDashboardActivity;

import java.util.ArrayList;
import java.util.List;

public class CourierPendingRequestFragment extends Fragment {

    private CourierDashboardFragmentPendingRequestBinding binding;
    private Courier courier;
    SharedPreferences courierInfoPreferences;
    private ListView lv_pendingRequestList;
    private ArrayAdapter pendingRequestOrdersAdapter;
    private DashboardController dashboardController;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = CourierDashboardFragmentPendingRequestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        courierInfoPreferences = this.getActivity().getSharedPreferences("LoginCourierPreferences", 0);
        String courierUsername = courierInfoPreferences.getString("courierUsername", "");
        courier = dashboardController.getCourier(courierUsername,this.getActivity());
        lv_pendingRequestList = (ListView) root.findViewById(R.id.lv_pendingRequest);
        displayPendingRequest();

        lv_pendingRequestList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // if available
                if(courier.getStatus())
                {
                    Phase1Order phase1Order = (Phase1Order) adapterView.getItemAtPosition(i);
                    AlertDialog.Builder builder = new AlertDialog.Builder(CourierPendingRequestFragment.this.getContext());
                    builder.setTitle("Confirm")
                            .setMessage("Are you sure you want to handle this order?" +
                                    "\n\nOrder ID:" + phase1Order.getOrderID() +
                                    "\nClient: " + phase1Order.getClient().getName() +
                                    "\nWasher: " + phase1Order.getWasher().getShopName() + "\n"
                            )
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    boolean isSuccessful = dashboardController.acceptPendingRequestOnCourier(courier.getCourierID(),
                                            phase1Order.getOrderID(), getContext());
                                    Log.e("ISSUCCESSFUL", isSuccessful + "");
                                    Log.e("COURIER STATUS REQUEST", courier.getStatus() + "");
                                    if(isSuccessful)
                                    {
                                        courier.setStatus(false);
                                        dashboardController.updateCourierStatus(courier.getCourierID(), 0, getContext());
                                        Log.e("COURIER STATUS REQUEST AFTER", courier.getStatus() + "");
                                        Toast.makeText(getContext(), "Successful!", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                                    }
                                    Intent intent = new Intent(CourierPendingRequestFragment.this.getContext(), CourierDashboardActivity.class);
                                    startActivity(intent);
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // User clicked No, do nothing or handle as needed
                                }
                            });

                    // Show the alert dialog
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else {

                }

            }
        });

        return root;
    }

    public void displayPendingRequest()
    {
        View root = binding.getRoot();
        TextView textview = root.findViewById(R.id.courierPendingRequestTextview2);
        if(courier.getStatus())
        {
            textview.setText("Tap any of the following orders:");
            List<Phase1Order> listHistory = new ArrayList<>();
            listHistory = dashboardController.getPendingRequestOnCourier(this.getActivity());
            Log.e("LISTHISTORY", listHistory.size() + "");
            if(listHistory.size() == 0)
            {
                textview.setText("No pending request as of the moment!");
            }
            pendingRequestOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listHistory);
            lv_pendingRequestList.setAdapter(pendingRequestOrdersAdapter);
        }
        else
        {
            textview.setText("You are currently handling delivery");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}