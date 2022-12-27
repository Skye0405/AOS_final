package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ChooseBus extends AppCompatActivity {
    TextView station;
    Button cancel, enter;
    Spinner scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosebus);
        station = findViewById(R.id.mail);
        cancel = findViewById(R.id.previous);
        enter = findViewById(R.id.enter);
        scan = findViewById(R.id.wait);

        //讀取資料庫 該站點所有公車車號
        /*
        ArrayList<String> bus_list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bus_list.add(bus_list_string);
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, bus_list);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        scan.setAdapter(adapter);
        */

        enter.setOnClickListener(view -> {
            Intent wait_intent = new Intent(this, WaitingBus.class);
            startActivity(wait_intent);
            finish();
        });

        cancel.setOnClickListener(view -> finish());//startActivity(new Intent(this, MainActivity.class))
    }
}