package com.example.administrator.arnavigatedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.administrator.arnavigatedemo.adapter.LoadMapsAdapter;
import com.example.administrator.arnavigatedemo.manager.MapLoadManager;
import com.example.administrator.arnavigatedemo.model.MapInfo;
import com.palmaplus.nagrand.data.DataList;
import com.palmaplus.nagrand.data.DataSource;
import com.palmaplus.nagrand.data.MapModel;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10/010.
 */

public class SearchActivity extends AppCompatActivity{
    private static final String TAG = "SearchActivity";
    private MapLoadManager mapLoadManager;
    private ListView listView;
    private LoadMapsAdapter mapsAdapter;
    private List<MapInfo> mapInfo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        mapLoadManager.setOnLoadMapsListener(new MapLoadManager.OnLoadMapsListener() {
            @Override
            public void loadMapsFinished() {
                mapInfo = mapLoadManager.getMapInfo();
                mapsAdapter = new LoadMapsAdapter(SearchActivity.this,mapInfo);
                listView.setAdapter(mapsAdapter);
            }
        });
        mapLoadManager.requestMap();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(SearchActivity.this,MainActivity.class);
                intent.putExtra("mapId",mapInfo.get(i).mapId);
                intent.putExtra("mapName",mapInfo.get(i).mapName);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        mapLoadManager = new MapLoadManager();
        listView = (ListView) findViewById(R.id.map_info_list);
    }
}
