package com.application.flickr.data.livedata;

import androidx.lifecycle.LiveData;

/**
 * Created by Harsh Jain on 18/03/19.
 */
public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(null);
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}