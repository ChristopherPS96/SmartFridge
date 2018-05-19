package com.example.christopher.smartfridge.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.christopher.smartfridge.Activity.MainActivity;
import com.example.christopher.smartfridge.CoustomPagerEnum;

public class MyPageAdapter extends PagerAdapter {

    private Context context;

    public MyPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    @NonNull
    public Object instantiateItem(@NonNull ViewGroup collection, int position) {
        CoustomPagerEnum coustomPagerEnum = CoustomPagerEnum.values()[position];
        LayoutInflater inflater = LayoutInflater.from(context);
        View layout = inflater.inflate(coustomPagerEnum.getLayoutID(), collection, false);
        MainActivity.setupContent(layout, coustomPagerEnum.getTitle());
        collection.addView(layout);
        return layout;
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        container.getChildAt(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup collection, int position,@NonNull Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return CoustomPagerEnum.values().length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        CoustomPagerEnum coustomPagerEnum = CoustomPagerEnum.values()[position];
        return coustomPagerEnum.getTitle();
    }
}
