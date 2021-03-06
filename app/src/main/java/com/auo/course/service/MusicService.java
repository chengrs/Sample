package com.auo.course.service;

import com.auo.course.R;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
	private static final String TAG = "MusicService";
	MediaPlayer player;

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		Log.i(TAG, "onCreate");
		player = MediaPlayer.create(this, R.raw.music);
		player.setLooping(false); // Set looping
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		player.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		player.stop();
	}
}
