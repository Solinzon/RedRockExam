package com.xushuzhan.redrockexam.data;

import java.util.ArrayList;

/**
 * Created by xushuzhan on 2016/5/14.
 */
public class Songs {

    public static final ArrayList<Songs> mySongs_om= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_nd= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_gt= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_hg= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_rb= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_my= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_yg= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_xl= new ArrayList<>();
//    public static final ArrayList<Songs> mySongs_rg= new ArrayList<>();




    //专辑id
    private int Albumid = 0;
    //专辑名称
    private String Albumname = "null";
    //专辑大图片，高宽300
    private String Albumpic_big= "null";
    //专辑小图片，高宽90
    private String Albumpic_small= "null";
    //mp3下载链接
    private String DownUrl= "null";
    //流媒体地址
    private String M4a= "null";
    //歌手id
    private String Singerid= "null";
    //歌手名
    private String Singername= "null";
    //歌曲id
    private String Songid= "null";
    //歌曲名称
    private String Songname= "null";

    public int getAlbumid() {
        return Albumid;
    }

    public void setAlbumid(int albumid) {
        Albumid = albumid;
    }

    public String getAlbumname() {
        return Albumname;
    }

    public void setAlbumname(String albumname) {
        Albumname = albumname;
    }

    public String getAlbumpic_big() {
        return Albumpic_big;
    }

    public void setAlbumpic_big(String albumpic_big) {
        Albumpic_big = albumpic_big;
    }

    public String getAlbumpic_small() {
        return Albumpic_small;
    }

    public void setAlbumpic_small(String albumpic_small) {
        Albumpic_small = albumpic_small;
    }

    public String getDownUrl() {
        return DownUrl;
    }

    public void setDownUrl(String downUrl) {
        DownUrl = downUrl;
    }

    public String getM4a() {
        return M4a;
    }

    public void setM4a(String m4a) {
        M4a = m4a;
    }

    public String getSingerid() {
        return Singerid;
    }

    public void setSingerid(String singerid) {
        Singerid = singerid;
    }

    public String getSingername() {
        return Singername;
    }

    public void setSingername(String singername) {
        Singername = singername;
    }

    public String getSongid() {
        return Songid;
    }

    public void setSongid(String songid) {
        Songid = songid;
    }

    public String getSongname() {
        return Songname;
    }

    public void setSongname(String songname) {
        Songname = songname;
    }
}
