package com.android.ui.kent.demo.framwork.glide;

import android.graphics.Bitmap;

public interface RequestListener {

    boolean onSuccess(Bitmap bitmap);

    boolean onFailure();

}
