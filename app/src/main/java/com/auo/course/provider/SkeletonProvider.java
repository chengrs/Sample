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

	/* 提供其他application insert的功能 */
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		return null;
	}

	/* 提供其他application delete的功能 */
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		return 0;
	}

	/* 提供其他application query的功能 */
	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		return null;
	}

	/* 提供其他application update的功能 */
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		return 0;
	}
}
