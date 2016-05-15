package com.xushuzhan.redrockexam.view.activity.aplication;

import android.app.Application;
import android.content.Context;

import com.xushuzhan.redrockexam.Utils.SQLiteUtil;

/**
 * Created by xushuzhan on 2016/5/10.
 */
public class MyApp extends Application {
    public static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        SQLiteUtil.init(this);

        mContext=getApplicationContext();
    }

    public static Context getmContext(){
        return mContext;
    }
}
