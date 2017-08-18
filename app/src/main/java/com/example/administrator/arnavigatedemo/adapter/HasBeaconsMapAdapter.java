package com.example.administrator.arnavigatedemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.arnavigatedemo.R;
import com.example.administrator.arnavigatedemo.model.BeaconInfo;
import com.example.administrator.arnavigatedemo.model.HasBeaconsMapInfo;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2/002.
 */

public class HasBeaconsMapAdapter extends BaseAdapter{

    private List<HasBeaconsMapInfo> mData;
    private Context mContext;

    public HasBeaconsMapAdapter(Context context, List<HasBeaconsMapInfo> data) {
        mContext = context;
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HasBeaconsMapInfo getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if(view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_hasbeacons_maps_info,viewGroup,false);
            holder = new ViewHolder(view);
        }else {
            holder = (ViewHolder) view.getTag();
        }
        holder.mapName.setText(getItem(i).mapName);
        holder.mapId.setText(getItem(i).mapId+"");
        holder.beaconNumber.setText("("+(getItem(i).beacons-1)+")");
        holder.uploadSuccess.setImageResource(getItem(i).isUploadSuccess?R.mipmap.upload_failed:R.mipmap.upload_succed);
        return view;
    }

    private class ViewHolder {
        private TextView mapName,mapId,beaconNumber;
        private ImageView uploadSuccess;
        public ViewHolder(View convertView) {
            mapName = convertView.findViewById(R.id.map_name);
            mapId = convertView.findViewById(R.id.map_id);
            beaconNumber = convertView.findViewById(R.id.beacon_number);
            uploadSuccess = convertView.findViewById(R.id.image_upload_success);
            convertView.setTag(this);
        }
    }
}
