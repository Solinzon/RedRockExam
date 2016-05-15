package com.xushuzhan.redrockexam.Utils;

import android.util.Log;

import com.xushuzhan.redrockexam.data.Songs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by xushuzhan on 2016/5/6.
 */
public class AnalyzeAPI implements Runnable {
    public static final String TAG = "AnalyzeAPI";
    private String API;
    AnalyzeAPIListener listener;
    public int position;

    public interface AnalyzeAPIListener {
        void onSuccess() throws MalformedURLException;
        void onFailed();
    }

    public AnalyzeAPI(final String API, AnalyzeAPIListener listener,int topPosition) {
        this.API = API;
        this.listener = listener;
        position=topPosition;
        //Toast.makeText(MainApplication.getContext(), "网络好像有点问题 (ಥ _ ಥ)..", Toast.LENGTH_SHORT).show();
        Thread thread = new Thread(this);
        thread.start();


    }

    @Override
    public void run() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(API);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(10 * 1000);
            connection.setReadTimeout(10 * 1000);
            InputStream in = connection.getInputStream();
            //读取输入流的内容
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(in));

            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            parseJsonWithJsonObject(response.toString());

        } catch (Exception e) {
            if(listener!=null){
                Log.d(TAG, "获得Json: 网络好像有点问题 (ಥ _ ಥ)· " + e.getMessage());
                listener.onFailed();

            }

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    public void parseJsonWithJsonObject(String JsonData) {
        //解析Json
        try {
            JSONObject object = new JSONObject(JsonData);
            JSONObject bodyJson = object.getJSONObject("showapi_res_body");
            JSONObject pageBeanJson=bodyJson.getJSONObject("pagebean");
            JSONArray songlistJsonArray=pageBeanJson.getJSONArray("songlist");
            Songs.mySongs_om.clear();
            for (int i = 0; i < songlistJsonArray.length(); i++) {

                try {
                    JSONObject jsonObject = songlistJsonArray.getJSONObject(i);
                    Songs mSongs=new Songs();
//                        mSongs.setAlbumid(jsonObject.getInt("albumid"));
//                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getInt("albumid"));
//                  mSongs.setAlbumname(jsonObject.getString("albumname"));
//                  Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("albumname"));
                        mSongs.setAlbumpic_big(jsonObject.getString("albumpic_big"));
                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("albumpic_big"));
                        mSongs.setAlbumpic_small(jsonObject.getString("albumpic_small"));
                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("albumpic_small"));
                        mSongs.setDownUrl(jsonObject.getString("downUrl"));
                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("downUrl"));
                        mSongs.setM4a(jsonObject.getString("url"));
                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("url"));
//                        mSongs.setSingerid(jsonObject.getString("singerid"));
//                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("singerid"));
                        mSongs.setSingername(jsonObject.getString("singername"));
                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("singername"));
                        mSongs.setSongid(jsonObject.getString("songid"));
                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getInt("songid"));
                        mSongs.setSongname(jsonObject.getString("songname"));
                        Log.d(TAG, "parseJsonWithJsonObject: "+jsonObject.getString("songname"));
                        Log.d(TAG, "parseJsonWithJsonObject: >>>>>>>>>>>>>>>>>>>>>>>"+i);
                     if(position==0){
                         Songs.mySongs_om.add(mSongs);
                     }else if(position==1){
                         Songs.mySongs_nd.add(mSongs);
                     }else if(position==2){
                         Songs.mySongs_gt.add(mSongs);
                     }else if(position==3){
                         Songs.mySongs_hg.add(mSongs);
                     }else if(position==4){
                         Songs.mySongs_rb.add(mSongs);
                     }else if(position==5){
                         Songs.mySongs_my.add(mSongs);
                     }else if(position==6){
                         Songs.mySongs_yg.add(mSongs);
                     }else if(position==7){
                         Songs.mySongs_xl.add(mSongs);
                     }else if(position==8){
                         Songs.mySongs_rg.add(mSongs);
                     }

                }catch (Exception e){
                    Log.d(TAG, "parseJsonWithJsonObject: 解析有点问题哟");
                }


            }


            if (listener != null) {
                listener.onSuccess();
            }

        } catch (Exception e) {
            Log.d(TAG, "解析Json: " + e.getMessage());
        }
    }
}
