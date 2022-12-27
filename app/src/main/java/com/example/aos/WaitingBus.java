package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class WaitingBus extends AppCompatActivity {
    TextView title, wait;
    Button cancel;
    Button appeal, record, collection;
    Button arrive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_bus);
        title = findViewById(R.id.title);
        wait = findViewById(R.id.wait);
        cancel = findViewById(R.id.cancel);
        appeal = findViewById(R.id.appeal);
        record = findViewById(R.id.record);
        collection = findViewById(R.id.collection);
        arrive = findViewById(R.id.arrive);

        arrive.setOnClickListener(view -> {
            //檢查資料庫變動 直到公車到站
            Intent on_intent = new Intent(this, OnBus.class);
            startActivity(on_intent);
            finish();
        });

        cancel.setOnClickListener(view -> finish());

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(this, Appeal.class);
            startActivity(appeal_intent);
        });
    }
}