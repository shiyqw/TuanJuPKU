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
package  com.example.elrid.tuanjupku.expandablelistitems;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elrid.tuanjupku.CoverFlowActivity;
import com.example.elrid.tuanjupku.InfoRetriever;
import  com.example.elrid.tuanjupku.MyListActivity;
import  com.example.elrid.tuanjupku.R;
import com.example.elrid.tuanjupku.Union;
import com.example.elrid.tuanjupku.googlecards.GoogleCardsActivity;
import com.nhaarman.listviewanimations.appearance.simple.AlphaInAnimationAdapter;

import java.util.List;

public class ExpandableListItemActivity extends MyListActivity implements View.OnClickListener {

    private static final int INITIAL_DELAY_MILLIS = 500;
    private MyExpandableListItemAdapter mExpandableListItemAdapter;

    private boolean mLimited;


    private int selectIndex = -1;

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
    protected void onCreate(final Bundle savedInstanceState) {
        Log.i("expand", "choice");
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_expandablelistitem_card);

        mExpandableListItemAdapter = new MyExpandableListItemAdapter(this);
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mExpandableListItemAdapter);
        alphaInAnimationAdapter.setAbsListView(getListView());

        assert alphaInAnimationAdapter.getViewAnimator() != null;
        alphaInAnimationAdapter.getViewAnimator().setInitialDelayMillis(INITIAL_DELAY_MILLIS);

        getListView().setAdapter(alphaInAnimationAdapter);

        Toast.makeText(this, R.string.explainexpand, Toast.LENGTH_LONG).show();

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
        course_image.setImageResource(R.drawable.ic_tabbar_course_pressed);
        course_text.setTextColor(blue);
        course_layout.setBackgroundResource(R.drawable.ic_tabbar_bg_click);
    }


    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expandablelistitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_expandable_limit:
                mLimited = !mLimited;
                item.setChecked(mLimited);
                mExpandableListItemAdapter.setLimit(mLimited ? 2 : 0);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onLove(final View view) {
        Log.i("onlove", "click");

        Button button = (Button) view.findViewById(R.id.expand_left_button);

        //button.setText("Clicked" + button.getTag());
        int position = (int)button.getTag();
        Union curUnion = InfoRetriever.unionList.get(position);
        Drawable drawable;
        boolean flag = false;
        Union theUnion = null;
        for(Union union:InfoRetriever.myUnionList) {
            if(union.unionID == curUnion.unionID)
            {
                flag = true;
                theUnion = union;
                break;
            }
        }
        if(flag) {
            if(curUnion.unionID == 8)
                return;
                drawable = getResources().getDrawable(R.drawable.heartgrey);
                InfoRetriever.myUnionList.remove(theUnion);

        }
        else {
            drawable = getResources().getDrawable(R.drawable.heartred);
            InfoRetriever.myUnionList.add(curUnion);
        }

        drawable.setBounds(0, 0, 48, 48);
        button.setCompoundDrawables(drawable, null, null, null);
        Log.i("onlove", "click");
        InfoRetriever.xmlSerialize();
        return ;
    }

    public void onEnterNewsList(final View view) {
            Log.i("onEnter", "click");

            Button button = (Button) view.findViewById(R.id.expand_right_button);

            InfoRetriever.curUnion = (int)button.getTag();
            Intent intent = new Intent();
            intent.setClass(ExpandableListItemActivity.this, GoogleCardsActivity.class);
            ExpandableListItemActivity.this.startActivity(intent);
            //button.setText("Clicked"+button.getTag());
            Log.i("onEnter", "click");
            return ;
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

                Intent intent = new Intent();
                intent.setClass(ExpandableListItemActivity.this, CoverFlowActivity.class);
                ExpandableListItemActivity.this.startActivity(intent);
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
