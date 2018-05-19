package com.example.christopher.smartfridge;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private ZXingScannerView mScannerView;
    private ViewPager myPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar =  findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(shouldAskPermissions()) {
            askPermissions();
        }
        mScannerView = new ZXingScannerView(this);
        setupViewPage();
    }

    public void setupViewPage() {
        MyPageAdapter adapter = new MyPageAdapter(this);
        myPager = findViewById(R.id.pager);
        myPager.setAdapter(adapter);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(myPager);
    }

    //Zugriff auf maincontent/settings Activity
    public static void setupContent(View view, String title) {
        if(title.equals("Hauptseite")) {
            TextView textView = view.findViewById(R.id.textView);
            textView.setText("Hurra");
        }
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

        Intent intentScan = new Intent(this, DialogBuilder.class);
        //Barcode mitgeben
        intentScan.putExtra("barcode", rawResult.getText() + " " + rawResult.getBarcodeFormat().toString());
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
