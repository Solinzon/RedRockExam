package com.xushuzhan.redrockexam.Utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.LruCache;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xushuzhan on 2016/5/4.
 * 异步下载的核心类，保存图片到手机缓存，将图片加入LruChaChe中等
 */
public class ImageDownloader {
    /**
     * 缓存Image的类，当储存Image的大小大于LruChaChe设定的值，系统自动释放内存
     */
    public static LruCache<String,Bitmap> mMemoryChaChe;

    /**
     * 操作文件对象的引用
     */
    private FileUtils fileUtils;

    /**
     * 下载Image的线程池
     */
    private ExecutorService mImageThreadPool = null;
    /**
     * 储存文件的名字
     */
    Context context;
    ProgressDialog progressDialog;
    public ImageDownloader(Context context){
        this.context=context;
        //获取系统分配给每个应用程序的最大内存，每个应用分配32M
        int maxMemory  = (int) Runtime.getRuntime().maxMemory();

        //给LruChaChe分配1/8  4M
        int mChacheSize  = maxMemory/8;
        mMemoryChaChe  = new LruCache<String, Bitmap>(mChacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };


        fileUtils = new FileUtils(context);
    }
    /**
     * 获取线程池的方法，因为涉及到并发的问题，所以加上同步锁
     */
    public  ExecutorService getThreadPool(){
        if(mImageThreadPool == null){
            synchronized (ExecutorService.class){
                if(mImageThreadPool == null){
                    //为了下载图片流畅，用了两个2个线程来下载
                    mImageThreadPool = Executors.newFixedThreadPool(2);
                }
            }
        }
        return mImageThreadPool;
    }

    /**
     * 添加Bitmap到内存缓存
     */
    public static void addBitmapToMemoryChache(String key , Bitmap bitmap){
        if(getBitmapFromMemChache(key)==null&&bitmap!=null){
            mMemoryChaChe.put(key,bitmap);
        }
    }

    /**
     * 从内存缓存中获取一个Bitmap
     */
    public static Bitmap getBitmapFromMemChache(String key){
        return mMemoryChaChe.get(key);
    }

    /**
     * 从内存缓存中获取Bitmap，如果没有就从SD卡或者手机缓存中获取，
     * SD卡或者手机缓存没有就去下载
     */
    public Bitmap downLoadImage(final String url,final onImageDownloaderListener listener){
        //替换url中非字符字母和非数字的字符，以防保存文件时候失败
        //例如以“http://baidu.jpg” 作为文件的名字，系统会认为baidu是一个目录，从而在没有创建目录的情况下保存文件，就会出错
        final String subUrl = url.replaceAll("[^\\w]","");

        Bitmap bitmap = showCacheBitmap(subUrl);
        if(bitmap!=null){
            Log.d("123456", "downLoadImage: "+mMemoryChaChe.size());
            return bitmap;
        }else {
            showProgress();

            final Handler handler = new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    progressDialog.dismiss();
                    listener.onImageLoader((Bitmap) msg.obj,url);

                }
            };

            getThreadPool().execute(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap= getBitmapFromUrl(url);
                    Message msg = handler.obtainMessage();
                    msg.obj=bitmap;
                    handler.sendMessage(msg);

                    try{
                        //保存在SD卡或者手机目录
                        fileUtils.saveBitmap(subUrl,bitmap);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    //将Bitmap加入缓存
                    addBitmapToMemoryChache(subUrl,bitmap);

                }
            });
        }
            return null;
        }
    /**
     * 从url中获取bitmap
     */
    private Bitmap getBitmapFromUrl(String url){
        Bitmap bitmap = null;
        HttpURLConnection connection = null;

        try {
            URL mImageUrl = new URL(url);
            connection= (HttpURLConnection) mImageUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(10*1000);
            connection.setConnectTimeout(10*1000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(connection!=null){
                connection.disconnect();
            }
        }

        return  bitmap;
    }

    /**
     * 获取Bitmap，内存中没有就去手机或者SD卡获取
     */

    public Bitmap showCacheBitmap(String url){
        if(getBitmapFromMemChache(url)!=null){
            return getBitmapFromMemChache(url);
        }else if(fileUtils.isFileExists(url)&&fileUtils.getFileSize(url)!=0){
            //从SD卡获取
            Bitmap bitmap = fileUtils.getBitmap(url);

            //将Bitmap加入内存缓存

            addBitmapToMemoryChache(url,bitmap);

            return bitmap;
        }

        return null;
    }


    /**
     * 异步下载的回调接口
     *
     */
    public interface onImageDownloaderListener{
        void onImageLoader(Bitmap bitmap, String url);
    }

    /**
     * 取消正在下载的任务
     */
    public synchronized void cancelTask(){
        if(mImageThreadPool!=null){
            mImageThreadPool.shutdown();
            mImageThreadPool=null;
        }
    }

    /**
     * 显示进度条
     */
    public void showProgress(){

        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("正在拼命获取加载中");
        progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

}
