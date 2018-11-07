package com.framgia.vhlee.photomatic.ui.upload;

import com.framgia.vhlee.photomatic.data.repository.DataRepository;
import com.framgia.vhlee.photomatic.data.source.local.LocalCallback;

import java.util.List;

public class UploadPresenter implements UploadContract.Presenter {
    private UploadContract.View mView;
    private DataRepository mRepository;

    public UploadPresenter(UploadContract.View view) {
        mView = view;
        mRepository = DataRepository.newInstance();
    }

    @Override
    public void getPhotos(String path) {
        mRepository.getLocalPhotos(path, new LocalCallback<String>() {
            @Override
            public void onSuccess(List<String> list) {
                mView.onSuccess(list);
            }

            @Override
            public void onFailure(String message) {
                mView.onFailure(message);
            }
        });
    }
}
