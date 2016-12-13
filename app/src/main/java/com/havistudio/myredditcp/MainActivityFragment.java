package com.havistudio.myredditcp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.havistudio.myredditcp.data.SubRedditContract;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    private Tracker mTracker;
    private RecyclerView mRecyclerView;
    private SubRedditAdapter mSubRedditAdapter;

    private static final String[] SUBREDDIT_COLUMNS = {
            SubRedditContract.SubRedditEntry._ID,
            SubRedditContract.SubRedditEntry.COLUMN_NAME,
            SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT,
            SubRedditContract.SubRedditEntry.COLUMN_ID,
            SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT_ID,
            SubRedditContract.SubRedditEntry.COLUMN_TITLE,
            SubRedditContract.SubRedditEntry.COLUMN_THUMBNAIL,
            SubRedditContract.SubRedditEntry.COLUMN_URL,
            SubRedditContract.SubRedditEntry.COLUMN_SCORE,
            SubRedditContract.SubRedditEntry.COLUMN_UPS,
            SubRedditContract.SubRedditEntry.COLUMN_DOWNS,
            SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS,
            SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE,
    };

    public MainActivityFragment() {
    }

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Obtain the shared Tracker instance.
        MyRedditCPApp application = (MyRedditCPApp) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        // Get a reference to the RecyclerView, and attach this adapter to it.
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview_subreddits);
        // Set the layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // create the empty view
        View emptyView = rootView.findViewById(R.id.recyclerview_subreddits_empty);

        mSubRedditAdapter = new SubRedditAdapter(getActivity(), mTracker,new SubRedditAdapter.SubRedditAdapterOnClickHandler() {
            @Override
            public void onClick(Long date, SubRedditAdapter.SubRedditAdapterViewHolder vh) {

            }

        }, 1, emptyView);

        mRecyclerView.setAdapter(mSubRedditAdapter);

        MobileAds.initialize(getActivity().getApplicationContext(), getString(R.string.banner_ad_unit_id));
        AdView mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(0, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                SubRedditContract.SubRedditEntry.CONTENT_URI,
                SUBREDDIT_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mSubRedditAdapter.swapCursor(data);
        if ( data.getCount() == 0 ) {
            getActivity().supportStartPostponedEnterTransition();
        } else {

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
