package com.example.christopher.smartfridge.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.christopher.smartfridge.DialogBuilder;
import com.example.christopher.smartfridge.OrmDataHelper;
import com.example.christopher.smartfridge.R;
import com.example.christopher.smartfridge.ScanItem;
import com.example.christopher.smartfridge.ScanItemAdapter;

import java.util.ArrayList;

public class ScanFragment extends Fragment {

    public static ArrayList<ScanItem> scanItems = new ArrayList<>();
    public static ScanItemAdapter scanItemAdapter;

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
    @SuppressWarnings("ConstantConditions")
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_scan_item, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fabScan);
        SearchView searchView = view.findViewById(R.id.filterScan);
        OrmDataHelper ormDataHelper = new OrmDataHelper(getActivity());
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager vp =  getActivity().findViewById(R.id.pager);
                vp.setCurrentItem(3);
            }
        });
        ListView scanList = view.findViewById(R.id.scanList);
        scanItemAdapter = new ScanItemAdapter(getActivity(), R.layout.scan_item_list, scanItems);
        scanList.setAdapter(scanItemAdapter);
        scanList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogBuilder dialogBuilder = new DialogBuilder(view.getContext());
                dialogBuilder.editScanItem(scanItems.get(position));
            }
        });
        searchView.setQueryHint("Suchen...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                scanItemAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                scanItemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        scanItemAdapter.clear();
        scanItemAdapter.addAll(ormDataHelper.getAllScanItem());
        return view;
    }
}
