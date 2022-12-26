package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button scan;
    Button appeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        scan = findViewById(R.id.scan);
        appeal = findViewById(R.id.appeal);

        scan.setOnClickListener(view -> {
            Intent scan_intent = new Intent(MainActivity.this, ChooseBus.class);
            //開啟相機 掃描QR code
            startActivity(scan_intent);
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(MainActivity.this, Appeal.class);
            startActivity(appeal_intent);
        });
        //setContentView(R.layout.scanner);
    }
}