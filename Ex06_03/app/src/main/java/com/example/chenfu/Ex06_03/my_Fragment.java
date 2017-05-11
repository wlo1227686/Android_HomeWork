package com.example.chenfu.Ex06_03;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class my_Fragment extends Fragment {
    private DataList dataList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            dataList = (DataList) getArguments().getSerializable("data");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        Log.e("------my_Fragment------","onCreateView");

        View view = inflater.inflate(R.layout.my_fragment,container,false);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.fragment_linearlayout);
        TextView textview = (TextView) view.findViewById(R.id.fragment_textview);

        linearLayout.setBackgroundColor(dataList.color);
        textview.setText(dataList.title);
        return view;
    }
}
