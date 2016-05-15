package com.xushuzhan.redrockexam.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xushuzhan.redrockexam.R;

/**
 * Created by xushuzhan on 2016/5/15.
 */
public class RecentMusicAdapter extends RecyclerView.Adapter<RecentMusicAdapter.MyViewHolder>{


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hot_music, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.songName.setText("Heartbeat");
        holder.singerName.setText("Christopher");
    }

    @Override
    public int getItemCount() {
        return 1;
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
