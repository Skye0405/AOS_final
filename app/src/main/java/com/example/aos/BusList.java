package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusList extends AppCompatActivity {

    SqlDataBaseHelper DH = null;
    SQLiteDatabase db ;
    ListView Listview1 ;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);
        DH = new SqlDataBaseHelper(this);
        db = DH.getReadableDatabase();
        queryBusDB();//查詢上車資料

        button = findViewById(R.id.button);


        button.setOnClickListener(view -> finish());
    }

    //查詢資料
    private void queryBusDB() {
        Listview1 = (ListView) findViewById(R.id.LV);
        db = DH.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT busNum, busStop, Count FROM bus_geton ORDER BY busNum", null);
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        //放入ArrayList
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("busNum", c.getString(0) + "->" + c.getString(1));
            item.put("Count", "上車預約人數:" + c.getString(2));
            System.out.println(c.getString(2));
            items.add(item);
            c.moveToNext();
        }
        c.close();

        //秀出資料
        SimpleAdapter SA = new SimpleAdapter(
                this,
                items,
                android.R.layout.simple_expandable_list_item_2,
                new String[]{"busNum", "Count"},
                new int[]{android.R.id.text1, android.R.id.text2}
        );
        Listview1.setAdapter(SA);
    }

}