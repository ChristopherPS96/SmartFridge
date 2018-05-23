package com.example.christopher.smartfridge.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.example.christopher.smartfridge.R;

public class OptionFragment extends Fragment {

    public static OptionFragment newInstance(String text) {
        OptionFragment optionFragment = new OptionFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg", text);
        optionFragment.setArguments(bundle);
        return optionFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings, container, false);
        Switch autofocus = view.findViewById(R.id.autofocus);
        Switch lightning = view.findViewById(R.id.lightning);
        Switch notifications = view.findViewById(R.id.notifications);
        EditText textsize = view.findViewById(R.id.textsize);
        Button saveSettings = view.findViewById(R.)
        return view;
    }


}
