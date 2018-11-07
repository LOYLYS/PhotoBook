package com.framgia.vhlee.photomatic.data.source.remote;

import android.net.Uri;

import com.framgia.vhlee.photomatic.data.model.Post;
import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.source.DataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

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
    public void reAuthenticate(String password, OnCompleteListener listener) {
        mFirebaseAccount.reAuthenticate(password, listener);
    }

    @Override
    public void updateUserData(User user, OnCompleteListener listener) {
        mFirebaseAccount.updateUserData(user, listener);
    }

    @Override
    public void getUserData(String userId, ValueEventListener listener) {
        mFirebaseAccount.getUserData(userId, listener);
    }

    @Override
    public void setPassword(String newPassword, OnCompleteListener listener) {
        mFirebaseAccount.setPassword(newPassword, listener);
    }

    @Override
    public void savePost(Post post, OnCompleteListener listener) {
        mFirebaseAccount.savePost(post, listener);
    }

    @Override
    public void uploadPhoto(Uri uri, OnSuccessListener<UploadTask.TaskSnapshot> successListener,
                            OnFailureListener failureListener) {
        RemoteStorage remoteStorage = new RemoteStorage();
        remoteStorage.uploadPhoto(uri, successListener, failureListener);
    }
}
