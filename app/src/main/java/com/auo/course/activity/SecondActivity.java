package com.auo.course.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.auo.course.R;

public class SecondActivity extends Activity {
	private final String TAG = "SecondActivity";

    private Button mOneBtn;
    private Button mTwoBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        mOneBtn = (Button) findViewById(R.id.btn_one);
        mTwoBtn = (Button) findViewById(R.id.btn_two);

        mOneBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "return 1");

                Intent intent=new Intent();

                intent.putExtra("result", "1");
                setResult(RESULT_OK,intent);
                finish();
            }
        });

        mTwoBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "return 2");

                Intent intent=new Intent();

                intent.putExtra("result", "2");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
