package com.framgia.vhlee.photomatic.ui.post;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.data.model.Post;
import com.framgia.vhlee.photomatic.ui.MainActivity;
import com.framgia.vhlee.photomatic.util.Constants;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.Date;

public class PostActivity extends AppCompatActivity
        implements View.OnClickListener, PostContract.View {
    private PostPresenter mPresenter;
    private EditText mEditCaption;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        initUI();
    }

    private void initUI() {
        mPresenter = new PostPresenter(this);
        Button buttonCancelPost = findViewById(R.id.button_cancel_post);
        Button buttonPost = findViewById(R.id.button_post);
        ImageView imagePreview = findViewById(R.id.image_preview);
        mEditCaption = findViewById(R.id.edit_caption);
        mProgressBar = findViewById(R.id.progress_posting);
        buttonCancelPost.setOnClickListener(this);
        buttonPost.setOnClickListener(this);
        Glide.with(this)
                .load(getIntent().getStringExtra(Constants.EXTRA_PHOTO))
                .into(imagePreview);
    }

    public static Intent getPostIntent(Context context, String source) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(Constants.EXTRA_PHOTO, source);
        return intent;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel_post:
                super.onBackPressed();
                break;
            case R.id.button_post:
                mProgressBar.setVisibility(View.VISIBLE);
                postPhoto();
                break;
            default:
                break;
        }
    }

    private void postPhoto() {
        Uri uri = Uri.fromFile(new File(getIntent().getStringExtra(Constants.EXTRA_PHOTO)));
        mPresenter.uploadPhoto(uri);
    }

    @Override
    public void onUploadSuccess(String path) {
        Post post = new Post();
        Date date = new Date();
        post.setCaption(mEditCaption.getText().toString());
        post.setCreateTime(date.getTime());
        post.setUserId(FirebaseAuth.getInstance().getUid());
        post.setPhotoUrl(path);
        mPresenter.savePost(post);
    }

    @Override
    public void onUploadFailure(String message) {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void onPostSuccess() {
        startActivity(MainActivity.getMainIntent(this));
        finish();
    }

    @Override
    public void onPostFailure(String message) {
        mProgressBar.setVisibility(View.GONE);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
