package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ChooseStation extends AppCompatActivity {
    TextView choose_station;
    Button previous, enter;
    Spinner station;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_station);
        choose_station = findViewById(R.id.choose_station);
        previous = findViewById(R.id.previous);
        enter = findViewById(R.id.enter);
        station = findViewById(R.id.station);

        //讀取資料庫 該路線所有車站
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

            finish();
        });

        previous.setOnClickListener(view -> finish());//startActivity(new Intent(this, MainActivity.class))
    }
}