package com.somtodev.lifeflow.views.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.adapters.BloodRequestAdapter;
import com.somtodev.lifeflow.models.BloodRequest;

public class HomeFragment extends Fragment {

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    FirestoreRecyclerAdapter<BloodRequest, BloodRequestAdapter.BloodRequestViewHolder> bloodRequestAdapter;
    TextView tvUsername;
    Button btnRequest;
    RecyclerView recyclerView;

    public HomeFragment() {
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        btnRequest = (Button) view.findViewById(R.id.btnShowMore);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvBloodRequests);

        tvUsername.setText("Welcome");

        Query query = firebaseFirestore.collection("requests").orderBy("dueDate", Query.Direction.ASCENDING).limit(5);
        FirestoreRecyclerOptions<BloodRequest> requests = new FirestoreRecyclerOptions.Builder<BloodRequest>().setQuery(query, BloodRequest.class).build();

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        bloodRequestAdapter = new BloodRequestAdapter(requests, view.getContext());
        recyclerView.setAdapter(bloodRequestAdapter);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }
}