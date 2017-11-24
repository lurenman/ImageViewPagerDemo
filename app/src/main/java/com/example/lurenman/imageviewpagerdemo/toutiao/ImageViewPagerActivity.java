package com.example.lurenman.imageviewpagerdemo.toutiao;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.example.lurenman.imageviewpagerdemo.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: baiyang.
 * Created on 2017/11/24.
 */

public class ImageViewPagerActivity extends FragmentActivity implements ViewPager.OnPageChangeListener {
    private static final String TAG = ImageViewPagerActivity.class.getSimpleName();
    public static final String IMG_URLS = "mImageUrls";
    public static final String POSITION = "position";
    private ViewPager mVpPics;
    private TextView mTvIndicator;
    private TextView mTvSave;
    private List<String> mImageUrls = new ArrayList<String>();
    private List<BigImageFragment> mFragments = new ArrayList<BigImageFragment>();
    private int mCurrentPosition;
    private Map<Integer,Boolean> mDownloadingFlagMap = new HashMap<>();//用于保存对应位置图片是否在下载的标识

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toutiao_image_view_pager);
        initViews();
        initDatas();
        initEvents();
    }
    private void initViews() {
        mVpPics=(ViewPager)findViewById(R.id.vp_pics);
        mTvIndicator=(TextView)findViewById(R.id.tv_indicator);
        mTvSave=(TextView)findViewById(R.id.tv_save);

    }
    private void initDatas() {
        Intent intent = getIntent();
        mImageUrls = intent.getStringArrayListExtra(IMG_URLS);
        int position = intent.getIntExtra(POSITION, 0);
        mCurrentPosition = position;

        for (int i=0;i<mImageUrls.size();i++) {
            String url = mImageUrls.get(i);
            BigImageFragment imageFragment = new BigImageFragment();

            Bundle bundle = new Bundle();
            bundle.putString(BigImageFragment.IMG_URL, url);
            imageFragment.setArguments(bundle);

            mFragments.add(imageFragment);//添加到fragment集合中
            mDownloadingFlagMap.put(i,false);//初始化map，一开始全部的值都为false
        }

        mVpPics.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mVpPics.addOnPageChangeListener(this);


        mVpPics.setCurrentItem(mCurrentPosition);// 设置当前所在的位置
        setIndicator(mCurrentPosition);//设置当前位置指示
    }

    private void setIndicator(int position) {
        mTvIndicator.setText(position + 1 + "/" + mImageUrls.size());//设置当前的指示
    }

    private void initEvents() {
        //保存图片
        mTvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mCurrentPosition = position;
        //页面变化时，设置当前的指示
        setIndicator(mCurrentPosition);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}
