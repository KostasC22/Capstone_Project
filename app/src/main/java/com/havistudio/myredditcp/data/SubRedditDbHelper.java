package com.havistudio.myredditcp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.havistudio.myreddit.data.SubRedditContract.SubRedditEntry;

/**
 * Created by kostas on 01/10/2016.
 */

public class SubRedditDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    static final String DATABASE_NAME = "subreddit.db";

    public SubRedditDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_SUBREDDIT_TABLE = "CREATE TABLE " + SubRedditEntry.TABLE_NAME + " (" +
                SubRedditEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SubRedditEntry.COLUMN_ID + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_DOMAIN + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_BANNED_BY + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_MEDIA_EMBED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SUBREDDIT + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SELFTEXT_HTML + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SELFTEXT + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_LIKES + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SUGGESTED_SORT + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_USER_REPORTS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SECURE_MEDIA + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_LINK_FLAIR_TEXT + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_GILDED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_ARCHIVED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_CLICKED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_REPORT_REASONS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_AUTHOR + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_MEDIA + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_NAME + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SCORE + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_APPROVED_BY + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_OVER_18 + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_HIDDEN + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_THUMBNAIL + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SUBREDDIT_ID + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_EDITED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_LINK_FLAIR_CSS_CLASS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_AUTHOR_FLAIR_CSS_CLASS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_DOWNS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_MOD_REPORTS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SECURE_MEDIA_EMBED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_SAVED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_REMOVAL_REASON + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_IS_SELF + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_HIDE_SCORE + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_PERMALINK + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_LOCKED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_STICKIED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_URL + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_AUTHOR_FLAIR_TEXT + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_QUARANTINE + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_CREATED_UTC + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_UPS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_NUM_COMMENTS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_VISITED + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_NUM_REPORTS + " TEXT NOT NULL, " +
                SubRedditEntry.COLUMN_DISTINGUISHED + " TEXT NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_SUBREDDIT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SubRedditEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
