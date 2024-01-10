package com.somtodev.lifeflow.views.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.adapters.BloodRequestAdapter;
import com.somtodev.lifeflow.lib.FirebaseUtils;
import com.somtodev.lifeflow.models.BloodRequest;
import com.somtodev.lifeflow.utils.Utils;
import com.somtodev.lifeflow.views.HomeScreen;

import java.util.Objects;

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

        FirebaseUtils firebaseUtils = new FirebaseUtils();

        tvUsername = (TextView) view.findViewById(R.id.tvUsername);
        btnRequest = (Button) view.findViewById(R.id.btnShowMore);
        recyclerView = (RecyclerView) view.findViewById(R.id.rvBloodRequests);

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeScreen homeScreen = (HomeScreen) getActivity();
                if (homeScreen != null) {
                    homeScreen.setCurrentFragment(new RequestFragment());
                }
            }
        });


        tvUsername.setText("Donating Saves Lives");
        firebaseUtils.database.collection("users").document(firebaseUtils.getCurrentUser().getUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (documentSnapshot.getData() == null) {
                            tvUsername.setText("Welcome");
                            return;
                        }

                        String firstname = (String) Objects.requireNonNull(documentSnapshot.getData()).get("firstname");
                        tvUsername.setText("Hello, " + firstname);
                    }
                }, 3000);

            }
        });


        Query query = firebaseUtils.database.collection("requests").orderBy("dueDate", Query.Direction.ASCENDING).whereGreaterThan("dueDate", Utils.getRawDateFormat()).limit(5);
        FirestoreRecyclerOptions<BloodRequest> requests = new FirestoreRecyclerOptions.Builder<BloodRequest>().setQuery(query, BloodRequest.class).build();

        query.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot snapshots, @Nullable FirebaseFirestoreException error) {
                if (error != null)
                    return;

                assert snapshots != null;
                for (DocumentChange change : snapshots.getDocumentChanges()) {
                    if (change.getType() == DocumentChange.Type.ADDED) {
                        HomeScreen homeScreen = (HomeScreen) getActivity();
                        if (homeScreen != null) {
                            homeScreen.updateRequestCount();
                        }
                        // Do Some Notification Magic
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), "channel_01")
                                .setSmallIcon(R.mipmap.icon)
                                .setContentTitle("New Blood Request")
                                .setContentText("Help someone today, by donating")
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    }
                }
            }
        });

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