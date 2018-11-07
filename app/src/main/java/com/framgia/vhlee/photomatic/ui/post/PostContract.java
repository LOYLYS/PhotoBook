package com.framgia.vhlee.photomatic.ui.post;

import android.net.Uri;

import com.framgia.vhlee.photomatic.data.model.Post;

public interface PostContract {
    interface Presenter {
        void uploadPhoto(Uri uri);

        void savePost(Post post);
    }

    interface View {
        void onUploadSuccess(String path);

        void onUploadFailure(String message);

        void onPostSuccess();

        void onPostFailure(String message);
    }
}
