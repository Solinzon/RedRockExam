package com.xushuzhan.redrockexam.view.Interface;

/**
 * Created by xushuzhan on 2016/5/10.
 */
public interface SQLiteOprate {
    public boolean insertSong(String table,String song_name,String singer_name,String album_pic_s,String album_pic_big,String m4a);

    public boolean deletePrice(String table, String price);

    public boolean updatePrice(String table, String price, String newPrice);

    public boolean queryAll(String table);
}
