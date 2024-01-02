package com.somtodev.lifeflow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.models.BloodRequest;

public class BloodRequestAdapter extends FirestoreRecyclerAdapter<BloodRequest, BloodRequestAdapter.BloodRequestViewHolder> {


    public BloodRequestAdapter(@NonNull FirestoreRecyclerOptions<BloodRequest> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull BloodRequestViewHolder holder, int position, @NonNull BloodRequest model) {

    }

    @NonNull
    @Override
    public BloodRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View bloodRequestView = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card, parent, false);
        return new BloodRequestViewHolder(bloodRequestView);
    }

    static class BloodRequestViewHolder extends RecyclerView.ViewHolder{
        public BloodRequestViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
