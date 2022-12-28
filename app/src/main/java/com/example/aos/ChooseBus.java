package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ChooseBus extends AppCompatActivity {
    TextView station;
    Button cancel, enter;
    Spinner scan;
    public SqlDataBaseHelper DH = null;
    public SQLiteDatabase db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choosebus);
        station = findViewById(R.id.mail);
        cancel = findViewById(R.id.previous);//上一頁
        enter = findViewById(R.id.enter);//確定按鈕
        scan = findViewById(R.id.wait);//下拉式選單

        //String bus_Stop = getIntent().getStringExtra("STATION"); //要帶入站名(busStop)->qrcode
        String bus_Stop ="3.中山三路";
        station.setText(bus_Stop);
        //讀取資料庫 該站點所有公車車號
        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        db.execSQL("DELETE FROM passenger WHERE Pid = '111111' and getonTime is null");
        System.out.println("查詢站牌的公車路線");
        Cursor cs = db.rawQuery("SELECT busNum FROM bus_geton where busStop = '" + bus_Stop + "'", null);
        ArrayList<String> bus_list = new ArrayList<>();
        while (cs.moveToNext()){
            bus_list.add(cs.getString(0));
            System.out.println(cs.getString(0));
        }
        cs.close();
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, bus_list);
        adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        scan.setAdapter(adapter);
        //選擇確定後存入資料庫
        enter.setOnClickListener(view -> {
            String Bus_Num = (String)scan.getSelectedItem(); //選中的選項
            System.out.println("公車號:"+ Bus_Num);
            String Pid = "111111";//乘客ID
            ContentValues values = new ContentValues();
            values.put("Pid",Pid);
            values.put("busNum",Bus_Num);
            values.put("OnStop",bus_Stop);
            db.insert("passenger",null,values);
            //點數+1
            db.execSQL("UPDATE point SET Point =Point + 1 WHERE Pid = '111111'");
            Cursor cP = db.rawQuery("SELECT Point FROM point WHERE Pid = '111111'", null);
            while (cP.moveToNext()){
                System.out.println("Point:"+ cP.getString(0));
            }
            cP.close();
            //bus_geton上車人數+1
            db.execSQL("UPDATE bus_geton SET Count =Count + 1 WHERE busStop = '" + bus_Stop + "' and busNum = '" + Bus_Num + "'");
            //顯示有無存入
            Cursor c = db.rawQuery("SELECT Pid,busNum,OnStop FROM passenger WHERE Pid = '" + Pid + "' and getonTime is null", null);
            while (c.moveToNext()){
                System.out.println("Pid:"+ c.getString(0));
                System.out.println("busNum:"+ c.getString(1));
                System.out.println("OnStop:"+ c.getString(2));
            }
            Cursor cr = db.rawQuery("SELECT Count FROM bus_geton WHERE busStop = '" + bus_Stop + "' and busNum = '" + Bus_Num + "'", null);
            while (cr.moveToNext()){
                System.out.println("Count:"+ cr.getString(0));
            }
            c.close();
            cr.close();
            //存入結束

            Intent wait_intent = new Intent(ChooseBus.this, WaitingBus.class);
            startActivity(wait_intent);
            finish();
        });

        cancel.setOnClickListener(view -> finish());//startActivity(new Intent(this, MainActivity.class))
    }
}