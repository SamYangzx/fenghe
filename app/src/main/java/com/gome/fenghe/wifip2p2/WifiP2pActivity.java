package com.gome.fenghe.wifip2p2;

import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;

import com.gome.fenghe.BaseActivity;
import com.gome.fenghe.R;
import com.gome.fenghe.utils.LogUtils;
import com.gome.fenghe.utils.ToastUtils;

import static com.gome.fenghe.wifip2p2.WifiP2pHelper.WIFIP2P_CONNECT_P2P_FAIL;
import static com.gome.fenghe.wifip2p2.WifiP2pHelper.WIFIP2P_CONNECT_P2P_SUCCESS;
import static com.gome.fenghe.wifip2p2.WifiP2pHelper.WIFIP2P_DEVICE_DISCOVERING;
import static com.gome.fenghe.wifip2p2.WifiP2pHelper.WIFIP2P_DEVICE_LIST_CHANGED;

public class WifiP2pActivity extends BaseActivity {
    private static final String TAG = WifiP2pActivity.class.getSimpleName();
    private Context mContext;
    private final IntentFilter mIntentFilter = new IntentFilter();

    private static WifiP2pHelper mWifiP2pHelper;
    private DeviceListFragment mDeviceListFrag;

    public WifiP2pHelper getWifiP2pHelper(){
        if(mWifiP2pHelper == null){
            mWifiP2pHelper = new WifiP2pHelper(this, mHandler);
        }
        return mWifiP2pHelper;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what){
                case WIFIP2P_DEVICE_DISCOVERING:
                    mDeviceListFrag.onInitiateDiscovery();
                    break;
                case WIFIP2P_DEVICE_LIST_CHANGED:
                    mDeviceListFrag.updateDevicesList(mWifiP2pHelper.getDeviceList());
                    break;
                case WIFIP2P_CONNECT_P2P_SUCCESS:
                    ToastUtils.toast(mContext, "connect success");
                    break;
                case WIFIP2P_CONNECT_P2P_FAIL:
                    ToastUtils.toast(mContext, "connect success");
                    break;
                default:
                    LogUtils.d(TAG, "unknown msg: " + what);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wifi_p2p2);
        mContext = this;
        initView();
        mWifiP2pHelper = new WifiP2pHelper(this, mHandler);
        registerReceiver();
        initForWifiDirect();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mWifiP2pHelper);
        super.onDestroy();
    }

    private void registerReceiver(){
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mWifiP2pHelper, mIntentFilter);
    }

    private void initView() {
        mDeviceListFrag = (DeviceListFragment)getFragmentManager()
                .findFragmentById(R.id.frag_list_p2p);
    }

    private void initForWifiDirect(){
        mWifiP2pHelper.discoverDevice();
    }
}
