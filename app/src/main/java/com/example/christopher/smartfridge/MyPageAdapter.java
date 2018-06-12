/*
 ** Erstellt von Christopher Schwandt, Anna Rochow, Jennifer Tönjes und Alina Pohl der SMIB
 */
package com.example.christopher.smartfridge;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.christopher.smartfridge.Fragments.MainFragment;
import com.example.christopher.smartfridge.Fragments.OptionFragment;
import com.example.christopher.smartfridge.Fragments.ScanFragment;
import com.example.christopher.smartfridge.Fragments.ScannerFragment;

class MyPageAdapter extends FragmentPagerAdapter {

@SuppressWarnings("WeakerAccess")
    public MyPageAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    //holt vorgegebene Fragments zurück und gibt Reihenfolge in der App vor
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

    //gibt Anzahl der Fragments an
    @Override
    public int getCount() {
        return 4;
    }

    //gibt den TabLayouts den notwendigen Titel
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
