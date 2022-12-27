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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button scan;
    Button appeal;

    //wu
    public SqlDataBaseHelper DH = null;
    public SQLiteDatabase db ;
    public ListView Listview1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        scan = findViewById(R.id.scan);
        appeal = findViewById(R.id.appeal);

        scan.setOnClickListener(view -> {
            Intent scan_intent = new Intent(MainActivity.this, ChooseBus.class);
            //開啟相機 掃描QR code
            startActivity(scan_intent);
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(MainActivity.this, Appeal.class);
            startActivity(appeal_intent);
        });
        //setContentView(R.layout.scanner);

        //wu
        /*
        setContentView(R.layout.activity_buslist);
        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        //addBusData();//新增資料ok
        //UpdateBusDB("uuu-1111","1-2");// 清空上下車的人數&改變車牌位置ok
        //UpdateonDB("uuu-1111");//抓上下車人數
        Record("111111","100","1-4",null);//上車預約ok
        //cancelon("111111","1-4");//取消上車預約ok
        //geton("111111","uuu-1111");//確定上車
        //Record("111111","100",null,"1-9");//下車預約
        queryBusDB();//查詢上車資料
         */
    }

    //WU
    //取消上車
    private void cancelon(String Pid,String OnStop){
        //刪除預約資料
        db.execSQL("DELETE FROM passenger WHERE Pid = '" + Pid + "' and getonTime is null");
        //上車人數-1
        db.execSQL("UPDATE bus_geton SET Count =Count - 1 WHERE busStop = '" + OnStop + "'");
    }
    //確定上車
    private  void geton(String Pid,String License){
        //passengere更新上車時間、車牌
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        db.execSQL("UPDATE passenger SET getonTime = '" + dateFormat.format(date) + "', License = '" + License + "' WHERE Pid = '" + Pid + "' and getonTime is null");

    }
    //乘車紀錄(預約上/下車時)、公車紀錄更新
    private void Record(String Pid,String busNum,String OnStop,String OffStop){
        if(OffStop == null){
            //上車紀錄(創新的一筆資料)
            ContentValues values = new ContentValues();
            values.put("Pid",Pid);
            values.put("busNum",busNum);
            values.put("OnStop",OnStop);
            //沒有加License因這裡不知道他上哪台，沒有加getonTime因為這裡是預約時間
            db.insert("passenger",null,values);
            //上車人數+1
            db.execSQL("UPDATE bus_geton SET Count =Count + 1 WHERE busStop = '" + OnStop + "'");

        }else{
            //下車紀錄(找最新一筆)
            db.execSQL("UPDATE passenger SET OffStop ='" + OffStop + "' WHERE Pid = '" + Pid + "' and busNum = '" + busNum + "' and OffStop is null");
        }

    }

    //bus新增資料
    private void addBusData ( ) {
        //新增上車資料
        ContentValues values = new ContentValues();
        values.put("busNum","100");
        values.put("busStop","1-9");
        values.put("Count",4);
        db.insert("bus_geton",null,values);

        //新增下車資料
        /*
        values = new ContentValues();
        values.put("License","uuu-1111");
        values.put("busStop","1-2");
        values.put("Count",1);
        db.insert("bus_getoff",null,values);
*/

    }
    //查詢資料
    private void queryBusDB() {
        Listview1 = (ListView) findViewById(R.id.LV);
        Cursor c= db.query("bus_geton", new String[]{"_id","busNum", "busStop", "Count"}, null, null, null, null, null, null);
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        //放入ArrayList
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("_id", c.getString(0) + "," + c.getString(1));
            item.put("busStop", c.getString(2) + "," + c.getString(3));
            items.add(item);
            c.moveToNext();
        }
        //加入下車資訊
        /*
        c= db.query("bus_getoff", new String[]{"_id","License", "busStop", "Count"}, null, null, null, null, null, null);
        //放入ArrayList
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("_id", c.getString(0) + "," + c.getString(1));
            item.put("busStop", c.getString(2) + "," + c.getString(3));
            items.add(item);
            c.moveToNext();
        }

         */
        c.close();

        //秀出資料
        SimpleAdapter SA = new SimpleAdapter(
                this,
                items,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{"_id", "busStop"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        Listview1.setAdapter(SA);
    }
    //關門時->公車清空預約人數,更新公車位置，更新乘客下車時間
    private void UpdateBusDB(String License, String busStop){//車牌號碼, 站牌
        String[] Stop= busStop.split("-");
        String Tail = Stop[1];
        int stopInt = Integer.valueOf(Stop[1]) + 1;
        String nextStop = busStop.replace(Tail, Integer.toString(stopInt));
        if(stopInt > 9){
            nextStop = busStop.replace(Tail, "1");
        }

        //bus_geton人數歸零
        db.execSQL("UPDATE bus_geton SET Count = 0 WHERE busStop = '" + busStop + "'");
        //bus_getoff更新位置
        db.execSQL("UPDATE bus_getoff SET busStop = '" + nextStop + "' WHERE License = '" + License + "'");
        //passengere更新下車時間
        Date date = new Date();
        SimpleDateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
        db.execSQL("UPDATE passenger SET getoffTime = '" + dateFormat.format(date) + "' WHERE License = '" + License + "' and OffStop = '" + busStop + "' and getoffTime is null");
    }
    //通知->抓資料
    private void UpdateonDB(String License){//先找預約下車的人數
        Cursor cs = db.rawQuery("SELECT busStop,Count FROM bus_getoff WHERE License = '" + License + "'", null);
        int id = 0;
        String Stop = "";
        while (cs.moveToNext()){
            Stop = cs.getString(0);
            id = Integer.valueOf(cs.getString(1));
        }
        cs.close();
        if(id < 1){
            //找預約上車的人
            //有沒有需要篩busNum?
            cs = db.rawQuery("SELECT Count FROM bus_geton WHERE busStop = '" + Stop + "'", null);
            while (cs.moveToNext()){
                id = Integer.valueOf(cs.getString(0));
            }
        }else{
            //清空下車人數
            db.execSQL("UPDATE bus_getoff SET Count = 0 WHERE License = '" + License + "'");
        }
        cs.close();
        //有人要上車或要下車
        if(id > 0){
            //要發出停車通知
        }
    }

}