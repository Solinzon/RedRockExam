package com.xushuzhan.redrockexam.view.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.Utils.FileUtils;
import com.xushuzhan.redrockexam.Utils.ImageDownloader;
import com.xushuzhan.redrockexam.Utils.SQLiteUtil;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PlayActivity extends AppCompatActivity implements View.OnClickListener {
    private Button play;
    private Button pause;
    private Button stop;
    private Button download;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private ImageView imageView;
    private TextView SongName;
    private TextView SingerName;

    public Notification notification;
    public NotificationManager notificationManager;
    FileUtils fileUtils;
    String m4a;
    String singerName;
    String songName;
    String albumBigPic;
    String songId;
    String downloadUrl;
    String albumSmallPic;
    SQLiteUtil sqLiteUtil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        sqLiteUtil = SQLiteUtil.getInstance();
        initView();




        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        stop.setOnClickListener(this);
        download.setOnClickListener(this);
        Intent intent = getIntent();
         m4a= intent.getStringExtra("m4a");
         singerName=intent.getStringExtra("singer_name");
        songName=intent.getStringExtra("song_name");
        albumBigPic = intent.getStringExtra("album_big_pic");
        songId=intent.getStringExtra("song_id");
        downloadUrl=intent.getStringExtra("url");
        albumSmallPic=intent.getStringExtra("album_small_pic");

        Log.d("111", "onCreate: "+downloadUrl);


        dowmAlbumPic();
        SongName.setText(songName);
        SingerName.setText(singerName);

        initMediaPlayer(m4a); // 初始化MediaPlayer
        fileUtils= new FileUtils(PlayActivity.this);


    }

    private void initView() {
        play = (Button) findViewById(R.id.play);
        pause = (Button) findViewById(R.id.pause);
        stop = (Button) findViewById(R.id.stop);
        imageView= (ImageView) findViewById(R.id.iv_album_big_pic);
        SongName= (TextView) findViewById(R.id.tv_song_info_name);
        SingerName= (TextView) findViewById(R.id.tv_song_info_singer_name);
        download= (Button) findViewById(R.id.button_paly_download);
    }

    private void initMediaPlayer(String url) {
        try {
            mediaPlayer.setDataSource(url); // 指定音频文件的路径
            mediaPlayer.prepare(); // 让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start(); // 开始播放
                    try {
                        sqLiteUtil.insertSong("music", singerName, songName, albumSmallPic, albumBigPic, m4a);
                    }catch (Exception e){
                        Log.d("123123", "onClick: "+e.getMessage());
                    }
                    }
                break;
            case R.id.pause:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause(); // 暂停播放
                }
                break;
            case R.id.stop:
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.reset(); // 停止播放
                    initMediaPlayer(m4a);
                }
                break;
            case R.id.button_paly_download:
                downLoadMp3();
            default:
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public void dowmAlbumPic(){
            final ImageDownloader mImageDownloader = new ImageDownloader(PlayActivity.this);
            Bitmap bitmap = mImageDownloader.downLoadImage(albumBigPic, new ImageDownloader.onImageDownloaderListener() {
                @Override
                public void onImageLoader(Bitmap bitmap, String url) {
                    imageView.setImageBitmap(bitmap);
                }
            });
            if (bitmap != null) {
                imageView.setImageBitmap(bitmap);
            }
    }

    public void downLrc(String songId){

    }

    public void downLoadMp3(){
        //开启异步任务
        downLoadTask dLoadTask=new downLoadTask();
        String urlString=downloadUrl;
        Log.d("222", "downLoadMp3: 我开始下载咯哟！"+urlString);
        dLoadTask.execute(urlString);
    }
    class downLoadTask extends AsyncTask<String, Integer, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(PlayActivity.this);
            // Sets the ticker text 设置贴标的文本,贴标会出现在状态栏一下
            builder.setTicker("开始下载咯");
            // Sets the small icon for the ticker 设置贴标的小图标
            builder.setSmallIcon(R.mipmap.icon);
            // Cancel the notification when clicked 点击时取消通知
            builder.setAutoCancel(true);

            Intent intent = new Intent();
            PendingIntent contentIntent = PendingIntent.getActivity(PlayActivity.this,
                    R.string.app_name, intent,
                    PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(contentIntent );

            notification = builder.build();
            //设置一个view
            RemoteViews view1 = new RemoteViews(getPackageName(), R.layout.progresbar_view);

            notification.contentView = view1;
            notification.flags= Notification.FLAG_AUTO_CANCEL;
            notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notification.contentIntent = contentIntent;
            notification.contentView.setImageViewResource(R.id.my_icon,R.mipmap.icon);

        }
        //onPostExecute方法用于在执行完后台任务后更新UI,显示结果
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected Void doInBackground(String... params) {
            String urlString =params[0];
            URL url;
            try {
                url = new URL(urlString);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.setConnectTimeout(10 * 1000);
                httpURLConnection.setReadTimeout(10 * 1000);
                int totalSize=httpURLConnection.getContentLength();//总的大小
                InputStream is= httpURLConnection.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                BufferedInputStream bis = new BufferedInputStream(is);
                byte[] b = new byte[4*1024];
                int len = 0;
                int realTimeLength=0;
                int lenth=totalSize;
                while((len = bis.read(b))!=-1){
                    bos.write(b,0,len);
                    bos.flush();
                    realTimeLength += len;
                    int currentProgess=(int)(((float)realTimeLength/(float)totalSize)*100);
                    this.publishProgress(currentProgess);
                    Log.d("456", "doInBackground:"+realTimeLength+">"+totalSize+">"+(int)(((float)realTimeLength/(float)totalSize)*100)+"%" );

                    notification.contentView.setProgressBar(R.id.noti_pd, 100,
                            currentProgess, false);
                    notification.contentView.setTextViewText(R.id.noti_tv, currentProgess
                            + "%");
                    // 发送消息
                    notificationManager.notify(0,notification);
                }
                fileUtils.saveFile(songName+".mp3",bos);

                bos.close();
            }catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }
}
