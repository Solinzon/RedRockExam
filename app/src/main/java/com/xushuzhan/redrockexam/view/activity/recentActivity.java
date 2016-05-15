package com.xushuzhan.redrockexam.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.adapter.HotMusicAdapter;
import com.xushuzhan.redrockexam.adapter.RecentMusicAdapter;

public class RecentActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        recyclerView = (RecyclerView) findViewById(R.id.recent_music_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RecentMusicAdapter adapter = new RecentMusicAdapter();
        recyclerView.setAdapter(adapter);
    }
}
