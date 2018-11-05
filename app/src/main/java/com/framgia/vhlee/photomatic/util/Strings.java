package com.framgia.vhlee.photomatic.util;

public class Strings {
    public static String append(String... strings) {
        StringBuilder builder = new StringBuilder();
        for (String string : strings) {
            builder.append(string);
        }
        return builder.toString();
    }
}
