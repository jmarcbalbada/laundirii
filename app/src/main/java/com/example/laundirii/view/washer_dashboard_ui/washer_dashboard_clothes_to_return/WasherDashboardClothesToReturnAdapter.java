package com.example.laundirii.view.washer_dashboard_ui.washer_dashboard_clothes_to_return;

import android.content.Context;
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
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID());
        holder.textViewClientName.setText("Client Name: " + order.getClient().getName());
        holder.textViewCourierStatus.setBackgroundColor(ContextCompat.getColor(context, R.color.washer_history));
        holder.textViewCourierStatus.setText("Courier Status: " + order.getCourierStatus());
        switch (order.getPhase2OrderStatus()) {
            case 10:
                //
                setStyle1(holder,order);
                break;
            case 11:
                setStyle2(holder,order);
                break;
            case 12:
                setStyle3(holder,order);
                break;
            case 13:
                setStyle4(holder,order);
                break;
            case 14:
                setStyle5(holder,order);
                break;
            case 15:
                setStyle6(holder,order);
                break;
        }
    }
    private void initialize(ClothesToReturnHolder holder,Phase2Order order){
        holder.textViewOrderId.setText("Order ID: " + order.getOrderID() +"-- Order Status: " + order.getPhase2OrderStatus()  );
        holder.textViewClientName.setText("Client Name: " + order.getClient().getName());
        holder.textViewCourierStatus.setText("Pending Waiting For Approval");
    }

    private void setStyle6(ClothesToReturnHolder holder, Phase2Order order) {
    }

    private void setStyle5(ClothesToReturnHolder holder, Phase2Order order) {
    }

    private void setStyle4(ClothesToReturnHolder holder, Phase2Order order) {
    }

    private void setStyle3(ClothesToReturnHolder holder, Phase2Order order) {
    }

    private void setStyle2(ClothesToReturnHolder holder, Phase2Order order) {
    }

    private void setStyle1(ClothesToReturnHolder holder, Phase2Order order) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ClothesToReturnHolder extends RecyclerView.ViewHolder {
        TextView textViewClientName;
        TextView textViewOrderId;
        TextView textViewCourierStatus;
        TextView textViewMessage;


        public ClothesToReturnHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewClientName = itemView.findViewById(R.id.textViewClientName);
            textViewCourierStatus = itemView.findViewById(R.id.textViewCourierStatus);
            textViewMessage = itemView.findViewById(R.id.textViewReference);
            // Initialize other TextViews or UI components as needed
        }
    }

}
