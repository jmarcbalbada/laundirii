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
import com.example.laundirii.model.Orders;
import com.example.laundirii.model.Phase1Order;
import com.example.laundirii.model.Phase2Order;

import java.util.List;

public class WasherDashboardHistoryAdapter extends RecyclerView.Adapter<WasherDashboardHistoryAdapter.PedingViewHolder> {
    private List<Orders> orders;
    Context context;

    public WasherDashboardHistoryAdapter(List<Orders> orders,Context context) {
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
        Orders order = orders.get(position);
        // if the history is Phase 1

        if(order instanceof Phase1Order){
            Phase1Order phase1Order = ((Phase1Order)order);
            holder.textViewOrderId.setText("To Washer  Order ID: " + phase1Order.getOrderID());
            holder.textViewClientName.setText("Client Name: " + phase1Order.getClient().getName());
            if(phase1Order.getPhase1OrderStatus() == -1 ){
                holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_history));
                holder.textViewCourierStatus.setText(" Status: Declined");
            }else{
                holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending9));
                holder.textViewCourierStatus.setText(" Status: Completed ");
            }
        }
        // if the history is Phase 2
        if(order instanceof Phase2Order){
            Phase2Order phase2Order = ((Phase2Order)order);
            holder.textViewOrderId.setText("To Client Order ID: " + phase2Order.getOrderID());
            holder.textViewClientName.setText("Client Name: " + phase2Order.getClient().getName());
            holder.textViewCourierStatus.setText("Status: " + phase2Order.getPhase2OrderStatus());
            if(phase2Order.getPhase2OrderStatus() == -1 ){
                holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_history));
                holder.textViewCourierStatus.setText(" Status: Declined");
            }else{
                holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending9));
                holder.textViewCourierStatus.setText(" Status: Completed ");
            }
        }

    }

    @Override
    public int getItemCount() {
        return orders.size();
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
