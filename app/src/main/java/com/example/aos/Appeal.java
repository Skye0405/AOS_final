package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Appeal extends AppCompatActivity {
    TextView appeal;
    Button cancel;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal);
        appeal = findViewById(R.id.mail);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(view -> startActivity(new Intent(this, MainActivity.class)));
    }
}