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

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elrid.tuanjupku.InfoRetriever;
import com.example.elrid.tuanjupku.MD5;
import com.example.elrid.tuanjupku.R;
import com.example.elrid.tuanjupku.util.BitmapCache;
import com.nhaarman.listviewanimations.ArrayAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GoogleCardsAdapter extends ArrayAdapter<Movie> {

    private final Context mContext;
    private final BitmapCache mMemoryCache;
    private List<Movie> movieList;
    File cache;

    GoogleCardsAdapter(final Context context, File f) {
        mContext = context;
        mMemoryCache = new BitmapCache();
        cache = f;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_googlecards_card, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) view.findViewById(R.id.activity_googlecards_card_textview);
            view.setTag(viewHolder);

            viewHolder.imageView = (ImageView) view.findViewById(R.id.activity_googlecards_card_imageview);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textView.setText(getItem(position).title);
        /*
        if(InfoRetriever.unionList == null)
            System.out.println("11111111111111111111111111111111111111");
        if(InfoRetriever.unionList.get(InfoRetriever.curUnion) == null)
            System.out.println("2222222222222222222222222222222222222222");
        if(InfoRetriever.unionList.get(InfoRetriever.curUnion).newsList == null)
            System.out.println("333333333333333333333333333333333333333333");
        if(InfoRetriever.unionList.get(InfoRetriever.curUnion).newsList.get(position) == null)
            System.out.println("444444444444444444444444444444444444444444");
        */
        System.out.println("the url is " + InfoRetriever.unionList.get(InfoRetriever.curUnion).newsList.get(position).iconURL);
        asyncloadImage(viewHolder.imageView, position, InfoRetriever.unionList.get(InfoRetriever.curUnion).newsList.get(position).iconURL);

        return view;
    }

    private void asyncloadImage(ImageView imgView, int position, String path) {
        AsyncImageTask task = new AsyncImageTask(imgView, position);
        task.execute(path);
    }

    private final class AsyncImageTask extends AsyncTask<String, Integer, Uri> {

        private ImageView imgView;
        private int position;

        public AsyncImageTask(ImageView imgv, int pos) {
            this.imgView = imgv;
            position = pos;
        }

        // 后台运行的子线程子线程
        @Override
        protected Uri doInBackground(String... params) {
            try {
                return getImageURI(params[0], cache, position);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // 这个放在在ui线程中执行
        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result);
            // 完成图片的绑定
            if (imgView != null && result != null) {
                imgView.setImageURI(result);
            }
        }

        public Uri getImageURI(String path, File cache, int position) throws Exception {
            String name = MD5.getMD5(path) + path.substring(path.lastIndexOf("."));

            File file = new File(cache, name);
            // 如果图片存在本地缓存目录，则不去服务器下载
            if (file.exists()) {
                return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI
            } else {
                // 从网络上获取图片
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                if (conn.getResponseCode() == 200) {

                    InputStream is = conn.getInputStream();
                    FileOutputStream fos = new FileOutputStream(file);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    is.close();
                    fos.close();
                    // 返回一个URI对象
                    return Uri.fromFile(file);
                }
            }
            return null;
        }
    }
    /*
    private void setImageView(final ViewHolder viewHolder, final int position) {
        int imageResId;
        switch (getItem(position).id % 5) {
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
        viewHolder.imageView.setImageBitmap(bitmap);

    }


    private void addBitmapToMemoryCache(final int key, final Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    private Bitmap getBitmapFromMemCache(final int key) {
        return mMemoryCache.get(key);
    }

    */
    @SuppressWarnings({"PackageVisibleField", "InstanceVariableNamingConvention"})
    private static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}