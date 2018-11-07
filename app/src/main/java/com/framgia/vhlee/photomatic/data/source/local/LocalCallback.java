package com.framgia.vhlee.photomatic.data.source.local;

import java.util.List;

public interface LocalCallback<T> {
    void onSuccess(List<T> list);

    void onFailure(String message);
}
