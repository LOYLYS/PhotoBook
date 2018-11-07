package com.framgia.vhlee.photomatic.data.source;

import android.net.Uri;

import com.framgia.vhlee.photomatic.data.model.Post;
import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.data.source.local.LocalCallback;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.UploadTask;

public interface DataSource {
    interface Local {
        void getLocalPhotos(String path, LocalCallback<String> callback);
    }

    interface Remote {
        void checkSate(FirebaseAuth.AuthStateListener listener);

        void login(String email, String password, OnCompleteListener listener);

        void register(String email, String password, OnCompleteListener listener);

        void reAuthenticate(String password, OnCompleteListener listener);

        void updateUserData(User user, OnCompleteListener listener);

        void getUserData(String userId, ValueEventListener listener);

        void setPassword(String newPassword, OnCompleteListener listener);

        void savePost(Post post, OnCompleteListener listener);

        void uploadPhoto(Uri uri, OnSuccessListener<UploadTask.TaskSnapshot> successListener, OnFailureListener failureListener);
    }
}
