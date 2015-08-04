package com.example.elrid.tuanjupku;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.example.elrid.tuanjupku.app.MyApplication;
import com.example.elrid.tuanjupku.expandablelistitems.ExpandableListItemActivity;
import com.example.elrid.tuanjupku.googlecards.GoogleCardsActivity;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import it.moondroid.coverflow.components.ui.containers.FeatureCoverFlow;

public class CoverFlowActivity extends Activity implements MenuAdapter.MenuListener,View.OnClickListener {

    private FeatureCoverFlow mCoverFlow;
    private CoverFlowAdapter mAdapter;
    private List<GameEntity> mData = new ArrayList<>(0);
    private TextSwitcher mTitle;
    List<Object> items = new ArrayList<Object>();
    private static final String STATE_ACTIVE_POSITION =
            "com.example.elrid.tuanjupku.CoverFlowActivity.activePosition";
    private MenuDrawer mMenuDrawer;
    private InfoListAdapter menuAdapter;
    private ListView mList;
 public String unionName;
    private int mActivePosition = 0;

    private RelativeLayout course_layout;
    private RelativeLayout found_layout;
    private RelativeLayout settings_layout;
    //定义底部导航栏中的ImageView与TextView
    private ImageView course_image;
    private ImageView found_image;
    private ImageView settings_image;
    private TextView course_text;
    private TextView settings_text;
    private TextView found_text;
    //定义要用的颜色值
    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int blue =0xFF0AB2FB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("coverflow", "choice");
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_coverflow);
        //android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        //actionBar.setTitle("团聚PKU");

        if (savedInstanceState != null) {
            mActivePosition = savedInstanceState.getInt(STATE_ACTIVE_POSITION);
        }
        if(InfoRetriever.firsttime) {
            try {
                InfoRetriever.getUnionList();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            InfoRetriever.firsttime = false;
        }
        mMenuDrawer = MenuDrawer.attach(this, MenuDrawer.Type.BEHIND, getDrawerPosition(), getDragMode());

        mMenuDrawer.setContentView(R.layout.activity_coverflow);
        //mMenuDrawer.setTouchMode(MenuDrawer.TOUCH_MODE_FULLSCREEN);
        mMenuDrawer.setSlideDrawable(R.drawable.ic_drawer);
        mMenuDrawer.setDrawerIndicatorEnabled(true);

        mList = new ListView(this);

        menuAdapter = new InfoListAdapter(this, items,null);


        mList.setAdapter(menuAdapter);


        mMenuDrawer.setMenuView(mList);

        if(InfoRetriever.messageList != null)
            InfoRetriever.messageList.clear();
        items.clear();
        items.add(new Category("消息列表"));
        for(Union union:InfoRetriever.myUnionList) {
            String url = "http://123.57.204.246:8002/info_android.php?id=" + union.unionID;
            System.out.println(url);
            JsonArrayRequest req = new JsonArrayRequest(url,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            //Log.d(TAG, response.toString());
                            System.out.println(response.toString());
                            if (response.length() > 0) {
                                try {
                                    InfoRetriever.getMessageList(response);
                                    items.clear();
                                    items.add(new Category("消息列表"));
                                    for (int i = 0; i < InfoRetriever.messageList.size(); i++)
                                        items.add(new Movie(InfoRetriever.messageList.get(i).unionNmae, (InfoRetriever.messageList.get(i).message)));
                                        menuAdapter.notifyDataSetChanged();
                                } catch (JSONException e) {
                                    //Log.e(TAG, "JSON Parsing error: " + e.getMessage());
                                }
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Log.e(TAG, "Server Error: " + error.getMessage());

                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();

                    // stopping swipe refresh
                }
            });
            MyApplication.getInstance().addToRequestQueue(req);
        }




        int[] unionList = new int[InfoRetriever.myUnionList.size()];
        for(int i = 0; i < InfoRetriever.myUnionList.size(); i++)
            unionList[i] = InfoRetriever.myUnionList.get(i).unionID;

        for (int i=0; i<unionList.length; ++i){
            switch (unionList[i]){
                case 1:
                    mData.add(new GameEntity(R.drawable.image_1, R.string.title1));
                    break;
                case 2:
                    mData.add(new GameEntity(R.drawable.image_2, R.string.title2));
                    break;
                case 3:
                    mData.add(new GameEntity(R.drawable.image_3, R.string.title3));
                    break;
                case 4:
                    mData.add(new GameEntity(R.drawable.image_4, R.string.title4));
                    break;
                case 5:
                    mData.add(new GameEntity(R.drawable.image_5, R.string.title5));
                    break;
                case 6:
                    mData.add(new GameEntity(R.drawable.image_6, R.string.title6));
                    break;
                case 7:
                    mData.add(new GameEntity(R.drawable.image_7, R.string.title7));
                    break;
                case 8:
                    mData.add(new GameEntity(R.drawable.image_8, R.string.title8));
                    break;
                case 9:
                    mData.add(new GameEntity(R.drawable.image_9, R.string.title9));
                    break;
                case 10:
                    mData.add(new GameEntity(R.drawable.image_10, R.string.title10));
                    break;
                case 11:
                    mData.add(new GameEntity(R.drawable.image_11, R.string.title11));
                    break;
                case 12:
                    mData.add(new GameEntity(R.drawable.image_12, R.string.title12));
                    break;
            }
        }

        mTitle = (TextSwitcher) findViewById(R.id.title);
        mTitle.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                LayoutInflater inflater = LayoutInflater.from(CoverFlowActivity.this);
                TextView textView = (TextView) inflater.inflate(R.layout.item_title, null);
                return textView;
            }
        });
        Animation in = AnimationUtils.loadAnimation(this, R.anim.slide_in_top);
        Animation out = AnimationUtils.loadAnimation(this, R.anim.slide_out_bottom);
        mTitle.setInAnimation(in);
        mTitle.setOutAnimation(out);

        mAdapter = new CoverFlowAdapter(this);
        mAdapter.setData(mData);
        mCoverFlow = (FeatureCoverFlow) findViewById(R.id.coverflow);
        mCoverFlow.setAdapter(mAdapter);

        mCoverFlow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                /*Toast.makeText(CoverFlowActivity.this,
                        getResources().getString(mData.get(position).titleResId),
                        Toast.LENGTH_SHORT).show();*/

                for (int i = 0; i < InfoRetriever.unionList.size(); i++) {
                    if (InfoRetriever.unionList.get(i).unionID == InfoRetriever.myUnionList.get(position).unionID)
                        InfoRetriever.curUnion = i;
                    System.out.println("CurUnion111: " + InfoRetriever.myUnionList.get(position).unionID + "!!!!!!!!!!!!!!!\n!!!!!!!!!!!!\n!!!!!!!");
                }

                Intent intent = new Intent();
                intent.setClass(CoverFlowActivity.this, GoogleCardsActivity.class);
                CoverFlowActivity.this.startActivity(intent);
            }

        });

        mCoverFlow.setOnScrollPositionListener(new FeatureCoverFlow.OnScrollPositionListener() {
            @Override
            public void onScrolledToPosition(int position) {
                mTitle.setText(getResources().getString(mData.get(position).titleResId));
            }

            @Override
            public void onScrolling() {
                mTitle.setText("");
            }
        });
        initViews();
    }


    public void initViews()
    {
        course_image = (ImageView) findViewById(R.id.course_image);
        found_image = (ImageView) findViewById(R.id.found_image);
        settings_image = (ImageView) findViewById(R.id.setting_image);
        course_text = (TextView) findViewById(R.id.course_text);
        found_text = (TextView) findViewById(R.id.found_text);
        settings_text = (TextView) findViewById(R.id.setting_text);
        course_layout = (RelativeLayout) findViewById(R.id.course_layout);
        found_layout = (RelativeLayout) findViewById(R.id.found_layout);
        settings_layout = (RelativeLayout) findViewById(R.id.setting_layout);
        course_layout.setOnClickListener(this);
        found_layout.setOnClickListener(this);
        settings_layout.setOnClickListener(this);
        found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
        found_text.setTextColor(blue);
        found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
    }
   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coverflow_activity, menu);
        return true;
    }*/

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.course_layout:
                setChioceItem(0);
                break;
            case R.id.found_layout:
                setChioceItem(1);
                break;
            case R.id.setting_layout:
                setChioceItem(2);
                break;
            default:
                break;
        }

    }

    public void setChioceItem(int index)
    {
        //重置选项+隐藏所有Fragment
        // FragmentTransaction transaction = fManager.beginTransaction();
        clearChioce();
        //hideFragments(transaction);
        switch (index) {
            case 0:
                course_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
                course_text.setTextColor(blue);
                course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);

                Intent intent = new Intent();
                intent.setClass(CoverFlowActivity.this, ExpandableListItemActivity.class);
                CoverFlowActivity.this.startActivity(intent);
                /*if (fg1 == null) {
                    // 如果fg1为空，则创建一个并添加到界面上
                    fg1 = new Fragment1();
                    transaction.add(R.id.content, fg1);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(fg1);
                }*/
                break;

            case 1:
                found_image.setImageResource(R.drawable.ic_tabbar_found_pressed);
                found_text.setTextColor(blue);
                found_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
                /*if (fg2 == null) {
                    // 如果fg1为空，则创建一个并添加到界面上
                    fg2 = new Fragment2();
                    transaction.add(R.id.content, fg2);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(fg2);
                }*/
                break;

            case 2:
                settings_image.setImageResource(R.drawable.ic_tabbar_settings_pressed);
                settings_text.setTextColor(blue);
                settings_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
                /*if (fg3 == null) {
                    // 如果fg1为空，则创建一个并添加到界面上
                    fg3 = new Fragment3();
                    transaction.add(R.id.content, fg3);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(fg3);
                }*/
                break;
        }
        //transaction.commit();
    }


    //定义一个重置所有选项的方法
    public void clearChioce()
    {
        course_image.setImageResource(R.drawable.ic_tabbar_course_normal);
        course_layout.setBackgroundColor(whirt);
        course_text.setTextColor(gray);
        found_image.setImageResource(R.drawable.ic_tabbar_found_normal);
        found_layout.setBackgroundColor(whirt);
        found_text.setTextColor(gray);
        settings_image.setImageResource(R.drawable.ic_tabbar_settings_normal);
        settings_layout.setBackgroundColor(whirt);
        settings_text.setTextColor(gray);
    }
}
