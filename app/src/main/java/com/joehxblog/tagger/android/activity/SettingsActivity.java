package com.joehxblog.tagger.android.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.joehxblog.tagger.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
    }

}