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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button scan;
    public SqlDataBaseHelper DH = null;
    public SQLiteDatabase db ;
    public ListView Listview1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bus_test);//暫時
        /*
        setContentView(R.layout.activity_main);
        title = findViewById(R.id.title);
        scan = findViewById(R.id.scan);

        scan.setOnClickListener(view -> {
            Intent scan_intent = new Intent(MainActivity.this, choosebus.class);
            startActivity(scan_intent);
        });
        //setContentView(R.layout.scanner);
        */
        DH = new SqlDataBaseHelper(this);
        //addData("yyy-222", "100","1-9",2);//新增資料
        //BusUpdateDB("uuu-1111");// 清空上下車的人數&改變車牌位置
        queryDB();//查詢資料
    }

    //新增資料
    private void addData(String licenseStr, String NumStr, String StopStr, int CountInt) {
        db = DH.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("License",licenseStr);
        values.put("busNum",NumStr);
        values.put("busStop",StopStr);
        values.put("Count",CountInt);
        db.insert("Bus",null,values);
    }
    //查詢資料
    private void queryDB() {
        db = DH.getWritableDatabase();
        Listview1 = (ListView) findViewById(R.id.LV);
        Cursor c= db.query("BusServer", new String[]{"_id", "License", "busNum", "busStop", "Count"}, null, null, null, null, null, null);
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();

        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("_id", c.getString(0) + " " + c.getString(1));
            item.put("busNum", c.getString(3) + "," + c.getString(4));
            items.add(item);
            c.moveToNext();
        }
        c.close();
        //秀出資料
        SimpleAdapter SA = new SimpleAdapter(
                this,
                items,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{"_id", "busNum"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        Listview1.setAdapter(SA);
    }

    //公車清空預約人數
    private  void BusUpdateDB(String License){
        int num = 0;
        db = DH.getWritableDatabase();
        ContentValues Values = new ContentValues();
        Values.put("Count",num);
        Values.put("License","");
        //db.update("BusServer",Values,"License = " + License,null);
        db.execSQL("UPDATE BusServer SET Count = 0, License = ' ' WHERE License = 'uuu-1111'");

    }
}