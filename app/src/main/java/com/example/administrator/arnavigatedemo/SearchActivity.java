package com.example.administrator.arnavigatedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.arnavigatedemo.adapter.LoadMapsAdapter;
import com.example.administrator.arnavigatedemo.manager.MapLoadManager;
import com.example.administrator.arnavigatedemo.model.MapInfo;
import com.palmaplus.nagrand.data.DataList;
import com.palmaplus.nagrand.data.DataSource;
import com.palmaplus.nagrand.data.MapModel;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Administrator on 2017/8/10/010.
 */

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "SearchActivity";
    private MapLoadManager mapLoadManager;
    private ListView listView;
    private LoadMapsAdapter mapsAdapter;
    private List<MapInfo> mapInfo;
    private ImageView mClearSearchContent;
    private EditText mSearchContent;
    private TextView mCancelSearchBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        initEvent();
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

    private void initEvent() {
        mClearSearchContent.setOnClickListener(this);
        mCancelSearchBtn.setOnClickListener(this);
    }

    private void initView() {
        mapLoadManager = new MapLoadManager();
        listView = (ListView) findViewById(R.id.map_info_list);
        mClearSearchContent = (ImageView) findViewById(R.id.clear_search_content);
        mSearchContent = (EditText) findViewById(R.id.search_edt_content);
        mCancelSearchBtn = (TextView) findViewById(R.id.cancel_search_button);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.clear_search_content:
                mSearchContent.setText(null);
                break;
            case R.id.cancel_search_button:
                finish();
                break;
        }
    }
}
