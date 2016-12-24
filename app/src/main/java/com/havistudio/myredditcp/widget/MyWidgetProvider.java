package com.havistudio.myredditcp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

import com.havistudio.myredditcp.MainActivity;
import com.havistudio.myredditcp.R;
import com.havistudio.myredditcp.sync.RedditSyncAdapter;

/**
 * Created by kostas on 15/12/2016.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class MyWidgetProvider extends ContentObserver {

    private AppWidgetManager mAppWidgetManager;
    private ComponentName mComponentName;

    MyWidgetProvider(AppWidgetManager mgr, ComponentName cn, Handler h) {
        super(h);
        mAppWidgetManager = mgr;
        mComponentName = cn;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public void onChange(boolean selfChange) {
        mAppWidgetManager.notifyAppWidgetViewDataChanged(
                mAppWidgetManager.getAppWidgetIds(mComponentName), R.id.widget_list);
    }

}