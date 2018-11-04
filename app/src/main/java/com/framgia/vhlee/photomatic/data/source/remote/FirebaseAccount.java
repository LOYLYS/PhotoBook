package com.framgia.vhlee.photomatic.data.source.remote;

import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.source.DataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseAccount implements DataSource.Remote {
    private static final String TABLE_USERS = "users";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    public FirebaseAccount() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    @Override
    public void checkSate(FirebaseAuth.AuthStateListener listener) {
        mAuth.addAuthStateListener(listener);
    }

    @Override
    public void login(String email, String password, OnCompleteListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    @Override
    public void register(String email, String password, OnCompleteListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    @Override
    public void updateUserData(User user, OnCompleteListener listener) {
        DatabaseReference mUserRef = mDatabase.getReference(TABLE_USERS);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        mUserRef.child(currentUser.getUid())
                .setValue(user)
                .addOnCompleteListener(listener);
    }

    @Override
    public void getUserData(String userId, ValueEventListener listener) {
        DatabaseReference mUserRef = mDatabase.getReference(TABLE_USERS);
        mUserRef.child(userId).addValueEventListener(listener);
    }
}
