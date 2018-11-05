package com.framgia.vhlee.photomatic.ui.editprofile;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    public static DatePickerDialog newInstance (Context context, DatePickerDialog.OnDateSetListener listener) {
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(context, listener, year, month, day);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return null;
    }
}