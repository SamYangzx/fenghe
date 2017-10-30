package com.gome.fenghe.wifip2p2;

import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.gome.fenghe.BaseActivity;
import com.gome.fenghe.R;
import com.gome.fenghe.utils.LogUtils;
import com.gome.fenghe.utils.SdcardUtils;
import com.gome.fenghe.utils.ToastUtils;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.File;
import java.util.Arrays;

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
    private Button mStartSearchBtn;
    private Button mConnectBtn;
    private Button mSendBtn;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public WifiP2pHelper getWifiP2pHelper() {
        if (mWifiP2pHelper == null) {
            mWifiP2pHelper = new WifiP2pHelper(this, mHandler);
        }
        return mWifiP2pHelper;
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            int what = msg.what;
            switch (what) {
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
//        initView();
        mWifiP2pHelper = new WifiP2pHelper(this, mHandler);
        registerReceiver();
        initForWifiDirect();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

//        initData();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mWifiP2pHelper);
        super.onDestroy();
    }


    private void registerReceiver() {
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(mWifiP2pHelper, mIntentFilter);
    }

    @Override
    public void initView() {
        mDeviceListFrag = (DeviceListFragment) getFragmentManager()
                .findFragmentById(R.id.frag_list_p2p);

        mStartSearchBtn = (Button) findViewById(R.id.search_btn);
        mStartSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mConnectBtn = (Button) findViewById(R.id.connect_btn);
        mConnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WifiP2pConfig config = new WifiP2pConfig();
                config.deviceAddress = "02:08:22:fc:ca:e1";
                config.wps.setup = WpsInfo.PBC;
            }
        });

        mSendBtn = (Button) findViewById(R.id.send_file_btn);
        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File[] files = SdcardUtils.getSendFileDirPath(mContext).listFiles();
                if (files != null) {
                    for (File file : files) {
                        LogUtils.d(TAG, "file: " + file);
                    }
                    mWifiP2pHelper.sendFiles((Arrays.asList(files)));
                }
            }
        });
    }

    private void initForWifiDirect() {
        mWifiP2pHelper.discoverDevice();
    }

    @Override
    public void initData() {
        if (!SdcardUtils.getReceivedFileDirPath(mContext).exists()) {
            SdcardUtils.getReceivedFileDirPath(mContext).mkdirs();
        }

        if (!SdcardUtils.getSendFileDirPath(mContext).exists()) {
            SdcardUtils.getSendFileDirPath(mContext).mkdirs();
        }
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("WifiP2p Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
