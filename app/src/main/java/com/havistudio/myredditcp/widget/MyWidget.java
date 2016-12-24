package com.havistudio.myredditcp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.widget.RemoteViews;

import com.havistudio.myredditcp.R;
import com.havistudio.myredditcp.data.SubRedditContract;

/**
 * Created by kostas on 24/12/2016.
 */

public class MyWidget extends AppWidgetProvider {

    private static HandlerThread sWorkerThread;
    private static Handler sWorkerQueue;
    private static MyWidgetProvider sDataObserver;
    private boolean mIsLargeLayout = true;

    public MyWidget(){
        sWorkerThread = new HandlerThread("MyWidgetProvider-worker");
        sWorkerThread.start();
        sWorkerQueue = new Handler(sWorkerThread.getLooper());
    }

    @Override
    public void onEnabled(Context context) {
        final ContentResolver r = context.getContentResolver();
        if (sDataObserver == null) {
            final AppWidgetManager mgr = AppWidgetManager.getInstance(context);
            final ComponentName cn = new ComponentName(context, MyWidget.class);
            sDataObserver = new MyWidgetProvider(mgr, cn, sWorkerQueue);
            r.registerContentObserver(SubRedditContract.SubRedditEntry.CONTENT_URI, true, sDataObserver);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i = 0; i < appWidgetIds.length; ++i) {
            RemoteViews layout = buildLayout(context, appWidgetIds[i], mIsLargeLayout);
            appWidgetManager.updateAppWidget(appWidgetIds[i], layout);
        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    private RemoteViews buildLayout(Context context, int appWidgetId, boolean largeLayout) {
        RemoteViews rv;
        Log.i("buildLayout", "largeLayout: " + largeLayout);
        if (largeLayout) {
            final Intent intent = new Intent(context, MyWidgetRemoteViewService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));
            rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                rv.setRemoteAdapter(appWidgetId, R.id.widget_list, intent);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                rv.setEmptyView(R.id.widget_list, R.id.widget_empty);
            }

        } else {
            rv = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
        }
        return rv;
    }

    @Override
    public void onAppWidgetOptionsChanged(Context context, AppWidgetManager appWidgetManager,
                                          int appWidgetId, Bundle newOptions) {
        int minWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_WIDTH);
        int maxWidth = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_WIDTH);
        int minHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MIN_HEIGHT);
        int maxHeight = newOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_MAX_HEIGHT);
        RemoteViews layout;
        if (minHeight < 100) {
            mIsLargeLayout = false;
        } else {
            mIsLargeLayout = true;
        }
        layout = buildLayout(context, appWidgetId, mIsLargeLayout);
        appWidgetManager.updateAppWidget(appWidgetId, layout);
    }
}
