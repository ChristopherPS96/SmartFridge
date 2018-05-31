package com.example.christopher.smartfridge;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.christopher.smartfridge.Fragments.MainFragment;
import com.example.christopher.smartfridge.Fragments.OptionFragment;
import com.example.christopher.smartfridge.Fragments.ScanFragment;
import com.example.christopher.smartfridge.Fragments.ScannerFragment;

public class MyPageAdapter extends FragmentPagerAdapter {

@SuppressWarnings("WeakerAccess")
    public MyPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return MainFragment.newInstance("MainFragment, Instance 1");
            case 1:
                return ScanFragment.newInstance("ScanFragment, Instance 1");
            case 2:
                return OptionFragment.newInstance("OptionFragment, Instance 1");
            case 3:
                return ScannerFragment.newInstance("ScannerFragment, Instance 1");
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Bestand";
            case 1:
                return "Scans";
            case 2:
                return "Optionen";
            case 3:
                return "Scanner";
            default:
                return null;
        }
    }




}
