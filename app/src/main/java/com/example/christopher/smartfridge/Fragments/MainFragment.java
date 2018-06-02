package com.example.christopher.smartfridge.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.christopher.smartfridge.BestandItem;
import com.example.christopher.smartfridge.BestandItemAdapter;
import com.example.christopher.smartfridge.DialogBuilder;
import com.example.christopher.smartfridge.OrmDataHelper;
import com.example.christopher.smartfridge.R;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private OrmDataHelper ormDataHelper;
    public static ArrayList<BestandItem> bestandList = new ArrayList<>();
    public static BestandItemAdapter bestandItemAdapter;

    public static MainFragment newInstance(String text) {
        MainFragment mainFragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg", text);
        mainFragment.setArguments(bundle);
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ormDataHelper = new OrmDataHelper(getActivity());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_maincontent, container, false);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        ListView bestandListView = view.findViewById(R.id.bestandList);
        SearchView searchView = view.findViewById(R.id.filter);
        bestandItemAdapter = new BestandItemAdapter(getActivity(), R.layout.bestand_item_list, bestandList);
        bestandListView.setAdapter(bestandItemAdapter);
        bestandListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DialogBuilder dialogBuilder = new DialogBuilder(getActivity());
                dialogBuilder.editBestandItem(bestandList.get(position));
            }
        });
        searchView.setQueryHint("Suchen...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                bestandItemAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                bestandItemAdapter.getFilter().filter(newText);
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(ormDataHelper.getAllScanItem() != null) {
                   DialogBuilder dialogBuilder = new DialogBuilder(getActivity());
                   dialogBuilder.listScanItem();
               }
            }
        });
        bestandItemAdapter.clear();
        bestandItemAdapter.addAll(ormDataHelper.getAllBestandItem());
        return view;
    }
}
