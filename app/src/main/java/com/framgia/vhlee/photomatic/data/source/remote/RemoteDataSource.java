package com.framgia.vhlee.photomatic.data.source.remote;

import com.framgia.vhlee.photomatic.data.source.DataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class RemoteDataSource implements DataSource.Remote {
    private static RemoteDataSource sRemoteDataSource;

    public RemoteDataSource() {
    }

    public static RemoteDataSource newInstance() {
        if (sRemoteDataSource == null) sRemoteDataSource = new RemoteDataSource();
        return sRemoteDataSource;
    }

    @Override
    public void checkSate(FirebaseAuth.AuthStateListener listener) {
        FirebaseAccount firebaseAccount = new FirebaseAccount();
        firebaseAccount.checkSate(listener);
    }

    @Override
    public void login(String email, String password, OnCompleteListener listener) {
        FirebaseAccount firebaseAccount = new FirebaseAccount();
        firebaseAccount.login(email, password, listener);
    }
}
