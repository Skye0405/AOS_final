package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Record extends AppCompatActivity {
    TextView record;
    TextView previous;
    Button update;
    TableLayout table;

    @SuppressLint({"MissingInflatedId", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        record = findViewById(R.id.record);
        previous = findViewById(R.id.previous);
        update = findViewById(R.id.update);
        table = findViewById(R.id.table);

        previous.setOnClickListener(view -> finish());//startActivity(new Intent(this, MainActivity.class))

        update.setOnClickListener(view -> {
            TableRow trow = new TableRow(this);
            trow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

            TextView bus_number = new TextView(this);
            bus_number.setText("bus_number");
            setParams(bus_number);
            TextView on = new TextView(this);
            on.setText("on");
            setParams(on);
            TextView off = new TextView(this);
            off.setText("off");
            setParams(off);
            TextView date = new TextView(this);
            date.setText("date");
            setParams(date);
            TextView on_time = new TextView(this);
            on_time.setText("on_time");
            setParams(on_time);
            TextView off_time = new TextView(this);
            off_time.setText("off_time");
            setParams(off_time);
            TextView license = new TextView(this);
            license.setText("license");
            setParams(license);

            trow.addView(bus_number);
            trow.addView(on);
            trow.addView(off);
            trow.addView(date);
            trow.addView(on_time);
            trow.addView(off_time);
            trow.addView(license);
            table.addView(trow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        });
    }

    private void setParams(TextView tv) {
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 0,5,0);
        tv.setBackgroundResource(R.drawable.border);
    }
}