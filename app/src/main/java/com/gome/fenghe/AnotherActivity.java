package com.gome.fenghe;

import android.os.Bundle;

import com.gome.fenghe.utils.LogUtils;

public class AnotherActivity extends BaseActivity {
    private static final String TAG = AnotherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtils.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
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

    }

    @Override
    public void initData() {

    }
}
