package com.example.administrator.arnavigatedemo.manager;

import android.util.Log;

import com.palmaplus.nagrand.data.DataList;
import com.palmaplus.nagrand.data.DataSource;
import com.palmaplus.nagrand.data.MapModel;

/**
 * Created by Administrator on 2017/8/10/010.
 */

public class MapLoadManager {

    private OnLoadMapsListener mOnLoadMapsListener;
    private DataSource dataSource;

    public MapLoadManager() {
        dataSource = new DataSource("http://api.ipalmap.com/");
    }

    public void setOnLoadMapsListener(OnLoadMapsListener onLoadMapsListener) {
        mOnLoadMapsListener = onLoadMapsListener;

    }

    public void requestMap() {
        dataSource.requestMaps(new DataSource.OnRequestDataEventListener<DataList<MapModel>>() {
            @Override
            public void onRequestDataEvent(DataSource.ResourceState resourceState, DataList<MapModel> mapModelDataList) {
                if (resourceState == DataSource.ResourceState.OK) {
                    if (mapModelDataList.getSize()>0) {
                        mOnLoadMapsListener.loadMapsFinished();
                        /*for (int i = 0;i < mapModelDataList.getSize();i++) {
                            MapModel poi = mapModelDataList.getPOI(i);
                            String s = MapModel.name.get(poi);
                            long id = MapModel.id.get(poi);
                        }*/
                    }
                }
            }
        });
    }

    public interface OnLoadMapsListener{
        void loadMapsFinished();
    }
}
