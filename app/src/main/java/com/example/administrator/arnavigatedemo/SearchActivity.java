package com.example.administrator.arnavigatedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.palmaplus.nagrand.data.DataList;
import com.palmaplus.nagrand.data.DataSource;
import com.palmaplus.nagrand.data.MapModel;

/**
 * Created by Administrator on 2017/8/10/010.
 */

public class SearchActivity extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();

    }

    private void initView() {

    }
}
