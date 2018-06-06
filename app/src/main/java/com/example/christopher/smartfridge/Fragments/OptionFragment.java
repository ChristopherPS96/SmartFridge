package com.example.christopher.smartfridge.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import com.example.christopher.smartfridge.BestandItem;
import com.example.christopher.smartfridge.OrmDataHelper;
import com.example.christopher.smartfridge.R;
import com.example.christopher.smartfridge.SettingsItem;

import java.util.ArrayList;
import java.util.Calendar;

public class OptionFragment extends Fragment {
    private Switch autofocus;
    private Switch notifications;
    private Switch lightning;

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
        super.onCreate(savedInstanceState);
        Button copyBestand = view.findViewById(R.id.bestandCopy);
        Button saveSettings = view.findViewById(R.id.saveSettings);
        autofocus = view.findViewById(R.id.autofocus);
        notifications = view.findViewById(R.id.notifications);
        lightning = view.findViewById(R.id.lightning);

        //setzt die alten Settings für User zur Änderung
        setOldSettings();
        copyBestand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copyBestand();
            }
        });
        saveSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSettings();
            }
        });
        return view;
    }

    public void copyBestand() {
        OrmDataHelper ormDataHelper = new OrmDataHelper(getActivity());
        if(ormDataHelper.getAllBestandItem() != null && ormDataHelper.getAllBestandItem().size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Kühlschrankinhalt: \n");
            for(BestandItem e : ormDataHelper.getAllBestandItem()) {
                stringBuilder.append(e.getAmount() + " Mal " + e.getScanItem().getName() + ", welches am "
                        + e.getAblaufDatum().get(Calendar.DAY_OF_MONTH) + "." + (e.getAblaufDatum().get(Calendar.MONTH) + 1) + "." + e.getAblaufDatum().get(Calendar.YEAR) + " abläuft! \n");
            }
            ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("Kühlschrankbestand", stringBuilder.toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getActivity(), "Inhalt wurde kopiert", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Bestand ist leer!", Toast.LENGTH_LONG).show();
        }
    }

    private void saveSettings(){
        OrmDataHelper helper = new OrmDataHelper(getActivity());
        //falls Settings vorhanden -> löschen, dann neue Settings
        if(helper.getSettingItem() != null && helper.getSettingItem().size() > 0){
            helper.deleteSettingItem(helper.getSettingItem().get(0));
        }
        SettingsItem settingsItem = new SettingsItem();
        settingsItem.setAutofocus(autofocus.isChecked());
        settingsItem.setLightning(lightning.isChecked());
        settingsItem.setNotifications(notifications.isChecked());
        Toast.makeText(getActivity(), "Einstellungen wurden gespeichert! Neustart für Effekt!", Toast.LENGTH_LONG).show();
        helper.saveSettingItem(settingsItem);
    }

    private void setOldSettings(){
        OrmDataHelper helper = new OrmDataHelper(getActivity());
        if(helper.getSettingItem()!= null && helper.getSettingItem().size() > 0){
            ArrayList<SettingsItem> oldSettings = helper.getSettingItem();
            autofocus.setChecked(oldSettings.get(0).isAutofocus());
            lightning.setChecked(oldSettings.get(0).isLightning());
            notifications.setChecked(oldSettings.get(0).isNotifications());
        }
        else{
            autofocus.setChecked(false);
            lightning.setChecked(false);
            notifications.setChecked(false);
        }
    }

}
