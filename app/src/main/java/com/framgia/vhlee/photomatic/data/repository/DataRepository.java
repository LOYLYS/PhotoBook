package com.framgia.vhlee.photomatic.data.repository;

import com.framgia.vhlee.photomatic.data.source.DataSource;
import com.framgia.vhlee.photomatic.data.source.remote.FirebaseAccount;
import com.framgia.vhlee.photomatic.data.source.remote.RemoteDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;

public class DataRepository implements DataSource.Remote {
    private static DataRepository sDataRepository;
    private DataSource.Remote mRemote;
    private DataSource.Local mLocal;

    public DataRepository() {
        mRemote = RemoteDataSource.newInstance();
    }

    public static DataRepository newInstance() {
        if (sDataRepository == null) sDataRepository = new DataRepository();
        return sDataRepository;
    }

    @Override
    public void checkSate(FirebaseAuth.AuthStateListener listener) {
        mRemote.checkSate(listener);
    }

    @Override
    public void login(String email, String password, OnCompleteListener listener) {
        mRemote.login(email,password, listener);
    }
}