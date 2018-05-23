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

import com.example.christopher.smartfridge.OrmDataHelper;
import com.example.christopher.smartfridge.R;
import com.example.christopher.smartfridge.SettingsItem;

public class OptionFragment extends Fragment {
    Switch autofocus;
    Switch lightning;
    Switch notifications;
    EditText textsize;
    Button saveSettingsButton;
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
        autofocus = view.findViewById(R.id.autofocus);
        lightning = view.findViewById(R.id.lightning);
        notifications = view.findViewById(R.id.notifications);
        textsize = view.findViewById(R.id.textsize);
        saveSettingsButton = view.findViewById(R.id.saveSettings);
        saveSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
        return view;
    }

        public void saveSettings(){
            OrmDataHelper helper = new OrmDataHelper(getActivity());
            if(helper.getSettingItem() != null){
                helper.deleteSettingItem(helper.getSettingItem());
            }
            SettingsItem settings = new SettingsItem();
            settings.setAutofocus(autofocus.isChecked());
            settings.setLightning(lightning.isChecked());
            settings.setNotifications(notifications.isChecked());
            settings.setTextSize(Integer.parseInt(textsize.getText().toString()));

            helper.saveSettingItem(settings);
        }
}
