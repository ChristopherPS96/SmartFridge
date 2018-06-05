package com.example.christopher.smartfridge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Switch;

import com.example.christopher.smartfridge.R;

public class SettingsActivity extends AppCompatActivity {
    private Switch autofocus;
    private Switch notifications;
    private Switch lightning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        autofocus = findViewById(R.id.autofocus);
        notifications = findViewById(R.id.notifications);
        lightning = findViewById(R.id.lightning);
    }
}
