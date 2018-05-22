package com.example.christopher.smartfridge.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.christopher.smartfridge.OrmDataHelper;
import com.example.christopher.smartfridge.R;
import com.example.christopher.smartfridge.ScanItem;
import com.example.christopher.smartfridge.ScanItemAdapter;

import java.util.ArrayList;

public class ScanFragment extends Fragment {

    public static ArrayList<ScanItem> scanItems = new ArrayList<>();
    private OrmDataHelper ormDataHelper;

    public static ScanFragment newInstance(String text) {
        ScanFragment scanFragment = new ScanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg", text);
        scanFragment.setArguments(bundle);
        return scanFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_scan_item, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fabScan);
        ormDataHelper = new OrmDataHelper(getActivity());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager vp =  getActivity().findViewById(R.id.pager);
                vp.setCurrentItem(3);
            }
        });
        ListView scanList = view.findViewById(R.id.scanList);
        ScanItemAdapter scanItemAdapter = new ScanItemAdapter(getActivity(), R.layout.scan_item_list, scanItems);
        scanList.setAdapter(scanItemAdapter);
        scanItems.clear();
        scanItems.addAll(ormDataHelper.getAllScanItem());
        return view;
    }
}
