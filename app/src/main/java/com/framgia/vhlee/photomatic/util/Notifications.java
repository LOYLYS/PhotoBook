package com.framgia.vhlee.photomatic.util;

import android.support.design.widget.Snackbar;
import android.view.View;

public class Notifications {
    public static void showSnackBar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }
}
