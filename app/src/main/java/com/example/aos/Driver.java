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
    String busStop = "";//下一站

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
        //抓即將抵達的站
        Cursor c = db.rawQuery("SELECT busStop FROM Bus where License = 'uuu-1111'", null);
        while (c.moveToNext()){
            busStop = c.getString(0);
            System.out.println("即將抵達:" + busStop);
        }
        c.close();
        station.setText("即將抵達\n" + busStop);

        //關門時->公車清空預約人數,更新公車位置
        leave.setOnClickListener(view -> {

            String Sub_Num = busStop.substring(0, 1);
            int stopInt = Integer.valueOf(Sub_Num) + 1;
            if(stopInt > 9){
                stopInt = 1;
            }
            String stopStr = Integer.toString(stopInt);
            //抓再下一站
            Cursor cr = db.rawQuery("SELECT busStop FROM bus_geton where busStop like '" + stopStr + ".%' and busNum = '100'", null);
            String nextStop = "";
            while (cr.moveToNext()){
                nextStop = cr.getString(0);
                System.out.println("下一站:"+ nextStop);
            }
            System.out.println("busStop:"+ busStop);
            System.out.println("nextStop:"+ nextStop);

            //Bus上車預約人數歸零
            db.execSQL("UPDATE bus_geton SET Count = 0 WHERE busStop = '" + busStop + "' and busNum = '100'");
            //Bus更新位置
            db.execSQL("UPDATE Bus SET busStop = '" + nextStop + "' WHERE License = 'uuu-1111'");
            //確認站牌更新成功
            Cursor cs = db.rawQuery("SELECT busStop FROM Bus WHERE License = 'uuu-1111'", null);
            while (cs.moveToNext()){
                System.out.println("下站:"+ cs.getString(0));
            }
            cs.close();
            busStop = nextStop;
            //顯示下一個站名
            station.setText("即將抵達\n" + nextStop);
            //停車需求
            need.setText("停車需求");
        });

        //下站通知->抓上下車人
        next.setOnClickListener(view -> {
            //抓資料 顯示下一站需不需要停車(先找預約下車)
            Cursor cs = db.rawQuery("SELECT count(*) FROM passenger WHERE License = 'uuu-1111' and OffStop = '" + busStop + "' and getoffTime is null", null);
            int Count = 0;
            while (cs.moveToNext()){
                Count = Integer.valueOf(cs.getString(0));
                System.out.println("預約下車人數:"+ cs.getString(0));
            }
            cs.close();
            System.out.println("無人預約下車");
            //無人預約下車，找預約上車
            if (Count < 1){
                cs = db.rawQuery("SELECT Count FROM bus_geton WHERE busNum = '100' and busStop = '" + busStop + "'", null);
                while (cs.moveToNext()){
                    Count = Integer.valueOf(cs.getString(0));
                    System.out.println("預約上車人數:"+ cs.getString(0));
                }
                cs.close();
                System.out.println("無人預約上車");
            }
            System.out.println("預約人數:" + Count);
            //歸零預約上車人數，預防同路線很接近的車也抓到要停
            db.execSQL("UPDATE bus_geton SET Count = 0 WHERE busStop = '" + busStop + "' and busNum = '100'");
            //判斷要不要停車
            if(Count < 1) {
                need.setText("請前往下一站");
            }else {
                need.setText("請靠站停車");
            }
            //確認
            Cursor co = db.rawQuery("SELECT Count FROM bus_geton WHERE busStop = '" + busStop + "' and busNum = '100'", null);
            while (co.moveToNext()){
                System.out.println("更新後預約上車人數:"+ Count);
            }
            co.close();
        });
        previous.setOnClickListener(view -> finish());


    }
}