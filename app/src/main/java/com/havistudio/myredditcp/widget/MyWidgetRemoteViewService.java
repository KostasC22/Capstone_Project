package com.havistudio.myredditcp.widget;

import android.annotation.TargetApi;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.havistudio.myredditcp.R;
import com.havistudio.myredditcp.SubRedditAdapter;
import com.havistudio.myredditcp.data.SubRedditContract;

/**
 * Created by kostas on 15/12/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyWidgetRemoteViewService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetRemoteViewsFactory(this.getApplicationContext(), intent);
    }
}

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
class WidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private static final String[] SUBREDDIT_COLUMNS = {
            SubRedditContract.SubRedditEntry._ID,
            SubRedditContract.SubRedditEntry.COLUMN_NAME,
            SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT,
            SubRedditContract.SubRedditEntry.COLUMN_ID,
            SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT_ID,
            SubRedditContract.SubRedditEntry.COLUMN_TITLE,
            SubRedditContract.SubRedditEntry.COLUMN_URL,
            SubRedditContract.SubRedditEntry.COLUMN_SCORE,
            SubRedditContract.SubRedditEntry.COLUMN_UPS,
            SubRedditContract.SubRedditEntry.COLUMN_DOWNS,
            SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS,
            SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE,
    };

    private static final String TAG = "MyWidgetService";

    private Context mContext;
    private int mAppWidgetId;
    private Cursor mCursor;

    public WidgetRemoteViewsFactory(Context context, Intent intent) {
        mContext = context;
        mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);


    }


    private Cursor data = null;

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (data != null) {
            data.close();
        }
        final long identityToken = Binder.clearCallingIdentity();

        data = mContext.getContentResolver().query(SubRedditContract.SubRedditEntry.CONTENT_URI, SUBREDDIT_COLUMNS,
                null,
                null,
                null);

        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {
        if (data != null) {
            data.close();
            data = null;
        }
    }

    @Override
    public int getCount() {
        Log.i(TAG, "getCount: " + data.getCount());
        return data == null ? 0 : data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (position == AdapterView.INVALID_POSITION ||
                data == null || !data.moveToPosition(position)) {
            return null;
        }
        final RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
//        views.setImageViewResource(R.id.widget_icon, R.mipmap.ic_launcher);

        int titleIndex = data.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_TITLE);
        int scoreIndex = data.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SCORE);
        int upsIndex = data.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_UPS);
        int downsIndex = data.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_DOWNS);
        int nocoIndex = data.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS);

        views.setTextViewText(R.id.widget_title, SubRedditAdapter.validateTitleWidget(data.getString(titleIndex)));
        views.setTextViewText(R.id.widget_score, "S:" + String.valueOf(data.getString(scoreIndex)));
        views.setTextViewText(R.id.widget_ups, "U:" + String.valueOf(data.getString(upsIndex)));
        views.setTextViewText(R.id.widget_downs, "D:" + String.valueOf(data.getString(downsIndex)));
        views.setTextViewText(R.id.widget_nocom, "C:" + String.valueOf(data.getString(nocoIndex)));

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        if (data.moveToPosition(position))
            return data.getLong(data.getColumnIndex(SubRedditContract.SubRedditEntry._ID));
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    private void setRemoteContentDescription(RemoteViews views, String description) {

    }

}
