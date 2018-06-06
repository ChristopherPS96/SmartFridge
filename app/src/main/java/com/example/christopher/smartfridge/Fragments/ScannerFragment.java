package com.example.christopher.smartfridge.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.christopher.smartfridge.DialogBuilder;
import com.example.christopher.smartfridge.OrmDataHelper;
import com.example.christopher.smartfridge.ScanItem;
import com.example.christopher.smartfridge.SettingsItem;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import java.util.ArrayList;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    private OrmDataHelper ormDataHelper;



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mScannerView = new ZXingScannerView(getActivity());
        ormDataHelper = new OrmDataHelper(getActivity());
        return mScannerView;
    }

    public static ScannerFragment newInstance(String text) {
        ScannerFragment scannerFragment = new ScannerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msg", text);
        scannerFragment.setArguments(bundle);
        return scannerFragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        OrmDataHelper helper = new OrmDataHelper(getContext());
        ArrayList<BarcodeFormat> formats = new ArrayList<BarcodeFormat>();
        formats.add(BarcodeFormat.EAN_8);
        formats.add(BarcodeFormat.EAN_13);

        mScannerView.setResultHandler(this);
        //enable/disable autofocus & flash
        if(helper.getSettingItem() != null) {
            SettingsItem settings = helper.getSettingItem().get(0);
            mScannerView.setAutoFocus(settings.isAutofocus());
            mScannerView.setFlash(settings.isLightning());
        }
        //allow suitable barcodes only
        mScannerView.setFormats(formats);
        mScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result rawResult) {
        boolean exists = false;
        if(ormDataHelper.getAllScanItem() != null) {
            for(ScanItem scanItem : ormDataHelper.getAllScanItem()) {
                if(scanItem.getBarcode().equals(rawResult.getText())) {
                    exists = true;
                }
            }
        }
        if(exists) {
            Toast.makeText(this.getActivity(),"Gegenstand ist bereits in ihrer Datenbank!", Toast.LENGTH_LONG).show();
        } else {
            DialogBuilder dialogBuilder = new DialogBuilder(this.getActivity());
            dialogBuilder.createNewScanItem(rawResult.getText());
        }
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mScannerView.resumeCameraPreview(ScannerFragment.this);
            }
        }, 2000);
    }
}
