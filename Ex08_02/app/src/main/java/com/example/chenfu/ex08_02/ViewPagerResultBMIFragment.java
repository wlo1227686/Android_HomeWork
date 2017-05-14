package com.example.chenfu.ex08_02;

import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewPagerResultBMIFragment extends Fragment {
    BMIbean bmibean;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//接取boundle載送的data資料
        if (getArguments() != null) {
            bmibean = (BMIbean) getArguments().getSerializable("data");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_result_bmi_view_pager, container, false);
        TextView fragment_Result_BMI_ViewPager_TextView =(TextView)view.findViewById(R.id.fragment_Result_BMI_ViewPager_TextView);
        LinearLayout fragment_Result_BMI_ViewPager_Linearout =(LinearLayout) view.findViewById(R.id.fragment_Result_BMI_ViewPager_LinearLayout);

//顯示BMI_Zoom的String
        fragment_Result_BMI_ViewPager_TextView.setText(bmibean.getBMI_Zoom());

//變更ViewPager的背景顏色
        switch (bmibean.getBMI_Zoom()){
            case "Underweight":
                fragment_Result_BMI_ViewPager_Linearout.setBackgroundColor(getResources().getColor(R.color.BMI_Color_Underweight,null));
                break;
            case "Normal":
                fragment_Result_BMI_ViewPager_Linearout.setBackgroundColor(getResources().getColor(R.color.BMI_Color_Normal,null));
                break;
            case "Overweight":
                fragment_Result_BMI_ViewPager_Linearout.setBackgroundColor(getResources().getColor(R.color.BMI_Color_Overweight,null));
                break;
            case "Obese":
                fragment_Result_BMI_ViewPager_Linearout.setBackgroundColor(getResources().getColor(R.color.BMI_Color_Obese,null));
                break;
            default:
        }
        return view;
    }
}
