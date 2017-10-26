package com.gome.fenghe;

import android.os.Bundle;

import com.gome.fenghe.utils.LogTool;

public class AnotherActivity extends BaseActivity {
    private static final String TAG = AnotherActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogTool.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_another);
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

    }

    @Override
    public void initData() {

    }
}
