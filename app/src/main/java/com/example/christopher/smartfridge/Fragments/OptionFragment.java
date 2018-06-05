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
import android.widget.Toast;

import com.example.christopher.smartfridge.BestandItem;
import com.example.christopher.smartfridge.OrmDataHelper;
import com.example.christopher.smartfridge.R;

import java.util.Calendar;

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
        Button copyBestand = view.findViewById(R.id.bestandCopy);
        final OrmDataHelper ormDataHelper = new OrmDataHelper(getActivity());
        copyBestand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
        return view;
    }
}
