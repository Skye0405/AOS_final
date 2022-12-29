package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button scan;
    Button appeal, record, collection;
    Button driver, movement;
    SqlDataBaseHelper DH = null;
    SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        scan = findViewById(R.id.scan);
        appeal = findViewById(R.id.appeal);
        record = findViewById(R.id.record);
        collection = findViewById(R.id.collection);
        driver = findViewById(R.id.driver);
        movement = findViewById(R.id.movement);

        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        Cursor cP = db.rawQuery("SELECT Point FROM point WHERE Pid = '111111'", null);
        while (cP.moveToNext()){
            collection.setText( cP.getString(0) + "點");
        }
        cP.close();
        //collection.setText("即將抵達");//點數


        scan.setOnClickListener(view -> {
            //開啟相機 掃描QR code
            Intent scanner_intent = new Intent(MainActivity.this, QrcodeScanner.class);
            //Intent scanner_intent = new Intent(MainActivity.this, ChooseBus.class);//測試
            startActivity(scanner_intent);
        });

        driver.setOnClickListener(view -> {
            Intent driver_intent = new Intent(MainActivity.this, Driver.class);
            //司機模式
            startActivity(driver_intent);
        });

        movement.setOnClickListener(view -> {
            Intent movement_intent = new Intent(MainActivity.this, BusList.class);
            startActivity(movement_intent);
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(MainActivity.this, Appeal.class);
            startActivity(appeal_intent);
        });

        record.setOnClickListener(view -> {
            Intent record_intent = new Intent(MainActivity.this, Record.class);
            startActivity(record_intent);
        });
        //setContentView(R.layout.scanner);
    }

}