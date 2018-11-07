package com.framgia.vhlee.photomatic.data.source.local;

import com.framgia.vhlee.photomatic.data.source.DataSource;

public class LocalDataSource implements DataSource.Local {
    private static LocalDataSource sLocalDataSource;

    public static LocalDataSource newInstance() {
        if (sLocalDataSource == null) {
            sLocalDataSource = new LocalDataSource();
        }
        return sLocalDataSource;
    }

    @Override
    public void getLocalPhotos(String path, LocalCallback<String> callback) {
        PhotoStorage photoStorage = new PhotoStorage(callback);
        photoStorage.execute(path);
    }
}
