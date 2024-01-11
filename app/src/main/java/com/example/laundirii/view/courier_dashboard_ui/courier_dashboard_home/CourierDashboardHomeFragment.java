package com.example.laundirii.view.courier_dashboard_ui.courier_dashboard_home;

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
import com.example.laundirii.databinding.CourierDashboardFragmentHomeBinding;
import com.example.laundirii.model.Courier;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;

import java.util.ArrayList;
import java.util.List;

public class CourierDashboardHomeFragment extends Fragment {

    private CourierDashboardFragmentHomeBinding binding;
    private ArrayAdapter pendingOrdersAdapter;
    private ListView lv_pendingOrders;
    public DashboardController dashboardController;
    SharedPreferences courierInfoPreferences;
    private TextView hasPendingText;
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
        hasPendingText = root.findViewById(R.id.hasPendingDeliveryText);
        boolean hasPendingTransactionOnPhase1 = dashboardController.hasActiveTransactionOnPhase1Order(courier.getCourierID(), getContext());
        Log.e("HASPENDINGONPHASE1", hasPendingTransactionOnPhase1 + "");
        Log.e("HASPENDINGONPHASE2", hasPendingTransactionOnPhase1 + "");
        boolean hasPendingTransactionOnPhase2 = dashboardController.hasActiveTransactionOnPhase2Order(courier.getCourierID(), getContext());
        // if has pending transaction on phase1order
        if(hasPendingTransactionOnPhase1)
        {
            Phase1Order phase1Order = dashboardController.getPendingDeliveryOnCourier(courier.getCourierID(), getContext());
            if(phase1Order == null)
            {
                phase1Order = new Phase1Order();
            }

            if(phase1Order.getPhase1OrderStatus() == 1)
            {
                showCustomDialogOnReceivedBooking(phase1Order);
            }
        }

