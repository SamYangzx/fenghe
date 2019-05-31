package com.fenghe.empty;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fenghe.empty.databinding.DatabindingBinding;
import com.fenghe.empty.model.User;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private User user;
    private DatabindingBinding activityMain2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.databinding);
        activityMain2Binding = DataBindingUtil.setContentView(this, R.layout.databinding);
        user = new User("leavesC", "123456");
        activityMain2Binding.setUserInfo(user);
        initData();
    }

    private void initData() {
        updateUser();
    }

    private void updateUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                user.setName("10s");
                Log.d(TAG, "name: " + user.getName());
                activityMain2Binding.setUserInfo(user);

            }
        }).start();
    }
}
