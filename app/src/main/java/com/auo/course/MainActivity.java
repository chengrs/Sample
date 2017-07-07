package com.auo.course;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.auo.course.activity.FirstActivity;
import com.auo.course.activity.SecondActivity;

public class MainActivity extends Activity {
	private final String TAG = "MainActivity";

	private final int REQUEST_CODE = 1;

	private final String CUSTOM_BROADCAST = "broadcast_action";

    private Button mStartActBtn;
    private Button mStartActResBtn;

    private Button mStartMusicBtn;
    private Button mStopMusicBtn;

    private Button mSendBtn;

    private Button mContactsBtn;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        mStartActBtn = (Button) findViewById(R.id.btn_act);
        mStartActResBtn = (Button) findViewById(R.id.btn_res);
        mStartMusicBtn = (Button) findViewById(R.id.btn_play);
        mStopMusicBtn = (Button) findViewById(R.id.btn_stop);
        mSendBtn = (Button) findViewById(R.id.btn_broadcast);
        mContactsBtn = (Button) findViewById(R.id.btn_contacts);

        mStartActBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Log.v(TAG, "start activity : FirstActivity");
                startActivity(new Intent(MainActivity.this, FirstActivity.class));
            }
        });

        mStartActResBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Log.v(TAG, "start activity : SecondActivity");
                startActivityForResult(new Intent(MainActivity.this, SecondActivity.class), REQUEST_CODE);
            }
        });

        mStartMusicBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Log.v(TAG, "start service");
            	startService(new Intent("com.auo.course.service.MUSIC"));
            }
        });

        mStopMusicBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            	Log.v(TAG, "stop activity");
            	stopService(new Intent("com.auo.course.service.MUSIC"));
            }
        });

        mSendBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "send broadcast");
                Intent intent = new Intent(CUSTOM_BROADCAST);
                sendBroadcast(intent); 
            }
        });
        mContactsBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, "retrieve contacts");
		        showContacts();
            }
        });
	}

    /* �i�ܦp��ϥ�content provider */
	private void showContacts() {
		ContentResolver cr = getContentResolver();
		/* ���o�S�wURI�̪���� */
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		if (cur.getCount() > 0) {
			while (cur.moveToNext()) {
				String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
				String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

				Log.i(TAG, "id = " + id);
				Log.i(TAG, "name = " + name);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode){
            case REQUEST_CODE:
            Toast.makeText(this, data.getExtras().getString("result"), 0).show();
		}
//        switch (resultCode) {
//	      case RESULT_OK:
//            Bundle b = data.getExtras();
//            String string = b.getString("CALCULATION");
//        }
	}
}