package com.xushuzhan.redrockexam.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.Utils.FileUtils;
import com.xushuzhan.redrockexam.Utils.ImageDownloader;
import com.xushuzhan.redrockexam.data.Songs;


/**
 * Created by xushuzhan on 2016/5/14.
 */
public class HotMusicAdapter extends RecyclerView.Adapter<HotMusicAdapter.MyViewHolder> implements View.OnClickListener{
    public static final String TAG = "HotMusicAdapter";
    String pageTAG ;
    Context adaptercontext;
    public static final int SHOW_IMAGE=0;
    public static final int SHOW_TOAST=1;

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
//用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Songs) v.getTag());
        }
    }

    //1.定义一个接口
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Songs clickedSongs);
    }
    //2.申明一个接口类型的变量
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    //3.创建一个setlisener的方法
    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
//




    public HotMusicAdapter(String tag, Context context) {
        pageTAG = tag;
        adaptercontext=context;
        FileUtils fileUtils = new FileUtils(context);
        Log.d(TAG, "HotMusicAdapter: "+pageTAG);

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if(pageTAG.equals("欧美")){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_music, parent, false);
                MyViewHolder holder = new MyViewHolder(view);
       //为创建的View注册点击事件
        view.setOnClickListener(this);
               return holder;
//        }else {
//            return null;
//        }

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        try{
            if(pageTAG.equals("欧美")){
            Log.d(TAG, "onBindViewHolder: 欧美执行了");
            holder.songName.setText(Songs.mySongs_om.get(position).getSongname());
            holder.singerName.setText(Songs.mySongs_om.get(position).getSingername());

            if(Songs.mySongs_om!=null&&Songs.mySongs_om.size()!=0) {
                final ImageDownloader mImageDownloader = new ImageDownloader(adaptercontext);
                Log.d(TAG, "onBindViewHolder: Image.girlsURL.size()="+Songs.mySongs_om.size());

                String myGirlsUrl = Songs.mySongs_om.get(position).getAlbumpic_small();
                Bitmap bitmap = mImageDownloader.downLoadImage(myGirlsUrl, new ImageDownloader.onImageDownloaderListener() {
                    @Override
                    public void onImageLoader(Bitmap bitmap, String url) {
                        holder.albumPic.setImageBitmap(bitmap);
                        Log.d(TAG, "onImageLoader: 回调中的if被执行啦,bitmap数组的大小是");
                    }
                });
                if (bitmap != null) {
                    holder.albumPic.setImageBitmap(bitmap);
                    Log.d(TAG, "ShowPicture: 已经下载过图片啦！第二个if执行了,bitmap数组的大小是");
                }
            }
        }

        //将数据保存在itemView的Tag中，以便点击时进行获取
        holder.itemView.setTag(Songs.mySongs_om.get(position));
        }catch (Exception e){
            Toast.makeText(adaptercontext, "亲,你可能没有联网哟", Toast.LENGTH_SHORT).show();
        }
//        else if(pageTAG.equals("港台")){
//            Log.d(TAG, "onBindViewHolder: 这里是内地哟！！！");
//            holder.songName.setText(Songs.mySongs_nd.get(position).getSongname());
//            holder.singerName.setText(Songs.mySongs_nd.get(position).getSingername());
//
//            if(Songs.mySongs_nd!=null&&Songs.mySongs_nd.size()!=0) {
//                final ImageDownloader mImageDownloader = new ImageDownloader(adaptercontext);
//                Log.d(TAG, "onBindViewHolder: Image.girlsURL.size()="+Songs.mySongs_nd.size());
//
//                String myGirlsUrl = Songs.mySongs_nd.get(position).getAlbumpic_small();
//                Bitmap bitmap = mImageDownloader.downLoadImage(myGirlsUrl, new ImageDownloader.onImageDownloaderListener() {
//                    @Override
//                    public void onImageLoader(Bitmap bitmap, String url) {
//                        holder.albumPic.setImageBitmap(bitmap);
//                        Log.d(TAG, "onImageLoader: 回调中的if被执行啦,bitmap数组的大小是");
//                    }
//                });
//                if (bitmap != null) {
//                    holder.albumPic.setImageBitmap(bitmap);
//                    Log.d(TAG, "ShowPicture: 已经下载过图片啦！第二个if执行了,bitmap数组的大小是");
//                }
//            }
//        }else if(pageTAG.equals("韩国")){
//            Log.d(TAG, "onBindViewHolder: 这里是港台哟！！！");
//        }else if(pageTAG.equals("日本")){
//            Log.d(TAG, "onBindViewHolder: 这里是韩国哟！！！");
//        }else if(pageTAG.equals("民谣")){
//            Log.d(TAG, "onBindViewHolder: 这里是日本哟！！！");
//        }else if(pageTAG.equals("摇滚")){
//            Log.d(TAG, "onBindViewHolder: 这里是民谣哟！！！");
//        }else if(pageTAG.equals("销量")){
//            Log.d(TAG, "onBindViewHolder: 这里是摇滚哟！！！");
//        }else if(pageTAG.equals("热歌")){
//            Log.d(TAG, "onBindViewHolder: 这里是销量哟！！！");
//        }else {
//            Log.d(TAG, "onBindViewHolder: 这里是热歌哟！！！");
//        }


    }

    @Override
    public int getItemCount() {
//        if(pageTAG.equals("欧美")) {
//            return Songs.mySongs_om.size();
//        }else if(pageTAG.equals("港台")){
//            return Songs.mySongs_nd.size();
//        }else if(pageTAG.equals("韩国")){
//            return Songs.mySongs_gt.size();
//        }else if(pageTAG.equals("日本")){
//            return Songs.mySongs_hg.size();
//        }else if(pageTAG.equals("民谣")){
//            return Songs.mySongs_rb.size();
//        }else if(pageTAG.equals("摇滚")){
//            return Songs.mySongs_my.size();
//        }else if(pageTAG.equals("销量")){
//            return Songs.mySongs_yg.size();
//        }else if(pageTAG.equals("热歌")){
//            return Songs.mySongs_xl.size();
//        }else {
//            return Songs.mySongs_rg.size();
//        }
        return 20;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView albumPic;
        TextView songName;
        TextView singerName;
        public MyViewHolder(View itemView) {
            super(itemView);
            albumPic= (ImageView) itemView.findViewById(R.id.iv_hot_music_album_pic);
            songName= (TextView) itemView.findViewById(R.id.tv_hotMusic_songName);
            singerName = (TextView) itemView.findViewById(R.id.tv_hotMusic_singerName);
        }
    }
}
