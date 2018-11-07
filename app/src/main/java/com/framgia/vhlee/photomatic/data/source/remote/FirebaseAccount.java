package com.framgia.vhlee.photomatic.data.source.remote;

import com.framgia.vhlee.photomatic.data.model.Post;
import com.framgia.vhlee.photomatic.data.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseAccount {
    private static final String TABLE_USERS = "users";
    private static final String TABLE_POSTS = "posts";
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;

    public FirebaseAccount() {
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
    }

    public void checkSate(FirebaseAuth.AuthStateListener listener) {
        mAuth.addAuthStateListener(listener);
    }

    public void login(String email, String password, OnCompleteListener listener) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public void register(String email, String password, OnCompleteListener listener) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(listener);
    }

    public void reAuthenticate(String password, OnCompleteListener listener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        AuthCredential credential =
                EmailAuthProvider.getCredential(currentUser.getEmail(), password);
        currentUser.reauthenticate(credential)
                .addOnCompleteListener(listener);
    }

    public void updateUserData(final User user, OnCompleteListener listener) {
        DatabaseReference userRef = mDatabase.getReference(TABLE_USERS);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userRef.child(currentUser.getUid())
                .setValue(user)
                .addOnCompleteListener(listener);
    }

    public void getUserData(String userId, ValueEventListener listener) {
        DatabaseReference mUserRef = mDatabase.getReference(TABLE_USERS);
        mUserRef.child(userId).addValueEventListener(listener);
    }

    public void setPassword(String newPassword, OnCompleteListener listener) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        currentUser.updatePassword(newPassword).addOnCompleteListener(listener);
    }

    public void savePost(Post post, OnCompleteListener listener) {
        DatabaseReference postsRef = mDatabase.getReference(TABLE_POSTS);
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(post).addOnCompleteListener(listener);
    }
}
