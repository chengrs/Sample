package com.auo.course.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

public class SkeletonProvider extends ContentProvider {

	/* initialize */
	@Override
	public boolean onCreate() {
		return false;
	}

	@Override
	public String getType(Uri uri) {
		return null;
	}

	/* ���Ѩ�Lapplication insert���\�� */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	/* ���Ѩ�Lapplication delete���\�� */
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	/* ���Ѩ�Lapplication query���\�� */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	/* ���Ѩ�Lapplication update���\�� */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
}
