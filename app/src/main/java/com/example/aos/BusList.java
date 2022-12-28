package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BusList extends AppCompatActivity {

    public SqlDataBaseHelper DH = null;
    public SQLiteDatabase db ;
    public ListView Listview1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buslist);
        DH = new SqlDataBaseHelper(this);
        db = DH.getWritableDatabase();
        queryBusDB();//查詢上車資料
    }

    //查詢資料
    private void queryBusDB() {
        Listview1 = (ListView) findViewById(R.id.LV);
        Cursor c = db.rawQuery("SELECT busNum, busStop, Count FROM bus_geton ORDER BY busNum", null);
        //Cursor c= db.query("bus_geton", new String[]{"busNum", "busStop", "Count"}, null, null, null, null, new String(busNum), null);
        List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        //放入ArrayList
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("busNum", c.getString(0) + "->" + c.getString(1));
            item.put("Count", "上車預約人數:" + c.getString(2));
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