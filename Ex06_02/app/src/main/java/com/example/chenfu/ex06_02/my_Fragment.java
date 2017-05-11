package com.example.chenfu.ex06_02;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class my_Fragment extends Fragment {

    @Nullable
    @Override
    //Override 父類別onCreateView 很像Activity的oncreate
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        String title = getArguments().getString("title");// 從boundle拿title
        int color = getArguments().getInt("color");//   從boundle拿color

        View view = inflater.inflate(R.layout.my_fragment,container,false);

        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.fragment_linearlayout);
        TextView textview = (TextView) view.findViewById(R.id.fragment_textview);

        linearLayout.setBackgroundColor(color);
        textview.setText(title);

        return view;
    }
}
