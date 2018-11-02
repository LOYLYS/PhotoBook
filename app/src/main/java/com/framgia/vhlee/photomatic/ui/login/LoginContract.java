package com.framgia.vhlee.photomatic.ui.login;

public interface LoginContract {
    interface Presenter {
        void checkState();

        void login(String email, String password);
    }

    interface View {
        void onSuccess();

        void onFailure(String message);
    }
}
