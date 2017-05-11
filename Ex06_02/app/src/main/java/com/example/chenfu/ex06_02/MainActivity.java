package com.example.chenfu.ex06_02;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    String TAG="FG1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        set_fragment(getString(R.string.house),getColor(R.color.colorhouse));
    }

    public void onclick_news(View view) {
        set_fragment(getString(R.string.news),getColor(R.color.colornews));
    }

    public void onclick_pets(View view) {
        set_fragment(getString(R.string.pets),getColor(R.color.colorpets));
    }

    public void onclick_house(View view) {
        set_fragment(getString(R.string.house),getColor(R.color.colorhouse));
    }

    public void onclick_map(View view) {
        set_fragment(getString(R.string.map),getColor(R.color.colormap));
    }

    public void set_fragment(String title,int color){

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        my_Fragment my_fragement = new my_Fragment();//new 一個自己建立的fragment

        Bundle bundle = new Bundle();

        bundle.putString("title",title);
        bundle.putInt("color",color);
        my_fragement.setArguments(bundle);//打包title跟color給my_fragment

        transaction.add(R.id.main_fragment,my_fragement,TAG).commit();

        setTitle(title);//更改title
    }
}
