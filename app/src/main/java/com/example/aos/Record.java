package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Record extends AppCompatActivity {
    TextView record;

    int record_list_length = 10;
    TextView[] passenger = new TextView[record_list_length];
    TextView[] bus_number = new TextView[record_list_length];
    TextView[] on = new TextView[record_list_length];
    TextView[] off = new TextView[record_list_length];
    TextView[] date = new TextView[record_list_length];
    TextView[] on_time = new TextView[record_list_length];
    TextView[] off_time = new TextView[record_list_length];
    TextView[] license = new TextView[record_list_length];
    Button previous;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        record = findViewById(R.id.record);
        previous = findViewById(R.id.previous);
        for(int i = 0; i < record_list_length; i++) {
            passenger[i] = new TextView(this);
            bus_number[i] = new TextView(this);
            on[i] = new TextView(this);
            off[i] = new TextView(this);
            date[i] = new TextView(this);
            on_time[i] = new TextView(this);
            off_time[i] = new TextView(this);
            license[i] = new TextView(this);
        }

        previous.setOnClickListener(view -> finish());//startActivity(new Intent(this, MainActivity.class))

    }
}