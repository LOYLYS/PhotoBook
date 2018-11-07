package com.framgia.vhlee.photomatic.ui.upload;

import java.util.List;

public interface UploadContract {
    interface Presenter {
        void getPhotos(String path);
    }

    interface View {
        void onSuccess(List<String> paths);

        void onFailure(String message);
    }
}
