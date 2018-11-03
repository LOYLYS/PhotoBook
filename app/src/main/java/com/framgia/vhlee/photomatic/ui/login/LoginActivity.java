package com.framgia.vhlee.photomatic.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.ui.MainActivity;
import com.framgia.vhlee.photomatic.ui.register.RegisterActivity;
import com.framgia.vhlee.photomatic.util.Constants;
import com.framgia.vhlee.photomatic.util.Notifications;

public class LoginActivity extends AppCompatActivity
        implements View.OnClickListener, LoginContract.View {
    private View mViewProgress;
    private View mViewLogin;
    private EditText mEmailField;
    private EditText mPasswordField;
    private LoginPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkSate();
        initUI();
    }

    private void checkSate() {
        mPresenter = new LoginPresenter(this);
        mPresenter.checkState();
    }

    private void initUI() {
        Button buttonLogin = findViewById(R.id.button_login);
        TextView textRegister = findViewById(R.id.text_register);
        mViewProgress = findViewById(R.id.view_progress);
        mViewLogin = findViewById(R.id.view_login);
        mEmailField = findViewById(R.id.input_email);
        mPasswordField = findViewById(R.id.input_password);
        mViewProgress.setVisibility(View.GONE);
        mEmailField.setText(getIntent().getStringExtra(Constants.EXTRA_EMAIL));
        buttonLogin.setOnClickListener(this);
        textRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_login:
                login();
                break;
            case R.id.text_register:
                String email = mEmailField.getText().toString();
                startActivity(RegisterActivity.getRegisterIntent(this, email));
                finish();
                break;
            default:
                break;
        }
    }

    private void login() {
        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();
        if (validField(email, password)) {
            mViewProgress.setVisibility(View.VISIBLE);
            mViewLogin.setClickable(false);
            mPresenter.login(email, password);
        }
    }

    private boolean validField(String email, String password) {
        if (email.isEmpty()) {
            mEmailField.setError(getString(R.string.error_required));
            return false;
        }
        if (password.isEmpty()) {
            mPasswordField.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess() {
        startActivity(MainActivity.getMainIntent(this));
        finish();
    }

    @Override
    public void onFailure(String message) {
        mViewProgress.setVisibility(View.GONE);
        mViewLogin.setClickable(true);
        Notifications.showSnackBar(getCurrentFocus(), message);
    }

    public static Intent getLoginIntent(Context context, String email) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.putExtra(Constants.EXTRA_EMAIL, email);
        return intent;
    }
}
