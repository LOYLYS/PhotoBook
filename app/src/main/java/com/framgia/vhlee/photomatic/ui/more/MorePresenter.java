package com.framgia.vhlee.photomatic.ui.more;

import android.support.annotation.NonNull;

import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.repository.DataRepository;
import com.framgia.vhlee.photomatic.ui.profile.ProfileContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class MorePresenter implements MoreContract.Presenter {
    private MoreContract.View mView;
    private DataRepository mRepository;

    public MorePresenter(MoreContract.View view) {
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
