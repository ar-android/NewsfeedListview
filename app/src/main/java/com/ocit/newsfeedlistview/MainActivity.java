package com.ocit.newsfeedlistview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * created by ahmad rosid
 */

public class MainActivity extends AppCompatActivity {

    private String URL;
    private ArrayList<FeedModel> data;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.lv);

        URL = "http://api.androidhive.info/feed/feed.json";
        getData();
    }

    public void getData() {
        JsonObjectRequest request = new JsonObjectRequest(URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        ResponseData mResponseData = gson.fromJson(response.toString(), ResponseData.class);
                        data = mResponseData.getFeed();

                        AdapterListview adapter = new AdapterListview(getApplicationContext(), data, MainActivity.this);
                        listview.setAdapter(adapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("eror volley", error.toString());
                    }
                });
        AplicationContext.getInstance().getRequestQueue().add(request);

    }

    private class ResponseData {
        public ArrayList<FeedModel> feed;

        public ArrayList<FeedModel> getFeed() {
            return feed;
        }
    }
}