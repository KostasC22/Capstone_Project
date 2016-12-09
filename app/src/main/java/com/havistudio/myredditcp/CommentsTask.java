package com.havistudio.myredditcp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.havistudio.myredditcp.api.Child;
import com.havistudio.myredditcp.api.Comments;
import com.havistudio.myredditcp.api.DataTypeAdapterFactory;
import com.havistudio.myredditcp.api.MyApiRetrofit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kostas on 07/09/2016.
 */
public class CommentsTask extends AsyncTask<Void, List<Child>, List<Child>> {

    private static final String TAG = "CommentsTask";
    private String mSubRedditId;
    private boolean returnCommentsOnly = true;

    public CommentsTask(String mSubRedditId){
        this.mSubRedditId = mSubRedditId;
    }

    public CommentsTask(String mSubRedditId, boolean returnCommentsOnly){
        this.mSubRedditId = mSubRedditId;
        this.returnCommentsOnly = returnCommentsOnly;
    }

    @Override
    protected List<Child> doInBackground(Void... tempo) {

        List<Child> userComments = new ArrayList<Child>();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new DataTypeAdapterFactory())
                .create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        MyApiRetrofit service = retrofit.create(MyApiRetrofit.class);
        Call<Comments[]> temp = service.getCommentsBySubredditId(mSubRedditId);

        try {
            Response<Comments[]> mTempo = temp.execute();
            Comments[] mExample = mTempo.body();
            Log.i(TAG, "doInBackground: "+mExample);
            for (Comments temp2: mExample) {
                //Log.i(TAG, "doInBackground: "+temp2);
            }
            if(returnCommentsOnly){
                userComments = mExample[1].getGeneralData().getChildren();
                displayComments(userComments, 4);
            } else {
                userComments = mExample[0].getGeneralData().getChildren();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return userComments;
    }

    private void displayComments(List<Child> Comments, int tabs){
        if(Comments != null){
            Log.i(TAG, "Comments:"+Comments.size());
            for (Child tempChild : Comments) {
                Log.i(TAG, makeTabs(tabs)+""+tempChild.getData().getId());
                if(tempChild.getData().getReplies()!= null) {
                    displayComments(tempChild.getData().getReplies().getData().getChildren(), tabs + 4);
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
}
