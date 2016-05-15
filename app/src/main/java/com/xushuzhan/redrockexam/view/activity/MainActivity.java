package com.xushuzhan.redrockexam.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.Utils.AnalyzeAPI;
import com.xushuzhan.redrockexam.Utils.GetLrc;
import com.xushuzhan.redrockexam.data.Songs;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    Button hotMusic;
    Button listMusic;
    Button recentMusic;
    private ViewPager mViewPager;
    private TextView mTextView;
    private LinearLayout ll;

    //点击图片后对应打开的URL
    private String myURL []={"http://y.qq.com/live/gary/index.html?g_f=bofangjiaodian",
            "http://y.qq.com/live/jessica/index_pre.html?g_f=bofangjiaodian",
            "http://v.qq.com/live/p/topic/5222/preview.html#",
            "http://y.qq.com/#type=index&url=http%3A%2F%2Fi.y.qq.com%2Fv8%2Ffcg-bin%2Ffcg_v8_mvout4web.fcg%3Fcmd%3D1%26format%3Dhtml%26tpl%3Dmvplay%26vid%3Dd00208ktvo4",
            "http://y.qq.com/#type=album&mid=002oar1k1Gg3Tb"};

    //图片的id
    private int[] imageIds={R.drawable.a,R.drawable.b,R.drawable.c,R.drawable.d,R.drawable.e};
    // 图片对应的标题
    private final String[] imageTitleTexts = { "实力派Gary首支中文单曲「没关系」",
            "完美女神Jessica全新数字专辑", "我是曹格 世界巡回演唱会·上海站", "魔天伦幕后花絮首曝", "格莱美最佳新人全新大碟" };

    //是否开始自动滑动
    private boolean isRunning=false;

    private int lastPoint=0;

    private List<ImageView> imageViewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //启动轮播图
        initCarouselfigureView();



    }


    private void initView() {
        hotMusic = (Button) findViewById(R.id.button_hot_music);
        hotMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, HotMusicActivity.class);
                startActivity(intent);


            }
        });

        listMusic= (Button) findViewById(R.id.button_list_music);
        listMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecentActivity.class);
                startActivity(intent);
            }
        });
    }














    private void initCarouselfigureView() {
        mViewPager= (ViewPager) findViewById(R.id.viewPager);
        mTextView= (TextView) findViewById(R.id.textView);
        ll= (LinearLayout) findViewById(R.id.ll);

        mTextView.setText(imageTitleTexts[0]);

        imageViewList=new ArrayList<ImageView>();
        for (int x=0 ; x<imageIds.length ; x++ ){
            ImageView imageView = new ImageView(this);
            imageView.setBackgroundResource(imageIds[x]);
            imageViewList.add(imageView);

            //添加指示点
            ImageView ivPoint=new ImageView(this);
            if(x==0){
                ivPoint.setBackgroundResource(R.drawable.point_select);
            }else {
                ivPoint.setBackgroundResource(R.drawable.point_no);
            }

            //给指示点设置间距
            LinearLayout.LayoutParams layoutParams =new LinearLayout.LayoutParams(-2, -2);
            layoutParams.leftMargin=20;

            ll.addView(ivPoint,layoutParams);
        }

        adapter=new MyViewPagerAdapter();
        mViewPager.setAdapter(adapter);


        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 当页面发生改变时调用
             * @param position
             */
            @Override
            public void onPageSelected(int position) {
                //同样道理和instantiateItem（）一样
                position=position % imageIds.length;

                mTextView.setText(imageTitleTexts[position]);

                //让指示点给着变化
                ll.getChildAt(lastPoint).setBackgroundResource(R.drawable.point_no);
                ll.getChildAt(position).setBackgroundResource(R.drawable.point_select);

                lastPoint=position;



            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        //这个必须设置，用最大数除2是得到中间数，这样无论向左还是向右都可以一直滑动；
        //如果设置为Integer.MAX_VALUE/2 可能打开activity轮播图不是从第一张开始；所以需要减去余数
        mViewPager.setCurrentItem(Integer.MAX_VALUE/2 - Integer.MAX_VALUE % imageIds.length);

        isRunning=true;
        handler.sendEmptyMessageDelayed(99,2000);

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(isRunning==true){
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);

                handler.sendEmptyMessageDelayed(99,4000);
            }
        }
    };

    private MyViewPagerAdapter adapter;

    class MyViewPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            //为了让viewpager可以一直向两边滑动，这里设置一个最大值；
            return Integer.MAX_VALUE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            //当上面的return改变为最大数的时候，这里的position就是最大数了，所以必须取余数，不然就会报错
            position=position % imageViewList.size();

            ImageView iv=imageViewList.get(position);
            container.addView(iv);

            View viewClick=imageViewList.get(position);
            final int mPosition=position;
            viewClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("MainActivity", "onClick: 你点击了我！！"+mPosition);
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(myURL[mPosition]));
                    startActivity(intent);
                }
            });

            return iv;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    protected void onDestroy() {
        isRunning=false;
        super.onDestroy();
    }


}
