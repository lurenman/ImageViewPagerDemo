package com.example.lurenman.imageviewpagerdemo.xinshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.lurenman.imageviewpagerdemo.R;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

/**
 * @author: baiyang.
 * Created on 2017/11/23.
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
    private static final String TAG = "ImageDetailFragment";
    private String mImageUrl;
    private View rootView;
    private PhotoView mImageView;
    private ProgressBar progressBar;
    private PhotoViewAttacher mAttacher;

    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment f = new ImageDetailFragment();
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // return super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.image_xinshi_detail_fragment, container, false);
            mImageView = (PhotoView) rootView.findViewById(R.id.image);
            progressBar = (ProgressBar) rootView.findViewById(R.id.loading);
            Log.e(TAG, "onCreateView:---------------- ");
        }else {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        }

        return rootView;
    }

    /*    策略解说：
        all:缓存源资源和转换后的资源
        none:不作任何磁盘缓存
        source:缓存源资源
        result：缓存转换后的资源*/
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        progressBar.setVisibility(View.VISIBLE);
        Glide.with(ImageDetailFragment.this).load(mImageUrl).placeholder(R.mipmap.icon_defaultimg) // 占位图
                .error(R.mipmap.icon_errorimg)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)//图片缓存模式
                .listener(new GlideRequestListener()).into(mImageView);
    }

    //glide 请求的回调
    private class GlideRequestListener implements RequestListener {

        @Override
        public boolean onException(Exception e, Object model, Target target, boolean isFirstResource) {
            Toast.makeText(getActivity(), "加载图片失败", Toast.LENGTH_SHORT).show();
            progressBar.setVisibility(View.GONE);
            return false;
        }

        //这个用于监听图片是否加载完成
        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, boolean isFromMemoryCache, boolean isFirstResource) {
            mAttacher = new PhotoViewAttacher(mImageView);
            mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
                @Override
                public void onPhotoTap(ImageView view, float x, float y) {
                    getActivity().finish();
                }
            });
            mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Toast.makeText(getActivity(), "触发长按事件", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
            progressBar.setVisibility(View.GONE);
            return false;
        }
    }
}
