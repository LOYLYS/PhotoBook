package com.framgia.vhlee.photomatic.data.source.remote;

import android.net.Uri;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class RemoteStorage {
    private FirebaseStorage mStorage;
    private StorageReference mStorageRef;

    public RemoteStorage() {
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference();
    }

    public void uploadPhoto(Uri uri, OnSuccessListener<UploadTask.TaskSnapshot> successListener,
                            OnFailureListener failureListener) {
        StorageReference riversRef = mStorageRef.child("images/" + uri.getLastPathSegment());
        UploadTask uploadTask = riversRef.putFile(uri);
        uploadTask.addOnFailureListener(failureListener)
                .addOnSuccessListener(successListener);
    }
}
