package com.example.chenfu.ex08_02;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class BMIFragment extends Fragment{
    String Preferences_Name="Ex08_02";
    MySQLiteOpenHelper sqliteHelper;
    EditText fragment_BMI_EditText_Height, fragment_BMI_EditText_Weight;
    Button fragment_BMI_Button;
    String Default_Height="";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//開啟SQL服務器*************************************************************
        if (sqliteHelper == null) {
            sqliteHelper = new MySQLiteOpenHelper(this.getActivity());
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.fragment_bmi, container, false);

        fragment_BMI_EditText_Height = (EditText) view.findViewById(R.id.fragment_BMI_EditText_Height);
        fragment_BMI_EditText_Height.setText(Load_Height_Preferences());//SharedPreferences***********************
        fragment_BMI_EditText_Weight = (EditText) view.findViewById(R.id.fragment_BMI_EditText_Weight);
//-------------------------------------------
        fragment_BMI_Button = (Button)view.findViewById(R.id.fragment_BMI_Button_submit);

        fragment_BMI_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        //判斷字串是否符合規定格式
                boolean useful_EditText_data = useful_EditText_data(fragment_BMI_EditText_Height)
                        & useful_EditText_data(fragment_BMI_EditText_Weight);
                if (!useful_EditText_data) {  return;   }
        //EditText取值
                Double Height = Double.parseDouble(fragment_BMI_EditText_Height.getText().toString());
                Double Weight = Double.parseDouble(fragment_BMI_EditText_Weight.getText().toString());
        //SharedPreferences
                Save_Height_Preferences(fragment_BMI_EditText_Height.getText().toString());
//<SQL存資料>----------------------------------
                Save_BMI_Data_SQLite(Height,Weight);
//<收起鍵盤>----------------------------------
                InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(fragment_BMI_EditText_Height.getWindowToken(), 0);//收起鍵盤*************
                imm.hideSoftInputFromWindow(fragment_BMI_EditText_Weight.getWindowToken(), 0);//收起鍵盤*************
            }
        });
        return view;
    }
//--<內儲存檔>------------------
    public void Save_Height_Preferences(String Height){
        SharedPreferences preferences = getActivity().getSharedPreferences(Preferences_Name,Context.MODE_PRIVATE);
        preferences.edit().putString("Height",Height).apply();
    }
//--<內儲讀檔>------------------
    public String Load_Height_Preferences(){
        SharedPreferences preferences = getActivity().getSharedPreferences(Preferences_Name,Context.MODE_PRIVATE);
        return preferences.getString("Height",Default_Height);
    }
    public void Save_BMI_Data_SQLite(double height,double Weight){
        BMIbean bmibean = new BMIbean(height,Weight);
        long rowId = sqliteHelper.insert(bmibean);
        if (rowId != -1) {
//--<SQL存檔成功>----------------
            FragmentManager fragmentManager = this.getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.main_FrameLayout, new ResultBMIFragment());
            fragmentTransaction.commit();
//------------------------------
            this.getActivity().setTitle(R.string.text_Home);            //設置App Title <MainActivity> setTitle("TitleName");
            ((MainActivity)getActivity()).setUpActionBar(); //顯示左上角的ActionBar的圖示
        } else {
//--<SQL存檔失敗>----------------
            Toast.makeText(this.getActivity(), "fail",Toast.LENGTH_SHORT).show();
        }
        sqliteHelper.close();
    }

    /*判斷參數是否合法*/
    private boolean useful_EditText_data(EditText editText) {

        String pattern = "[0-9]{1,3}+[.]+[0-9]{1}|[0-9]{1,3}";//{ 0.0 ~ 999.9  | 0 ~ 999  }
        String text = editText.getText().toString();
        if (!text.matches(pattern)) {
            if (text.isEmpty()) {
                editText.setError("Can not be null");//當editText為空值
            } else {
                editText.setError(editText.getText().toString() + " is not legal.(ex: 100 or 48.7 )");
            }
            return false;
        } else {
            return true;
        }
    }
}
