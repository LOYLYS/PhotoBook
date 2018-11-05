package com.framgia.vhlee.photomatic.ui.more;

import com.framgia.vhlee.photomatic.data.model.User;

public interface MoreContract {
    interface Presenter {
        void getProfile(String userId);
    }

    interface View {
        void onGetProfileSuccess(User user);

        void onFailure(String message);
    }
}
