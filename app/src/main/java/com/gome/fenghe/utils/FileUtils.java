package com.gome.fenghe.utils;

/**
 * Created by fenghe on 2017/9/30.
 */

public class FileUtils {
    public static final String TEST_FOLDER = "/storage/emulated/0/aaa/";
    public static final String TEST_TAR_FOLDER = "/storage/emulated/0/bbb/";


    public static String getSrcPath(String fileName) {
        return TEST_FOLDER + fileName;
    }

    public static String getTarPath(String fileName) {
        return TEST_TAR_FOLDER + fileName;
    }

}
