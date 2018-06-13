package com.example.inspiron.mysample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.inspiron.mysample.Database.AppDatabase;
import com.example.inspiron.mysample.View.CustomSwitch;

/**
 * Created by Harris on 28/2/2018
 * Project Name MySample.
 */

public class MainActivity extends AppCompatActivity {

    private AppDatabase mDb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = AppDatabase.getDatabaseInstance(getApplicationContext());
    }
}
