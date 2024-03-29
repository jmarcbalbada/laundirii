package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_receivedclothes;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.controller.DashboardController;
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardReceivedClothesAdapter extends RecyclerView.Adapter<WasherDashboardReceivedClothesAdapter.OrdersViewHolder> {

    private DashboardController dashboardController;
    private List<Phase1Order> orders;
    private Context context;

    public WasherDashboardReceivedClothesAdapter(List<Phase1Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        dashboardController = new DashboardController();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_receivedclothes, parent, false);
        return new OrdersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Phase1Order order = orders.get(position);
        // Bind your Phase1Order data to the ViewHolder
        // Bind other data as needed

        holder.textViewOrderId.setText("");
        holder.textViewClientName.setText("Client Name: " + order.getClientName());
        holder.textViewLaundryDatePlaced.setText("Date Placed: " + order.getDatePlaced());
        holder.textViewLaundryWeight.setText("Laundry Weight: " + order.getInitialLoad());
        holder.textViewPendingBalance.setText("Client Balance: " + order.getTotalDue());

        switch (order.getPhase1OrderStatus()) {
            case 4:
                // WasherReceived the clothes and will set the order status to 5
                setWasherWeightClothesStylee(holder,order);
                break;
            case 5:
                // Mark as Ready to Pick Up set status to 6
                setClothesReadyToPickUpStyle(holder,order);
                break;
            case 6:
                // Ready to Pick up (Not Complete)
                setClothesToBePickUpStyle(holder,order);
                break;
        }
    }

    private void setClothesToBePickUpStyle(OrdersViewHolder holder, Phase1Order order) {
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_here));
        holder.textViewCourierStatus.setText("Pending to be Picked Up");
    }

    private void setWasherWeightClothesStylee(OrdersViewHolder holder, Phase1Order order) {

        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_to_customer));
        holder.textViewLaundryWeight.setText("Initial Laundry Weight: " + order.getInitialLoad() +" KG");
        holder.textViewPendingBalance.setText("Initial Client Balance: " + ((order.getInitialLoad()*order.getWasher().getRatePerKg())+order.getTotalCourierAmount()));
        holder.textViewCourierStatus.setText("Clothes Need to be weighted");
        holder.itemView.setOnClickListener(view -> {
            // Handle item click here
            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestClothestoweight.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });

    }
    private void setClothesReadyToPickUpStyle(OrdersViewHolder holder, Phase1Order order) {
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_customer_waiting_for_approval));
        holder.textViewLaundryWeight.setText("Laundry Weight: " + order.getInitialLoad());
        holder.textViewPendingBalance.setText("Client Balance: " + order.getTotalDue());
        holder.textViewCourierStatus.setText("Clothes Need to Wash");
        holder.itemView.setOnClickListener(view -> {
            // Handle item click here
            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestClothesReadyToPickUp.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });
    }


    @Override
    public int getItemCount() {
        return orders.size();
    }
    public static class OrdersViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClientName;
        TextView textViewOrderId;
        TextView textViewLaundryDatePlaced;
        TextView textViewLaundryWeight;
        TextView textViewPendingBalance;
        TextView textViewCourierStatus;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewClientName = itemView.findViewById(R.id.textViewClientName);
            textViewLaundryDatePlaced = itemView.findViewById(R.id.textViewLaundryDatePlaced);
            textViewLaundryWeight = itemView.findViewById(R.id.textViewLaundryWeight);
            textViewPendingBalance = itemView.findViewById(R.id.textViewPendingBalance);
            textViewCourierStatus = itemView.findViewById(R.id.textViewCourierStatus);

            // Initialize other TextViews or UI components as needed
        }


    }


}

