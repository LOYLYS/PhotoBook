package com.framgia.vhlee.photomatic.ui.profile;

import com.framgia.vhlee.photomatic.data.model.User;

public interface ProfileContract {
    interface Presenter {
        void getProfile(String userId);
    }

    interface View {
        void onGetProfileSuccess(User user);

        void onFailure(String message);
    }
}
