package com.example.administrator.arnavigatedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.solver.Cache;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.administrator.arnavigatedemo.adapter.HasBeaconsMapAdapter;
import com.example.administrator.arnavigatedemo.model.BeaconInfo;
import com.example.administrator.arnavigatedemo.model.HasBeaconsMapInfo;
import com.example.administrator.arnavigatedemo.utils.CacheUtils;
import com.google.gson.Gson;
import com.palmaplus.nagrand.data.DataList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/8/008.
 */

public class LoadMapActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "LoadMapActivity";
    private HasBeaconsMapInfo mapInfo;
    private LinearLayout mSearchMaps;
    private ListView mHasBeaconsMapList;
    private List<HasBeaconsMapInfo> beaconMapsInfoList;
    private HasBeaconsMapAdapter mapAdapter;
    public final int REQUEST_CODE = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_map);
        initView();
        mSearchMaps.setOnClickListener(this);
        beaconMapsInfoList = new ArrayList<>();
        initHasBeaconsInfo();
        mHasBeaconsMapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(LoadMapActivity.this,MainActivity.class);
                intent.putExtra("mapId",beaconMapsInfoList.get(i).mapId);
                intent.putExtra("mapName",beaconMapsInfoList.get(i).mapName);
                startActivityForResult(intent,REQUEST_CODE);
            }
        });
    }

    public void initHasBeaconsInfo() {
        File absoluteFile = getApplicationContext().getCacheDir().getAbsoluteFile();
        for (File file : absoluteFile.listFiles()) {
            if (file.listFiles() != null&&file.listFiles().length != 0) {
                mapInfo = new HasBeaconsMapInfo();
                mapInfo.beacons = file.listFiles().length;
                String[] split = file.getName().split("-");
                mapInfo.mapName = split[0];
                CacheUtils instance = CacheUtils.getInstance(file.getName());
                String string = instance.getString(split[0]);
                Gson gson = new Gson();
                List<Double> list = gson.fromJson(string, List.class);
                if (list == null) return;
                for (double i : list) {
                    BeaconInfo beaconInfo = (BeaconInfo) instance.getSerializable(String.valueOf(i).substring(0,5));
                    if (beaconInfo.uploadSuccess) {

                    }else {
                        mapInfo.isUploadSuccess = true;
                    }
                }
                mapInfo.mapId = Integer.valueOf(split[1]);
                beaconMapsInfoList.add(mapInfo);
            }
        }
        mapAdapter = new HasBeaconsMapAdapter(this,beaconMapsInfoList);
        mHasBeaconsMapList.setAdapter(mapAdapter);
    }

    private void initView() {
        mSearchMaps = (LinearLayout) findViewById(R.id.serch_maps);
        mHasBeaconsMapList = (ListView) findViewById(R.id.has_beacons_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initHasBeaconsInfo();
    }

    @Override
    protected void onStop() {
        super.onStop();
        beaconMapsInfoList.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.serch_maps:
                startActivityForResult(new Intent(this,SearchActivity.class),REQUEST_CODE);
                break;
        }
    }

}
