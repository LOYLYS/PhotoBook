package com.framgia.vhlee.photomatic.data.source.remote;

import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.source.DataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class RemoteDataSource implements DataSource.Remote {
    private static RemoteDataSource sRemoteDataSource;
    private FirebaseAccount mFirebaseAccount;

    public RemoteDataSource() {
        mFirebaseAccount = new FirebaseAccount();
    }

    public static RemoteDataSource newInstance() {
        if (sRemoteDataSource == null) sRemoteDataSource = new RemoteDataSource();
        return sRemoteDataSource;
    }

    @Override
    public void checkSate(FirebaseAuth.AuthStateListener listener) {
        mFirebaseAccount.checkSate(listener);
    }

    @Override
    public void login(String email, String password, OnCompleteListener listener) {
        mFirebaseAccount.login(email, password, listener);
    }

    @Override
    public void register(String email, String password, OnCompleteListener listener) {
        mFirebaseAccount.register(email, password, listener);
    }

    @Override
    public void updateUserData(User user, OnCompleteListener listener) {
        mFirebaseAccount.updateUserData(user, listener);
    }
}
