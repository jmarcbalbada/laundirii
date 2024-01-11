package com.example.laundirii.view.client_dashboard_ui.client_dashboard_current;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentCurrentBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CurrentFragment extends Fragment {

    private ClientFragmentCurrentBinding binding;
    private Client client;
    private SharedPreferences clientInfoPreferences;
    private ListView lv_pendingOrders;
    private ArrayAdapter pendingClientOrdersAdapter;
    private DashboardController dashboardController;
    private Button bookServiceButton;
    private Date currentDate; // Added to store the current date and time
    private Handler handler;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ClientFragmentCurrentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences", 0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername, this.getActivity());
        Log.e("thisClient", client.toString());
        lv_pendingOrders = root.findViewById(R.id.current_transaction);
        bookServiceButton = root.findViewById(R.id.btn_book_service);
        handler = new Handler(Looper.getMainLooper());
        checkPopupStatus();
        displayPendingOrders();

        // Initialize the current date and time
        currentDate = new Date();

        bookServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Book Service", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(CurrentFragment.this.getContext(), BookServiceActivity.class);
                startActivity(intent);
            }
        });

        lv_pendingOrders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click here
                Phase1Order selectedOrder = (Phase1Order) parent.getItemAtPosition(position);

                switch(selectedOrder.getPhase1OrderStatus())
                {
                    case -1:
                        break;
                    case 0: showCustomDialog(selectedOrder);
                        break;
                    case 1:
                        break;
                    case 2: showCustomDialogClientHandover(selectedOrder);
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                        // ready to collect
                    case 6: showCustomDialogReadyCollect(selectedOrder);
                        break;
                }
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateTimerAsync(getPendingOrders());
        // Your code here, e.g., UI updates or interactions
    }

    private List<Phase1Order> getPendingOrders() {
        return dashboardController.getPendingDeliveryOnClient(client.getCustomerID(), getActivity());
    }

    // timer is active iff the dialog is active and showingi
    private void showCustomDialog(Phase1Order phase1Order) {
        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_client_timer_countdown, null);

        // Create the AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewDismiss = dialogView.findViewById(R.id.textViewDismiss);
        TextView timerTextView = dialogView.findViewById(R.id.textViewTime);

        updateTimerAsync(phase1Order, timerTextView, dialog);

        textViewDismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void showCustomDialogReadyCollect(Phase1Order phase1Order) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_client_ready_to_collect, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewNoButton = dialogView.findViewById(R.id.textViewClientReadyCollectContentNoButton);
        TextView textViewYesButton = dialogView.findViewById(R.id.textViewClientReadyCollectContentYesButton);

        textViewNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textViewYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = dashboardController.updatePhase1OrderStatus(phase1Order.getOrderID(), 7, getContext());
                dashboardController.insertPhase2Order(phase1Order.getClientID(),phase1Order.getWasherID(), phase1Order.getTotalDue(), phase1Order.getOrderID(), getContext());
                if(success)
                {
                    String notificationTitle = "Ready to collect order initiated!";
                    String notificationMessage = "Proceed to Ready to Collect orders tab to select SELF-COLLECT or COURIER-COLLECT option";
                    dashboardController.sendNotifications(0, phase1Order.getClientID(), 0,notificationTitle,notificationMessage,getContext());
                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
                displayPendingOrders();
            }
        });
    }


    public void checkPopupStatus()
    {
        List<Phase1Order> listPendingOrders = dashboardController.getPendingDeliveryOnClient(client.getCustomerID(), getActivity());

        for (Phase1Order order : listPendingOrders) {
            switch(order.getPhase1OrderStatus())
            {
                case -1:
                    break;
                case 0:
                    break;
                case 1:
                    break;
                case 2: showCustomDialogOnCourierArrived(order);
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
            }
        }
    }

    private void showCustomDialogClientHandover(Phase1Order phase1Order) {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_client_handover_courier, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewNoButton = dialogView.findViewById(R.id.textViewClientHandoverNoButton);
        TextView textViewYesButton = dialogView.findViewById(R.id.textViewClientHandoverYesButton);

        textViewNoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        textViewYesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean success = dashboardController.updatePhase1OrderStatus(phase1Order.getOrderID(), 3, getContext());
                displayPendingOrders();
                if(success)
                {
                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
                    //send notification to courier
                    String notificationTitle = "Client has handed over the laundry to courier!";
                    String notificationMessage = "Please hand over the laundry to washer to proceed.";
                    dashboardController.sendNotifications(0,0,phase1Order.getCourier().getCourierID(),notificationTitle,notificationMessage,getContext());

                    //send notification to washer
                    notificationMessage = "Wait for the courier to hand over the laundry!";
                    dashboardController.sendNotifications(phase1Order.getWasherID(),0,0,notificationTitle,notificationMessage,getContext());
                }
                else
                {
                    Toast.makeText(getContext(), "Something went wrong!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
    }

    private void showCustomDialogOnCourierArrived(Phase1Order phase1Order)
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_courier_arrived, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Reminder!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();

        TextView textViewOkayButton = dialogView.findViewById(R.id.textViewCourierHasArrivedContentOkayButton);
        TextView textViewContent = dialogView.findViewById(R.id.textViewCourierHasArrivedContent);

        //
        if(textViewContent != null)
        {
            textViewContent.setText("Your courier " + phase1Order.getCourier().getName() +
                    " under the order " + phase1Order.getOrderID() + " is already on the location. " +
                    "Please ready your laundry and hand it over to the courier."
            );
        }

        textViewOkayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }


    // timer is active iff the Current fragment is displaying/resume
    private void updateTimerAsync(List<Phase1Order> phase1OrderList) {
//        final Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                // Iterate through the list and update timers for each Phase1Order
                for (Phase1Order phase1Order : phase1OrderList) {
                    updateTimer(phase1Order);
                }

                // Check if any order is out of timer and update accordingly
                List<Phase1Order> ordersToUpdateStatus = new ArrayList<>();
                for (Phase1Order phase1Order : phase1OrderList) {
                    if(phase1Order.getPhase1OrderStatus() == 0)
                    {
                        if (isOutOfTimer(phase1Order)) {
                            ordersToUpdateStatus.add(phase1Order);
                        }
                    }
                }

                if (!ordersToUpdateStatus.isEmpty()) {
                    // Handle the out-of-timer case (e.g., update status and display)
                    for (Phase1Order order : ordersToUpdateStatus) {
                        dashboardController.updatePhase1OrderStatus(order.getOrderID(), -1, getContext());
                    }
                    displayPendingOrders();
                    //Toast.makeText(getContext(), "Timer has expired for some orders!", Toast.LENGTH_SHORT).show();
                }

                // Schedule the next update after a certain interval (e.g., 1 second)
                handler.postDelayed(this, 1000); // 1000 milliseconds (1 second) delay
            }
        });
    }

    // timer is active iff the dialog is active and showing
    private void updateTimerAsync(final Phase1Order phase1Order, final TextView timerTextView, AlertDialog dialog) {
//        final Handler handler = new Handler(Looper.getMainLooper());

        handler.post(new Runnable() {
            @Override
            public void run() {
                updateTimer(phase1Order, timerTextView);

                // Check if the order is out of timer
                if (isOutOfTimer(phase1Order)) {
                    // Handle the out-of-timer case (e.g., show a message)
                    timerTextView.setText("Timer has expired for this order!");
                    dashboardController.updatePhase1OrderStatus(phase1Order.getOrderID(), -1, getContext());
                    displayPendingOrders();
                    Toast.makeText(getContext(), "Timer has expired for the order!", Toast.LENGTH_SHORT).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        }
                    }, 5000); // 5000 milliseconds (5 seconds) delay
                } else {
                    // Continue updating the timer
                    handler.postDelayed(this, 1000); // Update every second
                }
            }
        });
    }

    //method overloading for updateAsyncTimer when the fragment is active
    private void updateTimer(Phase1Order phase1Order) {
        // Calculate the expiration date and time (5 minutes from datePlaced)
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(phase1Order.getDatePlaced());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Error parsing date: " + e.getMessage());
        }
        currentDate = new Date();

        calendar.add(Calendar.MINUTE, 5);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String dateString = sdf.format(calendar.getTime());
    }

    //method overloading for updateAsyncTimer when the dialog is active
    private void updateTimer(Phase1Order phase1Order, TextView timerTextView) {
        // Calculate the expiration date and time (5 minutes from datePlaced)
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date date = dateFormat.parse(phase1Order.getDatePlaced());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println("Error parsing date: " + e.getMessage());
        }
        currentDate = new Date();

        calendar.add(Calendar.MINUTE, 5);

        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        String dateString = sdf.format(calendar.getTime());
        timerTextView.setText(phase1Order.getWasher().getShopName() + " should accept the transaction on or before "
                + dateString + " or it will be automatically cancelled.");
    }

    // returns true if it exceeds to the specified time limit.
    private boolean isOutOfTimer(Phase1Order phase1Order) {
        try {
            // Parse the date string to a Date object
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date datePlaced = sdf.parse(phase1Order.getDatePlaced());

            // Calculate the difference in milliseconds
            long differenceInMillis = currentDate.getTime() - datePlaced.getTime();

            // Convert the difference to minutes
            long differenceInMinutes = differenceInMillis / (60 * 1000);

            // Check if the difference is greater than 5 minutes
            return differenceInMinutes >= 1;

        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void displayPendingOrders() {
        List<Phase1Order> listPendingOrders = dashboardController.getPendingDeliveryOnClient(client.getCustomerID(), getActivity());
        Log.e("listPendingOrders", listPendingOrders.size() + "");
        pendingClientOrdersAdapter = new ArrayAdapter<Phase1Order>(this.getContext(), android.R.layout.simple_list_item_1, listPendingOrders);
        lv_pendingOrders.setAdapter(pendingClientOrdersAdapter);
    }

//    private void displayPendingOrders() {
//        List<Phase1Order> listPendingOrders = dashboardController.getPendingDeliveryOnClient(client.getCustomerID(), getActivity());
//        Log.e("listPendingOrders", listPendingOrders.size() + "");
//
//        if (pendingClientOrdersAdapter == null) {
//            pendingClientOrdersAdapter = new ArrayAdapter<Phase1Order>(getContext(), android.R.layout.simple_list_item_1, listPendingOrders);
//            lv_pendingOrders.setAdapter(pendingClientOrdersAdapter);
//        } else {
//            // Clear existing data
//            pendingClientOrdersAdapter.clear();
//
//            // Add new data
//            pendingClientOrdersAdapter.addAll(listPendingOrders);
//
//            // Notify the adapter about the changes
//            pendingClientOrdersAdapter.notifyDataSetChanged();
//        }
//    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        binding = null;
    }
}
