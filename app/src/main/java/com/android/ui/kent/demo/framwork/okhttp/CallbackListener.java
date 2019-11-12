package com.android.ui.kent.demo.framwork.okhttp;

import java.io.InputStream;

/**
 * Created by songzhukai on 2019-11-12.
 */
public interface CallbackListener {

    void onSuccess(InputStream inputStream);

    void onFailure(Exception e);

}
