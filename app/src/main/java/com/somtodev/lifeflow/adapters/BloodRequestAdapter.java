package com.somtodev.lifeflow.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.models.BloodRequest;

public class BloodRequestAdapter extends FirestoreRecyclerAdapter<BloodRequest, BloodRequestAdapter.BloodRequestViewHolder> {

    private Context context;
    private final FirestoreRecyclerOptions<BloodRequest> requests;

    public BloodRequestAdapter(@NonNull FirestoreRecyclerOptions<BloodRequest> requests, Context context) {
        super(requests);
        this.requests = requests;
        this.context = context;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull BloodRequestViewHolder holder, int position, @NonNull BloodRequest model) {
        BloodRequest request = requests.getSnapshots().get(position);

        holder.tvCenterName.setText(request.getCenterName());
        holder.tvCenterLocation.setText(request.getCenterLocation());
        holder.tvCenterBloodRequestGroup.setText(request.getBloodGroup());
        holder.tvCenterBloodRequestPack.setText(String.valueOf(request.getPacks()));
    }

    @NonNull
    @Override
    public BloodRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bloodRequestView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card, parent, false);
        return new BloodRequestViewHolder(bloodRequestView);
    }

    public static class BloodRequestViewHolder extends RecyclerView.ViewHolder {

        TextView tvCenterName, tvCenterLocation, tvCenterBloodRequestGroup, tvCenterBloodRequestPack;

        public BloodRequestViewHolder(@NonNull View view) {
            super(view);

            tvCenterName = (TextView) view.findViewById(R.id.tvCenterName);
            tvCenterLocation = (TextView) view.findViewById(R.id.tvCenterLocation);
            tvCenterBloodRequestGroup = (TextView) view.findViewById(R.id.tvCenterBloodRequestGroup);
            tvCenterBloodRequestPack = (TextView) view.findViewById(R.id.tvCenterBloodRequestPack);
        }
    }
}
