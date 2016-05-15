package com.xushuzhan.redrockexam.view.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.Utils.AnalyzeAPI;
import com.xushuzhan.redrockexam.adapter.HotMusicAdapter;
import com.xushuzhan.redrockexam.data.Songs;

import java.net.MalformedURLException;

public class HotMusicActivity extends AppCompatActivity {
    public static final int SHOW_IMAGE = 0;
    public static final int SHOW_TOAST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_music);
//        int topId[] = new int[]{3, 5, 5, 16, 17, 18, 19, 23, 26};
//        //for(int i=0;i<=7;i++) {
//        AnalyzeAPI analyzeAPI = new AnalyzeAPI("http://route.showapi.com/213-4?showapi_appid=18992&topid=5&showapi_sign=68042539728f4ffc8e9ac2bb02a8a8b3", new AnalyzeAPI.AnalyzeAPIListener() {
//            @Override
//            public void onSuccess() throws MalformedURLException {
//                Log.d("HotMusicActivity", "onSuccess: 终于成功了,内地的大小是：" + Songs.mySongs_nd.size());
//                handler.sendEmptyMessage(SHOW_IMAGE);
//            }
//
//            @Override
//            public void onFailed() {
//                handler.sendEmptyMessage(SHOW_TOAST);
//            }
//        },1);
//        //  }


    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == SHOW_IMAGE) {
                //Toast.makeText(HotMusicActivity.this, "成功啦", Toast.LENGTH_SHORT).show();
                Log.d("123", "handleMessage: 66666");
            } else if (msg.what == SHOW_TOAST) {
                //Log.d("123", "handleMessage:555555 ");
                Toast.makeText(HotMusicActivity.this, "网络好像不太流畅 (ಥ _ ಥ)", Toast.LENGTH_SHORT).show();
            }
        }
    };

}

