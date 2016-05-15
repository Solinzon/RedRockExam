package com.xushuzhan.redrockexam.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.xushuzhan.redrockexam.view.Interface.SQLiteOprate;


/**
 * Created by xushuzhan on 2016/5/10.
 */
public class SQLiteUtil extends SQLiteOpenHelper implements SQLiteOprate {

    public static final String TAG="SQLiteUtil";
    //SQLite操作的实例
    public static SQLiteUtil mSQLiteUtil = null;

    //打开数据库时候返回的操作数据库的对象
    private SQLiteDatabase db = null;

    Context mContext;

    /**
     * 初始化SQLite的方法，在启动APP时调用
     *
     * @param context
     */

    public static void init(Context context) {
        if (mSQLiteUtil == null) {
            synchronized (SQLiteUtil.class) {
                mSQLiteUtil = new SQLiteUtil(context, "myMusic.db", null, 1);
            }
        }
    }

    /**
     * 暴露给调用者获取实例的方法
     *
     * @return
     */

    public static SQLiteUtil getInstance() {
        return mSQLiteUtil;
    }


    /**
     * 构造方法
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public SQLiteUtil(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        db = getWritableDatabase();

        //不知道什么意思》》》》》》》》》》》》》》》》》》》》》》》》》》
        mSQLiteUtil = this;

        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //写一些建表语句
        db.execSQL("create table music(id integer primary key" +
                " autoincrement," +
                "song_name text," +
                "singer_name text," +
                "album_pic_s text," +
                "album_pic_b text,"+
                "m4a text)"  );
   //     Toast.makeText(mContext, "创建成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>INSERT<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public boolean insertSong(String table,String song_name,String singer_name,String album_pic_s,String album_pic_big,String m4a) {
        try{
            ContentValues values = new ContentValues();
            values.put("song_name", song_name);
            values.put("singer_name", singer_name);
            values.put("album_pic_s", album_pic_s);
            values.put("album_pic_b", album_pic_big);
            values.put("m4a", m4a);


            db.insert(table, null, values);

            Log.e(TAG, "insertPrice:  插入数据啦" );
            return true;
        }catch (Exception e){
            Log.d(TAG, "insertPrice: 出现错误了："+e.getMessage());
            return  false;
        }

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>DELETE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                  /*会删除所有符合条件的所有数据，之后在实际应用中可以添加限制条件*/
    @Override
    public boolean deletePrice(String table,String price) {
        try {
            db.delete(table,"price=?",new String[]{price});
            Log.e(TAG, "insertPrice:  删除数据啦" );
            return true;
        }catch (Exception e){
            Log.d(TAG, "deletePrice: 出现错误了："+e.getMessage());
            return false;
        }

    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>UPDATE<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                /*会更新所有符合条件的所有数据，之后在实际应用中可以添加限制条件*/
    @Override
    public boolean updatePrice(String table,String price,String newPrice) {
        try{
            ContentValues values = new ContentValues();
            values.put("price",newPrice);

            db.update(table,values,"price = ?",new String[]{price});
            Log.d(TAG, "updatePrice: 更新数据啦");
            return true;
        }catch (Exception e){
            Log.d(TAG, "updatePrice: 出现错误了："+e.getMessage());
            return false;
        }

    }

    @Override
    public boolean queryAll(String table) {
        Cursor cursor = db.query(table, null, null, null, null, null, null);
        try {

            if(cursor.moveToFirst()){
                do{
//                            cursor.getInt(cursor.getColumnIndex("id")),
                            String a= cursor.getString(cursor.getColumnIndex("price"));
//                            cursor.getFloat(cursor.getColumnIndex("price")),
//                            cursor.getString(cursor.getColumnIndex("detail")));
                    Log.d(TAG, "queryAll: > price="+a);
                }while (cursor.moveToNext());}
            cursor.close();

            return true;
        }catch (Exception e){
            return  false;
        }
    }
    //>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>SEARCH<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

}
