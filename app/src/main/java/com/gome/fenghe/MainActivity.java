package com.gome.fenghe;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gome.fenghe.utils.AudioRecordUtils;
import com.gome.fenghe.utils.LogTool;
import com.gome.fenghe.wifip2p2.WifiP2pActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Context mContext;
    private Button mWifiBtn;
    AudioManager mAudioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LogTool.d(TAG, "onCreate.");
        mContext = this;
        initView();
    }


    @Override
    protected void onStart() {
        LogTool.v(TAG, "onStart");

//        LogTool.callStack(TAG);
        super.onStart();
    }


    @Override
    protected void onResume() {
        LogTool.v(TAG, "onResume");
//        LogTool.callStack(TAG);
        super.onResume();
    }


    @Override
    protected void onRestart() {
        LogTool.v(TAG, "onRestart");
//        LogTool.callStack(TAG);
        super.onRestart();
    }

    @Override
    protected void onPause() {
        LogTool.v(TAG, "onPause");
//        LogTool.callStack(TAG);

        super.onPause();
    }


    @Override
    protected void onStop() {
        LogTool.v(TAG, "onStop");
//        LogTool.callStack(TAG);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogTool.v(TAG, "onDestroy");
//        LogTool.callStack(TAG);

        super.onDestroy();
    }

    @Override
    public void initView() {
        mWifiBtn = (Button) findViewById(R.id.wifi_p2p_btn);
        mWifiBtn.setOnClickListener(mListener);
        Button mActivityTestBtn = (Button) findViewById(R.id.activity_test_btn);
        mActivityTestBtn.setOnClickListener(mListener);
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
                case R.id.activity_test_btn:
//                    startActivity(new Intent(mContext, CopyTestActivity.class));
                    test();
                    break;
                default:
                    break;
            }
        }
    };

    private void test() {
        AudioRecordUtils record = new AudioRecordUtils();
        record.setListener(new AudioRecordUtils.IVolumeListener() {
            @Override
            public void volumeChanged(int volume) {
                setCallVolume(volume);
            }
        });
        record.getNoiseLevel();
    }

    private void setCallVolume(int volume) {
        LogTool.d(TAG, "setCallVolume: " + volume);
        mAudioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, volume, AudioManager.FLAG_PLAY_SOUND);
        LogTool.d(TAG, "getCallVolume: " + mAudioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL));

    }

}
