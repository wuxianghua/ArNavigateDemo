package com.example.administrator.arnavigatedemo;

import android.content.Intent;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.arnavigatedemo.model.BeaconInfo;
import com.palmaplus.nagrand.core.Types;
import com.palmaplus.nagrand.data.DataSource;
import com.palmaplus.nagrand.position.Location;
import com.palmaplus.nagrand.position.ble.Beacon;
import com.palmaplus.nagrand.view.MapView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    protected MapView mapView;
    protected ViewGroup container;
    private Button startScan;
    private Button enSure;
    private ImageView mAddIcon;
    private LinearLayout mShowScanResult;
    private TextView mBeaconUuid;
    private TextView mBeaconMinor;
    private TextView mBeaconMajor;
    private Button mBtnCancle;
    private Button mBtnSave;
    private Beacon mBeacon;
    private Beacon mMoveBeacon;
    private TextView mScanedBeaconNumber;
    private TextView mAddBeaconNumber;
    private LinearLayout mSaveBeaconInfo;
    private LinearLayout mModifyBeaconInfo;
    private Button mDeleteBeaconInfo;
    private Button mMoveBeaconInfo;
    private Mark mMoveLocationMark;
    private BeaconInfo moveBeaconInfo;
    int widthPixels;
    int heightPixels;
    private BeaconInfo beaconInfo;
    private List<BeaconInfo> list;
    private Mark locationMark;
    private Intent mIntent;
    private Bundle mBundle;
    private List<Integer> minorList;
    private DataSource mDataSource;
    private ArrayList<Beacon> mDatas;
    private final int SHOW_BEACON_INFO = 1;
    private Button finishMove;
    Types.Point point;
    private BLEController bleController;
    private boolean isSaveBeaconInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        container.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        heightPixels = container.getHeight();
                        container.getViewTreeObserver()
                                .removeGlobalOnLayoutListener(this);
                    }
                });
        bleController = BLEController.getInstance();
        mAddIcon.setVisibility(View.GONE);
        mapView.setOverlayContainer(container);
        bleController.setOnScanBeaconNumberListener(new BLEController.OnScanBeaconNumberListener() {
            @Override
            public void scanResult(List<Beacon> beacons) {
                if (beacons == null) return;
                mScanedBeaconNumber.setText("扫描的蓝牙数："+beacons.size());
            }
        });
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (bleController.isScanning&&!isSaveBeaconInfo) {
                    mShowScanResult.setVisibility(View.GONE);
                    if (mMoveLocationMark != null) {
                        mMoveLocationMark.setScanedColor(1);
                    }
                    enSure.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
    }

    private void initEvent() {
        startScan.setOnClickListener(this);
        enSure.setOnClickListener(this);
        mBtnSave.setOnClickListener(this);
        mBtnCancle.setOnClickListener(this);
        mDeleteBeaconInfo.setOnClickListener(this);
        mModifyBeaconInfo.setOnClickListener(this);
        mMoveBeaconInfo.setOnClickListener(this);
        finishMove.setOnClickListener(this);
    }

    private void initView() {
        list = new ArrayList<BeaconInfo>();
        startScan = (Button) findViewById(R.id.startScan);
        enSure = (Button) findViewById(R.id.ensure);
        mAddIcon = (ImageView) findViewById(R.id.image_add);
        mapView = (MapView) findViewById(R.id.mapView);
        mShowScanResult = (LinearLayout) findViewById(R.id.show_beacon_result);
        mShowScanResult.setVisibility(View.GONE);
        mBeaconUuid = (TextView) findViewById(R.id.beacon_uuid_main);
        mBeaconMinor = (TextView) findViewById(R.id.beacon_minor_main);
        mBeaconMajor = (TextView) findViewById(R.id.beacon_major_main);
        mScanedBeaconNumber = (TextView) findViewById(R.id.scaned_number_beacon);
        mapView.getMap().startWithMapID(Constants.mapId);
        mBtnCancle = (Button) findViewById(R.id.cancle_save);
        mBtnSave = (Button) findViewById(R.id.save_beacon_data);
        container = (ViewGroup)findViewById(R.id.overlay_container);
        widthPixels = getResources().getDisplayMetrics().widthPixels;
        mSaveBeaconInfo = (LinearLayout) findViewById(R.id.beacon_info_save);
        mModifyBeaconInfo = (LinearLayout) findViewById(R.id.beacon_info_modify);
        mMoveBeaconInfo = (Button) findViewById(R.id.move_beacon_data);
        enSure.setVisibility(View.GONE);
        mDeleteBeaconInfo = (Button) findViewById(R.id.delete_beacon_info);
        finishMove = (Button) findViewById(R.id.move_finish);
        finishMove.setVisibility(View.GONE);
        mAddBeaconNumber = (TextView) findViewById(R.id.add_beacon_number);
        minorList = new ArrayList<>();
        mAddBeaconNumber.setText("添加的蓝牙数："+ list.size());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.drop();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.startScan:
                if (!bleController.isScanning) {
                    mAddIcon.setVisibility(View.VISIBLE);
                    startScan.setText("停止部署");
                    bleController.clearBeacons();
                    enSure.setVisibility(View.VISIBLE);
                    if (mDatas != null) {
                        mDatas.clear();
                    }
                    mScanedBeaconNumber.setText("扫描的蓝牙数："+0);
                    mShowScanResult.setVisibility(View.GONE);
                    bleController.start();
                }else {
                    mAddIcon.setVisibility(View.GONE);
                    startScan.setText("开始部署");
                    bleController.stop();
                    enSure.setVisibility(View.GONE);
                }
                break;
            case R.id.ensure:
                mDatas =  bleController.getBeacons();
                mIntent = new Intent(this,ShowBeaconInfoActivity.class);
                mBundle = new Bundle();
                mBundle.putParcelableArrayList("beacon",mDatas);
                mIntent.putExtra("bundle",mBundle);
                startActivityForResult(mIntent,SHOW_BEACON_INFO);
                break;
            case R.id.cancle_save:
                mShowScanResult.setVisibility(View.GONE);
                mDatas.clear();
                mScanedBeaconNumber.setText("扫描的蓝牙数："+0);
                bleController.clearBeacons();
                bleController.start();
                mAddIcon.setVisibility(View.VISIBLE);
                mapView.removeOverlay(locationMark);
                enSure.setVisibility(View.VISIBLE);
                break;
            case R.id.save_beacon_data:
                mShowScanResult.setVisibility(View.GONE);
                mDatas.clear();
                mAddIcon.setVisibility(View.VISIBLE);
                mScanedBeaconNumber.setText("扫描的蓝牙数："+0);
                mAddBeaconNumber.setText("添加的蓝牙数：" + list.size());
                bleController.clearBeacons();
                locationMark.setScanedColor(1);
                enSure.setVisibility(View.VISIBLE);
                break;
            case R.id.delete_beacon_info:
                mapView.removeOverlay(mMoveLocationMark);
                mShowScanResult.setVisibility(View.GONE);
                enSure.setVisibility(View.VISIBLE);
                list.remove(moveBeaconInfo);
                mAddBeaconNumber.setText("添加的蓝牙数：" + list.size());
                FileUtils.persistUserInfo(list,this);
                break;
            case R.id.move_beacon_data:
                mShowScanResult.setVisibility(View.GONE);
                mAddIcon.setVisibility(View.VISIBLE);
                finishMove.setVisibility(View.VISIBLE);
                list.remove(moveBeaconInfo);
                FileUtils.persistUserInfo(list,this);
                break;
            case R.id.move_finish:
                mapView.removeOverlay(mMoveLocationMark);
                finishMove.setVisibility(View.GONE);
                mAddIcon.setVisibility(View.GONE);
                addLocationMark(mMoveBeacon);
                locationMark.setScanedColor(1);
        }
    }

    public void addLocationMark(Beacon beacon) {
        beaconInfo = new BeaconInfo();
        beaconInfo.floorId = mapView.getMap().getFloorId();
        beaconInfo.minor = beacon.getMinor();
        beaconInfo.major = beacon.getMajor();
        beaconInfo.uuid = beacon.getProximityUUID();
        point = mapView.converToWorldCoordinate(widthPixels/2, heightPixels/2);
        beaconInfo.locationX = point.x;
        beaconInfo.locationY = point.y;
        locationMark = new Mark(this, new Mark.OnClickListenerForMark() {
            @Override
            public void onMarkSelect(Mark mark) {
                mShowScanResult.setVisibility(View.VISIBLE);
                mBeaconMinor.setText(mark.getMinor()+"");
                mBeaconMajor.setText(mark.getMajor()+"");
                mBeaconUuid.setText(mark.getUuid());
                mSaveBeaconInfo.setVisibility(View.GONE);
                enSure.setVisibility(View.GONE);
                isSaveBeaconInfo = false;
                mModifyBeaconInfo.setVisibility(View.VISIBLE);
                mDeleteBeaconInfo.setVisibility(bleController.isScanning?View.GONE:View.VISIBLE);
                mMoveBeaconInfo.setVisibility(bleController.isScanning?View.GONE:View.VISIBLE);
                moveBeaconInfo = mark.getBeaconInfo();
                mMoveLocationMark = mark;
                mMoveBeacon = mark.getBeacon();
                mMoveLocationMark.setScanedColor(3);
            }
        });
        locationMark.setFloorId(mapView.getMap().getFloorId());
        locationMark.init(new double[]{point.x, point.y});
        locationMark.setUuid(beacon.getProximityUUID());
        locationMark.setMajor(beacon.getMajor());
        locationMark.setMinor(beacon.getMinor());
        //将这个覆盖物添加到MapView中
        mapView.addOverlay(locationMark);
        locationMark.setBeacon(beacon);
        locationMark.setBeaconInfo(beaconInfo);
        list.add(beaconInfo);
        FileUtils.persistUserInfo(list,this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        bleController.setOnScanBeaconNumberListener(new BLEController.OnScanBeaconNumberListener() {
            @Override
            public void scanResult(List<Beacon> beacons) {
                if (beacons == null) return;
                mScanedBeaconNumber.setText("扫描的蓝牙数："+beacons.size());
            }
        });
        if (resultCode == RESULT_OK && requestCode == SHOW_BEACON_INFO) {
            mBeacon = data.getParcelableExtra("selectedBeacon");
            if (minorList.contains(mBeacon.getMinor())){
                Toast.makeText(this,"你已添加该beacon",Toast.LENGTH_LONG).show();
                return;
            }
            enSure.setVisibility(View.GONE);
            mAddIcon.setVisibility(View.GONE);
            mShowScanResult.setVisibility(View.VISIBLE);
            mBeaconMinor.setText(mBeacon.getMinor()+"");
            mBeaconMajor.setText(mBeacon.getMajor()+"");
            mBeaconUuid.setText(mBeacon.getProximityUUID());
            mSaveBeaconInfo.setVisibility(View.VISIBLE);
            minorList.add(mBeacon.getMinor());
            isSaveBeaconInfo = true;
            if (minorList.contains(mBeacon.getMinor()))
            mModifyBeaconInfo.setVisibility(View.GONE);
            addLocationMark(mBeacon);
        }
    }
}
