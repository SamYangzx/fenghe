package com.gome.fenghe;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenceTestActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.preferences.activity_preference_test);
        addPreferencesFromResource(R.xml.preferences);
    }
}
