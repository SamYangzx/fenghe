package com.gome.fenghe;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.gome.fenghe.utils.LogUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

public class ApkInfoActivity extends BaseActivity {
    private static final String TAG = ApkInfoActivity.class.getSimpleName();

    private static final String SETTING_PKG = "com.android.settings";
    private static final String MSG_PKG = "com.android.mms";
    private static final String SECURITY_PKG = "com.gome.security";
    private static final String LAUNCHER_PKG = "com.gome.launcher";
    private static final String APP_PKG = "com.android.providers.media"; //非系统应用

    private TextView mPkgInfoTv;
    private Button mGetPkg;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apk_info);
        mContext = ApkInfoActivity.this;

        initView();
    }


    @Override
    protected void onRestart() {
        LogUtils.v(TAG, "onRestart");
//        LogUtils.callStack(TAG);
        super.onRestart();
    }

    @Override
    public void initView() {
        mPkgInfoTv = (TextView) findViewById(R.id.pkg_info_tv);
        mGetPkg = (Button) findViewById(R.id.get_pkg_info);
        mGetPkg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                getSigByPkgName("com.gome.weather2");
//                getSigByPkgName(SETTING_PKG);
//                getSigByPkgName(MSG_PKG);
//                getSigByPkgName(SECURITY_PKG);
//                getSigByPkgName(LAUNCHER_PKG);
//                getSigByPkgName(APP_PKG);
                printApp();
            }
        });
    }

    @Override
    public void initData() {

    }

    private void getSigByPkgName(String pkgName) {
//        String pkgName = mPkgNameEdit.getText().toString();
        if (null != pkgName && pkgName.length() > 0) {
            try {
                Signature sig = this.getPackageManager().getPackageInfo(pkgName, PackageManager.GET_SIGNATURES).signatures[0];
                MessageDigest md5;
                md5 = MessageDigest.getInstance("MD5");
                String result = bytesToHexString(md5.digest(sig.toByteArray()));
                if (null != result && result.length() > 0) {
                    showResult("包名：" + pkgName + "\n 签名：" + result);
//                    mShakeButton.setVisibility(View.VISIBLE);
                } else {
                    showResult("读取失败，请重试或者检查应用是否有签名！");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
                showResult("应用未安装，请检查输入的包名是否正确！");
//                mShakeButton.setVisibility(View.GONE);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
                showResult("系统错误，请重启应用后重试！");
//                mShakeButton.setVisibility(View.GONE);
            }
        } else {
            showResult("请先在输入框输入需要查询签名应用的包名！");
//            mShakeButton.setVisibility(View.GONE);
        }
    }

    private void showResult(String tips) {
        mPkgInfoTv.setText(tips);
        Log.d(TAG, "result: " + tips);
    }


    private String bytesToHexString(byte md5Bytes[]) {
        final char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        if (md5Bytes != null && md5Bytes.length == 16) {
            char str[] = new char[16 * 2];
            int k = 0;
            for (int i = 0; i < 16; i++) {
                byte byte0 = md5Bytes[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } else {
            return "";
        }
    }

    private void printApp() {
        PackageManager packageManager = mContext.getPackageManager();
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfos) {
            if ((ApplicationInfo.FLAG_SYSTEM & packageInfo.applicationInfo.flags) != 0) {
                Log.w("应用", packageInfo.packageName + " " + packageInfo.applicationInfo.loadLabel(packageManager) + " 系统应用");
            } else {
                Log.d("应用", packageInfo.packageName + " " + packageInfo.applicationInfo.loadLabel(packageManager) + " 用户应用");
            }
        }
    }

}
