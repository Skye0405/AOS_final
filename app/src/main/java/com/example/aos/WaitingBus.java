package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class WaitingBus extends AppCompatActivity {
    TextView title, wait;
    Button cancel;
    Button appeal, record, collection;
    Button arrive;
    public String Pid = "111111";
    public SqlDataBaseHelper DH = null;
    public SQLiteDatabase db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting_bus);
        title = findViewById(R.id.title);
        wait = findViewById(R.id.wait);
        cancel = findViewById(R.id.cancel);
        appeal = findViewById(R.id.appeal);
        record = findViewById(R.id.record);
        collection = findViewById(R.id.collection);
        arrive = findViewById(R.id.arrive);

        arrive.setOnClickListener(view -> {
            //檢查資料庫變動 直到公車到站
            Intent on_intent = new Intent(this, OnBus.class);
            startActivity(on_intent);
            finish();
        });
        //取消->取消預約上車
        cancel.setOnClickListener(view -> {
            //先找到預約的上車站
            Cursor cs = db.rawQuery("SELECT OnStop FROM passenger where Pid = '" + Pid + "' and getonTime is null", null);
            String OnStop = "";
            while (cs.moveToNext()){
                OnStop = cs.getString(0);
                System.out.println("預約的上車站:"+ cs.getString(0));
            }
            cs.close();
            //刪除預約資料
            db.execSQL("DELETE FROM passenger WHERE Pid = '" + Pid + "' and getonTime is null");
            //上車人數-1
            db.execSQL("UPDATE bus_geton SET Count =Count - 1 WHERE busStop = '" + OnStop + "'");
            Cursor c = db.rawQuery("SELECT Pid,busNum,OnStop FROM passenger WHERE Pid = '" + Pid + "' and getonTime is null", null);
            while (c.moveToNext()){
                System.out.println("Pid:"+ c.getString(0));
                System.out.println("busNum:"+ c.getString(1));
                System.out.println("OnStop:"+ c.getString(2));
            }
            System.out.println("result:None");
            c.close();


            finish();
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(this, Appeal.class);
            startActivity(appeal_intent);
        });
    }
}