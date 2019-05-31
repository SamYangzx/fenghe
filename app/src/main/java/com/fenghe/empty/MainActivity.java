package com.fenghe.empty;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.first_tv)
    public TextView mFirstTv;
    @BindViews({R.id.tv1, R.id.tv2})
    List<TextView> mViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind( this ) ;
        mFirstTv.setText("I am butterKnife");
        initData();
    }

    private void initData() {
        updateView();
    }

    private void updateView() {
        mViews.get(0).setText("Text1");
        mViews.get(1).setText("Text2");
        mFirstTv.setText("ssss");
    }
}
