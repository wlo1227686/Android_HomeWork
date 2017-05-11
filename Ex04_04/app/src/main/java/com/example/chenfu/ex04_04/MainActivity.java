package com.example.chenfu.ex04_04;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SeekBar SB01,SB02,SB03;
    private TextView tvR,tvG,tvB;
    private LinearLayout main_page;
    String BG_R="00",BG_G="00",BG_B="00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FindView();//建立與XML元件關聯
        EventListener();



    }

    private void BackGroundColor() {//set background color
        StringBuilder SB_Data = new StringBuilder(""); //create StringBuilder
        SB_Data.append("#").append(BG_R).append(BG_G).append(BG_B);//append RGB Color
        main_page.setBackgroundColor(Color.parseColor(SB_Data.toString()));//set background color
        SB_Data.setLength(0);//clear StringBuilder
    }



    public void FindView(){
        SB01 = (SeekBar)findViewById(R.id.seekBarid01);
        SB02 = (SeekBar)findViewById(R.id.seekBarid02);
        SB03 = (SeekBar)findViewById(R.id.seekBarid03);
        main_page = (LinearLayout)findViewById(R.id.main_page);
        tvR = (TextView)findViewById(R.id.textViewid01);
        tvG = (TextView)findViewById(R.id.textViewid02);
        tvB = (TextView)findViewById(R.id.textViewid03);
    }
    public void EventListener(){
        SeekBar.OnSeekBarChangeListener listener = new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(seekBar == SB01){
                    BG_R=Integer.toHexString(progress);
                    if(BG_R.length()<=1){
                        BG_R="0"+BG_R.toString();
                    }
                    tvR.setText(BG_R.toString());
                }
                if(seekBar == SB02){
                    BG_G=Integer.toHexString(progress);
                    if(BG_G.length()<=1){
                        BG_G="0"+BG_G.toString();
                    }
                    tvG.setText(BG_G.toString());
                }
                if(seekBar== SB03){
                    BG_B=Integer.toHexString(progress);
                    if(BG_B.length()<=1){
                        BG_B="0"+BG_B.toString();
                    }
                    tvB.setText(BG_B.toString());
                }

                BackGroundColor();//call BackGroundColor
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        };
        SB01.setOnSeekBarChangeListener(listener);
        SB02.setOnSeekBarChangeListener(listener);
        SB03.setOnSeekBarChangeListener(listener);
    }

}
