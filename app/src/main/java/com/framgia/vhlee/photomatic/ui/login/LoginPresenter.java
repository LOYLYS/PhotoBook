package com.framgia.vhlee.photomatic.ui.login;

import android.support.annotation.NonNull;

import com.framgia.vhlee.photomatic.data.repository.DataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginPresenter implements LoginContract.Presenter {
    private LoginContract.View mView;
    private DataRepository mRepository;

    public LoginPresenter(LoginContract.View view) {
        mView = view;
        mRepository = DataRepository.newInstance();
    }

    @Override
    public void checkState() {
        mRepository.checkSate(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser currentUser = firebaseAuth.getCurrentUser();
                if (currentUser != null) mView.onSuccess();
            }
        });
    }

    @Override
    public void login(String email, String password) {
        mRepository.login(email, password, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) mView.onSuccess();
                else mView.onFailure(task.getException().getMessage());
            }
        });
    }
}
