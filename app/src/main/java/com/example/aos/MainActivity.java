package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button scan;
    Button appeal, record, collection;
    Button driver, movement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        scan = findViewById(R.id.scan);
        appeal = findViewById(R.id.appeal);
        record = findViewById(R.id.record);
        collection = findViewById(R.id.collection);
        driver = findViewById(R.id.driver);
        movement = findViewById(R.id.movement);

        scan.setOnClickListener(view -> {
            //開啟相機 掃描QR code
            Intent scanner_intent = new Intent(MainActivity.this, QrcodeScanner.class);
            startActivity(scanner_intent);
        });

        driver.setOnClickListener(view -> {
            Intent driver_intent = new Intent(MainActivity.this, Driver.class);
            //司機模式
            startActivity(driver_intent);
        });

        movement.setOnClickListener(view -> {
            Intent movement_intent = new Intent(MainActivity.this, BusList.class);
            startActivity(movement_intent);
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(MainActivity.this, Appeal.class);
            startActivity(appeal_intent);
        });

        record.setOnClickListener(view -> {
            Intent record_intent = new Intent(MainActivity.this, Record.class);
            startActivity(record_intent);
        });
        //setContentView(R.layout.scanner);
    }
}