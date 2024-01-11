package com.example.laundirii.view.client_dashboard_ui.client_dashboard_feedback;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.databinding.ClientFragmentCurrentBinding;
import com.example.laundirii.databinding.ClientFragmentFeedbackBinding;
import com.example.laundirii.databinding.ClientFragmentHistoryBinding;
import com.example.laundirii.model.Client;
import com.example.laundirii.model.Feedback;
import com.example.laundirii.model.Orders;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;
import com.example.laundirii.view.client_dashboard_ui.BookServiceActivity;
import com.example.laundirii.view.client_dashboard_ui.client_dashboard_current.CurrentFragment;

import java.util.ArrayList;
import java.util.List;

public class ClientFeedbackFragment extends Fragment {

    private ClientFragmentFeedbackBinding binding;
    private Client client;
    SharedPreferences clientInfoPreferences;
    private ListView lv_FeedbackList;
    private ArrayAdapter feedbackClientOrdersAdapter;
    private DashboardController dashboardController;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = ClientFragmentFeedbackBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        dashboardController = new DashboardController();
        clientInfoPreferences = this.getActivity().getSharedPreferences("LoginClientPreferences", 0);
        String clientUsername = clientInfoPreferences.getString("clientUsername", "");
        client = dashboardController.getClient(clientUsername, this.getActivity());
        Log.e("thisClient", client.toString());
        lv_FeedbackList = root.findViewById(R.id.feedback_transaction);
        displayFeedback();

        lv_FeedbackList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Orders selectedItem = (Orders) parent.getItemAtPosition(position);
                showFeedbackDialog(selectedItem);

            }
        });
        return root;
    }

    public void displayFeedback()
    {
        List<Orders> listToFeedback = new ArrayList<>();
        listToFeedback.addAll(dashboardController.getToFeedbackListOnPhase1(client.getCustomerID(), this.getActivity()));
        listToFeedback.addAll(dashboardController.getToFeedbackListOnPhase2(client.getCustomerID(), this.getActivity()));
        Log.e("ListFeedback", listToFeedback.size() + "");
        ArrayAdapter<Orders> feedbackClientOrdersAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, listToFeedback);
        lv_FeedbackList.setAdapter(feedbackClientOrdersAdapter);
    }

    private void showFeedbackDialog(Orders order)
    {
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.popup_feedback_ui, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Rate your order!");

        // Show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBarFeedback);
        EditText comment = dialogView.findViewById(R.id.editTextCommentFeedback);
        TextView textViewSubmitButton = dialogView.findViewById(R.id.btnSubmitFeedback);

        textViewSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String commentStr = comment.getText().toString();
                int roundedRating = Math.round(ratingBar.getRating());
                if(order instanceof Phase1Order)
                {
                    Phase1Order phase1Order = (Phase1Order) order;
                    Log.e("INSIDE PHASE1ORDER","phase1Order");
                    // rate courier
                    boolean success = dashboardController.insertFeedback(phase1Order.getClientID(),
                            commentStr,roundedRating,phase1Order.getCourier().getCourierID(),
                            0,phase1Order.getOrderID(),1,getContext());

                    boolean success2 = dashboardController.insertFeedback(phase1Order.getClientID(),
                            commentStr,roundedRating,0,
                            phase1Order.getWasherID(), phase1Order.getOrderID(),1,getContext());
                    dashboardController.updateWasherOverallRating(phase1Order.getWasherID(),getContext());
                    dashboardController.updateCourierOverallRating(phase1Order.getCourier().getCourierID(),getContext());
                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();

                    // send notif to both
                    String notificationTitle = "You received a rating from Client!";
                    String notificationMessage = "Client under the order ID " + phase1Order.getOrderID() + " rated you " + roundedRating + "/5. Thank you using Laundiri!";
                    dashboardController.sendNotifications(0, 0,phase1Order.getCourier().getCourierID(),notificationTitle,notificationMessage,getContext());
                    dashboardController.sendNotifications(phase1Order.getWasherID(), 0,0,notificationTitle,notificationMessage,getContext());
                    Log.e("SUCCESS1","success = " + success);
                    Log.e("SUCCESS2","success2 = " + success2);
                    Log.e("PHASE1ORDER",phase1Order + "");
                }
                else if(order instanceof Phase2Order)
                {
                    Phase2Order phase2Order = (Phase2Order) order;
                    Log.e("INSIDE PHASE2ORDER","phase2Order");
                    // rate courier
                    boolean success = dashboardController.insertFeedback(phase2Order.getClientID(),
                            commentStr,roundedRating,phase2Order.getCourier().getCourierID(),
                            0,phase2Order.getOrderID(),2,getContext());
                    boolean success2 = dashboardController.insertFeedback(phase2Order.getClientID(),
                            commentStr,roundedRating,0,
                            phase2Order.getWasherID(), phase2Order.getOrderID(),2,getContext());
                    dashboardController.updateWasherOverallRating(phase2Order.getWasherID(),getContext());
                    dashboardController.updateCourierOverallRating(phase2Order.getCourier().getCourierID(),getContext());
                    Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();

                    // send notif to both
                    String notificationTitle = "You received a rating from Client!";
                    String notificationMessage = "Client under the order ID " + phase2Order.getOrderID() + " rated you " + roundedRating + "/5. Thank you using Laundiri!";
                    dashboardController.sendNotifications(0, 0,phase2Order.getCourier().getCourierID(),notificationTitle,notificationMessage,getContext());
                    dashboardController.sendNotifications(phase2Order.getWasherID(), 0,0,notificationTitle,notificationMessage,getContext());
                    Log.e("SUCCESS1","success = " + success);
                    Log.e("SUCCESS2","success2 = " + success2);
                }
                displayFeedback();
                dialog.dismiss();
            }
        });

    }

}
