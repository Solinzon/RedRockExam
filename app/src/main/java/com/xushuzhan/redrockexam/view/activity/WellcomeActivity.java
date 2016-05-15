package com.xushuzhan.redrockexam.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.Utils.AnalyzeAPI;
import com.xushuzhan.redrockexam.data.Songs;

import java.net.MalformedURLException;

public class WellcomeActivity extends Activity {
    public static final int START_ACTIVITY=3;
    public static final int SHOW_IMAGE=0;
    public static final int SHOW_TOAST=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_wellcome);


           AnalyzeAPI analyzeAPI = new AnalyzeAPI("http://route.showapi.com/213-4?showapi_appid=18992&topid=3&showapi_sign=68042539728f4ffc8e9ac2bb02a8a8b3", new AnalyzeAPI.AnalyzeAPIListener() {
            @Override
            public void onSuccess() throws MalformedURLException {
                Log.d("HotMusicActivity", "onSuccess: 终于成功了,欧美的大小是："+ Songs.mySongs_om.size());
                handler.sendEmptyMessage(SHOW_IMAGE);
                handler.sendEmptyMessageDelayed(START_ACTIVITY,2*1000);
            }

            @Override
            public void onFailed() {
                handler.sendEmptyMessage(SHOW_TOAST);
            }
        },0);



    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case START_ACTIVITY:
                    startActivity(new Intent(WellcomeActivity.this,MainActivity.class));
                    finish();
                    break;
                case SHOW_IMAGE:
                    Toast.makeText(WellcomeActivity.this, "欢迎进入", Toast.LENGTH_SHORT).show();
                    break;
                case SHOW_TOAST:
                    Toast.makeText(WellcomeActivity.this, "网络好像不太流畅 (ಥ _ ಥ)，联了网再试吧", Toast.LENGTH_LONG).show();

                    handler1.sendEmptyMessageDelayed(5,3*1000);

            }
        }
    };
    Handler handler1=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what==5){
                finish();
            }
        }
    };

}
