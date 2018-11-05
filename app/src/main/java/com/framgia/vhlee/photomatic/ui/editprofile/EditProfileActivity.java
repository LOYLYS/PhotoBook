package com.framgia.vhlee.photomatic.ui.editprofile;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.framgia.vhlee.photomatic.R;
import com.framgia.vhlee.photomatic.data.model.User;
import com.framgia.vhlee.photomatic.util.Constants;
import com.framgia.vhlee.photomatic.util.Notifications;
import com.framgia.vhlee.photomatic.util.Strings;

public class EditProfileActivity extends AppCompatActivity
        implements View.OnClickListener, EditProfileContract.View,
        AdapterView.OnItemSelectedListener, DatePickerDialog.OnDateSetListener {
    private static final String SEPARATOR_DATE = ".";
    private EditProfilePresenter mPresenter;
    private EditText mEditEmail;
    private EditText mEditDes;
    private EditText mEditAddress;
    private EditText mEditSex;
    private EditText mEditBirthday;
    private EditText mEditPass;
    private EditText mEditRepeatPass;
    private Spinner mSpinnerSex;
    private ProgressBar mProgressChecking;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initUI();
        initData();
    }

    private void initData() {
        mPresenter.getProfile(getIntent().getStringExtra(Constants.EXTRA_USER_ID));
    }

    private void initUI() {
        mEditEmail = findViewById(R.id.input_email);
        mEditDes = findViewById(R.id.input_description);
        mEditAddress = findViewById(R.id.input_address);
        mEditSex = findViewById(R.id.input_sex);
        mEditBirthday = findViewById(R.id.input_birthday);
        mEditPass = findViewById(R.id.input_new_password);
        mEditRepeatPass = findViewById(R.id.input_repeat_new_password);
        mSpinnerSex = findViewById(R.id.spinner_sex);
        mProgressChecking = findViewById(R.id.progress_checking);
        Button buttonCancel = findViewById(R.id.button_cancel_edit);
        Button buttonSave = findViewById(R.id.button_save_edit);
        buttonCancel.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        mEditSex.setOnClickListener(this);
        mEditBirthday.setOnClickListener(this);
        mUser = new User();
        mPresenter = new EditProfilePresenter(this);
        initSpinner();
    }

    private void initSpinner() {
        ArrayAdapter<CharSequence> sexAdapter =
                ArrayAdapter.createFromResource(this,
                        R.array.sex_array, android.R.layout.simple_spinner_item);
        sexAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerSex.setAdapter(sexAdapter);
        mSpinnerSex.setOnItemSelectedListener(this);

    }

    public static Intent getEditProfileIntent(Context context, String userId) {
        Intent intent = new Intent(context, EditProfileActivity.class);
        intent.putExtra(Constants.EXTRA_USER_ID, userId);
        return intent;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_cancel_edit:
                super.onBackPressed();
                finish();
                break;
            case R.id.button_save_edit:
                if (checkField()) confirmPassword();
                break;
            case R.id.input_sex:
                mSpinnerSex.performClick();
                break;
            case R.id.input_birthday:
                showDatePickerDialog();
                break;
            default:
                break;
        }
    }

    private boolean checkField() {
        String newPass = mEditPass.getText().toString();
        String repeatNewPass = mEditRepeatPass.getText().toString();
        if (!repeatNewPass.equals(newPass)) {
            mEditRepeatPass.setError(getString(R.string.error_match_password));
            return false;
        }
        return true;
    }

    public void showDatePickerDialog() {
        DatePickerDialog dialog =
                DatePickerFragment.newInstance(EditProfileActivity.this, this);
        dialog.show();
    }

    private void confirmPassword() {
        mUser.setDescription(mEditDes.getText().toString());
        mUser.setAddress(mEditAddress.getText().toString());
        mUser.setBirthday(mEditBirthday.getText().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View dialogView = getLayoutInflater().inflate(R.layout.layout_dialog_input, null);
        final EditText editText = dialogView.findViewById(R.id.edit_dialog);
        builder.setView(dialogView)
                .setTitle(R.string.title_confirm_password_dialog)
                .setPositiveButton(R.string.option_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String oldPassword = editText.getText().toString();
                        if (oldPassword.isEmpty()) {
                            Notifications.showSnackBar(getCurrentFocus(),
                                    getString(R.string.notify_confirm_empty));
                            return;
                        }
                        mProgressChecking.setVisibility(View.VISIBLE);
                        mPresenter.reAuthenticate(oldPassword);
                    }
                })
                .setNegativeButton(R.string.option_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        builder.show();
    }

    /**
     * @param user get data to UI
     */
    @Override
    public void onGetProfileSuccess(User user) {
        mUser = user;
        mEditEmail.setText(user.getEmail());
        mEditDes.setText(user.getDescription());
        mEditAddress.setText(user.getAddress());
        mEditSex.setText(getSex(user.getSexual()));
        mEditBirthday.setText(user.getBirthday());
    }

    @Override
    public void onUpdateProfileSuccess() {
        mProgressChecking.setVisibility(View.GONE);
        Toast.makeText(this, R.string.notify_update_success, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onSetPasswordSuccess() {
        mPresenter.updateProfile(mUser);
    }

    @Override
    public void onReAuthenticateSuccess() {
        String newPassword = mEditPass.getText().toString();
        if (!newPassword.isEmpty()) mPresenter.setPassword(newPassword);
        else mPresenter.updateProfile(mUser);
    }

    @Override
    public void onFailure(String message) {
        mProgressChecking.setVisibility(View.GONE);
        Notifications.showSnackBar(getCurrentFocus(), message);
    }

    public String getSex(int code) {
        if (code == 1) return getString(R.string.text_male);
        if (code == 2) return getString(R.string.text_female);
        return getString(R.string.text_secret);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mEditSex.setText(adapterView.getItemAtPosition(i).toString());
        mUser.setSexual(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        String year = String.valueOf(i);
        String month = String.valueOf(++i1);
        String day = String.valueOf(i2);
        String birthday = Strings.append(day, SEPARATOR_DATE, month, SEPARATOR_DATE, year);
        mEditBirthday.setText(birthday);
    }
}
