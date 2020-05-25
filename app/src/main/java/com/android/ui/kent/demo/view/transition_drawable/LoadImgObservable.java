package com.android.ui.kent.demo.view.transition_drawable;

import com.android.ui.kent.demo.application.MyApplication;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.Nullable;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

/**
 * Created by songzhukai on 2019-10-17.
 */
public class LoadImgObservable extends Observable<Bitmap> {

    private Observer<? super Bitmap> mObserver;
    private String mUrl;

    final Disposable mDisposable = new Disposable() {
        boolean isDisposed = false;

        @Override
        public void dispose() {
            isDisposed = true;
        }

        @Override
        public boolean isDisposed() {
            return isDisposed;
        }
    };


    private void requestImg(){
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(mUrl))
                .setProgressiveRenderingEnabled(true)
                .build();

        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, MyApplication.getInstance().getApplicationContext());

        dataSource.subscribe(new BaseBitmapDataSubscriber() {
                                 @Override
                                 public void onNewResultImpl(@Nullable Bitmap bitmap) {
                                     // You can use the bitmap in only limited ways
                                     // No need to do any cleanup.
                                     Timber.i("fresco onNewResultImpl");
                                     mObserver.onNext(bitmap);

                                 }

                                 @Override
                                 public void onFailureImpl(DataSource dataSource) {
                                     // No cleanup required here.
                                     Timber.i("fresco onFailureImpl");
                                     mObserver.onError(new Exception("fresco onFailureImpl"));
                                 }
                             },
                CallerThreadExecutor.getInstance());
    }


    @Override
    protected void subscribeActual(Observer<? super Bitmap> observer) {
        this.mObserver = observer;
        observer.onSubscribe(mDisposable);
        requestImg();
    }

    public Observable<Bitmap> setImg(String url) {
        mUrl = url;
        return this;
    }

}
