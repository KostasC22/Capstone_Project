package com.havistudio.myredditcp.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.havistudio.myredditcp.CommentsTask;
import com.havistudio.myredditcp.R;
import com.havistudio.myredditcp.SubRedditDetailActivity;
import com.havistudio.myredditcp.api.Child;
import com.havistudio.myredditcp.api.Example;
import com.havistudio.myredditcp.api.MyApiRetrofit;
import com.havistudio.myredditcp.data.SubRedditContract;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.ExecutionException;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RedditSyncAdapter extends AbstractThreadedSyncAdapter {
    public final String LOG_TAG = RedditSyncAdapter.class.getSimpleName();
    public static final String ACTION_DATA_UPDATED =
            "com.havistudio.myredditcp.ACTION_DATA_UPDATED";

    public static final int SYNC_INTERVAL = 15;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
    private static final long DAY_IN_MILLIS = 1000 * 60 * 60 * 24;
    private static final int SUBREDDIT_NOTIFICATION_ID = 3005;
    private Context mContext;

    public RedditSyncAdapter(Context context, boolean autoInitialize) {

        super(context, autoInitialize);

        this.mContext = context;
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(LOG_TAG, "Starting sync");
        Log.i("testkostas", "Starting sync");

        Context context = getContext();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
        MyApiRetrofit service = retrofit.create(MyApiRetrofit.class);

        Map<String, String> temp2 = new HashMap<String, String>();
        //temp2.put("limit", "10");

        Call<Example> call2 = service.getNewPhotoSubreddits(temp2);
        try {
            Response<Example> tempo = call2.execute();

            List<Child> result = tempo.body().getGeneralData().getChildren();

            Vector<ContentValues> cVVector = new Vector<ContentValues>(result.size());

            for (Child temp : result) {
                ContentValues subRedditValues = new ContentValues();
                //Log.i("test123", "onPerformSync: "+temp.getData());

                String subRedditId = temp.getData().getId();

                Cursor mCursor = mContext.getContentResolver().query(
                        SubRedditContract.SubRedditEntry.CONTENT_URI,
                        new String[]{
                                SubRedditContract.SubRedditEntry.COLUMN_ID,
                                SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS,
                                SubRedditContract.SubRedditEntry.COLUMN_UPS,
                                SubRedditContract.SubRedditEntry.COLUMN_DOWNS,
                                SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE
                        },
                        SubRedditContract.SubRedditEntry.COLUMN_ID + " = ?",
                        new String[]{subRedditId},
                        null);

                if (!(mCursor.moveToFirst()) || mCursor.getCount() == 0) {

                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_ID, subRedditId);
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_DOMAIN, temp.getData().getDomain());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_BANNED_BY, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_MEDIA_EMBED, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT, temp.getData().getSubreddit());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SELFTEXT_HTML, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SELFTEXT, temp.getData().getSelftext());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_LIKES, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SUGGESTED_SORT, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_USER_REPORTS, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SECURE_MEDIA, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_LINK_FLAIR_TEXT, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_GILDED, temp.getData().getGilded() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_ARCHIVED, temp.getData().getArchived() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_CLICKED, temp.getData().getClicked() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_REPORT_REASONS, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_AUTHOR, temp.getData().getAuthor());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_MEDIA, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_NAME, temp.getData().getName());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SCORE, temp.getData().getScore() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_APPROVED_BY, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_OVER_18, temp.getData().getOver18() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_HIDDEN, temp.getData().getHidden() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_THUMBNAIL, temp.getData().getThumbnail());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT_ID, temp.getData().getSubredditId());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_EDITED, temp.getData().getEdited() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_LINK_FLAIR_CSS_CLASS, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_AUTHOR_FLAIR_CSS_CLASS, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_DOWNS, temp.getData().getDowns() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_MOD_REPORTS, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SECURE_MEDIA_EMBED, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SAVED, temp.getData().getSaved() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_REMOVAL_REASON, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_IS_SELF, temp.getData().getIsSelf() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_HIDE_SCORE, temp.getData().getHideScore() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_PERMALINK, temp.getData().getPermalink());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_LOCKED, temp.getData().getLocked() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_STICKIED, temp.getData().getStickied() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_URL, temp.getData().getUrl());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_AUTHOR_FLAIR_TEXT, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_QUARANTINE, temp.getData().getQuarantine() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_TITLE, temp.getData().getTitle());
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_CREATED_UTC, temp.getData().getCreatedUtc() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_UPS, temp.getData().getUps() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS, temp.getData().getNumComments() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_VISITED, temp.getData().getVisited() + "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_NUM_REPORTS, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_DISTINGUISHED, "");
                    subRedditValues.put(SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE, "0");

                    cVVector.add(subRedditValues);
                }

            }

            if (cVVector.size() > 0) {
                ContentValues[] cvArray = new ContentValues[cVVector.size()];
                cVVector.toArray(cvArray);
                int resultSQL = getContext().getContentResolver().bulkInsert(SubRedditContract.SubRedditEntry.CONTENT_URI, cvArray);

            }

            Cursor mCursor2 = mContext.getContentResolver().query(
                    SubRedditContract.SubRedditEntry.CONTENT_URI,
                    new String[]{
                            SubRedditContract.SubRedditEntry.COLUMN_ID,
                            SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS,
                            SubRedditContract.SubRedditEntry.COLUMN_UPS,
                            SubRedditContract.SubRedditEntry.COLUMN_DOWNS,
                            SubRedditContract.SubRedditEntry.COLUMN_THUMBNAIL,
                            SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE
                    },
                    SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE + " = ?",
                    new String[]{"1"},
                    null);

            if (mCursor2.moveToFirst()) {
                int columnIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_ID);
                int columnNoC = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS);
                int titleIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_TITLE);
                int thumbIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_THUMBNAIL);
                int subidIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT_ID);
                int idIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_ID);
                int scoreIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SCORE);
                int upsIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_UPS);
                int downsIndex = mCursor2.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_DOWNS);
                String columnID = mCursor2.getString(columnIndex);
                int cpComments = Integer.parseInt(mCursor2.getString(columnNoC));
                CommentsTask ct = new CommentsTask(columnID, false);
                try {
                    List<Child> comments = ct.execute().get();
                    for (Child tempChild : comments) {
                        int inCom = tempChild.getData().getNumComments();
                        if (cpComments != inCom) {
                            int commentsDiff = cpComments - inCom;
                            String title = context.getString(R.string.app_name);
                            Resources resources = mContext.getResources();

                            String contentText = tempChild.getData().getTitle().substring(0, 20) + "Num:" + commentsDiff;

                            // NotificationCompatBuilder is a very convenient way to build backward-compatible
                            // notifications.  Just throw in some data.
                            NotificationCompat.Builder mBuilder =
                                    new NotificationCompat.Builder(getContext())
                                            .setColor(getContext().getResources().getColor(R.color.colorPrimary))
                                            .setSmallIcon(R.drawable.ic_menu_send)
                                            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_menu_send))
                                            .setContentTitle(title)
                                            .setContentText(contentText);

                            // Make something interesting happen when the user clicks on the notification.
                            // In this case, opening the app is sufficient.
                            Intent resultIntent = new Intent(mContext, SubRedditDetailActivity.class);
                            resultIntent.putExtra("subredditId", columnID);
                            resultIntent.putExtra("subreddittitle", mCursor2.getString(titleIndex));
                            resultIntent.putExtra("subredditthumb", mCursor2.getString(thumbIndex));
                            resultIntent.putExtra("subredditsubid", mCursor2.getString(subidIndex));
                            resultIntent.putExtra("subredditid", mCursor2.getString(idIndex));
                            resultIntent.putExtra("subredditscore", mCursor2.getString(scoreIndex));
                            resultIntent.putExtra("subredditups", mCursor2.getString(upsIndex));
                            resultIntent.putExtra("subredditdowns", mCursor2.getString(downsIndex));

                            // The stack builder object will contain an artificial back stack for the
                            // started Activity.
                            // This ensures that navigating backward from the Activity leads out of
                            // your application to the Home screen.
                            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
                            stackBuilder.addNextIntent(resultIntent);
                            PendingIntent resultPendingIntent =
                                    stackBuilder.getPendingIntent(
                                            0,
                                            PendingIntent.FLAG_UPDATE_CURRENT
                                    );
                            mBuilder.setContentIntent(resultPendingIntent);

                            NotificationManager mNotificationManager =
                                    (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                            // WEATHER_NOTIFICATION_ID allows you to update the notification later on.
                            mNotificationManager.notify(SUBREDDIT_NOTIFICATION_ID, mBuilder.build());
                        }
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return;
    }

    public static void initializeSyncAdapter(Context context) {
        //Log.i("kostaslog", "initializeSyncAdapter: 2");
        getSyncAccount(context);
    }

    public static Account getSyncAccount(Context context) {
        //Log.i("kostaslog", "getSyncAccount: 3");
        // Get an instance of the Android account manager
        AccountManager accountManager =
                (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);

        // Create the account type and default account
        Account newAccount = new Account(
                context.getString(R.string.app_name), context.getString(R.string.sync_account_type));
        //Log.i("kostaslog", "getSyncAccount: 4");
        // If the password doesn't exist, the account doesn't exist
        try {
            if (null == accountManager.getPassword(newAccount)) {

            /*
             * Add the account and account type, no password or user data
             * If successful, return the Account object, otherwise report an error.
             */
                if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                    return null;
                }
                /*
                 * If you don't set android:syncable="true" in
                 * in your <provider> element in the manifest,
                 * then call ContentResolver.setIsSyncable(account, AUTHORITY, 1)
                 * here.
                 */

                onAccountCreated(newAccount, context);
            }
        } catch (SecurityException e){

        }
        //Log.i("kostaslog", "getSyncAccount: 5");
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        //Log.i("kostaslog", "onAccountCreated: 6");
        /*
         * Since we've created an account
         */
        RedditSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);

        /*
         * Without calling setSyncAutomatically, our periodic sync will not be enabled.
         */
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);

        /*
         * Finally, let's do a sync to get things started
         */
        syncImmediately(context);
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        //Log.i("kostaslog", "configurePeriodicSync: 7");
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().
                    syncPeriodic(syncInterval, flexTime).
                    setSyncAdapter(account, authority).
                    setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account,
                    authority, new Bundle(), syncInterval);
        }
    }

    public static void syncImmediately(Context context) {
        //Log.i("kostaslog", "syncImmediately: 6");
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context),
                context.getString(R.string.content_authority), bundle);
    }
}