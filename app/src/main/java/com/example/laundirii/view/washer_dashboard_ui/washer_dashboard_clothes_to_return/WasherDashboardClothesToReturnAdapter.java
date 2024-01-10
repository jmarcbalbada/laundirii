package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

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
import com.example.laundirii.model.Phase2Order;

import java.util.List;

public class WasherDashboardClothesToReturnAdapter extends RecyclerView.Adapter<WasherDashboardClothesToReturnAdapter.ClothesToReturnHolder> {

    private List<Phase2Order> orders;
    Context context;

    public WasherDashboardClothesToReturnAdapter(List<Phase2Order> orders, Context context){
        this.orders = orders;
        this.context = context;
    }

    @NonNull
    @Override
    public ClothesToReturnHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.washer_tolist_clothestoreturn, parent, false);
        return new WasherDashboardClothesToReturnAdapter.ClothesToReturnHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClothesToReturnHolder holder, int position) {
        Phase2Order order = orders.get(position);

        switch (order.getPhase2OrderStatus()) {
            case 10:
                // checking balance

                // Confirm Washer Payment Using Reference ID
                setClientPaymentConfirmationStyle(holder,order);
                break;
            case 11:
                // courier is on the way

                // Courier On the way to washer
                // courier will change the status to notify washer
                setCourierOnTheWayToWasherStyle(holder,order);
                break;
            case 12:
                // Courier Arrived ()

                // send the item to the courier (give the item and the payment)

                // washer click the button to confirm he sent the laundry and payment
                setWasherHandedOverTheClothesAndPaymentsToCourierStyle(holder,order);
                break;
            case 13:

                // courier will confirm (received the payment and received the clothes)

                // change the status to 14 to make status on the way to client
                setPendingConfirmingClientReceivedThePaymentAndClothesStyle(holder,order);
                break;
            case 14:
                // courier on the way to client

                // courier will change the status to 15 to notify the client
                setCourierOnTheWayToClientStyle(holder,order);
                break;
            case 15:
                // courier hand the item to client

                // client change the status to 16 mark the item as received
                setCourierArrivedStyle(holder,order);
                break;
            case 16:

                // completed
                setPhase2OrderCompleteTransactionStyle(holder,order);
                break;
        }
    }

    private void setPhase2OrderCompleteStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_rider_on_the_way_here));
        holder.textViewCourierStatus.setText("Transaction Completed");
    }

    private void setPhase2OrderCompleteTransactionStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending7));
        holder.textViewCourierStatus.setText("Completed");
    }

    private void setCourierArrivedStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending6));
        holder.textViewCourierStatus.setText("Courier Arrived");

    }

    private void setCourierOnTheWayToClientStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending5));
        holder.textViewCourierStatus.setText("Courier On the way to Client");

    }

    private void setPendingConfirmingClientReceivedThePaymentAndClothesStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending4));
        holder.textViewCourierStatus.setText("Courier Payment Confirmation");

    }

    private void setWasherHandedOverTheClothesAndPaymentsToCourierStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending3));
        holder.textViewCourierStatus.setText("Laundry Hand Over");
        holder.itemView.setOnClickListener(view->{
            Intent intent = new Intent(view.getContext(), WasherDashboardClothesToReturnClothesHandedOverToCourier.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });
    }

    private void setCourierOnTheWayToWasherStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending2));
        holder.textViewCourierStatus.setText("Courier On The Way To You");

    }

    private void setClientPaymentConfirmationStyle(ClothesToReturnHolder holder, Phase2Order order) {
        initialize(holder,order);
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.pending1));
        holder.textViewCourierStatus.setText("Payment Confirmation");
        holder.itemView.setOnClickListener(view->{
            Intent intent = new Intent(view.getContext(), WasherDashboardClothesToReturnClientPaymentConfirmation.class);
            intent.putExtra("selectedOrder", order);
            view.getContext().startActivity(intent);
        });

    }


    private void initialize(ClothesToReturnHolder holder,Phase2Order order){
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID() +" -- Order Status: " + order.getPhase2OrderStatus()  );
        holder.textViewClientName.setText("Client Name: " + order.getClient().getName());
        holder.textViewCourierStatus.setText("Pending Waiting For Approval");
        holder.textViewReference.setText("");
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class ClothesToReturnHolder extends RecyclerView.ViewHolder {
        TextView textViewClientName;
        TextView textViewOrderId;
        TextView textViewCourierStatus;
        TextView textViewReference;


        public ClothesToReturnHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewClientName = itemView.findViewById(R.id.textViewClientName);
            textViewCourierStatus = itemView.findViewById(R.id.textViewCourierStatus);
            textViewReference = itemView.findViewById(R.id.textViewReference);
            // Initialize other TextViews or UI components as needed
        }
    }

}
