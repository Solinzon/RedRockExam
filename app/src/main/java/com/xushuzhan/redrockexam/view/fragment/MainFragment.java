package com.xushuzhan.redrockexam.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xushuzhan.redrockexam.R;
import com.xushuzhan.redrockexam.adapter.FixedPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xushuzhan on 2016/5/14.
 */
public class MainFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    //fragment存储器
    private List<Fragment> mFragments;
    //tab条目中的内容
    private String[]titles = {"欧美","内地","港台","韩国","日本","民谣","摇滚","销量","热歌"};
    private FixedPagerAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot_music_main, container, false);

        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        initData();
        return view;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAdapter = new FixedPagerAdapter(getChildFragmentManager());
        mAdapter.setTitles(titles);//标题
        mFragments = new ArrayList<>();
        for (int i = 0; i < titles.length; i++) {
            mFragments.add(PageFragment.newInstance(titles[i]));

        }
        //把要显示的fragment集合传给adapter
        mAdapter.setFragments(mFragments);
        /**
         * 设置tablayout的模式
         *  MODE_SCROLLABLE  可以滑动，显示很多个tab中的几个展示出来
         */
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //给viewPager设置适配器
        mViewPager.setAdapter(mAdapter);
        //TabLayout绑定ViewPager
        mTabLayout.setupWithViewPager(mViewPager);

    }

}
