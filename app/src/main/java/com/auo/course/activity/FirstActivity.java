package com.auo.course.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class FirstActivity extends Activity {
    private final String TAG = "FirstActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    Log.v(TAG, "onCreate");
    }
}
