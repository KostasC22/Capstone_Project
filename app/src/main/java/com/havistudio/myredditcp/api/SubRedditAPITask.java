package com.havistudio.myredditcp.api;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.havistudio.myredditcp.SubRedditAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kostas on 07/09/2016.
 */
public class SubRedditAPITask extends AsyncTask<Void, Void, List<Child>> {

    private SubRedditAdapter mAdapter;
    private Context mContext;
    private static final String TAG = "TestAPITask";

    public SubRedditAPITask(){

    }

    public SubRedditAPITask(SubRedditAdapter mAdapter, Context mContext){
        this.mAdapter = mAdapter;
        this.mContext = mContext;
    }

    @Override
    protected List<Child> doInBackground(Void... voids) {

        List<Child> result = new ArrayList<Child>();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new DataTypeAdapterFactory())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        MyApiRetrofit service = retrofit.create(MyApiRetrofit.class);

        Map<String, String> temp2 = new HashMap<String, String>();
        temp2.put("limit", "10");

        Call<Example> call2 = service.getNewSubreddits(temp2);
        try {
            Response<Example> tempo = call2.execute();

            result = tempo.body().getGeneralData().getChildren();
            Log.i(TAG,"size:"+result.size());

            for(Child temp: result){
                Log.i(TAG, "doInBackground: "+temp.getData().toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
