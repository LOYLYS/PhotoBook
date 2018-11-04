package com.framgia.vhlee.photomatic.ui.profile;

import android.support.annotation.NonNull;

import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.repository.DataRepository;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfilePresenter implements ProfileContract.Presenter {
    private ProfileContract.View mView;
    private DataRepository mRepository;

    public ProfilePresenter(ProfileContract.View view) {
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
}
