package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class choosebus extends AppCompatActivity {
    TextView station;
    Button cancel, enter;
    Spinner scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosebus);
        station = findViewById(R.id.station);
        cancel = findViewById(R.id.cancel);
        enter = findViewById(R.id.enter);
        scan = findViewById(R.id.scan);

        cancel.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
    }
}