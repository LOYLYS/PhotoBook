package com.framgia.vhlee.photomatic.data.source.local;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

public class PhotoStorage extends AsyncTask<String, Void, List<String>> {
    private static final String[] EXTENSIONS = {".jpg", ".png", ".gif"};
    private LocalCallback<String> mCallback;

    public PhotoStorage(LocalCallback<String> callback) {
        mCallback = callback;
    }

    @Override
    protected List<String> doInBackground(String... paths) {
        List<String> photoPaths = new ArrayList<>();
        File directory = new File(paths[0]);
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                for (String extension : EXTENSIONS) {
                    if (file.getName().toLowerCase().endsWith(extension)) return true;
                }
                return false;
            }
        });

        for (File file : files) photoPaths.add(file.getPath());
        return photoPaths;
    }

    @Override
    protected void onPostExecute(List<String> list) {
        if (!list.isEmpty()) mCallback.onSuccess(list);
        else mCallback.onFailure("Not found anything");
    }

    private List<String> getAllPhotos(String path) {
        final List<String> photoPaths = new ArrayList<>();
        File directory = new File(path);
        File[] files = directory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) photoPaths.addAll(getAllPhotos(file.getPath()));
                for (String extension : EXTENSIONS) {
                    if (file.getName().toLowerCase().endsWith(extension)) return true;
                }
                return false;
            }
        });

        for (File file : files) photoPaths.add(file.getPath());
        return photoPaths;
    }
}
