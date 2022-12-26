package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
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
        //addBusData();//新增資料
        //UpdateBusDB("uuu-1111","1-2");// 清空上下車的人數&改變車牌位置(車牌,站牌)
        queryBusDB();//查詢上車資料
        UpdateonDB("uuu-1111");//抓上下車人數(車牌)
         */
    }

    //WU
    //bus新增資料
    private void addBusData ( ) {
        db = DH.getWritableDatabase();
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
        db = DH.getWritableDatabase();
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
        c.close();
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
    //關門時->公車清空預約人數,更新公車位置
    private void UpdateBusDB(String License, String busStop){//車牌號碼, 站牌
        db = DH.getWritableDatabase();
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
    }
    //通知->抓資料
    private void UpdateonDB(String License){//車牌號碼
        Cursor cs = db.rawQuery("SELECT busStop,Count FROM bus_getoff WHERE License = '" + License + "'", null);
        int id = 0;
        String Stop = "";
        while (cs.moveToNext()){
            Stop = cs.getString(0);
            id = Integer.valueOf(cs.getString(1));
        }
        cs.close();
        if(id < 1){
            cs = db.rawQuery("SELECT Count FROM bus_getoff WHERE busStop = '" + Stop + "'", null);
            while (cs.moveToNext()){
                id = Integer.valueOf(cs.getString(0));
            }
        }
        cs.close();
        if(id > 0){
            //要發出停車通知
        }
    }

}