package com.somtodev.lifeflow.lib;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtils {

    public FirebaseAuth firebaseAuth;
    public FirebaseFirestore database;


    public FirebaseUtils() {
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();
    }

    public FirebaseUser getCurrentUser() {
        return firebaseAuth.getCurrentUser();
    }


}