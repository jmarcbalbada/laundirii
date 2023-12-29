package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_history;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_pendingrequest.WasherDashboardPendingrequestConfirmation;

import java.util.List;

public class WasherDashboardHistoryAdapter extends RecyclerView.Adapter<WasherDashboardHistoryAdapter.PedingViewHolder> {
    private List<Phase1Order> orders;

    public WasherDashboardHistoryAdapter(List<Phase1Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public WasherDashboardHistoryAdapter.PedingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_pendingdeliveryrequest, parent, false);
        return new PedingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WasherDashboardHistoryAdapter.PedingViewHolder holder, int position) {
        Phase1Order order = orders.get(position);
        // Bind your Phase1Order data to the ViewHolder
        // Bind other data as needed
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID());
        holder.textViewClientName.setText("Client Name: " + order.getClientName());
        holder.textViewCourierStatus.setText("Courier Status: " + order.getCourierStatus());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestConfirmation.class);
                intent.putExtra("selectedOrder", order);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public void washerMarkReceivedFromCourier() {
        // Implement the functionality as needed
    }

    public static class PedingViewHolder extends RecyclerView.ViewHolder {
        TextView textViewClientName;
        TextView textViewOrderId;
        TextView textViewCourierStatus;

        public PedingViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewClientName = itemView.findViewById(R.id.textViewClientName);
            textViewCourierStatus = itemView.findViewById(R.id.textViewCourierStatus);
            // Initialize other TextViews or UI components as needed
        }
    }
}