package com.fenghe.empty;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fenghe.empty.databinding.DatabindingBinding;
import com.fenghe.empty.databinding.Databinding2Binding;
import com.fenghe.empty.model.User;
import com.fenghe.empty.model.User2;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private User user;
    private User2 user2;
    private DatabindingBinding activityMainBinding;
    private Databinding2Binding activityMain2Binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.databinding);
        activityMain2Binding = DataBindingUtil.setContentView(this, R.layout.databinding2);
        user = new User("leavesC", "123456");
        user2 = new User2("leaveff", "123456");
        activityMain2Binding.setUserInfo(user2);
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
                user2.setFirstName("10s");

                Log.d(TAG, "name: " + user2.getFirstName());
//                Log.d(TAG, "name: " + user.getName());
//                activityMainBinding.setUserInfo(user);

            }
        }).start();
    }
}
