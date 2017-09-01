package com.gome.fenghe;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.gome.fenghe.utils.LogTool;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        LogTool.v(TAG, "onCreate");
        LogTool.callStack(TAG);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    protected void onStart() {
        LogTool.callStack(TAG);
        super.onStart();
    }


    @Override
    protected void onResume() {
//        LogTool.v(TAG, "onResume");
        LogTool.callStack(TAG);
        super.onResume();
    }


    @Override
    protected void onRestart() {
//        LogTool.v(TAG, "onRestart");
        LogTool.callStack(TAG);
        super.onRestart();
    }

    @Override
    protected void onPause() {
//        LogTool.v(TAG, "onPause");
        LogTool.callStack(TAG);

        super.onPause();
    }


    @Override
    protected void onStop() {
//        LogTool.v(TAG, "onStop");
        LogTool.callStack(TAG);

        super.onStop();
    }

    @Override
    protected void onDestroy() {
//        LogTool.v(TAG, "onDestroy");
        LogTool.callStack(TAG);

        super.onDestroy();
    }
}
