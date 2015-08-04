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

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.elrid.tuanjupku.InfoListAdapter;
import com.example.elrid.tuanjupku.InfoRetriever;
import com.example.elrid.tuanjupku.MyListAdapter;
import com.example.elrid.tuanjupku.R;
//import com.example.shiyqw.myapplication.util.BitmapCache;
import com.example.elrid.tuanjupku.Union;
import com.nhaarman.listviewanimations.itemmanipulation.expandablelistitem.ExpandableListItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyExpandableListItemAdapter extends ExpandableListItemAdapter<Integer> {

    private final Context mContext;
    //   private final BitmapCache mMemoryCache;
  //  protected List<Union> unionList;

    public List<Union> unionList;

    /**
     * Creates a new ExpandableListItemAdapter with the specified list, or an empty list if
     * items == null.
     */
    public MyExpandableListItemAdapter(final Context context) {
        super(context, R.layout.activity_expandablelistitem_card, R.id.activity_expandablelistitem_card_title, R.id.activity_expandablelistitem_card_content);
        mContext = context;
        // mMemoryCache = new BitmapCache();
       // initList();
        Log.i("eml", "init1");
        initList();
        for (int i = 0; i < unionList.size(); i++) {
            add(i);
        }
        Log.i("eml", "init1");
    }


    private void initList() {
        /*unionList = new ArrayList<Union>();
        Union union = new Union("union1", "url1", 0);
        unionList.add(union);
        union = new Union("union2", "url2", 1);
        unionList.add(union);
        union = new Union("union3", "url3", 2);
        unionList.add(union);
        union = new Union("union4", "url4", 3);
        unionList.add(union);
        union = new Union("union5", "url5", 4);
        unionList.add(union);*/
        unionList = InfoRetriever.unionList;

    }




    @NonNull
    @Override
    public View getTitleView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        TextView tv = (TextView) convertView;
        if (tv == null) {
            tv = new TextView(mContext);
        }
        tv.setTextColor(Color.BLACK);
        tv.setTextSize(25);
        tv.setText(unionList.get((int) getItem(position)).unionName);
        return tv;
    }


    @NonNull
    @Override
    public View getContentView(final int position, final View convertView, @NonNull final ViewGroup parent) {
        /*
        ImageView imageView = (ImageView) convertView;
        if (imageView == null) {
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        int imageResId;
        switch (getItem(position) % 5) {
            case 0:
                imageResId = R.drawable.img_nature1;
                break;
            case 1:
                imageResId = R.drawable.img_nature2;
                break;
            case 2:
                imageResId = R.drawable.img_nature3;
                break;
            case 3:
                imageResId = R.drawable.img_nature4;
                break;
            default:
                imageResId = R.drawable.img_nature5;
        }

        Bitmap bitmap = getBitmapFromMemCache(imageResId);
        if (bitmap == null) {
            bitmap = BitmapFactory.decodeResource(mContext.getResources(), imageResId);
            addBitmapToMemoryCache(imageResId, bitmap);
        }
        imageView.setImageBitmap(bitmap);

        return imageView;
        */
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.union_info, parent, false);
        }


        TextView info = (TextView) view.findViewById(R.id.union_info_item);
        Button left_button = (Button) view.findViewById(R.id.expand_left_button);
        left_button.setTag(position);
        //Drawable drawable= mContext.getResources().getDrawable(R.drawable.heartgrey);
        Union curUnion = InfoRetriever.unionList.get(position);
        Drawable drawable;
        boolean flag = false;
        for(Union union:InfoRetriever.myUnionList) {
            if(union.unionID == curUnion.unionID)
            {
                flag = true;
                break;
            }
        }
        if(flag) {
            drawable = mContext.getResources().getDrawable(R.drawable.heartred);
        }
        else {
            drawable = mContext.getResources().getDrawable(R.drawable.heartgrey);
        }

        drawable.setBounds(0, 0, 48, 48);
     //   left_button.setCompoundDrawables(drawable, null, null, null);
        left_button.setCompoundDrawables(drawable, null, null, null);
        Button right_button = (Button) view.findViewById(R.id.expand_right_button);
        right_button.setTag(position);
        drawable= mContext.getResources().getDrawable(R.drawable.news);

        drawable.setBounds(0, 0, 48, 48);
        right_button.setCompoundDrawables(drawable, null, null, null);

        //   info.setText("This is a simple info\n\n\n\n\n\n");
        info.setText(unionList.get(position).iconURL);
        Log.i("elv", ""+position);
        return view;
    }
/*
    private void addBitmapToMemoryCache(final int key, final Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
      //      mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(final int key) {
       // return mMemoryCache.get(key);
    }
    */

}