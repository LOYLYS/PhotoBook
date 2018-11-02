package com.framgia.vhlee.photomatic.ui.register;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.util.Constants;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public static Intent getRegisterIntent(Context context, String email) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(Constants.EXTRA_EMAIL, email);
        return intent;
    }
}
