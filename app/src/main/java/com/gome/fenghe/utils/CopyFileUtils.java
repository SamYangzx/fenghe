package com.gome.fenghe.utils;

import android.util.TimeUtils;

import java.io.*;
import java.nio.channels.*;

/**
 * Created by fenghe on 2017/9/30.
 */

public class CopyFileUtils {
    private static final String TAG = CopyFileUtils.class.getSimpleName();

    public static void copyFileUsingFileStreams(File source, File dest) throws IOException {
        long startTime = System.currentTimeMillis();
//        LogTool.d(TAG, "startTime: " );
        LogTool.d(TAG, "copyFileUsingFileStreams.source: " + source);
        InputStream input = null;
        OutputStream output = null;
        try {
            input = new FileInputStream(source);
            output = new FileOutputStream(dest);
            byte[] buf = new byte[1024];
            int bytesRead;
            while ((bytesRead = input.read(buf)) > 0) {
                output.write(buf, 0, bytesRead);
            }
        } finally {
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        }
        long endTime = System.currentTimeMillis();
        LogTool.i(TAG, "sopy source: " + source.getAbsolutePath() + " take time: " + (endTime - startTime));
    }


    public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        long startTime = System.currentTimeMillis();
        LogTool.d(TAG, "copyFileUsingFileChannels.source: " + source);
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
        long endTime = System.currentTimeMillis();
        LogTool.i(TAG, "sopy source: " + source.getAbsolutePath() + " take time: " + (endTime - startTime));
    }
}
