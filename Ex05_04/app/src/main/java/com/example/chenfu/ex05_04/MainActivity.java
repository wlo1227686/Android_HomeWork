package com.example.chenfu.ex05_04;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

/* https://github.com/QuadFlask/colorpicker */
import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

public class MainActivity extends AppCompatActivity {
    private int orignColor = 0xffffffff;
    private GeometricView geometricView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        geometricView = (GeometricView)findViewById(R.id.geometricView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.option_menu01_01://形狀(方形)
                geometricView.setRect(true);
                break;
            case R.id.option_menu01_02://形狀(圓形)
                geometricView.setRect(false);
                break;

            case R.id.option_menu_02://顏色
                changeColor_dialog();
                break;
        }
        geometricView.invalidate();
        return true;
    }

    private void changeColor_dialog(){
        ColorPickerDialogBuilder
                .with(MainActivity.this)
                .setTitle(R.string.title_option_menu02_dialog_title)
                .initialColor(orignColor)
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)//幾層
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {/*被按下的顏色*/ }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {/*ok鍵*/
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        geometricView.setColor_argb(selectedColor);// 0X FF FF FF FF
                        geometricView.invalidate();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {/*close鍵*/}
                })
                .build()
                .show();
    }
}
