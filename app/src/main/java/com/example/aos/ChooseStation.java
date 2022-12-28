package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ChooseStation extends AppCompatActivity {
    TextView choose_station;
    Button previous, enter;
    Spinner station;
    String Pid = "111111";//乘客ID
    SqlDataBaseHelper DH = null;
    SQLiteDatabase db ;
    String busNum = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_station);
        choose_station = findViewById(R.id.choose_station);
        previous = findViewById(R.id.previous);
        enter = findViewById(R.id.enter);
        station = findViewById(R.id.station);

        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        //抓公車路
        Cursor cs = db.rawQuery("SELECT busNum FROM passenger where Pid = '" + Pid + "' and OffStop is null", null);
        while (cs.moveToNext()){
            busNum = cs.getString(0);
            System.out.println("公車號:"+ cs.getString(0));
        }
        //抓公車路的所有站
        cs = db.rawQuery("SELECT busStop FROM bus_geton where busNum = '" + busNum + "'", null);
        ArrayList<String> bus_list = new ArrayList<>();
        while (cs.moveToNext()){
            bus_list.add(cs.getString(0));
            System.out.println(cs.getString(0));
        }
        cs.close();

        //讀取資料庫 該路線所有車站
        /*
        ArrayList<String> bus_list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            bus_list.add(bus_list_string);
        }
        */
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, bus_list);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        station.setAdapter(adapter);

        enter.setOnClickListener(view -> {
            //預約下車站入資料庫
            String bus_Stop = (String)station.getSelectedItem(); //選中的選項
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = sf.format(cal.getTime());
            System.out.println("今天日期:"+ currentDate);
            db.execSQL("UPDATE passenger SET OffStop ='" + bus_Stop + "' WHERE Pid = '" + Pid + "' and busNum = '" + busNum + "' and OffStop is null");

            //Intent on_intent = new Intent(this, OnBus.class);
            //startActivity(on_intent);
            finish();
        });

        previous.setOnClickListener(view -> finish());//startActivity(new Intent(this, MainActivity.class))
    }
}