package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class WaitingBus extends AppCompatActivity {
    TextView title, wait;
    Button cancel;
    Button appeal, record, collection;
    Button arrive;
    String Pid = "111111";//乘客ID
    SqlDataBaseHelper DH = null;
    SQLiteDatabase db ;
    String OnStop = "";//預約的上車站
    String nextStop = "";
    String busNum = "";
    String License = "";//獲得車牌號
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

        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        //db.execSQL("DELETE FROM passenger WHERE Pid = '111111' and getoffTime is null");

        //先找到預約的上車站
        Cursor cs = db.rawQuery("SELECT OnStop,busNum FROM passenger where Pid = '" + Pid + "' and getonTime is null", null);
        while (cs.moveToNext()){
            OnStop = cs.getString(0);
            busNum = cs.getString(1);
            System.out.println("預約的上車站:"+ OnStop);
        }
        cs.close();

        //檢查資料庫變動 直到公車到站(待檢查)
        String Sub_Num = OnStop.substring(0, 1);
        System.out.println("站數:"+ Sub_Num);
        int stopInt = Integer.valueOf(Sub_Num) + 1;
        if(stopInt > 9){
            stopInt = 1;
        }
        String stopStr = Integer.toString(stopInt);
        //demo用---
        Cursor cr = db.rawQuery("SELECT busStop FROM bus_geton where busStop like '" + stopStr + ".%' and busNum = '" + busNum + "'", null);
        nextStop = "";//預約的上車站的下一站
        while (cr.moveToNext()){
            nextStop = cr.getString(0);
            System.out.println("下一站:"+ nextStop);
        }
        cr.close();
        //那一站的上車人數歸零
        db.execSQL("UPDATE bus_geton SET Count = 0 WHERE busStop = '" + OnStop + "' and busNum = '" + busNum + "'");
        //讓公車到站了
        db.execSQL("UPDATE Bus SET busStop ='" + nextStop + "' WHERE busNum = '" + busNum + "'");
        //----demo用
        /*
        while(1 == 1){

            try {
                // 休眠 30 秒
                TimeUnit.SECONDS.sleep(30);
                Cursor c = db.rawQuery("SELECT License FROM Bus where busStop = '" + stopStr + "%' and busNum = '" + busNum + "'", null);
                License = "";//獲得車牌號
                while (c.moveToNext()){
                    License = c.getString(0);
                    System.out.println("車牌:"+ cs.getString(0));
                }
                c.close();
                if(!License.equals("")){
                    //找到車牌了
                    break;
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

         */

        arrive.setOnClickListener(view -> {
            System.out.println("logggggggggggggggg");
            Cursor c = db.rawQuery("SELECT License FROM Bus where busStop = '" + nextStop + "' and busNum = '" + busNum + "'", null);
            License = "";//獲得車牌號
            while (c.moveToNext()){
                License = c.getString(0);
                System.out.println("車牌:"+ c.getString(0));
            }
            c.close();
            //passenger新增車牌跟上車時間
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf= new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            String ct = sf.format(cal.getTime());
            String Date = ct.substring(0, 10);
            String Time = ct.substring(12, 19);
            db.execSQL("UPDATE passenger SET License ='" + License + "', getonTime = '" + Time + "',Date = '" + Date + "' WHERE Pid = '" + Pid + "' and busNum = '" + busNum + "' and OnStop = '" + OnStop + "' and OffStop is null");
            System.out.println("tttttttttttttttt");

            Intent on_intent = new Intent(this, OnBus.class);
            startActivity(on_intent);
            finish();
        });

        //取消->取消預約上車
        cancel.setOnClickListener(view -> {
            //刪除預約資料
            db.execSQL("DELETE FROM passenger WHERE Pid = '" + Pid + "' and OnStop = '" + OnStop + "' and getonTime is null");
            //上車人數-1
            db.execSQL("UPDATE bus_geton SET Count =Count - 1 WHERE busStop = '" + OnStop + "'");
            Cursor c = db.rawQuery("SELECT Pid,busNum,OnStop FROM passenger WHERE Pid = '" + Pid + "' and getonTime is null", null);
            //確定有沒有刪
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