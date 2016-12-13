package com.havistudio.myredditcp;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.havistudio.myredditcp.api.Child;
import com.havistudio.myredditcp.api.Data;
import com.havistudio.myredditcp.data.SubRedditContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A placeholder fragment containing a simple view.
 */
public class SubRedditDetailActivityFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "Main3ActivityFragment";

    @Bind(R.id.make_subscribe)
    Button buttonSubcribe;

    private static final String[] SUBREDDIT_COLUMNS = {
            SubRedditContract.SubRedditEntry._ID,
            SubRedditContract.SubRedditEntry.COLUMN_NAME,
            SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT,
            SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT_ID,
            SubRedditContract.SubRedditEntry.COLUMN_TITLE,
            SubRedditContract.SubRedditEntry.COLUMN_THUMBNAIL,
            SubRedditContract.SubRedditEntry.COLUMN_URL,
            SubRedditContract.SubRedditEntry.COLUMN_SCORE,
            SubRedditContract.SubRedditEntry.COLUMN_UPS,
            SubRedditContract.SubRedditEntry.COLUMN_DOWNS,
    };

    private long mId;
    private String mTitle, mThumb, mSubRedditId,msId,mScore, mDowns, mUps;
    private int mSuscribe;
    public static MyAdapter adapter;
    private ArrayList<AdapterObject> myArray1;

    public SubRedditDetailActivityFragment() {
    }

    public void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mId = getActivity().getIntent().getExtras().getLong("subredditId");
        mTitle = getActivity().getIntent().getExtras().getString("subreddittitle");
        mThumb = getActivity().getIntent().getExtras().getString("subredditthumb");
        mSubRedditId = getActivity().getIntent().getExtras().getString("subredditsubid");
        msId = getActivity().getIntent().getExtras().getString("subredditid");
        mScore = getActivity().getIntent().getExtras().getString("subredditscore");
        mUps = getActivity().getIntent().getExtras().getString("subredditups");
        mDowns = getActivity().getIntent().getExtras().getString("subredditdowns");
        try{
            mSuscribe = Integer.parseInt(getActivity().getIntent().getExtras().getString("subredditsubscribe"));
        } catch (Exception e){
            mSuscribe = 0;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sub_reddit_detail, container, false);
        ButterKnife.bind(this, rootView);

        TextView tTitleText = (TextView) rootView.findViewById(R.id.detailsubredditTitle);
        tTitleText.setText(mTitle);

        ImageView tThumbView = (ImageView) rootView.findViewById(R.id.detailsubredditThumb);
        Picasso.with(getActivity()).load(mThumb).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fit().into(tThumbView);

        TextView tScoreText = (TextView) rootView.findViewById(R.id.fragment_text_score);
        tScoreText.setText("Score: "+mScore);

        TextView tUpsText = (TextView) rootView.findViewById(R.id.fragment_text_ups);
        tUpsText.setText("Ups: "+mUps);

        TextView tDownsText = (TextView) rootView.findViewById(R.id.fragment_text_downs);
        tDownsText.setText("Downs: "+mDowns);

        if(mSuscribe == 0){
            buttonSubcribe.setText("Subscribe");
        } else {
            buttonSubcribe.setText("Unsubscribe");
        }

        ListView myListView = (ListView) rootView.findViewById(R.id.unitListView);
        myArray1 = new ArrayList<AdapterObject>();
        adapter = new MyAdapter(getActivity(), R.layout.comment_row, myArray1);
        myListView.setAdapter(adapter);

        CommentsTask tat5 = new CommentsTask(msId);
        try {
            List<Child> comments = tat5.execute().get();
            displayComments(comments, 4, 0);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return rootView;
    }

    private void displayComments(List<Child> Comments, int tabs, int marginMultiplier){
        if(Comments != null){
            for (Child tempChild : Comments) {
                Data tempData = tempChild.getData();
                myArray1.add(new AdapterObject(tempData.getId(),tempData.getBody(), new Long(tempData.getScore()), new Long(tempData.getUps()), new Long(tempData.getDowns()), new Long(marginMultiplier)));
                if(tempChild.getData().getReplies()!= null) {
                    displayComments(tempChild.getData().getReplies().getData().getChildren(), tabs + 4, marginMultiplier + 1);
                }
            }
        }
    }

    private String makeTabs(int tabs){
        StringBuffer temp = new StringBuffer();
        for (int i = 0; i < tabs; i++) {
            temp.append(" ");
        }
        return temp.toString();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(),
                SubRedditContract.SubRedditEntry.buildSubRedditUri(mId),
                SUBREDDIT_COLUMNS,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int titleIndex = data.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_TITLE);
        Log.i("onLoadFinished1", "onLoadFinished2: "+data.getString(titleIndex));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @OnClick(R.id.make_subscribe)
    public void makeSubscribe() {
        Log.i(TAG, "makeSubscribe: ");
        buttonSubcribe.setEnabled(false);
        MakeSuscribeAsyncTask mfat = new MakeSuscribeAsyncTask(getActivity(), buttonSubcribe,mId, msId);
        mfat.execute();
    }
}
