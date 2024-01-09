package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laundirii.R;
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardHistoryAdapter extends RecyclerView.Adapter<WasherDashboardHistoryAdapter.PedingViewHolder> {
    private List<Phase1Order> orders;
    Context context;

    public WasherDashboardHistoryAdapter(List<Phase1Order> orders,Context context) {
        this.orders = orders;
        this.context = context;
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
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID());
        holder.textViewClientName.setText("Client Name: " + order.getClient().getName());
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_history));
        holder.textViewCourierStatus.setText("Courier Status: " + order.getCourierStatus());

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
