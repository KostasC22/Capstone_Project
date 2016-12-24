package com.havistudio.myredditcp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.havistudio.myredditcp.data.SubRedditContract;
import com.squareup.picasso.Picasso;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by kostas on 12/09/2016.
 */
public class SubRedditAdapter extends RecyclerView.Adapter<SubRedditAdapter.SubRedditAdapterViewHolder>{

    private Cursor mCursor;
    private Context context;
    private SubRedditAdapterOnClickHandler data = null;
    private View mEmptyView;
    final private int layoutResourceId;
    private boolean mUseTodayLayout = true;
    private Tracker mTracker;

    public SubRedditAdapter(Context context, Tracker mTracker, SubRedditAdapterOnClickHandler data, int layoutResourceId, View emptyView) {
        this.context = context;
        this.data = data;
        this.layoutResourceId = layoutResourceId;
        mEmptyView = emptyView;
        this.mTracker = mTracker;
    }

    public class SubRedditAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView mTitleView, mScoreView, mUpsView, mDownView, mNoComms;
        public final ImageView mThumbView;

        public SubRedditAdapterViewHolder(View view) {
            super(view);
            mTitleView = (TextView) view.findViewById(R.id.list_item_title);
            mThumbView = (ImageView) view.findViewById(R.id.list_item_thumbnail);
            mScoreView = (TextView) view.findViewById(R.id.list_item_score);
            mUpsView = (TextView) view.findViewById(R.id.list_item_ups);
            mDownView = (TextView) view.findViewById(R.id.list_item_downs);
            mNoComms = (TextView) view.findViewById(R.id.list_item_nofcoms);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            mCursor.moveToPosition(adapterPosition);
            int idColumnIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry._ID);
            long currentId = mCursor.getLong(idColumnIndex);
            int titleIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_TITLE);
            int thumbIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_THUMBNAIL);
            int subidIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SUBREDDIT_ID);
            int idIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_ID);
            int scoreIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SCORE);
            int upsIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_UPS);
            int downsIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_DOWNS);
            int subscribeIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SUBSCRIBE);
            Log.i("Main3ActivityFragment", "onClick1: "+currentId);
            String mTitle = mCursor.getString(titleIndex);
            Intent intent = new Intent(context, SubRedditDetailActivity.class);
            intent.putExtra("subredditId", currentId);
            intent.putExtra("subreddittitle",  mTitle);
            intent.putExtra("subredditthumb",  mCursor.getString(thumbIndex));
            intent.putExtra("subredditsubid",  mCursor.getString(subidIndex));
            intent.putExtra("subredditid",  mCursor.getString(idIndex));
            intent.putExtra("subredditscore",  mCursor.getString(scoreIndex));
            intent.putExtra("subredditups",  mCursor.getString(upsIndex));
            intent.putExtra("subredditdowns",  mCursor.getString(downsIndex));
            intent.putExtra("subredditsubscribe",  mCursor.getString(subscribeIndex));

            mTracker.setScreenName("SubReddit~" + mTitle);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());

            context.startActivity(intent);
        }
    }

    @Override
    public void onBindViewHolder(SubRedditAdapterViewHolder subRedditAdapterViewHolder, int position) {
        mCursor.moveToPosition(position);
        int titleIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_TITLE);
        subRedditAdapterViewHolder.mTitleView.setText(validateTitle(mCursor.getString(titleIndex)));
        int scoreIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_SCORE);
        subRedditAdapterViewHolder.mScoreView.setText("Score:"+ String.valueOf(mCursor.getString(scoreIndex)));
        int upsIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_UPS);
        subRedditAdapterViewHolder.mUpsView.setText("Ups:"+ String.valueOf(mCursor.getString(upsIndex)));
        int downsIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_DOWNS);
        subRedditAdapterViewHolder.mDownView.setText("Downs:"+ String.valueOf(mCursor.getString(downsIndex)));
        int nocoIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_NUM_COMMENTS);
        subRedditAdapterViewHolder.mNoComms.setText("Comments:"+ String.valueOf(mCursor.getString(nocoIndex)));
        int thumbIndex = mCursor.getColumnIndex(SubRedditContract.SubRedditEntry.COLUMN_THUMBNAIL);

        String imgThumb = null;
        String tempImage = null;
        try {
            tempImage = mCursor.getString(thumbIndex);
            imgThumb = URLDecoder.decode(tempImage, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if(imgThumb!= null){
            Picasso.with(context).load(imgThumb).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).fit().into(subRedditAdapterViewHolder.mThumbView);
        }

    }

    public static interface SubRedditAdapterOnClickHandler {
        void onClick(Long date, SubRedditAdapterViewHolder vh);
    }

    public SubRedditAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        int layoutId = R.layout.list_item_subreddit;
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(layoutId, viewGroup, false);
        view.setFocusable(true);
        return new SubRedditAdapterViewHolder(view);
    }

    public int getCount() {
        return 0;
    }

    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        SubRedditHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new SubRedditHolder();
            //holder.imgIcon = (ImageView) row.findViewById(R.id.grid_item_image);

            row.setTag(holder);
        } else {
            holder = (SubRedditHolder) row.getTag();
        }


        return row;
    }

    static class SubRedditHolder {
        ImageView imgIcon;
    }

    public void setUseTodayLayout(boolean useTodayLayout) {
        mUseTodayLayout = useTodayLayout;
    }

    @Override
    public int getItemCount() {
        if ( null == mCursor ) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
        mEmptyView.setVisibility(getItemCount() == 0 ? View.VISIBLE : View.GONE);
    }

    public static String validateTitle(String input){
        StringBuilder temp = new StringBuilder(input);

        if(temp.length()> 64){
            temp.delete(64,temp.length()-1);
        }

        return temp.toString();
    }

    public static String validateTitleWidget(String input){
        StringBuilder temp = new StringBuilder(input);

        if(temp.length()> 32){
            temp.delete(32,temp.length()-1);
        }

        return temp.toString();
    }
}
