package com.example.lurenman.imageviewpagerdemo.xinshi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;

import com.example.lurenman.imageviewpagerdemo.R;

import java.util.ArrayList;

/**
 * @author: baiyang.
 * Created on 2017/11/23.
 */

public class ImagePagerActivity extends FragmentActivity {
    private static final String TAG = "ImagePagerActivity";
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLARRAYS = "image_urlArrays";
    private HackyViewPager mPager;
    private int pagerPosition;
    private TextView indicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_xinshi_detail_pager);
        mPager = (HackyViewPager) findViewById(R.id.vp_pager);
        indicator = (TextView) findViewById(R.id.indicator);
        initVariables();
        initEvents();
    }

    private void initVariables() {
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        ArrayList<String> urlArrays = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLARRAYS);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urlArrays);
        mPager.setAdapter(mAdapter);
        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        indicator.setText(text);
    }

    //这块的考虑再说
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //   outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private void initEvents() {
        // 更新下标
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
                CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
                indicator.setText(text);
            }

        });
        mPager.setCurrentItem(pagerPosition);

    }


    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public ArrayList<String> urlArrays;

        public ImagePagerAdapter(FragmentManager fm, ArrayList<String> urlArrays) {
            super(fm);
            this.urlArrays = urlArrays;
        }

        @Override
        public int getCount() {
            return urlArrays == null ? 0 : urlArrays.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = urlArrays.get(position);
            return ImageDetailFragment.newInstance(url);

        }

    }
}
