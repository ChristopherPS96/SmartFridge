package com.example.christopher.smartfridge;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.christopher.bestands_app.R;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

public class DialogBuilder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_item_list);
        final ListView listViewScanItem = findViewById(R.id.listViewScanItems);
        //wenn ein Eintrag ausgewählt wurde
        listViewScanItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int position, long id) {
                int index = listViewScanItem.getPositionForView(v);
                gewaehlterScanItemTitel = listViewScanItem.getItemAtPosition(index).toString();
            }
        });
        DialogScanItem(null);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////

    //Quelle: http://www.androidbegin.com/tutorial/android-ormlite-with-sqlite-database-tutorial/
    final ScanItem scanItem = new ScanItem();
    ListView listViewScanItem = findViewById(R.id.listViewScanItems);

    private OrmDbHelper ormDbHelper = null;

    private OrmDbHelper getOrmDbHelper(){
        if(ormDbHelper == null){
            ormDbHelper = OpenHelperManager.getHelper(this, OrmDbHelper.class);
        }
        return ormDbHelper;
    }


    @Override
    public void onDestroy(){
        super.onDestroy();

        if(ormDbHelper != null){
            OpenHelperManager.releaseHelper();
            ormDbHelper = null;
        }
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////


    String gewaehlterScanItemTitel = "";
    String AenderungTitel;
    boolean neuerEintrag = false;


    //Alert Dialog zum Bearbeiten, löschen und erstellen
    public void DialogScanItem(View view) {
        //Alert Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("ScanItem");       //Titel setzen

        final EditText Eintrag = new EditText(this);   //Edittext zum Dialog hinzufügen

        LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        Eintrag.setLayoutParams(layoutparams);
        builder.setView(Eintrag);
        //wenn bearbeiten
        if (gewaehlterScanItemTitel.length() > 0) {   //wenn Item aus Liste gewählt wurde -> Daten in EditText zeigen
            Eintrag.setText(gewaehlterScanItemTitel);
            neuerEintrag = false;    //kein neuer Eintrag
        } else {
            neuerEintrag = true; //neuer Eintrag
        }

        //OKAY Button
        builder.setPositiveButton("Speichern", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AenderungTitel = Eintrag.getText().toString();

                //wenn Name des ScanItems nicht leer und es ein neuer Eintrag ist
                if (AenderungTitel.length() > 0 && neuerEintrag) {

////////////////////hier zur datenbank hinzufügen  - später extra methode draus machen////////////////

                    //Quelle: http://www.androidbegin.com/tutorial/android-ormlite-with-sqlite-database-tutorial/

                    Intent intent = getIntent();
                    Bundle bundle = intent.getExtras();

                    if (bundle != null) {
                        String barcode = (String) bundle.get("barcode");
                        scanItem.setName(AenderungTitel);
                        scanItem.setBarcode(barcode);
                    }

                    try{
                        final Dao<ScanItem, Integer> scanItemDao = getOrmDbHelper().createScanItemDAO();
                        scanItemDao.create(scanItem);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
//////////////////////////////////////////////////////////////////////////////////////////////////////

                    setResult(RESULT_OK);

                } else if (AenderungTitel.length() <= 0) {      //wenn kein Name angegeben
                    Toast.makeText(DialogBuilder.this, "Bitte gib vorher was ein.", Toast.LENGTH_SHORT).show();
                } else if (!neuerEintrag) {      //wenn Update eines bereits vorhandenen ScanItems
                    UpdateScanItem();
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
        builder.setNeutralButton("Löschen", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //////hier in DB löschen//////
            }
        });
        builder.show();     //zeigen
    }

    public void UpdateScanItem() {
        //////hier DB updaten//////
    }

    //////////////////////hier Zeugs aus DB holen und in Listview schmeißen//////////////////

}
