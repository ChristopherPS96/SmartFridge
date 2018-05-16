package com.example.christopher.smartfridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.example.christopher.bestands_app.R;

public class SettingsActivity extends AppCompatActivity {
    private Switch autofocus;
    private Switch notifications;
    private Switch lightning;
    private EditText textsize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        autofocus = (Switch) findViewById(R.id.autofocus);
        notifications = (Switch) findViewById(R.id.notifications);
        lightning = (Switch) findViewById(R.id.lightning);
        textsize = (EditText) findViewById(R.id.textsize);
    }
}
