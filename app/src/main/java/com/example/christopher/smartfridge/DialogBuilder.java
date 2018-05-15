package com.example.christopher.smartfridge;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.christopher.bestands_app.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.Date;
import java.sql.SQLException;

public class DialogBuilder extends AppCompatActivity {

    final public static int SCANITEM = 0;
    final public static int SCANITEMEXIST = 1;
    final public static int BESTANDITEM = 2;
    final public static int BESTANDITEMEXIST = 3;
    private  EditText editText = null;
    private DatePicker datePicker = null;
    private OrmDataHelper ormDataHelper;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public DialogBuilder(Context context) {
        this.context = context;
        ormDataHelper = new OrmDataHelper(context);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }

    //Alert Dialog zum Bearbeiten, löschen und erstellen (ScanItem/BestandItem kann NULL, wenn nicht vorhanden)
    public void DialogConstructor(final int id, final ScanItem scanItem, final BestandItem bestandItem) {
        //Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        switch (id) {
            case SCANITEM:
                builder.setTitle("ScanItem anlegen");
                editText = new EditText(context);
                editText.setLayoutParams(layoutparams);
                builder.setView(editText);
                break;
            case SCANITEMEXIST:
                builder.setTitle("ScanItem bearbeiten");
                editText = new EditText(context);
                editText.setLayoutParams(layoutparams);
                builder.setView(editText);
                editText.setText(scanItem.getName());
                break;
            case BESTANDITEM:
                builder.setTitle("BestandItem anlegen");
                datePicker = new DatePicker(context);
                datePicker.setLayoutParams(layoutparams);
                builder.setView(datePicker);
                break;
            case BESTANDITEMEXIST:
                builder.setTitle("BestandItem bearbeiten");
                datePicker = new DatePicker(context);
                datePicker.setLayoutParams(layoutparams);
                builder.setView(datePicker);
                datePicker.init(bestandItem.getAblaufDatum().getYear(), bestandItem.getAblaufDatum().getMonth(), bestandItem.getAblaufDatum().getDay(), null);
                break;
        }


        //OKAY Button
        builder.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (id) {
                    case SCANITEM:
                        if(editText.getText() != null && editText.getText().toString().length() > 2) {
                            ormDataHelper.saveScanItem(new ScanItem(scanItem.getBarcode(), editText.getText().toString()));
                        } else {
                            Toast.makeText(context, "Eingabe zu kurz!", Toast.LENGTH_LONG).show();
                            DialogConstructor(SCANITEM, scanItem, bestandItem);
                        }

                        break;
                    case SCANITEMEXIST:
                        if(editText.getText() != null && editText.getText().toString().length() > 2) {
                            ormDataHelper.saveScanItem(new ScanItem(scanItem.getBarcode(), editText.getText().toString()));
                            ormDataHelper.deleteScanItem(scanItem);
                        } else {
                            Toast.makeText(context, "Eingabe zu kurz!", Toast.LENGTH_LONG).show();
                            DialogConstructor(SCANITEMEXIST, scanItem, bestandItem);
                        }
                        break;
                    case BESTANDITEM:
                        Date dateTemp = null;
                        dateTemp.setYear(datePicker.getYear());
                        dateTemp.setMonth(datePicker.getMonth());
                        dateTemp.setDate(datePicker.getDayOfMonth());
                        ormDataHelper.saveBestandItem(new BestandItem(scanItem, dateTemp));
                        break;
                    case BESTANDITEMEXIST:
                        Date date = null;
                        date.setYear(datePicker.getYear());
                        date.setMonth(datePicker.getMonth());
                        date.setDate(datePicker.getDayOfMonth());
                        ormDataHelper.saveBestandItem(new BestandItem(scanItem, date));
                        ormDataHelper.deleteBestandItem(bestandItem);
                        break;
                }
            }
        });
        //ABBRECHEN Button
        builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                setResult(RESULT_CANCELED);
            }
        });

        //gewählten Eintrag löschen
        if(id == SCANITEMEXIST || id == BESTANDITEMEXIST) {
            builder.setNeutralButton("Löschen", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(id == SCANITEMEXIST) {
                        ormDataHelper.deleteScanItem(scanItem);
                    }
                    if(id == BESTANDITEMEXIST) {
                        ormDataHelper.deleteBestandItem(bestandItem);
                    }
                }
            });
        }
        builder.show();     //zeigen
    }
}
