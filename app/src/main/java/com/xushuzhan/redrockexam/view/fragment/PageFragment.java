package com.xushuzhan.redrockexam.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.adapter.HotMusicAdapter;
import com.xushuzhan.redrockexam.data.Songs;
import com.xushuzhan.redrockexam.view.activity.MainActivity;
import com.xushuzhan.redrockexam.view.activity.PlayActivity;

/**
 * Created by xushuzhan on 2016/5/14.
 */
public class PageFragment extends Fragment {
    public static final String TAG = "PageFragment";
    public static final int SHOW_IMAGE=0;
    public static final int SHOW_TOAST=1;

    private View mView;
    /**
     * key值
     */
    private static final String KEY = "EXTRA";

    private String title;



    /**
     * 在这里我们提供一个静态的方法来实例化PageFragment
     * 在这里我们传入一个参数，用来得到title，然后我们拿到这个title设置给内容
     *
     * @param extra

     * @return
     */
    public static PageFragment  newInstance(String extra){
        //利用bundle传值
        Bundle bundle = new Bundle();
        bundle.putString(KEY,extra);
        //实例化
        PageFragment fragment = new PageFragment();
        fragment.setArguments(bundle);
        return fragment;
    }
    private RecyclerView recyclerview;
    private TextView mTextView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            title = bundle.getString(KEY);
            Log.d(TAG, "onCreateView: "+title);
        }
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_hot_music_page, container, false);
        }
        initView();
        return mView;
    }


    public void initView() {
//
//        mTextView = (TextView) mView.findViewById(R.id.text_fragment);
//        mTextView.setText(title);
        try {
            recyclerview = (RecyclerView) mView.findViewById(R.id.hot_music_recyclerview);
            recyclerview.setLayoutManager(new LinearLayoutManager(mView.getContext()));
            HotMusicAdapter adapter = new HotMusicAdapter(title, getContext());
            recyclerview.setAdapter(adapter);

            adapter.setOnItemClickListener(new HotMusicAdapter.OnRecyclerViewItemClickListener() {
                @Override
                public void onItemClick(View view, Songs clickedSongs) {
                    Intent intent = new Intent(mView.getContext(), PlayActivity.class);

                    intent.putExtra("m4a", clickedSongs.getM4a());
                    intent.putExtra("singer_name", clickedSongs.getSingername());
                    intent.putExtra("song_name", clickedSongs.getSongname());
                    intent.putExtra("album_big_pic", clickedSongs.getAlbumpic_big());
                    intent.putExtra("song_di", clickedSongs.getSongid());
                    intent.putExtra("url", clickedSongs.getDownUrl());
                    startActivity(intent);

                }
            });
        }catch (Exception e){
            Toast.makeText(getContext(), "你的网络不太顺畅哟", Toast.LENGTH_SHORT).show();
        }

    }


}
