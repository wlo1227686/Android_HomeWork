package com.example.chenfu.ex08_02;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    List<BMIbean> dataList;
    public ViewPagerAdapter(FragmentManager fm,List<BMIbean> DataList) {
        super(fm);
        this.dataList=DataList;
    }

    @Override
    public Fragment getItem(int position) {
        BMIbean data = dataList.get(position);

        ViewPagerResultBMIFragment viewpager_fragment = new ViewPagerResultBMIFragment();

//用Boundle傳送第index項的資料
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        viewpager_fragment.setArguments(args);

        return viewpager_fragment;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }
}
