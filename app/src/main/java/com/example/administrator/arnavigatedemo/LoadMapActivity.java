package com.example.administrator.arnavigatedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.example.administrator.arnavigatedemo.model.HasBeaconsMapInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8/008.
 */

public class LoadMapActivity extends AppCompatActivity implements View.OnClickListener{
    private HasBeaconsMapInfo mapInfo;
    private LinearLayout mSearchMaps;
    private List<HasBeaconsMapInfo> beaconMapsInfoList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_map);
        mSearchMaps = (LinearLayout) findViewById(R.id.serch_maps);
        mSearchMaps.setOnClickListener(this);
        initView();
        beaconMapsInfoList = new ArrayList<>();
        File absoluteFile = getCacheDir().getAbsoluteFile();
        for (File file : absoluteFile.listFiles()) {
            if (file.listFiles() != null&&file.listFiles().length != 0) {
                mapInfo = new HasBeaconsMapInfo();
                mapInfo.beacons = file.listFiles().length;
                mapInfo.mapName = file.getName();
                beaconMapsInfoList.add(mapInfo);
            }
        }
    }

    private void initView() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.serch_maps:
                startActivity(new Intent(this,SearchActivity.class));
                break;
        }
    }
}
