package com.example.elrid.tuanjupku;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class SwipeListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Movie> movieList;
    File cache;

    public SwipeListAdapter(Activity activity, List<Movie> movieList, File f) {
        this.activity = activity;
        this.movieList = movieList;
        cache = f;
    }



    @Override
    public int getCount() {
        return movieList.size();
    }

    @Override
    public Object getItem(int location) {
        return movieList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView title = (TextView) convertView.findViewById(R.id.ItemTitle);
        TextView content = (TextView) convertView.findViewById(R.id.ItemContent);
        ImageView imgView = (ImageView) convertView.findViewById(R.id.ItemImage);

        title.setText(String.valueOf(movieList.get(position).title));
        content.setText(movieList.get(position).content);
        //imgView.setImageResource(R.drawable.image_1);
        asyncloadImage(imgView, position, InfoRetriever.unionList.get(InfoRetriever.curUnion).newsList.get(position).iconURL);

        return convertView;
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
}