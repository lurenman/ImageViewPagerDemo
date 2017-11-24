package com.example.lurenman.imageviewpagerdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.lurenman.imageviewpagerdemo.toutiao.ImageViewPagerActivity;
import com.example.lurenman.imageviewpagerdemo.xinshi.ImagePagerActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView tv_xinshi;
    private TextView tv_toutiao;
    private ArrayList<String> urlArrays = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_xinshi = (TextView) findViewById(R.id.tv_xinshi);
        tv_toutiao = (TextView) findViewById(R.id.tv_toutiao);
        initDatas();
        initEvents();
    }

    private void initDatas() {
        String[] imagesUrl = {
                "http://img.my.csdn.net/uploads/201407/26/1406383299_1976.jpg",
                "http://img.my.csdn.net/uploads/201407/26/1406383291_6518.jpg",
                "https://timgsa.baidu" + "" + "" + "" + ""
                        + ".com/timg?image&quality=80&size=b9999_10000&sec=1492603173910&di" +
                        "=56a7ddd19599e6c69407b5fc00713d68&imgtype=0&src=http%3A%2F%2Fimg2.ph.126.net" +
                        "%2Ff9lpAnpwJvmY9j_JwOt30w%3D%3D%2F1753307630030769317.jpg",
                "http://wx1.sinaimg" + "" + "" + "" + "" + "" +
                        ".cn/mw600/006wXKLaly1fes36o79f2j30m80etgmq.jpg", "http://wx1.sinaimg" +
                "" + "" + "" + ".cn/thumb180/005Sbq5bly1fertaiisehg30cc06yu0z.gif",
                "http://file.ataw.cn/HospPerformance/Model/Image/2017/06/20/File/20170620173507137A9A7CC4BD991149058A765A34095728CF.jpg?ut=20170620173516",
        };
        List<String> list = Arrays.asList(imagesUrl);
        urlArrays.addAll(list);
    }

    private void initEvents() {
        tv_xinshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ImagePagerActivity.class);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLARRAYS, urlArrays);
                intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, 0);//我们传一个位置0
                startActivity(intent);

            }
        });
        tv_toutiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //传到展示图片的viewPager
                Intent intent = new Intent(MainActivity.this, ImageViewPagerActivity.class);
                intent.putStringArrayListExtra(ImageViewPagerActivity.IMG_URLS,urlArrays);
                intent.putExtra(ImageViewPagerActivity.POSITION,0);
                startActivity(intent);
            }
        });

    }
}
