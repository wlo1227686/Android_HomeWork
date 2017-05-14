package com.example.chenfu.ex08_02;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResultBMIFragment extends Fragment {
    ViewPager fragment_Result_BMI_ViewPager;
    MySQLiteOpenHelper sqliteHelper;
    public  TextView fragment_Result_BMI_data_BMI,
            fragment_Result_BMI_data_Weight,
            fragment_Result_BMI_data_WeightDifference,
            fragment_Result_BMI_data_WeightDifference_kg,
            fragment_Result_BMI_Time;
    List<BMIbean> listBmi;
    ImageView fragment_Result_BMI_data_WeightDifference_up,fragment_Result_BMI_data_WeightDifference_down;
    Button fragment_result_bmi_button_delete;
    int nowPage = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_result_bmi, container, false);
//--<TextView>---------------
        fragment_Result_BMI_data_BMI =(TextView)view.findViewById(R.id.fragment_Result_BMI_data_BMI);
        fragment_Result_BMI_data_Weight =(TextView)view.findViewById(R.id.fragment_Result_BMI_data_Weight);
        fragment_Result_BMI_data_WeightDifference =(TextView)view.findViewById(R.id.fragment_Result_BMI_data_WeightDifference);
        fragment_Result_BMI_data_WeightDifference_kg=(TextView)view.findViewById(R.id.fragment_Result_BMI_data_WeightDifference_kg);
        fragment_Result_BMI_Time=(TextView)view.findViewById(R.id.fragment_Result_BMI_Time);
//--<Viewpager>---------------
        fragment_Result_BMI_ViewPager = (ViewPager) view.findViewById(R.id.fragment_Result_BMI_ViewPager);

//--<Button>-----------------
        fragment_result_bmi_button_delete = (Button) view.findViewById(R.id.fragment_Result_BMI_Button);
//--<ImageView>---------------
        fragment_Result_BMI_data_WeightDifference_up=(ImageView)view.findViewById(R.id.fragment_Result_BMI_data_WeightDifference_up);
        fragment_Result_BMI_data_WeightDifference_down=(ImageView)view.findViewById(R.id.fragment_Result_BMI_data_WeightDifference_down);


        control_view_pager();
//--<刪除按鍵>-----按下時的監聽事件
        fragment_result_bmi_button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("button_onclick", String.valueOf(listBmi.get(nowPage).getId()));
//--<AlertDialog>------------*************************************************************
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.fragment_BMI_alert_tilte)
                        .setIcon(R.drawable.alert)
                        .setMessage(R.string.fragment_BMI_alert_message)
                        .setPositiveButton(R.string.fragment_BMI_alert_text_Buttob_Yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                sqliteHelper.delete(listBmi.get(nowPage).getId());
                                control_view_pager();
                            }
                        })
                        .setNegativeButton(R.string.fragment_BMI_alert_text_Buttob_No, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            }
        });
        return view;
    }
    public void control_TextView(){
        double newWeight=0;
        double oldWeight=0;
        double ans=0;
//--<TextView_CreateTime>------------
        fragment_Result_BMI_Time.setText(listBmi.get(nowPage).getCreateTime());
//--<TextView_BMI>------------
        fragment_Result_BMI_data_BMI.setText(listBmi.get(nowPage).getBMI());
//--<TextView_Height>------------
        fragment_Result_BMI_data_Weight.setText(String.valueOf(listBmi.get(nowPage).getWeight()));
//--<TextView_HeightDifference>------------
        Log.e("control_TextView","nowPage="+nowPage);
//判斷ViewPager框架內的資料數< ViewPager的初始值為0 >
        if(nowPage == 0 ){
            ans=0;//控制體重差值的圖案
            fragment_Result_BMI_data_WeightDifference_kg.setVisibility(View.GONE);
            fragment_Result_BMI_data_WeightDifference.setText("    ");
        }else{
            fragment_Result_BMI_data_WeightDifference_kg.setVisibility(View.VISIBLE);
            newWeight=listBmi.get(nowPage).getWeight();
            oldWeight=listBmi.get(nowPage-1).getWeight();
            ans=newWeight-oldWeight;
            fragment_Result_BMI_data_WeightDifference.setText(String.valueOf(Math.abs(ans)));
        }
        if(ans>0)
        {
            //體重比上次高
            fragment_Result_BMI_data_WeightDifference_up.setVisibility(View.VISIBLE);
            fragment_Result_BMI_data_WeightDifference_down.setVisibility(View.GONE);
        }else if(ans<0){
            //體重比上次低
            fragment_Result_BMI_data_WeightDifference_up.setVisibility(View.GONE);
            fragment_Result_BMI_data_WeightDifference_down.setVisibility(View.VISIBLE);
        }else{
            //控制體重差值的圖案不顯示
            fragment_Result_BMI_data_WeightDifference_up.setVisibility(View.GONE);
            fragment_Result_BMI_data_WeightDifference_down.setVisibility(View.GONE);
        }



    }
    public void control_view_pager() {
        load_listdata();//取得ViewPager所需要的資料
        if(listBmi.size()==1){
            nowPage =0;
            fragment_result_bmi_button_delete.setVisibility(View.INVISIBLE);
            control_TextView();
        }

        ViewPagerAdapter viewpageradapter = new ViewPagerAdapter(this.getActivity().getSupportFragmentManager(), listBmi);
        fragment_Result_BMI_ViewPager.setAdapter(viewpageradapter);

        fragment_Result_BMI_ViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
//當前ViewPager的Index
                nowPage = position;
                control_TextView();
                Log.e("@@@@@@@@@","ViewPager position="+position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
        fragment_Result_BMI_ViewPager.setCurrentItem(listBmi.size() - 1);//永遠顯示最新的那筆資訊
    }
    public void load_listdata(){
        //從SQLite取得資料
        sqliteHelper = new MySQLiteOpenHelper(this.getActivity());
        listBmi = sqliteHelper.getAllData();
        Log.e("load_listdata-----","View_Pager="+String.valueOf(listBmi.size()));
    }

}



