package com.xushuzhan.redrockexam.Utils;

import android.util.Log;

import com.xushuzhan.redrockexam.data.Songs;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by xushuzhan on 2016/5/15.
 */
public class GetLrc {
    String id;
    GetLrcListener listener;

    public interface GetLrcListener {
        void onSuccess() throws MalformedURLException;
    }

    public GetLrc(final String id, GetLrcListener listener) throws MalformedURLException {
        this.id = id;
        final String myURL = "https://route.showapi.com/213-2?musicid=" + id + "&showapi_appid=18992&showapi_sign=68042539728f4ffc8e9ac2bb02a8a8b3";
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(myURL);
                    connection = (HttpURLConnection) url.openConnection();

                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(10 * 1000);
                    connection.setReadTimeout(10 * 1000);
                    InputStream in = null;
                    in = connection.getInputStream();
                    //读取输入流的内容
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(in,"utf-8"));

                    StringBuilder response = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    parseJsonWithJsonObject(response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();


    }

    public void parseJsonWithJsonObject(String JsonData) {
        //解析Json
        try {
            JSONObject object = new JSONObject(JsonData);
            JSONObject bodyJson = object.getJSONObject("showapi_res_body");
            String lrc = bodyJson.getString("lyric");

            //>>>>>>>>>>>>>.
            URLEncoder.encode(lrc, "utf-8");


            Log.d("lrc", "parseJsonWithJsonObject: "+lrc);

        } catch (Exception e) {
            Log.d("888", "parseJsonWithJsonObject: 解析有点问题哟");
        }
        if(listener!=null)

        {
            try {
                listener.onSuccess();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

    }




}


