package com.framgia.vhlee.photomatic.ui.register;

import android.support.annotation.NonNull;

import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.repository.DataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class RegisterPresenter implements RegisterContract.Presenter {
    private RegisterContract.View mView;
    private DataRepository mRepository;

    public RegisterPresenter(RegisterContract.View view) {
        mView = view;
        mRepository = DataRepository.newInstance();
    }

    @Override
    public void register(String email, String password) {
        mRepository.register(email, password, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) mView.onSuccess();
                else mView.onFailure(task.getException().getMessage());
            }
        });
    }

    @Override
    public void updateUserData(User user) {
        mRepository.updateUserData(user, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) mView.onUpdateSuccess();
                else mView.onUpdateFailure(task.getException().getMessage());
            }
        });
    }
}
