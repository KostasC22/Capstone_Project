package com.havistudio.myredditcp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class SubRedditDetailActivity extends AppCompatActivity {

    private String mIdSubreddit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_reddit_detail);
        if (savedInstanceState == null) {

            SubRedditDetailActivityFragment details = new SubRedditDetailActivityFragment();
            details.setArguments(getIntent().getExtras());
            Log.i("Main3ActivityFragment", "onClick2: "+getIntent().getExtras().getLong("subredditId"));

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getIntent().getExtras().getString("subreddittitle"));
        Log.i("Main3ActivityFragment",getIntent().getExtras().getString("subredditid"));
        mIdSubreddit = getIntent().getExtras().getString("subredditid");
        setSupportActionBar(toolbar);
        // Provice the back button on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Sharing SubReddit URL");
                i.putExtra(Intent.EXTRA_TEXT, "https://www.reddit.com/r/pics/comments/"+mIdSubreddit);
                startActivity(Intent.createChooser(i, "Share SubReddit URL"));
            }
        });
    }

}
