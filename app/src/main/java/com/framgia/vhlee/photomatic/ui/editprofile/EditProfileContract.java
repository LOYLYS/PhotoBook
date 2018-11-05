package com.framgia.vhlee.photomatic.ui.editprofile;

import com.framgia.vhlee.photomatic.data.model.User;

public interface EditProfileContract {
    interface Presenter {
        void getProfile(String userId);

        void updateProfile(User user);

        void reAuthenticate(String password);

        void setPassword(String newPassword);
    }

    interface View {
        void onGetProfileSuccess(User user);

        void onUpdateProfileSuccess();

        void onSetPasswordSuccess();

        void onReAuthenticateSuccess();

        void onFailure(String message);
    }
}
