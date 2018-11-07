package com.framgia.vhlee.photomatic.ui.post;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.framgia.vhlee.photomatic.data.model.Post;
import com.framgia.vhlee.photomatic.data.repository.DataRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.UploadTask;

public class PostPresenter implements PostContract.Presenter {
    private PostContract.View mView;
    private DataRepository mRepository;

    public PostPresenter(PostContract.View view) {
        mView = view;
        mRepository = DataRepository.newInstance();
    }

    @Override
    public void uploadPhoto(Uri uri) {
        mRepository.uploadPhoto(uri, new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                StorageMetadata metadata = taskSnapshot.getMetadata();
                mView.onUploadSuccess(metadata.getPath());
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                mView.onUploadFailure(e.getMessage());
            }
        });
    }

    @Override
    public void savePost(Post post) {
        mRepository.savePost(post, new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) mView.onPostSuccess();
                else mView.onPostFailure(task.getException().getMessage());
            }
        });
    }
}
