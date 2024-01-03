package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.WasherDashboardFragmentPendingrequestBinding;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Washer;

import java.util.Date;
import java.util.List;

public class WasherDashboardPendingrequestFragment extends Fragment {

    private WasherDashboardFragmentPendingrequestBinding binding;
    private RecyclerView recyclerView;
    private WasherDashboardPendingrequestAdapter washerDashboardPendingrequestAdapter;
    private Washer washer;
    private Date currentDate;

    private SharedPreferences washerInfoPreferences;

    private DashboardController dashboardController = new DashboardController();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = WasherDashboardFragmentPendingrequestBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.washer_pendingrequest_tolist_recyclerviewer);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // fetch courier
        washerInfoPreferences = this.getActivity().getSharedPreferences("LoginWasherPreferences", 0);
        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
        washer = dashboardController.getWasher(washerUsername, this.getActivity());
        List<Phase1Order> orders = dashboardController.getPendingDeliveriesOnWasher(washer.getWasherID(), getContext());

         washerDashboardPendingrequestAdapter = new WasherDashboardPendingrequestAdapter(orders,getContext());
        recyclerView.setAdapter(washerDashboardPendingrequestAdapter);
        currentDate = new Date();

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
//package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest;
//
//        import android.content.SharedPreferences;
//        import android.os.Bundle;
//        import android.os.Handler;
//        import android.os.Looper;
//        import android.util.Log;
//        import android.view.LayoutInflater;
//        import android.view.View;
//        import android.view.ViewGroup;
//
//        import androidx.annotation.NonNull;
//        import androidx.fragment.app.Fragment;
//        import androidx.recyclerview.widget.LinearLayoutManager;
//        import androidx.recyclerview.widget.RecyclerView;
//
//        import com.example.laundirii.R;
//        import com.example.laundirii.controller.DashboardController;
//        import com.example.laundirii.databinding.WasherDashboardFragmentPendingrequestBinding;
//        import com.example.laundirii.model.Phase1Order;
//        import com.example.laundirii.model.Washer;
//
//        import java.text.ParseException;
//        import java.text.SimpleDateFormat;
//        import java.util.ArrayList;
//        import java.util.Date;
//        import java.util.List;
//        import java.util.Locale;
//        import java.util.Timer;
//
//public class WasherDashboardPendingrequestFragment extends Fragment {
//
//    private WasherDashboardFragmentPendingrequestBinding binding;
//    private RecyclerView recyclerView;
//    private WasherDashboardPendingrequestAdapter washerDashboardPendingrequestAdapter;
//    private Washer washer;
//    private Date currentDate;
//    private Timer timer;
//    private Handler handler;
//
//    private SharedPreferences washerInfoPreferences;
//
//    private DashboardController dashboardController = new DashboardController();
//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container, Bundle savedInstanceState) {
//
//
//        binding = WasherDashboardFragmentPendingrequestBinding.inflate(inflater, container, false);
//        View root = binding.getRoot();
//
//        recyclerView = root.findViewById(R.id.washer_pendingrequest_tolist_recyclerviewer);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        // fetch courier
//        washerInfoPreferences = this.getActivity().getSharedPreferences("LoginWasherPreferences", 0);
//        String washerUsername = washerInfoPreferences.getString("washerUsername", "");
//        washer = dashboardController.getWasher(washerUsername, this.getActivity());
//        List<Phase1Order> orders = dashboardController.getPendingDeliveriesOnWasher(washer.getWasherID(), getContext());
//        startHandler(washerUsername);
////
////         washerDashboardPendingrequestAdapter = new WasherDashboardPendingrequestAdapter(orders,getContext());
////        recyclerView.setAdapter(washerDashboardPendingrequestAdapter);
//        return root;
//    }
//
//    private void startHandler(String washerUsername) {
//        handler = new Handler(Looper.getMainLooper());
//        handler.post(new Runnable() {
//            @Override
//            public void run() {
//                // This code will run on the UI thread
//                washer = dashboardController.getWasher(washerUsername, getContext());
//                List<Phase1Order> orders = dashboardController.getPendingDeliveriesOnWasher(washer.getWasherID(), getContext());
//
//                washerDashboardPendingrequestAdapter = new WasherDashboardPendingrequestAdapter(orders, getContext());
//                recyclerView.setAdapter(washerDashboardPendingrequestAdapter);
//
//                // create an array to store out-of-time orders
//                List<Phase1Order> ordersToUpdateStatus = new ArrayList<>();
//                // check if out of time and store in array
//                for (Phase1Order order : orders) {
//                    if (isOutOfTimer(order)) {
//                        ordersToUpdateStatus.add(order);
//                    }
//                }
//                // if there are orders in the array, update the status
//                if (!ordersToUpdateStatus.isEmpty()) {
//                    for (Phase1Order order : ordersToUpdateStatus) {
//                        dashboardController.updatePhase1OrderStatus(order.getOrderID(), -1, getContext());
//                    }
//                }
//
//                // Schedule the handler to run again after a delay
//                handler.postDelayed(this, 1000); // 1000 milliseconds = 1 second
//            }
//        });
//    }
//    private void stopHandler() {
//        // Remove any pending callbacks to stop the handler
//        handler.removeCallbacksAndMessages(null);
//    }
//
//    private boolean isOutOfTimer(Phase1Order phase1Order) {
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//            Date datePlaced = sdf.parse(phase1Order.getDatePlaced());
//
//            // Update currentDate to the current date and time
//            currentDate = new Date();
//
//            long differenceInMillis = currentDate.getTime() - datePlaced.getTime();
//            long differenceInMinutes = differenceInMillis / (60 * 1000);
//
//            boolean isOutOfTimer = differenceInMinutes >= 1;
//            Log.e("isOutOfTimer", isOutOfTimer + "" + "\ndateplaced: " + datePlaced.getTime() + "\ncurrent: " + currentDate.getTime() + "\ndiff: " + differenceInMinutes);
//
//            return isOutOfTimer;
//        } catch (ParseException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        startHandler(washer.getUsername());
//
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        stopHandler();
//        binding = null;
//    }
//
//
//}