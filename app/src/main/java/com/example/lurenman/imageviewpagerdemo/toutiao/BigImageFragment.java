package com.example.lurenman.imageviewpagerdemo.toutiao;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.Target;
import com.example.lurenman.imageviewpagerdemo.R;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
//import com.sunfusheng.glideimageview.progress.CircleProgressView;

/**
 * @author: baiyang.
 * Created on 2017/11/24.
 */

public class BigImageFragment extends LazyLoadFragment {
    private static final String TAG = "BigImageFragment";
    public static final String IMG_URL = "imgUrl";
    private View rootView;
    private PhotoView mIvPic;
    private CircleProgressView mCircleProgressView;
    private Activity mActivity;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_toutiao_big_image, container, false);
            mIvPic = (PhotoView) rootView.findViewById(R.id.pv_pic);
            mCircleProgressView = (CircleProgressView) rootView.findViewById(R.id.progressView);
            initData();
            initEvents();
        } else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }
        return rootView;
    }

    //初始化一些数据
    private void initData() {

    }

    private void initEvents() {
        mIvPic.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                mActivity.finish();
            }
        });
    }

    @Override
    protected void onFragmentFirstVisible() {
        //当第一次可见的时候，加载数据
         loadData();
    }

    //加载网络数据
    private void loadData() {
        Log.e(TAG, "loadData: --------------");
       /* String imgUrl = getArguments().getString(IMG_URL);

        GlideImageLoader imageLoader = new GlideImageLoader(mIvPic);
        imageLoader.setOnGlideImageViewListener(imgUrl, new OnGlideImageViewListener() {
            @Override
            public void onProgress(int percent, boolean isDone, GlideException exception) {
                if (exception != null && !TextUtils.isEmpty(exception.getMessage())) {

                    Log.e(TAG, "exception onProgress: "+ exception.getMessage());
                }
                mCircleProgressView.setProgress(percent);
                mCircleProgressView.setVisibility(isDone ? View.GONE : View.VISIBLE);
            }
        });

        RequestOptions options = imageLoader.requestOptions(R.color.placeholder_color)
                .centerCrop()
                .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);;

        final RequestBuilder<Drawable> requestBuilder = imageLoader.requestBuilder(imgUrl, options);
        requestBuilder.transition(DrawableTransitionOptions.withCrossFade())
                .into(new SimpleTarget<Drawable>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        if (resource.getIntrinsicHeight() > DisplayUtil.getScreenHeight(mActivity)) {
                            mIvPic.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        }
                        requestBuilder.into(mIvPic);
                    }
                });*/
       //用郭霖大神的方法
        final String imgUrl = getArguments().getString(IMG_URL);
        ProgressInterceptor.addListener(imgUrl, new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                Log.e(TAG, "onProgress: -----------"+progress );
                mCircleProgressView.setProgress(progress);
            }
        });
        Glide.with(BigImageFragment.this)
                .load(imgUrl)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
               // .override(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .into(new GlideDrawableImageViewTarget(mIvPic) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        //这块使用内存的时候不调用
                        Log.e(TAG, "onLoadStarted: -----------");

                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        Log.e(TAG, "onResourceReady: --------------");
                        ProgressInterceptor.removeListener(imgUrl);
                        mCircleProgressView.setVisibility(View.GONE);
                    }
                });

    }
}
