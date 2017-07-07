package com.auo.course.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import com.auo.course.R;

public class MyBroadReciever extends BroadcastReceiver {
	private final String TAG = "MyBroadReceiver";

	/* ����Broadcast��A�|�۰�trigger�C��BroadcastReceiver��onReceive */
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "" + intent.getAction());

        /* �qintent.getAction()�̭��o�����쪺broadcast�ƥ󪺤��e */
        if (intent.getAction().equals("broadcast_action")) {
        	Toast.makeText(context, "custom broadcast", Toast.LENGTH_LONG).show();
        	return;
        }

        MediaPlayer.create(context, R.raw.demo_receiver).start();
        showToast(context, intent);
    }

	private void showToast(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Object messages[] = (Object[]) bundle.get("pdus");
		SmsMessage smsMessage[] = new SmsMessage[messages.length];

		for (int n = 0; n < messages.length; n++) {
			smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
		}

		// show message
		Toast toast = Toast.makeText(context,
				"Received SMS: " + smsMessage[0].getMessageBody(),
				Toast.LENGTH_LONG);
		toast.show();
	}
}