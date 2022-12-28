package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Record extends AppCompatActivity {
    TextView record;
    TextView previous;
    Button update;
    TableLayout table;
    SqlDataBaseHelper DH = null;
    SQLiteDatabase db ;

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

        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        ArrayList<String> bus_numberA = new ArrayList<>();
        ArrayList<String> onA = new ArrayList<>();
        ArrayList<String> offA = new ArrayList<>();
        ArrayList<String> dateA = new ArrayList<>();
        ArrayList<String> on_timeA = new ArrayList<>();
        ArrayList<String> off_timeA = new ArrayList<>();
        ArrayList<String> licenseA = new ArrayList<>();

        //放入搭車紀錄
        Cursor cs = db.rawQuery("SELECT busNum, OnStop, OffStop, Date, getonTime, getoffTime, License FROM passenger WHERE Pid = '111111'", null);
        while (cs.moveToNext()){
            bus_numberA.add(cs.getString(0));
            onA.add(cs.getString(1));
            offA.add(cs.getString(2));
            dateA.add(cs.getString(3));
            on_timeA.add(cs.getString(4));
            off_timeA.add(cs.getString(5));
            licenseA.add(cs.getString(6));
        }
        cs.close();

        TableRow trow = new TableRow(this);
        TextView bus_number = new TextView(this);
        TextView on = new TextView(this);
        TextView off = new TextView(this);
        TextView date = new TextView(this);
        TextView on_time = new TextView(this);
        TextView off_time = new TextView(this);
        TextView license = new TextView(this);

        for(int j = 0; j < bus_numberA.size(); j++) {
            trow = new TableRow(this);
            trow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
            bus_number = new TextView(this);
            on = new TextView(this);
            off = new TextView(this);
            date = new TextView(this);
            on_time = new TextView(this);
            off_time = new TextView(this);
            license = new TextView(this);

            bus_number.setText(bus_numberA.get(j));
            setParams(bus_number);
            on.setText(onA.get(j));
            setParams(on);
            off.setText(offA.get(j));
            setParams(off);
            date.setText(dateA.get(j));
            setParams(date);
            on_time.setText(on_timeA.get(j));
            setParams(on_time);
            off_time.setText(off_timeA.get(j));
            setParams(off_time);
            license.setText(licenseA.get(j));
            setParams(license);

            trow.addView(bus_number);
            trow.addView(on);
            trow.addView(off);
            trow.addView(date);
            trow.addView(on_time);
            trow.addView(off_time);
            trow.addView(license);
            table.addView(trow, new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));

        }


        update.setOnClickListener(view -> {
            /*
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
           */
        });

    }

    private void setParams(TextView tv) {
        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        tv.setGravity(Gravity.CENTER);
        tv.setPadding(5, 0,5,0);
        tv.setBackgroundResource(R.drawable.border);
    }
}