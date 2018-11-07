package com.framgia.vhlee.photomatic.ui.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.util.Constants;

public class PostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
    }

    public static Intent getPostIntent(Context context, String source) {
        Intent intent = new Intent(context, PostActivity.class);
        intent.putExtra(Constants.EXTRA_PHOTO, source);
        return intent;
    }
}
