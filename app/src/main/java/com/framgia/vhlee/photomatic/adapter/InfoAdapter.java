package com.framgia.vhlee.photomatic.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoAdapter extends SimpleAdapter {
    private ArrayList<HashMap<String, String>> mItems;
    private LayoutInflater mInflater;

    public InfoAdapter(Context context, List<? extends Map<String, ?>> data,
                       int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mInflater = LayoutInflater.from(context);
        mItems = new ArrayList<>();
    }

}
