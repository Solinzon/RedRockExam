package com.xushuzhan.redrockexam.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by xushuzhan on 2016/5/4.
 */
public class FileUtils {
    /**
     * SD卡的根目录
     */
    private static String mSdRootPath = Environment.getExternalStorageDirectory().getPath();
    /**
     * 手机缓存根目录
     **/
    private static String mDataRootPath = null;

    /**
     * 保存Image的目录名
     */
    private final static String FOLDER_NAME = "/RockMusic";

    public FileUtils(Context context) {
        mDataRootPath = context.getCacheDir().getPath();
    }

    /**
     * 获取储存Image的目录
     *
     * @return
     */
    private String getStorageDirectory() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ?
                mSdRootPath + FOLDER_NAME : mDataRootPath + FOLDER_NAME;
    }

    /**
     * 保存Image的方法，有sd卡就存到sd卡，没有就存到手机目录
     */
    public void saveBitmap(String fileName, Bitmap bitmap) throws IOException {
        if (bitmap == null) {
            return;
        }
        String path = getStorageDirectory();
        File folderFile = new File(path);
        if(!folderFile.exists()){
            folderFile.mkdir();
        }
        File file = new File(path+File.separator+fileName);
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
        fos.flush();
        fos.close();
    }

    /**
     * 从手机或者sd卡中获取bitmap
     */
    public Bitmap getBitmap(String fileName){
        return BitmapFactory.decodeFile(getStorageDirectory()+File.separator+fileName);
    }

    /**
     * 判断文件是否存在
     */
    public boolean isFileExists(String fileName){
        return new File (getStorageDirectory()+File.separator+fileName).exists();
    }

    /**
     *获取文件的大小
     */
    public long getFileSize(String fileName){
        return new File(getStorageDirectory()+File.separator+fileName).length();
    }

    /**
     * 删除sd卡或者手机的缓存图片和目录
     */
    public void deleteFile(){
        File dirFile = new File(getStorageDirectory());
        if(!dirFile.exists()){
            return;
        }
        if(dirFile.isDirectory()){
            String[] children = dirFile.list();
            for(int i=0;i<children.length;i++){
                new File(dirFile,children[i]).delete();
            }
        }
        dirFile.delete();
    }

    /**
     * 获取文件个数
     */
    public int fileCount(){
        return new File(getStorageDirectory()).list().length;
    }

    /**
     * 保存MP3的方法，有sd卡就存到sd卡，没有就存到手机目录
     */
    public void saveFile(String fileName, ByteArrayOutputStream bos) throws IOException {
        try {
            //在指定目录创建一个空文件并获取文件对象
            File folderFile = new File(getStorageDirectory());
            if(!folderFile.exists()){
                folderFile.mkdir();
            }
            //获取一个写入文件流对象
            File file = new File(getStorageDirectory()+File.separator+fileName);
            file.createNewFile();
            OutputStream ouput =new FileOutputStream(file);
            ouput.write(bos.toByteArray());
            ouput.close();
            bos.close();
        } catch (Exception e) {
            // TODO: handleexception
            e.printStackTrace();
        }

    }
}
