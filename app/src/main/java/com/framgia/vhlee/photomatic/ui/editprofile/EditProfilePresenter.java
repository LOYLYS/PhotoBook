package com.framgia.vhlee.photomatic.ui.editprofile;

import android.support.annotation.NonNull;

import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.repository.DataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class EditProfilePresenter implements EditProfileContract.Presenter {
    private EditProfileContract.View mView;
    private DataRepository mRepository;

    public EditProfilePresenter(EditProfileContract.View view) {
        mView = view;
        mRepository = DataRepository.newInstance();
    }

    @Override
    public void getProfile(String userId) {
        mRepository.getUserData(userId, new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                mView.onGetProfileSuccess(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                mView.onFailure(databaseError.getMessage());
            }
        });
    }

    @Override
    public void updateProfile(User user) {
        mRepository.updateUserData(user, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) mView.onUpdateProfileSuccess();
                else mView.onFailure(task.getException().getMessage());
            }
        });
    }

    @Override
    public void reAuthenticate(String password) {
        mRepository.reAuthenticate(password, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) mView.onReAuthenticateSuccess();
                else mView.onFailure(task.getException().getMessage());
            }
        });
    }

    @Override
    public void setPassword(String newPassword) {
        mRepository.setPassword(newPassword, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) mView.onSetPasswordSuccess();
                else mView.onFailure(task.getException().getMessage());
            }
        });
    }
}