        if(hasPendingTransactionOnPhase2)
        {
            Phase2Order pendingDelivery = dashboardController.getPendingDeliveryOnCourierOnPhase2(courier.getCourierID(), this.getActivity());
            if(pendingDelivery == null)
            {
                pendingDelivery = new Phase2Order();
            }

            switch(pendingDelivery.getPhase2OrderStatus())
            {
                case 10:
                    break;
                case 11: showPopUpCourierReceivedBookingPhase2(pendingDelivery);
                    break;
                case 12:
                    break;
                case 13:
                    break;
                case 14:
                    break;
                case 15:
                    break;
                case 16:
                    break;
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
                Object selectedItem = parent.getItemAtPosition(position);

                if(selectedItem instanceof Phase1Order)
                {
                    Phase1Order selectedOrder = (Phase1Order) parent.getItemAtPosition(position);
                    switch(selectedOrder.getPhase1OrderStatus())
                    {
                        case -1:
                            break;
                        case 0:
                            break;
                        case 1: showCustomDialogOnNotifyingClient(selectedOrder);
                            break;
                        case 2:
                            break;
                        case 3:
                            break;
                        case 4: showCustomDialogOnReceivingWasherPaymentPhase1(selectedOrder);
                            break;
                        case 5: showCustomDialogOnReceivingWasherPaymentPhase1(selectedOrder);
                            break;
                    }
                }else if(selectedItem instanceof Phase2Order)
                {
                    Phase2Order selectedOrder = (Phase2Order) parent.getItemAtPosition(position);
                    switch(selectedOrder.getPhase2OrderStatus())
                    {
                        case -1:
                            break;
                        case 0:
                            break;
                        case 10:
                            break;
                        case 11: showPopUpNotifyWasherCourierArrived(selectedOrder);
                            break;
                        case 12:
                            break;
                        case 13: showCustomDialogOnReceivingWasherPaymentPhase2(selectedOrder);
                            break;
                        case 14: showCustomDialogOnNotifyingClientPhase2(selectedOrder);
                            break;
                        case 15:
                            break;
                        case 16:
                            break;
                    }
                }

            }
        });
        return root;
    }

    private void showCustomDialogOnReceivingWasherPaymentPhase1(Phase1Order phase1Order)
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_courier_received_paymentwasher_phase1, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewNoButton = dialogView.findViewById(R.id.textViewReceivedPaymentNoButton);
        TextView textViewYesButton = dialogView.findViewById(R.id.textViewReceivedPaymentYesButton);

        textViewYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = dashboardController.setCourierStatusPhase1OrderOnDatabase(courier.getCourierID(), true, getContext());
                dashboardController.setCourierStatusOnDatabase(courier.getCourierID(),true,getContext());
                displayPendingOrders();
                if(success)
                {
                    courier.setStatus(true);
                    Toast.makeText(getContext(), "Received payment!", Toast.LENGTH_SHORT).show();
                    //send notification to washer
                    String notificationTitle = "Courier has received your payment!";
                    String notificationMessage = "Thank you for the payment.";
                    dashboardController.sendNotifications(phase1Order.getWasherID(), 0,0,notificationTitle,notificationMessage,getContext());
                    lv_pendingOrders.setVisibility(View.GONE);
                }
                else
                {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();

            }
        });

        textViewNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    private void showCustomDialogOnNotifyingClient(Phase1Order phase1Order)
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
                    //send notification to client
                    String notificationTitle = "Your courier has arrived";
                    String notificationMessage = "Please hand-over the laundry to courier";
                    dashboardController.sendNotifications(0,phase1Order.getClientID(),0,notificationTitle,notificationMessage,getContext());
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
        boolean hasActiveOnPhase1 = dashboardController.hasActiveTransactionOnPhase1Order(courier.getCourierID(),getContext());
        boolean hasActiveOnPhase2 = dashboardController.hasActiveTransactionOnPhase2Order(courier.getCourierID(),getContext());
        Log.e("COURIER STATUS", courier.getStatus() + "");
        if(!courier.getStatus())
        {
            // check if has active pending orders on phase 1 or hasn't been paid
            if(hasActiveOnPhase1)
            {
                hasPendingText.setText("");
                Phase1Order pendingDelivery = dashboardController.getPendingDeliveryOnCourier(courier.getCourierID(), this.getActivity());
                if (pendingDelivery != null) {
                    Log.e("PendingDeliveryPhase1", pendingDelivery.toString());
                    List<Phase1Order> listPendingDelivery = new ArrayList<>();
                    listPendingDelivery.add(pendingDelivery);

                    Log.e("LISTPENDINGDELIVERY", listPendingDelivery.size() + "");

                    pendingOrdersAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listPendingDelivery);
                    lv_pendingOrders.setAdapter(pendingOrdersAdapter);
                } else {
                    // Handle the case when pendingDelivery is null
                    Log.e("LISTPENDINGDELIVERY", "Pending delivery is null");
                }
            }

            if(hasActiveOnPhase2)
            {
                hasPendingText.setText("");
                Phase2Order pendingDelivery = dashboardController.getPendingDeliveryOnCourierOnPhase2(courier.getCourierID(), this.getActivity());

                if (pendingDelivery != null) {
                    Log.e("PendingDeliveryPhase2", pendingDelivery.toString());
                    List<Phase2Order> listPendingDelivery = new ArrayList<>();
                    listPendingDelivery.add(pendingDelivery);

                    Log.e("LISTPENDINGDELIVERY", listPendingDelivery.size() + "");

                    pendingOrdersAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listPendingDelivery);
                    lv_pendingOrders.setAdapter(pendingOrdersAdapter);
                }
            }

            if(!hasActiveOnPhase1 && !hasActiveOnPhase2)
            {
                hasPendingText.setText("Turn on status to start accepting orders!");
            }

        }
        else
        {
            // display No pending delivery by creating textview
            hasPendingText.setText("No pending delivery for now!");
        }
    }

    // PHASE 2 POP UPS / DIALOGS

    private void showPopUpCourierReceivedBookingPhase2(Phase2Order phase2Order)
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_courier_received_booking_phase2, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewDismiss = dialogView.findViewById(R.id.textViewCourierReceivedPhase2DismissButton);
        TextView textViewContent = dialogView.findViewById(R.id.textViewCourierReceivedPopUpPhase2Content);

        if(textViewContent != null)
        {
            textViewContent.setText("COLLECT FROM WASHER TO CLIENT\n\nOrder ID: " + phase2Order.getOrderID()
                    + "\nClient: " + phase2Order.getClient().getName()
                    + "\nClient Address: " + phase2Order.getClient().getAddress()
                    + "\nWasher: " + phase2Order.getWasher().getShopName()
                    + "\nWasher Address: " + phase2Order.getWasher().getShopLocation()
            );
        }

        textViewDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showPopUpNotifyWasherCourierArrived(Phase2Order phase2Order)
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_notify_washer_courier_arrived, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewCancel = dialogView.findViewById(R.id.textViewCourierArrivedWasherTitleCancelButton);
        TextView textViewNotify = dialogView.findViewById(R.id.textViewCourierArrivedWasherTitleNotifyButton);

        textViewNotify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardController.updatePhase2OrderStatus(phase2Order.getOrderID(),12,getContext());
                displayPendingOrders();
                //send notification to washer
                String notificationTitle = "Your courier has arrived!";
                String notificationMessage = "Your courier under order ID " + phase2Order.getOrderID() + " has arrived. Please prepare the laundry.";
                dashboardController.sendNotifications(phase2Order.getWasherID(), 0,0,notificationTitle,notificationMessage,getContext());
                dialog.dismiss();

            }
        });

        textViewCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showCustomDialogOnReceivingWasherPaymentPhase2(Phase2Order phase2Order)
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_courier_received_paymentwasher_phase1, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewNoButton = dialogView.findViewById(R.id.textViewReceivedPaymentNoButton);
        TextView textViewYesButton = dialogView.findViewById(R.id.textViewReceivedPaymentYesButton);

        textViewYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dashboardController.updatePhase2OrderStatus(phase2Order.getOrderID(),14,getContext());
                displayPendingOrders();
                //send notification to washer
                String notificationTitle = "Courier has received your payment!";
                String notificationMessage = "Thank you for the payment.";
                dashboardController.sendNotifications(phase2Order.getWasherID(), 0,0,notificationTitle,notificationMessage,getContext());
                Toast.makeText(getContext(), "Received payment!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });

        textViewNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void showCustomDialogOnNotifyingClientPhase2(Phase2Order phase2Order)
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
                dashboardController.updatePhase2OrderStatus(phase2Order.getOrderID(),15,getContext());
                displayPendingOrders();
                //send notification to washer
                String notificationTitle = "Your courier has arrived!";
                String notificationMessage = "Your courier under order ID " + phase2Order.getOrderID() + " has arrived. Please get the laundry and confirm the transaction to complete.";
                dashboardController.sendNotifications(0, phase2Order.getClientID(),0,notificationTitle,notificationMessage,getContext());
                Toast.makeText(getContext(), "Notified Client!", Toast.LENGTH_SHORT).show();
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}