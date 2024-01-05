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
import com.example.laundirii.model.Phase1Order;

import java.util.List;

public class WasherDashboardReceivedClothesAdapter extends RecyclerView.Adapter<WasherDashboardReceivedClothesAdapter.OrdersViewHolder> {

    private List<Phase1Order> orders;
    private Context context;

    public WasherDashboardReceivedClothesAdapter(List<Phase1Order> orders, Context context) {
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_receivedclothes, parent, false);
        return new OrdersViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        Phase1Order order = orders.get(position);
        // Bind your Phase1Order data to the ViewHolder
        // Bind other data as needed

        switch (order.getPhase1OrderStatus()) {
            case 4:
                // WasherReceived the clothes and will set the order status to 5
                //TODO
                // Make the logic
                setWasherWeightClothesStylee(holder,order);
                break;
            case 5:
                // Mark as Ready to Pick Up set status to 6
                //TODO
                // Make the logic
                setClothesReadyToPickUpStyle(holder,order);
                break;
            case 6:
                // Ready to Pick up

                setClothesToBePickUpStyle(holder,order);
                break;
        }
    }

    private void initialize(OrdersViewHolder holder, Phase1Order order) {
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID() +"-- Order Status: " + order.getPhase1OrderStatus()  );
        holder.textViewClientName.setText("Client Name: " + order.getClientName());
        holder.textViewCourierStatus.setText("Error: Out of Status");
    }
    private void setClothesToBePickUpStyle(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_here));
        holder.textViewCourierStatus.setText("Pending to be Picked Up");
//       TODO Phase2Order

//        holder.itemView.setOnClickListener(view -> {
//            // Handle item click here
//            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestToBePickUp.class);
//            intent.putExtra("selectedOrder", order);
//            view.getContext().startActivity(intent);
//        });
    }

    private void setWasherWeightClothesStylee(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_to_customer));
        holder.textViewCourierStatus.setText("Clothes Need to be weighted");
        holder.itemView.setOnClickListener(view -> {
            // Handle item click here
            Intent intent = new Intent(view.getContext(), WasherDashboardPendingrequestClothestoweight.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });

    }
    private void setClothesReadyToPickUpStyle(OrdersViewHolder holder, Phase1Order order) {
        this.initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_customer_waiting_for_approval));
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
        TextView textViewCourierStatus;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewClientName = itemView.findViewById(R.id.textViewClientName);
            textViewCourierStatus = itemView.findViewById(R.id.textViewCourierStatus);

            // Initialize other TextViews or UI components as needed
        }

    }


}

