package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private ViewPager myPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(shouldAskPermissions()) {
            askPermissions();
        }
        setupViewPage();
    }

    public void setupViewPage() {
        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager());
        myPager = findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.setOffscreenPageLimit(3);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(myPager);
    }

    @Override
    public void onBackPressed() {
        if(myPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            myPager.setCurrentItem(myPager.getCurrentItem() - 1);
        }
    }



    protected boolean shouldAskPermissions() {      //Quelle: https://stackoverflow.com/questions/8854359/exception-open-failed-eacces-permission-denied-on-android
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {       //Quelle: https://stackoverflow.com/questions/8854359/exception-open-failed-eacces-permission-denied-on-android
        String[] permissions = {
                "android.permission.CAMERA",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
}