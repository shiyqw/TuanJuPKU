/*
 * Copyright 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.elrid.tuanjupku.googlecards;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.elrid.tuanjupku.BaseActivity;
import com.example.elrid.tuanjupku.InfoRetriever;
import com.example.elrid.tuanjupku.Message;
import com.example.elrid.tuanjupku.News;
import com.example.elrid.tuanjupku.R;
import com.example.elrid.tuanjupku.WebActivity;
import com.example.elrid.tuanjupku.app.MyApplication;
import com.nhaarman.listviewanimations.appearance.simple.SwingBottomInAnimationAdapter;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.OnDismissCallback;
import com.nhaarman.listviewanimations.itemmanipulation.swipedismiss.SwipeDismissAdapter;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.List;


public class GoogleCardsActivity extends BaseActivity implements OnDismissCallback,SwipeRefreshLayout.OnRefreshListener {

    private static final int INITIAL_DELAY_MILLIS = 300;

    private GoogleCardsAdapter mGoogleCardsAdapter;

    //modify here
    private SwipeRefreshLayout swipeRefreshLayout;
    private String URL_TOP_250 = "http://api.androidhive.info/json/imdb_top_250.php?offset=";
    private int offSet = 0;
    private String TAG = GoogleCardsActivity.class.getSimpleName();
    private List<Movie> movieList;
    private SwingBottomInAnimationAdapter swingBottomInAnimationAdapter;
    int irank = 0;
    private File cache;
    //=============

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlecards);
        /********************************Cache*****************************************/
        cache = new File(Environment.getExternalStorageDirectory(), "TuanJuPKUache");
        if(!cache.exists()){
            cache.mkdirs();
        }
        /*************************************************************************/
        ListView listView = (ListView) findViewById(R.id.activity_googlecards_listview);


        mGoogleCardsAdapter = new GoogleCardsAdapter(this,cache);
        SwingBottomInAnimationAdapter swingBottomInAnimationAdapter = new SwingBottomInAnimationAdapter(new SwipeDismissAdapter(mGoogleCardsAdapter, this));
        swingBottomInAnimationAdapter.setAbsListView(listView);

        assert swingBottomInAnimationAdapter.getViewAnimator() != null;
        swingBottomInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        listView.setAdapter(swingBottomInAnimationAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoRetriever.curNews = position;

                Intent intent = new Intent();
                intent.setClass(GoogleCardsActivity.this, WebActivity.class);
                GoogleCardsActivity.this.startActivity(intent);
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setClickable(true);


        /**
         * Showing Swipe Refresh animation on activity create
         * As animation won't start on onCreate, post runnable is used
         */
        swipeRefreshLayout.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        swipeRefreshLayout.setRefreshing(true);

                                        fetchMovies();
                                    }
                                }
        );

    }
        /**
         * This method is called when swipe refresh is pulled down
         */
        @Override
        public void onRefresh() {
            fetchMovies();
        }

        /**
         * Fetching movies json by making http call
         */
    /*private void fetchMovies() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        String url = URL_TOP_250 + offSet;

        Movie m = new Movie(irank++, "hehe");

        mGoogleCardsAdapter.add(0, m);
        mGoogleCardsAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);



        // Volley's json array request object
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());

                        if (response.length() > 0) {

                            // looping through json and adding to movies list
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject movieObj = response.getJSONObject(i);

                                    int rank = movieObj.getInt("rank");
                                    String title = movieObj.getString("title");

                                    Movie m = new Movie(rank, title);

                                    mGoogleCardsAdapter.add(0, m);

                                    // updating offset value to highest value
                                    if (rank >= offSet)
                                        offSet = rank;

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }

                            mGoogleCardsAdapter.notifyDataSetChanged();
                        }

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
//        MyApplication.getInstance().addToRequestQueue(req);
    }*/


    /**
     * Fetching movies json by making http call
     */
    private void fetchMovies() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        //String url = URL_TOP_250 + offSet;
        int unionID = InfoRetriever.unionList.get(InfoRetriever.curUnion).unionID;
        String url = "http://123.57.204.246:8002/news_android.php?id=" + unionID;
        System.out.println(url);
        // Volley's json array request object
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        System.out.println(response.toString());
                        if (response.length() > 0) {
                            try {
                                List<News> newsList = InfoRetriever.getNewsList(response);

                                //String jsonString = "[{\"id\":2,\"URL\":\"http:\\/\\/course.pku.edu.cn\",\"title\":\"actor\",\"abstract\":\"hehe\",\"iconURL\":\"http:\\/\\/pic.nipic.com\\/2007-11-09\\/200711912453162_2.jpg\",\"thedate\":\"2015-6-3\"},{\"id\":3,\"URL\":\"http:\\/\\/pan.baidu.com\",\"title\":\"actress\",\"abstract\":\"sou\",\"iconURL\":\"http:\\/\\/pic.nipic.com\\/2007-11-09\\/200711912453162_2.jpg\",\"thedate\":\"2013-2-3\"}]";
                                //JSONArray json= new JSONArray(jsonString);
                                //List<News> newsList = InfoRetriever.getNewsList(json);
                                //movieList.clear();
                                mGoogleCardsAdapter.clear();
                                for (News news : newsList) {
                                    Movie m = new Movie(0, news.newsAbstract);
                                    mGoogleCardsAdapter.add(0, m);

                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }

                            mGoogleCardsAdapter.notifyDataSetChanged();
                        }
                        // }

                        // stopping swipe refresh
                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Server Error: " + error.getMessage());

                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                // stopping swipe refresh
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        // Adding request to request queue
        MyApplication.getInstance().addToRequestQueue(req);
    }





    @Override
    public void onDismiss(@NonNull final ViewGroup listView, @NonNull final int[] reverseSortedPositions) {
        for (int position : reverseSortedPositions) {
            //mGoogleCardsAdapter.remove(position);
        }
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        //清空缓存
        File[] files = cache.listFiles();
        if(files != null) {
            for (File file : files) {
                file.delete();
            }
        }
        cache.delete();
    }
}
