package com.havistudio.myredditcp;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kostas on 02/12/2016.
 */

public class MyAdapter extends ArrayAdapter<AdapterObject> {
    private final Context context;
    private final int resourceID;
    private static final String TAG = "MyAdapter";

    public MyAdapter(Context context, int resource, ArrayList<AdapterObject> bah) {
        super(context, resource, bah);
        this.context = context;
        this.resourceID = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(resourceID, parent, false);

        AdapterObject temp = getItem(position);

        LinearLayout layout = (LinearLayout) rowView.findViewById(R.id.root_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int left = getPixelValue(context, context.getResources().getDimension(R.dimen.margin_top_list_item)) * Integer.parseInt(temp.getPadding()+ "");
        params.setMargins(left, 0, 0, 0);
        layout.setLayoutParams(params);

        LinearLayout layout2 = (LinearLayout) rowView.findViewById(R.id.testlayout);
        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int left2 = getPixelValue(context, context.getResources().getDimension(R.dimen.margin_top_list_item));
        params2.setMargins(left, 0, 0, 0);
        layout2.setLayoutParams(params2);

        TextView commentContentTextView = (TextView) rowView.findViewById(R.id.comment_content);
        commentContentTextView.setText(temp.getContent());

        TextView commentScoreTextView = (TextView) rowView.findViewById(R.id.comment_score);
        commentScoreTextView.setText("Score:"+temp.getScore());

        TextView commentUpsTextView = (TextView) rowView.findViewById(R.id.comment_ups);
        commentUpsTextView.setText("Ups:"+temp.getUps());

        TextView commentDownsTextView = (TextView) rowView.findViewById(R.id.comment_downs);
        commentDownsTextView.setText("Downs:"+temp.getDowns());

        //Log.i(TAG,position+"_"+getItem(position));

        return rowView;
    }

    public static int getPixelValue(Context context, float dimenId) {
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }
}