package com.example.chenfu.Ex06_03;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter{
    List<DataList> dataList;

    public ViewPagerAdapter(FragmentManager fm, List<DataList> DataList) {
        super(fm);
        dataList = DataList;
    }

    @Override
    public Fragment getItem(int position) {
        DataList data = dataList.get(position);
        my_Fragment my_fragment = new my_Fragment();
        Bundle args = new Bundle();
        args.putSerializable("data", data);
        my_fragment.setArguments(args);
        return my_fragment;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}
