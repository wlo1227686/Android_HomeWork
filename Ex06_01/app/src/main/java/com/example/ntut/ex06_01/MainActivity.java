package com.example.ntut.ex06_01;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private EditText edt01_Hei, edt02_Wei;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findview();
    }

    private void findview() {
        edt01_Hei = (EditText) findViewById(R.id.edt01_height);
        edt02_Wei = (EditText) findViewById(R.id.edt02_weight);

        /*設定editText的監聽事件*/
        edt02_Wei.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                /* When Action_Done OnClick (鍵盤右下角的按鍵)*/
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    do_submit_To_BMI();
                    handled = true;
                }
                return handled;
            }
        });
    }

    /*啟動計算BMI*/
    public void do_submit_To_BMI() {
/*先判斷是否為垃圾資料*/
        boolean isValid = isValid(edt01_Hei) & isValid(edt02_Wei);

        if (!isValid) {
            return;
        }
/*取得EditText上的值*/
        Double height = Double.parseDouble(edt01_Hei.getText().toString());
        Double Weight = Double.parseDouble(edt02_Wei.getText().toString());

        Bmi_model my_bmi = new Bmi_model(height, Weight);
/*使意圖*/
        Intent intent = new Intent(MainActivity.this, MainResult.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("MY_BMI", my_bmi); //Object的物件
/*裝載貨物*/
        intent.putExtras(bundle);
/*開始執行*/
        startActivity(intent);
        edt01_Hei.setText("");
        edt02_Wei.setText("");
    }

    /*Submit(簡單事件處理法)*/
    public void submit_onclick(View view) {

        do_submit_To_BMI();
    }

    /*判斷參數是否合法*/
    private boolean isValid(EditText editText) {

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
