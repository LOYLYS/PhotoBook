package com.framgia.vhlee.photomatic.data.source;

import com.framgia.vhlee.photomatic.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;

public interface DataSource {
    interface Local {

    }

    interface Remote {
        void checkSate(FirebaseAuth.AuthStateListener listener);

        void login(String email, String password, OnCompleteListener listener);

        void register(String email, String password, OnCompleteListener listener);

        void reAuthenticate(String password, OnCompleteListener listener);

        void updateUserData(User user, OnCompleteListener listener);

        void getUserData(String userId, ValueEventListener listener);

        void setPassword(String newPassword, OnCompleteListener listener);
    }
}
