package com.auo.course.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class MyContentProvider extends ContentProvider {

	public static final String PROVIDER_NAME = "com.auo.ee";

	public static final Uri CONTENT_URI = Uri.parse("content://"
			+ PROVIDER_NAME + "/employee");

	public static final String _ID = "_id";
	public static final String TITLE = "title";
	public static final String NAME = "name";

	private static final int EMPLOYEES = 1;
	private static final int EMPLOYEE_ID = 2;

	private static final UriMatcher uriMatcher;
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(PROVIDER_NAME, "employee", EMPLOYEES);
		uriMatcher.addURI(PROVIDER_NAME, "employee/#", EMPLOYEE_ID);
	}

	// ---for database use---
	private SQLiteDatabase employeeDB;
	private static final String DATABASE_NAME = "Employees";
	private static final String DATABASE_TABLE = "names";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE + " (" + _ID
			+ " integer primary key autoincrement, " + TITLE
			+ " text not null, " + NAME + " text not null);";

	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w("Content provider database",
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			onCreate(db);
		}
	}

	@Override
	public boolean onCreate() {
		Context context = getContext();
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		employeeDB = dbHelper.getWritableDatabase();
		return (employeeDB == null) ? false : true;
	}

	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)) {
		/* get all employee */
		case EMPLOYEES:
			return "vnd.android.cursor.dir/vnd.auo.employees ";
			/* get a particular employee */
		case EMPLOYEE_ID:
			return "vnd.android.cursor.item/vnd.auo.employees ";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID = employeeDB.insert(DATABASE_TABLE, "", values);

		if (rowID > 0) {
			Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
			getContext().getContentResolver().notifyChange(_uri, null);
			return _uri;
		}
		throw new SQLException("Failed to insert row into " + uri);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = 0;

		switch (uriMatcher.match(uri)) {
		case EMPLOYEES:
			count = employeeDB.delete(DATABASE_TABLE, selection, selectionArgs);
			break;
		case EMPLOYEE_ID:
			String id = uri.getPathSegments().get(1);
			count = employeeDB.delete(DATABASE_TABLE, _ID + " = " + id
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""),
					selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(DATABASE_TABLE);

		if (uriMatcher.match(uri) == EMPLOYEE_ID) {
			sqlBuilder.appendWhere(_ID + " = " + uri.getPathSegments().get(1));
		}

		if (sortOrder == null || sortOrder == "") {
			sortOrder = TITLE;
		}

		Cursor c = sqlBuilder.query(employeeDB, projection, selection,
				selectionArgs, null, null, sortOrder);

		c.setNotificationUri(getContext().getContentResolver(), uri);

		return c;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = 0;

		switch (uriMatcher.match(uri)) {
		case EMPLOYEES:
			count = employeeDB.update(DATABASE_TABLE, values, selection,
					selectionArgs);
			break;
		case EMPLOYEE_ID:
			count = employeeDB.update(
					DATABASE_TABLE, values, _ID + " = "
				    + uri.getPathSegments().get(1)
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		getContext().getContentResolver().notifyChange(uri, null);

		return count;
	}
}