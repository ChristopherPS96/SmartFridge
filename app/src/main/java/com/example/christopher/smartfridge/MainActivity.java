package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private OrmDataHelper ormDataHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(shouldAskPermissions()) {
            askPermissions();
        }
        mScannerView = new ZXingScannerView(this);
        ormDataHelper = new OrmDataHelper(this);
        setupViewPage();
    }

    public void setupViewPage() {
        MyPageAdapter adapter = new MyPageAdapter(this);
        ViewPager myPager = findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(myPager);
    }

    //Zugriff auf maincontent/settings Activity
    public static void setupContent(View view, String title) {
        if(title.equals("Hauptseite")) {
            FloatingActionButton fab = view.findViewById(R.id.fab);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Test", Toast.LENGTH_LONG).show();
                }
            });
        }
        if(title.equals("Scanseite")) {
            FloatingActionButton fab = view.findViewById(R.id.fabScan);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "Alternativer Test", Toast.LENGTH_LONG).show();
                }
            });
        }
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        boolean exists = false;
        for(ScanItem scanItem : ormDataHelper.getAllScanItem()) {
            if(scanItem.getBarcode().equals(rawResult.getText())) {
                exists = true;
            }
        }
        if(exists) {
            Toast.makeText(this,"Gegenstand ist bereits in ihrer Datenbank!", Toast.LENGTH_LONG).show();
        } else {
            DialogBuilder dialogBuilder = new DialogBuilder(this);
            dialogBuilder.createNewScanItem(rawResult.getText());
        }
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