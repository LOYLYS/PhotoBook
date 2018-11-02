package com.framgia.vhlee.photomatic.data.source.remote;

import com.framgia.vhlee.photomatic.data.source.DataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseAccount implements DataSource.Remote {
    private FirebaseAuth mAuth;

    public FirebaseAccount() {
        mAuth = FirebaseAuth.getInstance();
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
}
