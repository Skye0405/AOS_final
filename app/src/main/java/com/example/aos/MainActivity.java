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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {
    TextView title;
    Button scan;
    Button appeal, record, collection;
    Button driver;

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
        record = findViewById(R.id.record);
        collection = findViewById(R.id.collection);
        driver = findViewById(R.id.driver);

        scan.setOnClickListener(view -> {
            Intent scan_intent = new Intent(MainActivity.this, ChooseBus.class);
            //開啟相機 掃描QR code
            startActivity(scan_intent);
        });

        driver.setOnClickListener(view -> {
            Intent driver_intent = new Intent(MainActivity.this, Driver.class);
            //司機模式
            startActivity(driver_intent);
        });

        appeal.setOnClickListener(view -> {
            Intent appeal_intent = new Intent(MainActivity.this, Appeal.class);
            startActivity(appeal_intent);
        });
        //setContentView(R.layout.scanner);

    }

    //WU

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

}