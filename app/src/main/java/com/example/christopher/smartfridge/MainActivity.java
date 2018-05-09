package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.christopher.bestands_app.R;
import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(shouldAskPermissions()) {
            askPermissions();
        }
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startScan();
            }
        });
        mScannerView = new ZXingScannerView(this);
    }

    //NUR EINE TESTMETHODE ZUM ZEIGEN WIE FILTER FUNKTIONIEREN
    private BestandItemAdapter bestandItemAdapter;
    private ListView test; //NUR EINE TESTVARIABLE
    private EditText test2; //NUR EINE TESTVARIABLE
    public void setFilter() {
        bestandItemAdapter = new BestandItemAdapter(this,R.layout.bestand_item_list, null);
        test.setAdapter(bestandItemAdapter);
        test2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                MainActivity.this.bestandItemAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void startScan() {
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
       // Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        //Toast.makeText(this, rawResult.getText() + " " + rawResult.getBarcodeFormat().toString(), Toast.LENGTH_SHORT).show();
        //////////hier Dialog-Feld zum Bearbeiten des Scanitems aufrufen(?)//////////

        Intent intentScan = new Intent(this, DialogBuilder.class);
        startActivityForResult(intentScan, 123);
    }

    protected boolean shouldAskPermissions() {      //Quelle: https://stackoverflow.com/questions/8854359/exception-open-failed-eacces-permission-denied-on-android
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(23)
    protected void askPermissions() {       //Quelle: https://stackoverflow.com/questions/8854359/exception-open-failed-eacces-permission-denied-on-android
        String[] permissions = {
                "android.permission.CAMERA",
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }
}
