package com.havistudio.myredditcp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;

import com.havistudio.myredditcp.data.SubRedditContract;
import com.havistudio.myredditcp.data.SubRedditDbHelper;

/**
 * Created by kostas on 17/01/2016.
 */
public class MakeSuscribeAsyncTask extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "MakeSuscribeAsyncTask";

    private String tempText = "Unfavorite";
    private Context mContext;
    private String subredditId;
    private Button mButton;
    private int mFavorite;
    private Long id;

    public MakeSuscribeAsyncTask(Context mContext, Button mButton, Long id, String subredditId) {
        this.mContext = mContext;
        this.mButton = mButton;
        this.subredditId = subredditId;
        this.id = id;
        Log.i(TAG, "constructor: ");
    }

    @Override
    protected Void doInBackground(Void... params) {

        Log.i(TAG, "doInBackground: "+SubRedditContract.SubRedditEntry.CONTENT_URI);

        Cursor favoriteCursor = mContext.getContentResolver().query(
                SubRedditContract.SubRedditEntry.CONTENT_URI,
                new String[]{
                        SubRedditContract.SubRedditEntry.COLUMN_ID,
                        SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE
                },
                SubRedditContract.SubRedditEntry.COLUMN_ID + " = ?",
                new String[]{subredditId},
                null);

        if (favoriteCursor.moveToFirst()) {
            int columnSubscribeIndex = favoriteCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE);
            Log.i(TAG, "doInBackground subscribe: "+favoriteCursor.getString(columnSubscribeIndex));
            int currentValue = Integer.parseInt(favoriteCursor.getString(columnSubscribeIndex));
            Log.i(TAG, "doInBackground: ");
            if (currentValue == 0) {
                currentValue = 1;
            } else {
                currentValue = 0;
                tempText = "Unsubscribe";
            }
            SubRedditDbHelper dbHelper = new SubRedditDbHelper(mContext);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues myValues = new ContentValues();
            myValues.put(SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE, currentValue+"");
            db.update(SubRedditContract.SubRedditEntry.TABLE_NAME, myValues, SubRedditContract.SubRedditEntry.COLUMN_ID + "= ?", new String[]{subredditId});
        }

        return null;
    }

    protected void onPostExecute(Void result) {
        mButton.setText(tempText);
        mButton.setEnabled(true);
    }

}

