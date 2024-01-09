package com.somtodev.lifeflow.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.adapters.BloodRequestAdapter;
import com.somtodev.lifeflow.lib.FirebaseUtils;
import com.somtodev.lifeflow.models.BloodRequest;

public class RequestFragment extends Fragment {

    public RequestFragment() {
    }


    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<BloodRequest, BloodRequestAdapter.BloodRequestViewHolder> bloodRequestAdapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.rvBloodRequests);
        Query query = FirebaseUtils.database.collection("requests");
        FirestoreRecyclerOptions<BloodRequest> requests = new FirestoreRecyclerOptions.Builder<BloodRequest>().setQuery(query, BloodRequest.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        bloodRequestAdapter = new BloodRequestAdapter(requests, view.getContext());
        recyclerView.setAdapter(bloodRequestAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        bloodRequestAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        bloodRequestAdapter.stopListening();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.request_fragment, container, false);
    }
}