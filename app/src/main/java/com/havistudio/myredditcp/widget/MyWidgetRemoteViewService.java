package com.havistudio.myredditcp.widget;

import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.havistudio.myredditcp.R;
import com.havistudio.myredditcp.data.SubRedditContract;

/**
 * Created by kostas on 15/12/2016.
 */

public class MyWidgetRemoteViewService extends RemoteViewsService {

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

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RemoteViewsFactory() {
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
                return data == null ? 0 : data.getCount();
            }

            @Override
            public RemoteViews getViewAt(int i) {
                return null;
            }

            @Override
            public RemoteViews getLoadingView() {
                return new RemoteViews(getPackageName(), R.layout.widget_list_item);
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            private void setRemoteContentDescription(RemoteViews views, String description) {
                views.setContentDescription(R.id.widget_icon, description);
            }
        };
    }
}
