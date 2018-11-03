package com.framgia.vhlee.photomatic.ui.register;

import com.framgia.vhlee.photomatic.data.model.User;

public interface RegisterContract {
    interface Presenter {
        void register(String email, String password);

        void updateUserData(User user);
    }

    interface View {
        void onSuccess();

        void onFailure(String message);

        void onUpdateSuccess();

        void onUpdateFailure(String message);
    }
}
