package com.gome.fenghe;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.gome.fenghe.utils.AudioRecordUtils;
import com.gome.fenghe.utils.LogUtils;
import com.gome.fenghe.wifip2p2.WifiP2pActivity;

import java.io.IOException;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;
    private Button mWifiBtn;
    private Button mEndBtn;
    AudioManager mAudioManager;
    private Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogUtils.d(TAG, "onCreate.");
        mContext = this;
        initView();
    }


    @Override
    protected void onStart() {
        LogUtils.v(TAG, "onStart");

//        LogUtils.callStack(TAG);
        super.onStart();
    }


    @Override
    protected void onResume() {
        LogUtils.v(TAG, "onResume");
//        LogUtils.callStack(TAG);
        super.onResume();
    }


    @Override
    protected void onRestart() {
        LogUtils.v(TAG, "onRestart");
//        LogUtils.callStack(TAG);
        super.onRestart();
    }

    @Override
    protected void onPause() {
        LogUtils.v(TAG, "onPause");
//        LogUtils.callStack(TAG);

        super.onPause();
    }


    @Override
    protected void onStop() {
        LogUtils.v(TAG, "onStop");
//        LogUtils.callStack(TAG);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtils.v(TAG, "onDestroy");
//        LogUtils.callStack(TAG);

        super.onDestroy();
    }

    @Override
    public void initView() {
        mWifiBtn = (Button) findViewById(R.id.wifi_p2p_btn);
        mWifiBtn.setOnClickListener(mListener);
        Button mActivityTestBtn = (Button) findViewById(R.id.start_btn);
        mActivityTestBtn.setOnClickListener(mListener);

        mEndBtn = (Button)findViewById(R.id.end_btn);
        mEndBtn.setOnClickListener(mListener);
    }

    @Override
    public void initData() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wifi_p2p_btn:
                    startActivity(new Intent(MainActivity.this, WifiP2pActivity.class));
                    break;
                case R.id.start_btn:
//                    startActivity(new Intent(mContext, CopyTestActivity.class));
                    test();
                    break;
                case R.id.end_btn:
                    stopVolumeCalculate();
                    break;
                default:
                    break;
            }
        }
    };

    private AudioRecordUtils mRecordUtils;
    private void test() {
//        mRecordUtils = new AudioRecordUtils();
//        mRecordUtils.setListener(new AudioRecordUtils.IVolumeListener() {
//            @Override
//            public void volumeChanged(int volume) {
//                setCallVolume(volume);
//            }
//        });
//        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL);
//        mRecordUtils.adjustVolume(volume);
        //com.android.settings/com.android.settings.Settings
        ComponentName cmp = new ComponentName("com.android.settings", "com.android.settings.Settings");
        Intent intent = new Intent();
        intent.setComponent(cmp);
        startActivity(intent);
//        flashLight();
//        Intent intent = new Intent(Settings.ACTION_SETTINGS);
//        startActivity(intent);

    }

    private void setCallVolume(int volume) {
        Log.d(TAG, "setCallVolume: " + volume);
        mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, volume, AudioManager.FLAG_SHOW_UI);
        Log.d(TAG, "getCallVolume: " + mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));

    }

    private void stopVolumeCalculate(){
        if(mRecordUtils != null){
            mRecordUtils.stopCalculateVolume();
        }
    }


    private void flashLight() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                initFlashLight();
                while (i < 1000000000) {
                    i++;
                    if (i % 10 < 2) {
                        if (!mLightOn) {
                            openFlashlight();
                        }
                    } else {
                        if (mLightOn) {
                            closeFlashlight();
                        }
                    }
//                    try{
//                        Thread.sleep(1);
//                    }catch (Exception e){
//
//                    }
                }
            }
        }).start();
    }


    private boolean mLightOn = false;

    private void initFlashLight(){
        camera = Camera.open();
        parameters = camera.getParameters();
        try {
            camera.setPreviewTexture(new SurfaceTexture(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    Camera.Parameters parameters ;

    //打开闪光灯
    private void openFlashlight() {
        Log.d(TAG, "openFlashlight start");
        mLightOn = true;
//        camera = Camera.open();
//        Camera.Parameters parameters = camera.getParameters();
//        try {
//            camera.setPreviewTexture(new SurfaceTexture(0));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
//        camera.startPreview();
        Log.d(TAG, "openFlashlight end");
    }

    //关闭闪光灯
    private void closeFlashlight() {
        Log.d(TAG, "closeFlashlight start");
        mLightOn = false;
//        Camera.Parameters parameters = camera.getParameters();
        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
//        camera.stopPreview();
//        camera.release();
        Log.d(TAG, "closeFlashlight end");
    }

}
