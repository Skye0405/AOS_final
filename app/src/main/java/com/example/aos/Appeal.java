package com.example.aos;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Appeal extends AppCompatActivity {
    EditText location, time, bus_number, driver, user, contact, subject, content;
    Button cancel, send;

    public SqlDataBaseHelper DH = null;
    public SQLiteDatabase db ;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appeal);
        cancel = findViewById(R.id.previous);
        location = findViewById(R.id.location);
        time = findViewById(R.id.time);
        bus_number = findViewById(R.id.bus_number);
        driver = findViewById(R.id.driver);
        user = findViewById(R.id.user_name);
        contact = findViewById(R.id.contact);
        subject = findViewById(R.id.subject);
        content = findViewById(R.id.content);
        send = findViewById(R.id.send);

        cancel.setOnClickListener(view -> finish());//startActivity(new Intent(this, MainActivity.class))



        send.setOnClickListener(view -> {
            String s_location = location.getText().toString();
            String s_time = time.getText().toString();
            String s_bus_number = bus_number.getText().toString();
            String s_driver = driver.getText().toString();
            String s_user = user.getText().toString();
            String s_contact = contact.getText().toString();
            String s_subject = subject.getText().toString();
            String s_content = content.getText().toString();

            if (s_location.isEmpty())
                System.out.println("Empty");
            else
                System.out.println("Non empty");

            //可能要加必填訊息未填的錯誤訊息(跳對話窗等等)


            //判斷必填資料 送資料庫(已完成)
            DH = new SqlDataBaseHelper(this);
            db = DH.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("Location",s_location);
            values.put("Time",s_time);
            values.put("License",s_bus_number);
            if (!s_driver.isEmpty()){
                values.put("Driver",s_driver);
            }
            values.put("Subject",s_subject);
            values.put("Content",s_content);
            values.put("Passenger",s_user);
            if (!s_user.isEmpty()){
                values.put("Passenger",s_user);
            }
            if (!s_contact.isEmpty()){
                values.put("Contact",s_contact);
            }
            db.insert("complain",null,values);

            finish();
        });
    }
}