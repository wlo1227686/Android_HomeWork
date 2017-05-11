package com.example.chenfu.Ex06_03;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.M)
public class MainActivity extends AppCompatActivity {
    private ViewPager vpMember;
    private List<DataList> datalist;
    String as[]={"news","pets","house","map"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<DataList> datalist = getData();
        vpMember = (ViewPager) findViewById(R.id.main_viewpager);
        ViewPagerAdapter viewpagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), datalist);
        setTitle(as[0]);
        vpMember.setAdapter(viewpagerAdapter);
        vpMember.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setTitle(as[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    private List<DataList> getData() {
        datalist = new ArrayList<>();
        datalist.add(new DataList(getString(R.string.news), getColor(R.color.colornews)));
        datalist.add(new DataList(getString(R.string.pets), getColor(R.color.colorpets)));
        datalist.add(new DataList(getString(R.string.house), getColor(R.color.colorhouse)));
        datalist.add(new DataList(getString(R.string.map), getColor(R.color.colormap)));
        return datalist;
    }

    public void onclick_news(View view) {
        vpMember.setCurrentItem(0);
    }
    public void onclick_pets(View view) {
        vpMember.setCurrentItem(1);
    }

    public void onclick_house(View view) {
        vpMember.setCurrentItem(2);
    }

    public void onclick_map(View view) {
        vpMember.setCurrentItem(3);
    }

}
