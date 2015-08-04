package com.example.elrid.tuanjupku;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.elrid.tuanjupku.app.MyApplication;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class GetMainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener,MenuAdapter.MenuListener{

    private String TAG = GetMainActivity.class.getSimpleName();

    private String URL_TOP_250 = "http://api.androidhive.info/json/imdb_top_250.php?offset=";
    private static final String STATE_ACTIVE_POSITION =
            "com.example.elrid.tuanjupku.GetMainActivity.activePosition";
    private MenuDrawer mMenuDrawer;
    private MenuAdapter mAdapter;
    private ListView mList;

    private int mActivePosition = 0;

    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private SwipeListAdapter adapter;
    private List<Movie> movieList;

    // initially offset will be 0, later will be updated while parsing the json
    private int offSet = 0;

    private File cache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        System.out.printf("the program is now here ===========================!\n");
        if (savedInstanceState != null) {
            mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
        }
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("团聚PKU");
        /********************************Cache*****************************************/
        cache = new File(Environment.getExternalStorageDirectory(), "TuanJuPKUache");
        if(!cache.exists()){
            cache.mkdirs();
        }
        /*************************************************************************/
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, getDrawerPosition(), getDragMode());
        //setContentView(R.layout.getactivity_main);
        mMenuDrawer.setContentView(R.layout.getactivity_main);
        mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        mMenuDrawer.setSlideDrawable(R.drawable.ic_drawer);
        mMenuDrawer.setDrawerIndicatorEnabled(true);

        List<Object> items = new ArrayList<Object>();
        items.add(new Category("我的"));
        items.add(new Item("我关注的社团", R.drawable.ic_action_refresh_dark));
        items.add(new Item("我加入的社团", R.drawable.ic_action_select_all_dark));
        items.add(new Category("账户"));
        items.add(new Item("我的资料", R.drawable.ic_action_refresh_dark));
        items.add(new Item("切换账户", R.drawable.ic_action_select_all_dark));
        items.add(new Category("系统"));
        items.add(new Item("设置", R.drawable.ic_action_refresh_dark));
        items.add(new Item("退出", R.drawable.ic_action_select_all_dark));

        mList = new ListView(this);

        mAdapter = new MenuAdapter(this, items);
        mAdapter.setListener(this);
        mAdapter.setActivePosition(mActivePosition);

        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mItemClickListener);

        mMenuDrawer.setMenuView(mList);

        listView = (ListView) findViewById(R.id.listView);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);

        movieList = new ArrayList<>();
        adapter = new SwipeListAdapter(this, movieList, cache);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                InfoRetriever.curNews = position;

                Intent intent = new Intent();
                intent.setClass(GetMainActivity.this, WebActivity.class);
                GetMainActivity.this.startActivity(intent);

            }
        });

        swipeRefreshLayout.setOnRefreshListener(this);

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
    private void fetchMovies() {

        // showing refresh animation before making http call
        swipeRefreshLayout.setRefreshing(true);

        // appending offset to url
        //String url = URL_TOP_250 + offSet;
        int unionID = InfoRetriever.unionList.get(InfoRetriever.curUnion).unionID;
        String url = "http://123.57.204.246:8002/news_android.php" + "?id=8 ";
        System.out.println(url);
        // Volley's json array request object
        JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, response.toString());
                        System.out.println(response.toString());
                        //if (response.length() > 0) {

                            // looping through json and adding to movies list
                            /*for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject movieObj = response.getJSONObject(i);

                                    int rank = movieObj.getInt("rank");
                                    String title = movieObj.getString("title");

                                    Movie m = new Movie(rank, title);

                                    movieList.add(0, m);

                                    // updating offset value to highest value
                                    if (rank >= offSet)
                                        offSet = rank;

                                } catch (JSONException e) {
                                    Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }*/
                        if (response.length() > 0) {
                            try {
                                List<News> newsList = InfoRetriever.getNewsList(response);

                                //String jsonString = "[{\"id\":2,\"URL\":\"http:\\/\\/course.pku.edu.cn\",\"title\":\"actor\",\"abstract\":\"hehe\",\"iconURL\":\"http:\\/\\/pic.nipic.com\\/2007-11-09\\/200711912453162_2.jpg\",\"thedate\":\"2015-6-3\"},{\"id\":3,\"URL\":\"http:\\/\\/pan.baidu.com\",\"title\":\"actress\",\"abstract\":\"sou\",\"iconURL\":\"http:\\/\\/pic.nipic.com\\/2007-11-09\\/200711912453162_2.jpg\",\"thedate\":\"2013-2-3\"}]";
                                //JSONArray json= new JSONArray(jsonString);
                                //List<News> newsList = InfoRetriever.getNewsList(json);
                                movieList.clear();
                                for (News news : newsList) {
                                    movieList.add(new Movie(news.newsTitle, news.newsAbstract));
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                            }

                            adapter.notifyDataSetChanged();
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }   */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                mMenuDrawer.toggleMenu();
                return true;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    protected int getDragMode() {
        return MenuDrawer.MENU_DRAG_CONTENT;
    }


    protected void onMenuItemClicked(int position, Item item) {
        //mMenuDrawer.closeMenu();
    }

    protected Position getDrawerPosition() {
        return Position.START;
    }

    @Override
    public void onBackPressed() {
        final int drawerState = mMenuDrawer.getDrawerState();
        if (drawerState == MenuDrawer.STATE_OPEN || drawerState == MenuDrawer.STATE_OPENING) {
            mMenuDrawer.closeMenu();
            return;
        }

        super.onBackPressed();
    }

    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            mActivePosition = position;
            mMenuDrawer.setActiveView(view, position);
            mAdapter.setActivePosition(position);
            onMenuItemClicked(position, (Item) mAdapter.getItem(position));


        }
    };

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVE_POSITION, mActivePosition);
    }

    @Override
    public void onActiveViewChanged(View v) {
        mMenuDrawer.setActiveView(v, mActivePosition);
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
