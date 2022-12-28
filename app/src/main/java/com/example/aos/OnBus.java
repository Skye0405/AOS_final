package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class OnBus extends AppCompatActivity {
    TextView title, on_bus;
    Button appeal, record, collection;
    Button arrive, destination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_bus);
        title = findViewById(R.id.title);
        on_bus = findViewById(R.id.on_bus);
        appeal = findViewById(R.id.appeal);
        record = findViewById(R.id.record);
        collection = findViewById(R.id.collection);
        destination = findViewById(R.id.destination);
        arrive = findViewById(R.id.arrive);

        //檢查資料庫變動 直到公車到站

        arrive.setOnClickListener(view -> {
            finish();
        });

        destination.setOnClickListener(view -> {
            Intent choose_station_intent = new Intent(this, ChooseStation.class);
            startActivity(choose_station_intent);
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(this, Appeal.class);
            startActivity(appeal_intent);
        });

        record.setOnClickListener(view -> {
            Intent record_intent = new Intent(this, Record.class);
            startActivity(record_intent);
        });
    }
}