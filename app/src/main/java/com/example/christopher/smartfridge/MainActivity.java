package com.example.christopher.smartfridge;

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
        setupViewPage();
    }

    public void setupViewPage() {
        MyPageAdapter adapter = new MyPageAdapter(getSupportFragmentManager());
        myPager = findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        myPager.setOffscreenPageLimit(1);
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
}