package com.gome.fenghe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gome.fenghe.wifip2p.WiFiDirectActivity;
import com.gome.fenghe.wifip2p2.WifiP2pActivity;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button mWifiBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mWifiBtn = (Button) findViewById(R.id.wifi_p2p_btn);
        mWifiBtn.setOnClickListener(mListener);
    }


    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.wifi_p2p_btn:
                    startActivity(new Intent(MainActivity.this, WifiP2pActivity.class));
                    break;
            }
        }
    };

}
