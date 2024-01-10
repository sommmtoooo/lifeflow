package com.somtodev.lifeflow.views.fragments;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.somtodev.lifeflow.R;
import com.somtodev.lifeflow.adapters.BloodRequestAdapter;
import com.somtodev.lifeflow.lib.FirebaseUtils;
import com.somtodev.lifeflow.models.BloodRequest;
import com.somtodev.lifeflow.utils.Utils;
import com.somtodev.lifeflow.views.HomeScreen;

public class RequestFragment extends Fragment {

    public RequestFragment() {
    }


    private RecyclerView recyclerView;
    private FirestoreRecyclerAdapter<BloodRequest, BloodRequestAdapter.BloodRequestViewHolder> bloodRequestAdapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        recyclerView = (RecyclerView) view.findViewById(R.id.rvBloodRequests);
        FirebaseUtils firebaseUtils = new FirebaseUtils();
        Query query = firebaseUtils.database.collection("requests").whereGreaterThan("dueDate", Utils.getRawDateFormat());
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
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(view.getContext(), "channel_01")
                                .setSmallIcon(R.mipmap.icon)
                                .setContentTitle("New Blood Request")
                                .setContentText("Let's help someone today, by donating !")
                                .setPriority(NotificationCompat.PRIORITY_HIGH);
                        NotificationManager manager = (NotificationManager) view.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                        manager.notify(0, builder.build());
                    }
                }
            }
        });
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