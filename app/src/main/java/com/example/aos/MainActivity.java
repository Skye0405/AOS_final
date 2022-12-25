package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button scan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        scan = findViewById(R.id.scan);

        scan.setOnClickListener(view -> {
            Intent scan_intent = new Intent(MainActivity.this, choosebus.class);
            startActivity(scan_intent);
        });
        //setContentView(R.layout.scanner);
    }
}