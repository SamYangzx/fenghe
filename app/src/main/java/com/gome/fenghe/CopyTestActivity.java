package com.gome.fenghe;

import android.os.Bundle;

import com.gome.fenghe.utils.CopyFileUtils;
import com.gome.fenghe.utils.FileUtils;
import com.gome.fenghe.utils.LogTool;

import java.io.File;

public class CopyTestActivity extends BaseActivity {
    private static final String TAG = CopyTestActivity.class.getSimpleName();

    private static final String[] SRC_FILES = new String[]{"test.log", "yourname.jpg", "test.mp4", "127.3gp", "128.3gp"};
    private static final String[] TARGET_FILES = SRC_FILES;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_copy_test);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i = 0; i < SRC_FILES.length; i++) {
                        String srcPath = FileUtils.getSrcPath(SRC_FILES[i]);
                        String destPath = FileUtils.getTarPath(TARGET_FILES[i]);
                        LogTool.d(TAG, "i: " + i + " , srcPath: " + srcPath);
                        LogTool.d(TAG, "i: " + i + " , destPath: " + destPath);
                        File src = new File(srcPath);
                        File dest = new File(destPath);
                        LogTool.d(TAG, "src exist: " + src.exists());
                        LogTool.d(TAG, "dest exist: " + dest.exists());
                        CopyFileUtils.copyFileUsingFileStreams(src, dest);
                        CopyFileUtils.copyFileUsingFileChannels(src, dest);
                    }
                } catch (Exception e) {
                    LogTool.e(TAG, "e: " + e);
//            e.printStackTrace();
                }
            }
        }).start();

    }
}
