package com.framgia.vhlee.photomatic.ui.register;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.util.Constants;
import com.framgia.vhlee.photomatic.util.Notifications;

import java.util.Date;

public class RegisterActivity extends AppCompatActivity
        implements RegisterContract.View, View.OnClickListener {
    private RegisterContract.Presenter mPresenter;
    private View mViewRegister;
    private View mViewProcess;
    private EditText mEditEmail;
    private EditText mEditPassword;
    private EditText mEditRepeatPassword;
    private EditText mEditName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
    }

    @Override
    public void onSuccess() {
        User user = new User();
        Date date = new Date();
        user.setEmail(mEditEmail.getText().toString());
        user.setNickName(mEditName.getText().toString());
        user.setJoinTime(date.getTime());
        mPresenter.updateUserData(user);
    }

    @Override
    public void onFailure(String message) {
        mViewProcess.setVisibility(View.GONE);
        mViewRegister.setClickable(true);
        Notifications.showSnackBar(getCurrentFocus(), message);
    }

    @Override
    public void onUpdateSuccess() {
        // TODO Firebase auto login
    }

    @Override
    public void onUpdateFailure(String message) {
        mViewProcess.setVisibility(View.GONE);
        mViewRegister.setClickable(true);
        Notifications.showSnackBar(getCurrentFocus(), message);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_register:
                register();
                break;
            case R.id.text_login:
                super.onBackPressed();
                break;
            default:
                break;
        }
    }

    private void initUI() {
        mPresenter = new RegisterPresenter(this);
        mViewRegister = findViewById(R.id.view_register);
        mViewProcess = findViewById(R.id.view_progress);
        mEditEmail = findViewById(R.id.input_email);
        mEditPassword = findViewById(R.id.input_password);
        mEditRepeatPassword = findViewById(R.id.input_repeat_password);
        mEditName = findViewById(R.id.input_user_name);
        Button buttonRegister = findViewById(R.id.button_register);
        TextView textLogin = findViewById(R.id.text_login);
        buttonRegister.setOnClickListener(this);
        textLogin.setOnClickListener(this);
    }

    private void register() {
        String email = mEditEmail.getText().toString();
        String password = mEditPassword.getText().toString();
        String repeatPassword = mEditRepeatPassword.getText().toString();
        String name = mEditName.getText().toString();
        if (validField(email, password, repeatPassword, name)) {
            mViewProcess.setVisibility(View.VISIBLE);
            mViewRegister.setClickable(false);
            mPresenter.register(email, password);
        }
    }

    private boolean validField(String email, String password, String repeatPassword, String name) {
        if (email.isEmpty()) {
            mEditEmail.setError(getString(R.string.error_required));
            return false;
        }
        if (password.isEmpty()) {
            mEditPassword.setError(getString(R.string.error_required));
            return false;
        } else if (!repeatPassword.equals(password)) {
            mEditRepeatPassword.setError(getString(R.string.error_match_password));
            return false;
        }
        if (name.isEmpty()) {
            mEditName.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }

    public static Intent getRegisterIntent(Context context, String email) {
        Intent intent = new Intent(context, RegisterActivity.class);
        intent.putExtra(Constants.EXTRA_EMAIL, email);
        return intent;
    }
}
