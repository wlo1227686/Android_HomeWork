package com.example.chenfu.ex08_02;

import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstUse();//***********************

        setUpActionBar();
        initDrawer();
        initBody();
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    public  void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //Boolean firstUse()
            actionBar.setDisplayHomeAsUpEnabled(firstUse());//左上角的Menu選項
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //左上角的Menu選項的行為
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void initDrawer() {
        // DrawerLayout 抽屜
        drawerLayout = (DrawerLayout) findViewById(R.id.main_Drawerlayout);//Main_xml的
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.text_Open, R.string.text_Close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        // Navigation 導航/響導/引導
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//關閉滑動抽屜****************************
        NavigationView view = (NavigationView) findViewById(R.id.main_navigation_view);//左側抽屜
        view.getMenu().getItem(1).setChecked(true);//設定Navigation按鍵的陰影****************************
        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(false);//Navigation 被點選時的陰影

                drawerLayout.closeDrawers();//關閉抽屜
                Fragment fragment;
                switch (menuItem.getItemId()) {
                    case R.id.menu_item_Add:
                        switchFragment(new BMIFragment());
                        setTitle(R.string.text_Add);
                        break;
                    case R.id.menu_item_Home:
                        switchFragment(new ResultBMIFragment());
                        setTitle(R.string.text_Home);
                        break;
                    case R.id.menu_item_Set:
                        switchFragment(new SetFragment());
                        setTitle(R.string.text_Set);
                        break;
                    default:
                        fragment = new BMIFragment();
                        switchFragment(fragment);
                        setTitle(R.string.text_Add);
                        break;
                }
                return true;
            }
        });
    }

    private void initBody() {
        switchFragment(new BMIFragment());
        setTitle(R.string.text_Add);
    }

    private boolean firstUse() {
        //判斷sql內的資料數,無資料回傳false
        int size = new MySQLiteOpenHelper(this).getAllData().size();
        if (size == 0) {
            return false;
        } else
            return true;
    }

    public void switchFragment(Fragment fragment) {
        //--(fragment_BMI,fragment_Result)----兩種
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_FrameLayout, fragment);
        fragmentTransaction.commit();
    }
}
