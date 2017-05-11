package com.example.ntut.ex06_01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainResult extends AppCompatActivity {
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        findview();
        /*接收Bundle的包裹*/
        Bundle bundle = getIntent().getExtras();
        Object my_bmi = bundle.getSerializable("MY_BMI");//強迫轉型為Bmi_mode類別

        if (my_bmi != null) {
            tvResult.setText(my_bmi.toString());
        }

    }

    private void findview() {
        tvResult = (TextView) findViewById(R.id.tv_result_01);
    }

    public void back_onclick(View view) {
        finish();
    }
}
