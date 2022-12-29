package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class OnBus extends AppCompatActivity {
    TextView title, on_bus;
    Button appeal, record, collection;
    Button arrive, destination, info;
    String Pid = "111111";//乘客ID
    SqlDataBaseHelper DH = null;
    SQLiteDatabase db ;
    String OffStop = "";//預約的下車站
    String busNum = "";
    String License = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_bus);
        title = findViewById(R.id.title);
        on_bus = findViewById(R.id.on_bus);
        appeal = findViewById(R.id.appeal);
        record = findViewById(R.id.record);
        collection = findViewById(R.id.collection);
        destination = findViewById(R.id.destination);
        arrive = findViewById(R.id.arrive);
        info = findViewById(R.id.info);

        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        Cursor cP = db.rawQuery("SELECT Point FROM point WHERE Pid = '111111'", null);
        while (cP.moveToNext()){
            collection.setText( cP.getString(0) + "點");
        }
        cP.close();
        //點選更新
        arrive.setOnClickListener(view -> {
            //取得下車站
            Cursor cs = db.rawQuery("SELECT OffStop,busNum,License FROM passenger where Pid = '111111' and OffStop is not null and getoffTime is null", null);
            while (cs.moveToNext()){
                OffStop = cs.getString(0);
                busNum = cs.getString(1);
                License = cs.getString(2);
                System.out.println("預約的下車站:"+ cs.getString(0));
            }
            cs.close();
            if(!OffStop.equals("")) {
                String Sub_Num = OffStop.substring(0, 1);
                System.out.println("站數:" + Sub_Num);
                int stopInt = Integer.valueOf(Sub_Num) + 1;
                if (stopInt > 9) {
                    stopInt = 1;
                }
                String stopStr = Integer.toString(stopInt);
                //demo用---
                Cursor cr = db.rawQuery("SELECT busStop FROM bus_geton where busStop like '" + stopStr + ".%' and busNum = '" + busNum + "'", null);
                String nextStop = "";//預約的下車站的下一站
                while (cr.moveToNext()) {
                    nextStop = cr.getString(0);
                    System.out.println("下一站:" + nextStop);
                }
                cr.close();
                //那一站的上車人數歸零
                db.execSQL("UPDATE bus_geton SET Count = 0 WHERE busStop = '" + OffStop + "' and busNum = '" + busNum + "'");
                //讓公車到站了
                db.execSQL("UPDATE Bus SET busStop ='" + nextStop + "' WHERE busNum = '" + busNum + "' and License = '" + License + "'");
                //----demo用

                //passenger新增下車時間
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                String ct = sf.format(cal.getTime());
                String Date = ct.substring(0, 10);
                String Time = ct.substring(11, 19);
                db.execSQL("UPDATE passenger SET getoffTime = '" + Time + "' WHERE Pid = '" + Pid + "' and busNum = '" + busNum + "' and OffStop = '" + OffStop + "' and Date = '" + Date + "' and getoffTime is null");
                Intent main = new Intent(OnBus.this, MainActivity.class);
                startActivity(main);
                finish();
            }

        });

        destination.setOnClickListener(view -> {
            Intent choose_station_intent = new Intent(this, ChooseStation.class);
            startActivity(choose_station_intent);
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(this, Appeal.class);
            startActivity(appeal_intent);
        });

        info.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(this, BusList.class);
            startActivity(appeal_intent);
        });

        record.setOnClickListener(view -> {
            Intent record_intent = new Intent(this, Record.class);
            startActivity(record_intent);
        });
    }

}