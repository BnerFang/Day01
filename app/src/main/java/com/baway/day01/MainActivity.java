package com.baway.day01;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.baway.day01.adapter.MyViewPagerAdapter;
import com.baway.day01.bean.BanerBean;
import com.baway.day01.util.HttpUtil;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:  方诗康
 */

public class MainActivity extends AppCompatActivity {


    private String dataPath = "https://www.zhaoapi.cn/ad/getAd";

    private List<BanerBean.DataBean> mDataBeans = new ArrayList<>();

    private ViewPager mVP;
    /**
     * XXXXXXXXXXXX
     */
    private TextView mTxtName;
    private MyViewPagerAdapter mMyViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化控件
        initView();
    }

    private void initData(String json) {

        mMyViewPagerAdapter = new MyViewPagerAdapter(this, mDataBeans);

        mVP.setAdapter(mMyViewPagerAdapter);
        new HttpUtil(new HttpUtil.HttpLinear() {
            @Override
            public void success(String data) {
                Gson gson = new Gson();
                BanerBean banerBean = gson.fromJson(data, BanerBean.class);
                BanerBean.DataBean bean = banerBean.getData();
                List<String> images = bean.getImages();
                for (int i = 0; i < images.size(); i++) {
                    
                }
            }

            @Override
            public void fail() {

            }
        }).get(dataPath);
    }

    private void initView() {
        mVP = (ViewPager) findViewById(R.id.v_p);
        mTxtName = (TextView) findViewById(R.id.txt_name);
    }
}
