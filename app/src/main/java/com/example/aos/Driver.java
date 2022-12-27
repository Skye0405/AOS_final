package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class Driver extends AppCompatActivity {
    TextView station, need;
    Button leave, next;
    Button previous;
    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        station = findViewById(R.id.station);
        need = findViewById(R.id.need);
        leave = findViewById(R.id.leave);
        next = findViewById(R.id.next);
        previous = findViewById(R.id.previous);

        leave.setOnClickListener(view -> {
            //抓資料 顯示下一個站名
            i++;
            String s = "第"+ i +"站";
            station.setText(s);
        });
        next.setOnClickListener(view -> {
            //抓資料 顯示下一站需不需要停車
            if(i%2==0){
                need.setText("要");
            }
            else{
                need.setText("不要");
            }
        });
        previous.setOnClickListener(view -> finish());
    }
}