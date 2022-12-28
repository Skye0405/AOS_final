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

public class Driver extends AppCompatActivity {
    TextView station, need;
    Button leave, next;
    Button previous;
    int i=0;
    SqlDataBaseHelper DH = null;
    SQLiteDatabase db ;
    String busStop = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        station = findViewById(R.id.station);
        need = findViewById(R.id.need);//停車需求
        leave = findViewById(R.id.leave);
        next = findViewById(R.id.next);//下站通知
        previous = findViewById(R.id.previous);

        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        Cursor c = db.rawQuery("SELECT busStop FROM Bus where License = 'uuu-1111'", null);
        busStop = "";//獲得車牌號
        while (c.moveToNext()){
            busStop = c.getString(0);
            System.out.println("下一站:"+ busStop);
        }
        c.close();
        station.setText(busStop);

        //關門時->公車清空預約人數,更新公車位置，更新乘客下車時間
        leave.setOnClickListener(view -> {
            String Sub_Num= busStop.split(".")[0];
            int stopInt = Integer.valueOf(Sub_Num) + 1;
            if(stopInt > 9){
                stopInt = 1;
            }
            String stopStr = Integer.toString(stopInt);
            Cursor cr = db.rawQuery("SELECT busStop FROM bus_geton where busStop like '" + stopStr + ".%' and busNum = '100'", null);
            String nextStop = "";
            while (cr.moveToNext()){
                nextStop = cr.getString(0);
                System.out.println("下一站:"+ nextStop);
            }
            System.out.println("busStop:"+ busStop);
            System.out.println("nextStop:"+ nextStop);
            //bus_geton人數歸零
            db.execSQL("UPDATE bus_geton SET Count = 0 WHERE busStop = '" + busStop + "' and busNum = '100'");
            //Bus更新位置
            db.execSQL("UPDATE Bus SET busStop = '" + nextStop + "' WHERE License = 'uuu-1111'");
            //passengere更新下車時間
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sf= new SimpleDateFormat("hh:mm:ss");
            String currenttime = sf.format(cal.getTime());
            db.execSQL("UPDATE passenger SET getoffTime = '" + currenttime + "' WHERE License = 'uuu-1111' and OnStop = '" + busStop + "' and getoffTime is null");// and getoffTime is null

            //確認乘客紀錄更新成功
            Cursor cs = db.rawQuery("SELECT Pid, getoffTime FROM passenger WHERE License = 'uuu-1111'", null);
            while (cs.moveToNext()){
                System.out.println("Pid:"+ cs.getString(0));
                System.out.println("getoffTime:"+ cs.getString(1));
            }
            //確認站牌更新成功
            cs = db.rawQuery("SELECT busStop FROM Bus WHERE License = 'uuu-1111'", null);
            while (cs.moveToNext()){
                System.out.println("公車位置:"+ cs.getString(0));
            }
            cs.close();

            //顯示下一個站名
            station.setText(nextStop);
            //停車需求
            need.setText("停車需求");
        });

        //下站通知->抓上下車人
        next.setOnClickListener(view -> {
            //抓資料 顯示下一站需不需要停車(先找預約下車)
            Cursor cs = db.rawQuery("SELECT Count(1) FROM passenger WHERE License = 'uuu-1111' and OffStop = '" + busStop + "' and getoffTime is null", null);
            int Count = 0;
            while (cs.moveToNext()){
                Count = Integer.valueOf(cs.getString(0));
                System.out.println("預約下車人數"+ cs.getString(0));
            }
            cs.close();
            //無人預約下車，找預約上車
            if (Count < 1){
                cs = db.rawQuery("SELECT Count FROM bus_geton WHERE busNum = '100' and busStop = '" + busStop + "'", null);
                while (cs.moveToNext()){
                    Count = Integer.valueOf(cs.getString(0));
                    System.out.println("預約上車人數"+ cs.getString(0));
                }
                cs.close();
            }
            if(Count < 1) {
                need.setText("請靠站停車");
            }else {
                need.setText("請前往下一站");
            }
        });
        previous.setOnClickListener(view -> finish());


    }
}