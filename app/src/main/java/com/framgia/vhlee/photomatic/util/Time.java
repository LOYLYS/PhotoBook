package com.framgia.vhlee.photomatic.util;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class Time {
    private static final String DATE_FORMAT = "dd.MM.yyyy";
    private static final String SECRET = "Secret";

    public static String convertTime(long milliseconds) {
        if (milliseconds ==0) return SECRET;
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.US);
        return dateFormat.format(milliseconds);
    }
}
