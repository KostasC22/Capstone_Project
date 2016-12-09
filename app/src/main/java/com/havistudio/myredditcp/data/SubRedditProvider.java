package com.havistudio.myredditcp.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.util.Log;

/**
 * Created by kostas on 01/10/2016.
 */

public class SubRedditProvider extends ContentProvider {

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private SubRedditDbHelper mOpenHelper;

    static final int SUBREDDIT = 100;
    static final int SUBREDDIT_WITH_ID = 101;

    private static final SQLiteQueryBuilder sSubRedditQueryBuilder;

    static{
        sSubRedditQueryBuilder = new SQLiteQueryBuilder();
    }

    //location.location_setting = ?
    private static final String sIdSelection =
            SubRedditContract.SubRedditEntry._ID + " = ? ";

    private Cursor getSubRedditById(Uri uri, String[] projection, String sortOrder) {
        String id = SubRedditContract.SubRedditEntry.getIdFromUri(uri);

        String[] selectionArgs;
        String selection;

        selection = sIdSelection;
        selectionArgs = new String[]{id};

        return sSubRedditQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );
    }


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = SubRedditContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, SubRedditContract.PATH_SUBREDDIT, SUBREDDIT);
        matcher.addURI(authority, SubRedditContract.PATH_SUBREDDIT + "/*", SUBREDDIT_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new SubRedditDbHelper(getContext());
        return true;
    }

    @Override
    public String getType(Uri uri) {

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SUBREDDIT:
                return SubRedditContract.SubRedditEntry.CONTENT_TYPE;
            case SUBREDDIT_WITH_ID:
                return SubRedditContract.SubRedditEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs,
                        String sortOrder) {
        Cursor retCursor;
        Log.i("SubRedditProvider", "query: " + uri);
        Log.i("SubRedditProvider", "sUriMatcher.match(uri): " + sUriMatcher.match(uri));

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(SubRedditContract.SubRedditEntry.TABLE_NAME);

        switch (sUriMatcher.match(uri)) {
            // "subreddit"
            case SUBREDDIT: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        SubRedditContract.SubRedditEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SUBREDDIT_WITH_ID: {
                retCursor = getSubRedditById(uri, projection, sortOrder);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case SUBREDDIT: {
                long _id = db.insert(SubRedditContract.SubRedditEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = SubRedditContract.SubRedditEntry.buildSubRedditUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) selection = "1";
        switch (match) {
            case SUBREDDIT:
                rowsDeleted = db.delete(
                        SubRedditContract.SubRedditEntry.TABLE_NAME, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(
            Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        switch (match) {
            case SUBREDDIT:
                rowsUpdated = db.update(SubRedditContract.SubRedditEntry.TABLE_NAME, values, selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Log.i("bulkInsert", "bulkInsert: " + uri);
        switch (match) {
            case SUBREDDIT:
                db.beginTransaction();
                int returnCount = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(SubRedditContract.SubRedditEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            returnCount++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }
                getContext().getContentResolver().notifyChange(uri, null);
                return returnCount;
            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
